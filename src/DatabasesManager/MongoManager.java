package DatabasesManager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class MongoManager {

	static String path;
	static ArrayList<String> files;
	
	public MongoManager(String path0, String files0){
		path = path0;
		files = new ArrayList<String>();
		String[] temp = files0.split(PathHandler.SEPARATOR);
		for (int i = 0 ; i < temp.length ; i++){
			files.add(temp[i]);
		}
		//System.out.println("path " + path);
		//System.out.println("files " + files);
	}
	
	public void initConnection() throws IOException{
		//init mongod process
		try 
        {
			String proc = "cmd /C ";
			if (File.separator == "/"){
				proc += "mongod.exe & ";
			} else {
				proc+= "Start /B mongod.exe";
			}
			Process p=Runtime.getRuntime().exec("cmd /C Start /B mongod.exe"); 
			System.out.println("Launching Mongob");
        }
		catch(IOException e1) {System.out.println("Launching mongob process NOK " + e1);} 
		
		//enter/create the database
		for (String s : files){
			importMongoDB(s);
		}
	}
	
	public void clear() throws IOException{
		try 
        {
			for (String s : files){
				String[] temp = s.split("\\.");
				Process p=Runtime.getRuntime().exec("mongo --eval db." + temp[0] + ".drop()"); 
			}
			System.out.println("Mongo databases cleared");
        }
		catch(IOException e1) {System.out.println("clear for Mongo NOK " + e1);} 
			
	}
	
	public static String importMongoDB(String file) throws IOException{
		String result = "";
		try 
        { 
			//remove the extension of the file by taking the 0 index
			String[] temp = file.split("\\.");
			//drop the old tables
			Process p=Runtime.getRuntime().exec("mongo --eval db." + temp[0] + ".drop()"); 
            p.waitFor(); 
			//send the request
            p=Runtime.getRuntime().exec("mongoimport --db test --collection " + temp[0] + " --drop --file  Databases\\" + file); 
            p.waitFor(); 
            BufferedReader reader=new BufferedReader(
                new InputStreamReader(p.getInputStream())
            ); 
            String line; 
            while((line = reader.readLine()) != null) 
            { 
            	 result += line + "\n";
            } 

        }
        catch(IOException e1) {System.out.println("importMongoDB NOK " + e1);} 
        catch(InterruptedException e2) {System.out.println("importMongoDB NOK " + e2);} 
		return result;
	}
	
	public String[][] sendMongoRequest(String request) throws IOException{
			return sendMongoRequest("Mongo.m",request);
	}
	
	public String[][] sendMongoRequest(String file, String request) throws IOException{

		String result = "";
		//formating command
		try {

			String content = request + ".forEach(printjson);";
			boolean remove = false;
			File file1 = new File(file);

			// if file doesnt exists, then create it
			if (!file1.exists()) {
				file1.createNewFile();
				//remove
				remove = true;
			}

			FileWriter fw = new FileWriter(file1.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();

			System.out.println("Compilation file in Mongo command created");
			
			//sending command
			Process p=Runtime.getRuntime().exec("cmd /C mongo test "+ file); 
            p.waitFor(); 
            BufferedReader reader=new BufferedReader(
                new InputStreamReader(p.getInputStream())
            ); 
            String line; 
            while((line = reader.readLine()) != null) 
            { 
                result += line +  "\n";
            } 
            
            //remove compiling file if didn't exist before
        	if (remove){
        		if(file1.delete()){
        			System.out.println("Compilation file " + file1.getName() + " has been deleted!");
        		}else{
        			System.out.println("Delete operation is failed.");
        		} }
            System.out.println("End of request"); 
            return this.parseMongoResult(result);

		} catch (IOException e) {e.printStackTrace();}
        catch(InterruptedException e2) {System.out.println("Fail to launch mysql command");} 
		return null;
		}
	
	private String[][] parseMongoResult(String result) {
			String[] temp = result.split("\\{");
			String[] temp1 = temp[1].split("\"");
			//temp[0] est le message de MongoDB
			int nbLignes = temp.length - 1;
			int nbAttributs = temp1.length/4;
			String[][] tab = new String[nbLignes][nbAttributs];
			int cpt = 0;
			int cpt2 = 0;
			//attributs et premi�re ligne
			for (int i = 1 ; i < temp1.length ; i+=2){
				if ((i+1) % 4 == 0){
					tab[1][cpt++] = temp1[i];
				} else {
					tab[0][cpt2++] = temp1[i];
				}
			}
			//reste des lignes
			for (int i = 2 ; i < nbLignes ; i++){
				cpt = 0;
				temp1 = temp[i].split("\"");
				for (int j = 1 ; j < temp1.length ; j+=2){
					if ((j+1) % 4 == 0){
						tab[i][cpt++] = temp1[j];
					}
				}
			}
		return tab;
	}

	public static void test(){
		try 
        { 
            Process p=Runtime.getRuntime().exec("mongo --eval \"db\""); 
            p.waitFor(); 
            BufferedReader reader=new BufferedReader(
                new InputStreamReader(p.getInputStream())
            ); 
            String line; 
            while((line = reader.readLine()) != null) 
            { 
                System.out.println(line);
            } 

        }
        catch(IOException e1) {System.out.println("Test NOK " + e1);} 
        catch(InterruptedException e2) {System.out.println("Test NOK " + e2);} 

        System.out.println("Test OK"); 
    } 
}
