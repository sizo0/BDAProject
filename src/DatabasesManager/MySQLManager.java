package DatabasesManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import sun.tools.jar.CommandLine;

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
	
	public static void test() throws IOException{
		{
			try 
            { 
                Process p=Runtime.getRuntime().exec("cmd /C file.bat"); 
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
            catch(IOException e1) {} 
            catch(InterruptedException e2) {} 

            System.out.println("Done"); 
        } 
	}
	
	public static void main(String[] args) throws IOException{
		test();
	}

}
