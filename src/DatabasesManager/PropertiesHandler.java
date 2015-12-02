package DatabasesManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;
 
/**
 * @author Crunchify.com
 * 
 */
 
public class PropertiesHandler {
	String result = "";
	InputStream inputStream;
	 final static int PATHCST = 0;
	 final static int MYSQLFILE = 1;
	 final static int MONGODBFILE = 2;
	 final static String SEPARATOR = "&&&";
	 static Boolean CompileFile;
	 static Boolean Clean;
	 static String NomSQL1;
	 static String NomMongo1;
	
	public HashMap<Integer,String> getPropValues() throws IOException {
		HashMap<Integer,String> list = new HashMap<Integer,String>();
 
		try {
			Properties prop = new Properties();
			String path = "DatabasesManager/";
			String propFileName = path + "databaseFiles.properties";
 
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
 
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
 
			//get the path
			String DBPath1 = prop.getProperty("DBPath1");
			String DBPath2 = prop.getProperty("DBPath2");
			
			//check the path, and put it in DBPath
			String DBPath = "";			
			File fpath = new File(DBPath1);
			if(fpath.isDirectory()) { 
				System.out.println(DBPath1 + " is the database file");
				DBPath = DBPath1 + File.separator;
			} else {
				fpath = new File(DBPath2);
				if(fpath.isDirectory()) { 
					System.out.println(DBPath2 + " is the database file");
					DBPath = DBPath2 + File.separator;
				} else {
					System.out.println("The database folder paths in the configuration file are not correct");
				}
					DBPath = DBPath2;
			}
			list.put(PATHCST,DBPath);
			
			//get the files and types
			String file1 = prop.getProperty("file1");
			int type1 = Integer.parseInt(prop.getProperty("type1"));
			String file2 = prop.getProperty("file2");
			int type2 = Integer.parseInt(prop.getProperty("type2"));
			String file3 = prop.getProperty("file3");
			int type3 = Integer.parseInt(prop.getProperty("type3"));/*
			String file4 = prop.getProperty("file4");
			int type4 = Integer.parseInt(prop.getProperty("type4"));*/
			
			//get the user properties
			//TODO
			NomSQL1 = prop.getProperty("NomSQL1");
			NomMongo1 = prop.getProperty("NomMongo1");
			String CleanFiles = prop.getProperty("Clean");
			Clean = Boolean.parseBoolean(CleanFiles);
			String KeepCompFile = prop.getProperty("DeleteCompFile");
			CompileFile = Boolean.parseBoolean(KeepCompFile);
			
			//Check the files
			File f = new File(DBPath + File.separator + file1);
			if(f.exists() && !f.isDirectory()) { 
				list.put(type1,file1);
				System.out.println(file1 + " successfully added");
			} else {
				System.out.println(file1 + " is not found");
			}
			
			f = new File(DBPath + File.separator + file2);
			if(f.exists() && !f.isDirectory()) { 
				if (list.containsKey(type2)){
					String s = list.get(type2) + SEPARATOR;
					list.put(type2,s + file2);
				} else {
					list.put(type2,file2);
				}
				System.out.println(file2 + " successfully added");
			} else {
				System.out.println(file2 + " is not found");
			}
			
			f = new File(DBPath + File.separator + file3);
			if(f.exists() && !f.isDirectory()) { 
				if (list.containsKey(type3)){
					String s = list.get(type3) + SEPARATOR;
					list.put(type3,s + file3);
				} else {
					list.put(type3,file3);
				}
				System.out.println(file3 + " successfully added");
			} else {
				System.out.println(file3 + " is not found");
			}
			/*
			f = new File(DBPath + File.separator + file4);
			if(f.exists() && !f.isDirectory()) { 
				if (list.containsKey(type3)){
					String s = list.get(type3) + SEPARATOR;
					list.put(type4,s + file4);
				} else {
					list.put(type4,file4);
				}
				System.out.println(file4 + " successfully added");
			} else {
				System.out.println(file4 + " is not found");
			}*/
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			inputStream.close();
		}
		return list;
	}
}