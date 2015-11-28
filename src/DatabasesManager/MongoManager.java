package DatabasesManager;

import java.util.ArrayList;

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
	
	public void test(){
		
	}

}
