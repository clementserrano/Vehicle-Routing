package test;

import main.AlgoGen;
import main.Graphe;
import main.Tabou;

public class Test {
    public static void main(String[] args) {
        Graphe graphe = new Graphe("data01.txt");
        System.out.println(graphe);

        Tabou tabou = new Tabou();
        System.out.println(tabou.findSolution(graphe));

        AlgoGen algoGen = new AlgoGen();
        System.out.println(algoGen.findSolution(graphe));

        System.out.println("Fin");
    }
}
