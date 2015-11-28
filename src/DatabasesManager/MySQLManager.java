package DatabasesManager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MySQLManager {

	static String path;
	static ArrayList<String> files;
	
	public MySQLManager(String path0, String files0){
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
		this.sendMYSQLRequest("create database y;");
		//enter/create the database
		for (String s : files){
			this.initMySQLTable("Databases" + File.separator + s);
		}
	}
	
	
	public void clear() throws IOException{
		sendMYSQLRequest("drop database y");
		System.out.println("MYSQL databases cleared");
	}
	public String sendMYSQLRequest(String request) throws IOException{
		return this.sendMYSQLRequest("MySQLRequest.m",request);
	}
	
	public String sendMYSQLRequest(String file,String request) throws IOException{
		{
			String result = "";
			//formating command
			try {
				String content = "";
				if (!request.equals("create database y;")){
					content += "use y;\n";
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
            	if (remove){
	        		if(file1.delete()){
	        			System.out.println("Compilation file " + file1.getName() + " has been deleted!");
	        		}else{
	        			System.out.println("Delete operation is failed.");
	        		} }
                System.out.println("End of request"); 
                return result;

			} catch (IOException e) {e.printStackTrace();}
            catch(InterruptedException e2) {System.out.println("Fail to launch mysql command");} 
			return "Error";}
        }
	
	public static String initMySQLTable(String file) throws IOException{
		try {
			String result = "";

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
            System.out.println("End of request"); 
            return result;

		} catch (IOException e) {e.printStackTrace();}
        catch(InterruptedException e2) {System.out.println("Fail to launch mysql command");} 
		return "Error";
	}
	
	public void test() throws IOException{
		{
			try 
            { 
                Process p=Runtime.getRuntime().exec("cmd /C mysql -u root < SQLRequêteExemple.m"); 
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
