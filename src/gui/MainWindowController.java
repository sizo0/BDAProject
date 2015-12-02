package gui;

import DatabasesManager.DatabaseManager;
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

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    @FXML
    private Button executeQuery;

    @FXML
    private TableView mysqlTable;

    @FXML
    private TableView mongoTable;

    private final DatabaseManager dbM;

    public MainWindowController(DatabaseManager dbM) {
        this.dbM = dbM;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        executeQuery.setOnAction(event -> {
            try {
                // TODO: Transformer la requête du champs texte en une requête SQL + une requête MongoDB (utiliser le travail d'Hassan)

                String[][] mysqlRes = dbM.sendSQLDatabaseRequest("select * from DataRow where Prenom = \"Aspen\";");
                String[][] mongoRes = dbM.sendMongoRequest("db.EcoleMongoDB.find({IdEcole:\"5\"})");

                populateTable(mysqlTable, mysqlRes);
                populateTable(mongoTable, mongoRes);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
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

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("test");
    }
}
