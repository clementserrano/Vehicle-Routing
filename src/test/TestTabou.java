package test;

import main.graphe.Graphe;
import main.tabou.Tabou;

public class TestTabou {
    public static void main(String[] args) {
        Graphe graphe = new Graphe("data02.txt", 0);
        //System.out.println(graphe);
        graphe.setCapacite(100);

        System.out.println("\nTabou");
        Tabou tabou = new Tabou();
        System.out.println(tabou.findSolution(graphe,null));

        System.out.println("Fin");
    }
}
