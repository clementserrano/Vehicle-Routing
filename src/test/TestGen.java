package test;

import main.graphe.Graphe;
import main.genetique.AlgoGen;

public class TestGen {
    public static void main(String[] args) {
        Graphe graphe = new Graphe("data01.txt", 1);
        graphe.setCapacite(100);

        System.out.println("\nAlgoGen");
        AlgoGen algoGen = new AlgoGen();
        System.out.println("\nRésultat : " + algoGen.findSolution(graphe));

        System.out.println("Fin");
    }
}
