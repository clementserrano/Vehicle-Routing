package test;

import main.graphe.Graphe;
import main.génétique.AlgoGen;
import main.tabou.Tabou;

public class Test {
    public static void main(String[] args) {
        Graphe graphe = new Graphe("data01.txt");
        //System.out.println(graphe);
        graphe.setCapacite(100);

        System.out.println("\nTabou");
        Tabou tabou = new Tabou();
        System.out.println(tabou.findSolution(graphe));

        System.out.println("\nAlgoGen");
        AlgoGen algoGen = new AlgoGen();
        System.out.println("\nFinal : " + algoGen.findSolution(graphe) + "\n");

        System.out.println("Fin");
    }
}
