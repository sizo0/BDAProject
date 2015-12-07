package gui;

import DatabasesManager.DatabaseInitializer;
import DatabasesManager.MongoDBManager;
import DatabasesManager.MySQLManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;
import javafx.util.Pair;
import org.jooq.conf.ParamType;
import org.w3c.xqparser.ParseException;
import org.w3c.xqparser.XPath;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MainWindowController implements Initializable {

    @FXML
    private ChoiceBox originalXQuery;

    @FXML
    private Button executeOriginalQuery;

    @FXML
    private TextField mysqlXQuery;

    @FXML
    private Button executeMysqlXQuery;

    @FXML
    private TextField mongoXQuery;

    @FXML
    private Button executeMongoXQuery;

    @FXML
    private TextField mysqlQuery;

    @FXML
    private TextField mongoQuery;

    @FXML
    private TableView mysqlTable;

    @FXML
    private TableView mongoTable;

    @FXML
    private TableView finalTable;

    private enum ComputeMode { ALL, MYSQL, MONGO };
    private final DatabaseInitializer dbM;
    private final MySQLManager mysql;
    private final MongoDBManager mongo;

    private ArrayList<String> queries = new ArrayList<String>();
    private Map<String, Pair<String, String>> queriesSplits = new HashMap<String, Pair<String, String>>();

    public MainWindowController(DatabaseInitializer dbM) {
        this.dbM = dbM;
        mysql = dbM.getMysql();
        mongo = dbM.getMongo();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        queries.add(
            "for $a in document(\"personne\")//formation, $b in document(\"EcoleMongoDB\") where $a/IDEcole = $b/IdEcole return $a, $b"
        );
        queriesSplits.put(queries.get(0), new Pair<String, String>(
            "for $a in document(\"personne\")//formation return $a",
            "for $b in document(\"EcoleMongoDB\")//EcoleMongoDB return $b"
        ));

        queries.add(
            "for $a in document(\"personne\")//personne where $a/prenom = \"Aspen\" return $a"
        );
        queriesSplits.put(queries.get(1), new Pair<String, String>(
            "for $a in document(\"personne\")//personne where $a/prenom = \"Aspen\" return $a",
            ""
        ));

        queries.add(
            "for $a in document(\"EcoleMongoDB\")//Nom where $a = \"INSA de Rennes\" return $a"
        );
        queriesSplits.put(queries.get(2), new Pair<String, String>(
            "",
            "for $a in document(\"EcoleMongoDB\")//Nom where $a = \"INSA de Rennes\" return $a"
        ));

        queries.add(
            "for $a in document(\"personne\")//personne where $a/nom = \"Macias\", $b in document(\"ecole\") where $a/IDEcole = $b/IdEcole return $a, $b"
        );
        queriesSplits.put(queries.get(3), new Pair<String, String>(
            "for $a in document(\"personne\")//personne where $a/nom = \"Macias\" return $a",
            "for $b in document(\"EcoleMongoDB\")//EcoleMongoDB return $b"
        ));

        originalXQuery.setItems(FXCollections.observableArrayList(queries));

        executeOriginalQuery.setOnAction(event -> { compute(ComputeMode.ALL); });
        executeMysqlXQuery.setOnAction(event -> { compute(ComputeMode.MYSQL); });
        executeMongoXQuery.setOnAction(event -> { compute(ComputeMode.MONGO); });
    }

    public void compute(ComputeMode mode) {
        try {
            // Recovers the original xQuery
            String xQuery = (String)originalXQuery.getValue();
            if(xQuery == null && mode == ComputeMode.ALL) {
                return;
            }

            // Splits the original xQuery into queries for each Database
            if(mode == ComputeMode.ALL) {
                mysqlXQuery.setText(queriesSplits.get(xQuery).getKey());
                mongoXQuery.setText(queriesSplits.get(xQuery).getValue());
            }
            else if(mode == ComputeMode.MYSQL) {
                mongoXQuery.setText("");
            }
            else if(mode == ComputeMode.MONGO) {
                mysqlXQuery.setText("");
            }
            String xQueryForMysql = mysqlXQuery.getText();
            String xQueryForMongo = mongoXQuery.getText();


            // Converts xQuery for MySQL Database to SQL Query
            String mysqlQuery = "";
            if(xQueryForMysql.length() > 0) {
                XPath mysqlParser = new XPath(xQueryForMysql);
                mysqlQuery = mysqlParser.XPath2().getSql().getSQL(ParamType.INLINED);
            }
            this.mysqlQuery.setText(mysqlQuery);

            // Converts xQuery for Mongo Database to Mongo Query
            String mongoQuery = "";
            if(xQueryForMongo.length() > 0) {
                // TODO: MongoDB ne marche pas avec le where (donc la transformation est codé en durs pour l'exemple)
                if(mode == ComputeMode.ALL && xQuery.equals(queries.get(2))) {
                    mongoQuery = "db.EcoleMongoDB.find({Nom: \"INSA de Rennes\"})";
                }
                else {
                    XPath mongoParser = new XPath(xQueryForMongo);
                    mongoQuery = mongoParser.XPath2().getMongodb().get();
                }
            }
            this.mongoQuery.setText(mongoQuery);

            // Executes the queries on each Database
            String[][] mysqlRes = null;
            String[][] mongoRes = null;
            if(mysqlQuery.length() > 0) {
                mysqlRes = mysql.sendMYSQLRequest(mysqlQuery);
            }
            if(mongoQuery.length() > 0) {
                mongoRes = mongo.sendMongoRequest(mongoQuery);
            }

            // Joins the two results into one
            int indexIdEcoleForPersonne = -1;
            if(mysqlRes != null) {
                for(int i = 0; i < mysqlRes[0].length; i++) {
                    if(mysqlRes[0][i].equals("IDEcole")) {
                        indexIdEcoleForPersonne = i;
                        break;
                    }
                }
            }
            int indexIdEcoleForEcole = -1;
            if(mongoRes != null) {
                for(int i = 0; i < mongoRes[0].length; i++) {
                    if(mongoRes[0][i].equals("IdEcole")) {
                        indexIdEcoleForEcole = i;
                        break;
                    }
                }
            }

            String[][] finalRes = null;
            if(indexIdEcoleForEcole != -1 && indexIdEcoleForPersonne != -1) {
                finalRes = new String[mysqlRes.length][];
                for(int i = 0; i < mysqlRes.length; i++) {
                    finalRes[i] = new String[mysqlRes[0].length + mongoRes[0].length];
                    for(int j = 0; j < mysqlRes[i].length; j++) {
                        finalRes[i][j] = mysqlRes[i][j];
                    }
                    if(i == 0) {
                        for(int j = 0; j < mongoRes[0].length; j++) {
                            finalRes[i][mysqlRes[i].length + j] = mongoRes[0][j];
                        }
                    }
                    else {
                        for(int j = 1; j < mongoRes.length; j++) {
                            if(mysqlRes[i][indexIdEcoleForPersonne].equals(mongoRes[j][indexIdEcoleForEcole])) {
                                for(int k = 0; k < mongoRes[j].length; k++) {
                                    finalRes[i][mysqlRes[i].length + k] = mongoRes[j][k];
                                }
                            }
                        }
                    }
                }
            }

            // Displays results
            populateTable(mysqlTable, mysqlRes);
            populateTable(mongoTable, mongoRes);
            populateTable(finalTable, finalRes);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void populateTable(TableView table, String[][] data) {
        table.getItems().clear();
        table.getColumns().clear();
        if(data == null) {
            return;
        }
        ObservableList<String[]> items = FXCollections.observableArrayList();
        items.addAll(Arrays.asList(data));
        items.remove(0); //remove titles from data
        for (int i = 0; i < data[0].length; i++) {
            TableColumn tc = new TableColumn(data[0][i]);
            final int colNo = i;
            tc.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> p) {
                    return new SimpleStringProperty((p.getValue()[colNo]));
                }
            });
            table.getColumns().add(tc);
        }
        table.setItems(items);
    }
}
