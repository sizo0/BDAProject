package DatabasesManager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MySQLManager {

	static String path;
	static ArrayList<String> files;
	
	public MySQLManager(String path0, String files0){
		path = path0;
		files = new ArrayList<String>();
		String[] temp = files0.split(PropertiesHandler.SEPARATOR);
		for (int i = 0 ; i < temp.length ; i++){
			files.add(temp[i]);
		}
		//System.out.println("path " + path);
		//System.out.println("files " + files);
	}
	
	public void initConnection() throws IOException{
		this.sendMYSQLRequest("create database " + PropertiesHandler.NomSQL1 + ";");
		//enter/create the database
		for (String s : files){
			this.initMySQLTable("Databases" + File.separator + s);
		}
	}
	
	
	public void clear() throws IOException{
		sendMYSQLRequest("drop database " + PropertiesHandler.NomSQL1);
		System.out.println("MYSQL database " + PropertiesHandler.NomSQL1 + " cleared");
	}
	public String[][] sendMYSQLRequest(String request) throws IOException{
		System.out.println("Executing Query :" + request);
		return this.sendMYSQLRequest("MySQLRequest.m",request);
	}
	
	public String[][] sendMYSQLRequest(String file,String request) throws IOException{
		{
			String result = "";
			//formating command
			try {
				String content = "";
				if (!request.equals("create database " + PropertiesHandler.NomSQL1 + ";")){
					content += "use " + PropertiesHandler.NomSQL1  + ";\n";
				}
				 content += request;
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

				System.out.println("Compilation file in MYSQL command created");
				//sending command
				Process p=Runtime.getRuntime().exec("cmd /C mysql -u root < "+ file); 
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
            	if (!PropertiesHandler.CompileFile){
            		if (remove){
	        		if(file1.delete()){
	        			System.out.println("Compilation file " + file1.getName() + " has been deleted!");
	        		}else{
	        			System.out.println("Delete operation is failed.");
	        		} } }
                System.out.println("End of request"); 
                return parseSQLResult(result);

			} catch (IOException e) {e.printStackTrace();}
            catch(InterruptedException e2) {System.out.println("Fail to launch mysql command");} 
			return null;}
        }
	
	private String[][] parseSQLResult(String result) {
		// TODO Auto-generated method stub
		String[] temp = result.split("\n");
		String[] temp0 = temp[0].split("\t");
		int nbLignes = temp.length;
		int nbAttributs = temp0.length;
		String[][] tab = new String[nbLignes][nbAttributs];
		String[] tempTab = new String[nbAttributs];
		//init attributs
		for (int i = 0 ; i < temp0.length ; i++){
			tab[0][i] = temp0[i];
			//System.out.println("1 " + temp0[i]);
		}
		//init donn�es
		for (int i = 1 ; i < temp.length ; i++){
			String[] temp1 = temp[i].split("\t");
			for (int j = 0 ; j < temp1.length ; j++){
				tab[i][j] = temp1[j];
				//System.out.println(i + " " + temp1[j]);
			}
		}
		return tab;
	}
	
	public static ArrayList<String> cloneList(ArrayList<String> list) {
		ArrayList<String> clone = new ArrayList<String>(list.size());
	    for(String item: list){
	    	clone.add(item);
	    }
	    return clone;
	}
	
	

	public String initMySQLTable(String file) throws IOException{
		try {
			// lire un fichier
			String chaine = "";
			InputStream ips=new FileInputStream(file); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String ligne;
			while ((ligne=br.readLine())!=null){
				chaine+=ligne+"\n";
			}
			br.close(); 
			//ajouter nombase + fichier
			this.sendMYSQLRequest(file+"m",chaine);
			
		} catch (IOException e) {e.printStackTrace();} 
		return "Error";
	}
	
	public void test() throws IOException{
		{
			try 
            { 
                Process p=Runtime.getRuntime().exec("cmd /C mysql -u root < SQLRequ�teExemple.m"); 
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

}
