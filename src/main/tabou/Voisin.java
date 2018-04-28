package main.tabou;

import main.graphe.Sommet;

import java.util.List;

public class Voisin {
    private List<Sommet> solution;
    private Float distance;
    private Permutation permutation;

    public Voisin(List<Sommet> solution, Float distance, Permutation permutation) {
        this.solution = solution;
        this.distance = distance;
        this.permutation = permutation;
    }

    public List<Sommet> getSolution() {
        return solution;
    }

    public void setSolution(List<Sommet> solution) {
        this.solution = solution;
    }

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }

    public Permutation getPermutation() {
        return permutation;
    }

    public void setPermutation(Permutation permutation) {
        this.permutation = permutation;
    }
}
