package main.gui;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Paint;
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
                HashMap<Integer, Sommet> sommets = new HashMap<>();
                for (Sommet sommet : graphe.getAdjacence().keySet()) {
                    if (sommet.getIndex() == 0) {
                        g.setFill(Paint.valueOf("red"));
                    }
                    g.fillText(sommet.getIndex() + "", getX(sommet)+2, getY(sommet)-2);
                    g.fillOval(getX(sommet)-3, getY(sommet)-3,6,6);
                    sommets.put(sommet.getIndex(), sommet);
                    if (sommet.getIndex() == 0) {
                        g.setFill(Paint.valueOf("black"));
                    }
                }

                String res = algo.findSolution(graphe, controller);
                List<Integer> solution = Arrays.asList(res.split(";")).stream().map(sommet -> Integer.valueOf(sommet)).collect(Collectors.toList());
                g.moveTo(getX(graphe.getSommetDepart()), getY(graphe.getSommetDepart()));
                g.setLineWidth(1.3);
                g.setFont(new Font(17));
                int indexColor = 0;
                String[] color = {"red", "blue", "green", "grey", "brown", "orange", "pink", "purple", "cyan", "magenta"};
                for (Integer sommet : solution) {
                    g.lineTo(getX(sommets.get(sommet)), getY(sommets.get(sommet)));
                    g.stroke();
                    if (sommet == 0) {
                        g.setStroke(Paint.valueOf(color[indexColor]));
                        g.beginPath();
                        g.moveTo(getX(graphe.getSommetDepart()), getY(graphe.getSommetDepart()));
                        if (color.length <= indexColor) indexColor = 0;
                        indexColor++;
                    }
                }

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
        return sommet.getX() * 15;
    }

    private float getY(Sommet sommet) {
        return 10 + sommet.getY() * 8;
    }
}
