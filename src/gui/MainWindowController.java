package gui;

import DatabasesManager.DatabaseInitializer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableColumn;
import javafx.util.Callback;
import org.jooq.Record;
import org.jooq.SelectJoinStep;
import org.jooq.conf.ParamType;
import org.w3c.xqparser.ParseException;
import org.w3c.xqparser.XPath;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Vector;

public class MainWindowController implements Initializable {

    @FXML
    private Button executeQuery;

    @FXML
    private TableView mysqlTable;

    @FXML
    private TableView mongoTable;

    @FXML
    private TableView finalTable;

    private final DatabaseInitializer dbM;

    public MainWindowController(DatabaseInitializer dbM) {
        this.dbM = dbM;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        executeQuery.setOnAction(event -> {
            try {
                // Recovers the original xQuery
                String xQuery = "for $a in document(\"personnes\")//formations where $a//nom = \"Dupont\" return $a";

                // Splits the original xQuery into queries for each Database
                String xQueryForMysql =  "for $a in document(\"personnes\")//formations where $a//nom = \"Dupont\" return $a";
                String xQueryForMongo =  "for $a in document(\"personnes\")//formations where $a//nom = \"Dupont\" return $a";

                // Converts xQuery for MySQL Database to SQL Query
                // String mysqlQuery = "select * from Personne where Prenom = \"Aspen\"";
                String mysqlQuery = "select * from Personne join Formation on Personne.IDFormation = Formation.IDFormation";
                //XPath mysqlParser = new XPath(new BufferedReader(new InputStreamReader(new java.io.StringBufferInputStream(xQueryForMysql), "UTF-8")));
                //String mysqlQuery = mysqlParser.XPath2().getSQL(ParamType.INLINED);

                // Converts xQuery for Mongo Database to Mongo Query
                //String mongoQuery = "db.EcoleMongoDB.find({IdEcole:\"5\"})";
                String mongoQuery = "db.EcoleMongoDB.find()";
                //XPath monogParser = new XPath(new BufferedReader(new InputStreamReader(new java.io.StringBufferInputStream(xQueryForMongo), "UTF-8")));
                //String mongoQuery = monogParser.XPath2().getMongo(ParamType.INLINED);

                // Executes the queries on each Database
                String[][] mysqlRes = dbM.sendSQLDatabaseRequest(mysqlQuery);
                String[][] mongoRes = dbM.sendMongoRequest(mongoQuery);

                // Joins the two results into one
                int indexIdEcoleForPersonne = 0;
                for(int i = 0; i < mysqlRes[0].length; i++) {
                    if(mysqlRes[0][i].equals("IDEcole")) {
                        indexIdEcoleForPersonne = i;
                        break;
                    }
                }
                int indexIdEcoleForEcole = 0;
                for(int i = 0; i < mongoRes[0].length; i++) {
                    if(mongoRes[0][i].equals("IdEcole")) {
                        indexIdEcoleForEcole = i;
                        break;
                    }
                }

                String[][] finalRes = new String[mysqlRes.length][];
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

                // Displays results
                populateTable(mysqlTable, mysqlRes);
                populateTable(mongoTable, mongoRes);
                populateTable(finalTable, finalRes);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            /*catch (ParseException e) {
                e.printStackTrace();
            }*/
        });
    }

    private void populateTable(TableView table, String[][] data) {
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
