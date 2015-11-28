package DatabasesManager;

import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

public class DatabasesManager {

	static HashMap<Integer,String> data;
	
	public static void main(String[] args) throws IOException {
		System.out.println("Init mySQL databases");
		System.out.println("Get configuration from databasesFiles.properties");
		getData();
		System.out.println(data);
		
		System.out.println("Init SQL relations");
		MySQLManager mysql = new MySQLManager(data.get(0), data.get(1));
	}

	public static void getData() throws IOException{
		PathHandler properties = new PathHandler();
		data = properties.getPropValues();
	}
}
