package main.génétique;

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

}
