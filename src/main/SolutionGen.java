package main;

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
