package main.gui;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.genetique.AlgoGen;
import main.graphe.Graphe;
import main.tabou.Tabou;

import java.io.IOException;

public class App extends Application {

    // Tabou
    @FXML
    private TextField tNumDS;
    @FXML
    private TextField tIterations;
    @FXML
    private TextField tVoisinage;
    @FXML
    private TextField tTabou;

    // Algo gen
    @FXML
    private TextField agNumDS;
    @FXML
    private TextField agIterations;
    @FXML
    private TextField agPopulation;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            primaryStage.setTitle("Vehicle Routing Problem");
            AnchorPane rootLayout = FXMLLoader.load(App.class.getResource("App.fxml"));
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void launchTabou() {
        Graphe graphe = new Graphe("data" + tNumDS.getText() + ".txt", 0);
        graphe.setCapacite(100);
        Tabou tabou = new Tabou();
        tabou.setITERATION_MAX(Integer.valueOf(tIterations.getText()));
        tabou.setTAILLE_LISTE_TABOU(Integer.valueOf(tTabou.getText()));
        tabou.setTAILLE_VOISINAGE(Integer.valueOf(tVoisinage.getText()));
        String param = "Itération max : " + tIterations.getText()
                + "                Taille liste Tabou : " + tTabou.getText()
                + "                Taille voisinage : " + tVoisinage.getText();
        launchAlgo(tabou, graphe, param);
    }

    @FXML
    public void launchAlgoGen() {
        Graphe graphe = new Graphe("data" + agNumDS.getText() + ".txt", 1);
        graphe.setCapacite(100);
        AlgoGen algoGen = new AlgoGen();
        algoGen.setITERATION_MAX(Integer.valueOf(agIterations.getText()));
        algoGen.setPOPULATION_SIZE(Integer.valueOf(agPopulation.getText()));
        String param = "Itération max : " + agIterations.getText()
                + "                Taille population de départ : " + agPopulation.getText();
        launchAlgo(algoGen, graphe, param);
    }

    private void launchAlgo(Algo algo, Graphe graphe, String param) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Res.class.getResource("Res.fxml"));
            AnchorPane rootLayout = loader.load();
            Res res = loader.getController();
            res.setGraphe(graphe);
            res.setAlgo(algo);
            Stage stage = new Stage();
            stage.setTitle(algo.getNom());
            stage.setScene(new Scene(rootLayout));
            stage.show();
            res.updateParam(param);
            res.startAlgo();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
