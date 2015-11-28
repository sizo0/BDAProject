package DatabasesManager;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

/* Documentation rapide du package:
 * DatabaseManager gère tout le package
 * Path Handler gère le path, et le chargement des fichiers via le fichier properties databasesFiles.properties
 * databasesFiles.properties contient le path des bases de données, les fichiers concernés et leurs types
 * MySQLManager gère les requêtes mySQL via cmd
 * MongoManager gère les requêtes noSQL
 * 
 * Pour utiliser le package
 * Actualiser le contenu du fichier databasesFiles.properties
 * Creer un objet DatabaseManager
 * Regarder lignes de printées en cas de problème
 * utiliser sendSQLDatabaseRequest pour envoyer une requête SQL.
 * L'output de la fonction est un String contenant le resultat de la requête.
 * Le format est souvent moche
 * 
 * Problèmes possibles
 * mysql et mongo ne sont pas dans la variable système path
 * mysql: root account has a password
 */

public class DatabasesManager {

	static HashMap<Integer,String> data;
	static MySQLManager mysql;
	static MongoManager mongo;
	
	public DatabasesManager() throws IOException{
		System.out.println("Init mySQL databases");
		System.out.println("Get configuration from databasesFiles.properties");
		getData();
		System.out.println(data);
		
		System.out.println("Init SQLManager");
		mysql = new MySQLManager(data.get(0), data.get(1));
		System.out.println("Init SQL databases files");
		mysql.initConnection();

		//MySQLManager mongo = new MongoManager(data.get(0), data.get(2));
		
	}
	
	public String sendSQLDatabaseRequest(String request) throws IOException{
		return mysql.sendMYSQLRequest(request);
	}

	public static void getData() throws IOException{
		PathHandler properties = new PathHandler();
		data = properties.getPropValues();
	}
	
	public static void main(String[] args) throws IOException {
		DatabasesManager dbM = new DatabasesManager();
		System.out.println(dbM.sendSQLDatabaseRequest("select * from Liaison"));
	}
}