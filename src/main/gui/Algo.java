package main.gui;

import main.graphe.Graphe;

public interface Algo {
    String findSolution(Graphe graphe, Res res);

    String getNom();
}
