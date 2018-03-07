package main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.joining;

public class Tabou {

    public String findSolution(Graphe graphe) {
        // Création d'une solution X0
        List<Sommet> solution = new ArrayList<>();
        Set<Sommet> sommetsParcourus = new HashSet<>();

        solution.add(graphe.getSommetDepart());
        sommetsParcourus.add(graphe.getSommetDepart());
        Sommet sommetSuivant = graphe.getSommetDepart();

        while(sommetsParcourus.size() != graphe.getAdjacence().keySet().size()){
            int sommeQuantite = 0;
            while(sommeQuantite < graphe.getCapacite()){
                sommetSuivant = getVoisinProche(graphe.getAdjacence().get(sommetSuivant), sommetsParcourus);
                if(sommeQuantite + sommetSuivant.getQuantite() < graphe.getCapacite()){
                    sommeQuantite += sommetSuivant.getQuantite();
                    solution.add(sommetSuivant);
                }
                System.out.println(sommetSuivant.getIndex());
            }
            solution.add(graphe.getSommetDepart());
        }

        return solution.stream().map(sommet -> sommet.toString()).collect(joining(""));
    }

    private Sommet getVoisinProche(List<Arc> arcs, Set<Sommet> sommetsParcourus){
        double distanceMin = arcs.get(0).getDistance();
        Sommet resultat = arcs.get(0).getSommetB();

        for (Arc arc : arcs){
            if(!sommetsParcourus.contains(arc.getSommetB()) && arc.getDistance() < distanceMin){
                distanceMin = arc.getDistance();
                resultat = arc.getSommetB();
            }
        }

        return resultat;
    }
}
