package DatabasesManager;
import java.io.IOException;
import java.util.HashMap;

/* Documentation rapide du package:
 * DatabaseManager g�re tout le package
 * Path Handler g�re le path, et le chargement des fichiers via le fichier properties databasesFiles.properties
 * databasesFiles.properties contient le path des bases de donn�es, les fichiers concern�s et leurs types
 * MySQLManager g�re les requ�tes mySQL via cmd
 * MongoManager g�re les requ�tes noSQL
 * 
 * Pour utiliser le package
 * Actualiser le contenu du fichier databasesFiles.properties
 * Creer un objet DatabaseManager
 * Regarder lignes de print�es en cas de probl�me
 * utiliser sendSQLDatabaseRequest pour envoyer une requ�te SQL.
 * L'output de la fonction est un String contenant le resultat de la requ�te.
 * Les requ�tes rendent des tableaux de tableaux de String
 * La premi�re ligne contient les types
 * Les lignes suivantes contiennent les donn�es
 * 
 * Probl�mes possibles et restrictions
 * mysql et mongo ne sont pas dans la variable syst�me path
 * mysql: root account has a password
 * � chaque saut de ligne pour Mongo, une commande est lanc�e. Ne pas couper les requ�tes avec des sauts de lignes
 * Les tests dans le main de la classe DatabaseManager testent la partie MongoDB et la partie SQL. Il n'y a pas de test sur les
 * retours des fonctions au cas o� les fichiers d'entr�e sont mauvais.
 * Auteur: Dan Seeruttun
 */

public class DatabaseManager {

	static HashMap<Integer,String> data;
	static MySQLManager mysql;
	static MongoManager mongo;
	
	public DatabaseManager() throws IOException{
		//getting data from configuration file
		System.out.println("Init mySQL databases");
		System.out.println("Get configuration from databasesFiles.properties");
		this.getData();
		
		//setting up for SQL
		System.out.println("Init SQLManager");
		mysql = new MySQLManager(data.get(0), data.get(1));
		System.out.println("Init SQL databases files");
		mysql.initConnection();

		//setting up for mongo
		System.out.println("Init MongoManager");
		mongo = new MongoManager(data.get(0), data.get(2));
		System.out.println("Init Mongo databases files");
		mongo.initConnection();
	}
	
	public String[][] sendSQLDatabaseRequest(String request) throws IOException{
		return mysql.sendMYSQLRequest(request);
	}
	
	public String[][] sendMongoRequest(String request) throws IOException{
		return mongo.sendMongoRequest(request);
	}

	public void getData() throws IOException{
		PathHandler properties = new PathHandler();
		data = properties.getPropValues();
	}
	
	public void clear() throws IOException{
		mysql.clear();
		mongo.clear();
	}
	
	public void print2DimTableInConsole(String[][] tab)
	{
	   for(int i = 0; i < tab.length; i++)
	   {
	      for(int j = 0; j < tab[i].length; j++)
	      {
	         System.out.print(tab[i][j] + " ");
	      }
	      System.out.println();
	   }
	}
	
	public static void main(String[] args) throws IOException {
		DatabaseManager dbM = new DatabaseManager();
		dbM.print2DimTableInConsole(dbM.sendSQLDatabaseRequest("select * from Personne where prenom = Juarez;"));
		dbM.print2DimTableInConsole(dbM.sendMongoRequest("db.EcoleMongoDB.find()"));
		dbM.clear();
	}
}