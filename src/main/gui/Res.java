package main.gui;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import main.graphe.Graphe;
import main.graphe.Sommet;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Res {

    @FXML
    private TextArea console;

    @FXML
    private Canvas canvas;

    @FXML
    private Label numIter;

    private Algo algo;
    private Graphe graphe;

    public Res() {

    }

    public void startAlgo() {
        Res controller = this;

        Task task = new Task<Void>() {
            @Override
            public Void call() {
                GraphicsContext g = canvas.getGraphicsContext2D();
                g.setFont(new Font(20));
                HashMap<Integer, Sommet> sommets = new HashMap<>();
                for (Sommet sommet : graphe.getAdjacence().keySet()) {
                    g.fillText(sommet.getIndex() + "", getX(sommet), getY(sommet));
                    sommets.put(sommet.getIndex(), sommet);
                }

                String res = algo.findSolution(graphe, controller);
                List<Integer> solution = Arrays.asList(res.split(";")).stream().map(sommet -> Integer.valueOf(sommet)).collect(Collectors.toList());
                g.moveTo(getX(graphe.getSommetDepart()), getY(graphe.getSommetDepart()));
                g.setLineWidth(1.0);
                for (Integer sommet : solution) {
                    g.lineTo(getX(sommets.get(sommet)), getY(sommets.get(sommet)));
                    g.stroke();
                }
                g.setLineWidth(0);

                return null;
            }
        };
        new Thread(task).start();
    }

    public void updateConsole(String txt) {
        console.appendText(txt + "\n");
    }

    public void updateIter(int i) {
        numIter.setText(i + "");
    }

    public void setAlgo(Algo algo) {
        this.algo = algo;
    }

    public void setGraphe(Graphe graphe) {
        this.graphe = graphe;
    }

    private float getX(Sommet sommet) {
        return sommet.getX() * 8;
    }

    private float getY(Sommet sommet) {
        return 10 + sommet.getY() * 5;
    }
}
