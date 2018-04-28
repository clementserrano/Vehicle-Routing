package main;

import main.graphe.Graphe;
import main.graphe.Sommet;

import java.util.Arrays;
import java.util.List;

public class SolutionGen {

    List<Sommet> listeSommets;

    public SolutionGen() {

    }

    public SolutionGen(List<Sommet> listeSommets) {
        this.listeSommets = listeSommets;
    }

    public List<Sommet> getListeSommets() {
        return listeSommets;
    }

    public boolean containsAll(Graphe graphe) {
        System.out.println(Arrays.asList(graphe.getAdjacence().keySet()));
        return this.listeSommets.containsAll(Arrays.asList(graphe.getAdjacence().keySet()));
    }
}
