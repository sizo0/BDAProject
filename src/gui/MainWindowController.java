/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import DatabasesManager.DatabaseManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 * @author jmarcou
 */
public class MainWindowController implements Initializable {

    @FXML
    private Button executeQuery;

    private final DatabaseManager dbM;

    public MainWindowController(DatabaseManager dbM) {
        this.dbM = dbM;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        executeQuery.setOnAction(event -> {
            try {
                dbM.print2DimTableInConsole(dbM.sendSQLDatabaseRequest("select * from Personne where Prenom = \"Aspen\";"));
                dbM.print2DimTableInConsole(dbM.sendMongoRequest("db.EcoleMongoDB.find({IdEcole:\"5\"})"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("test");
    }
}
