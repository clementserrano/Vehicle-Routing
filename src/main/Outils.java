package main;

import main.graphe.Arc;
import main.graphe.Graphe;
import main.graphe.Sommet;

import java.util.*;

public class Outils {

    public static double distance(Sommet a, Sommet b){
        return Math.round(Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2)));
    }

    public static Sommet getVoisinProche(List<Arc> arcs, Set<Sommet> sommetsParcourus){
        double distanceMin = Integer.MAX_VALUE;
        Sommet resultat = null;

        for (Arc arc : arcs){
            if(!sommetsParcourus.contains(arc.getSommetB()) && arc.getDistance() < distanceMin){
                distanceMin = arc.getDistance();
                resultat = arc.getSommetB();
            }
        }

        return resultat;
    }

    public static float distanceTotale(List<Sommet> solution){
        float distanceTotale = 0;
        for(int i = 0; i < solution.size() -1; i++){
            distanceTotale += Outils.distance(solution.get(i), solution.get(i + 1));
        }
        return distanceTotale;
    }

    public static boolean capaciteRespectee(Graphe graphe, List<Sommet> solution){
        int quantiteTotale = 0;
        for(Sommet s : solution){
            if(s.getIndex() == 0){
                quantiteTotale = 0;
            }
            quantiteTotale += s.getQuantite();
            if(quantiteTotale > graphe.getCapacite()){
                return false;
            }
            if(s.equals(solution.get(solution.size()-1)) && s.getIndex() != 0){
                return false;
            }
        }
        return true;
    }

    public static String getQuantiteTotale(Graphe graphe, List<Sommet> solution){
        String resultat = "";
        int quantiteTotale = 0;
        for(Sommet s : solution){
            if(s.getIndex() == 0){
                resultat += quantiteTotale + ", ";
                quantiteTotale = 0;
            }
            quantiteTotale += s.getQuantite();
        }
        return resultat;
    }

    public static int getRandomBetween(int debut, int fin){
        Random r = new Random();
        return r.nextInt(fin - debut) + debut;
    }

    public static List<Sommet> findFirstSolution(Graphe graphe) {
        List<Sommet> solution = new ArrayList<>();
        Set<Sommet> sommetsParcourus = new HashSet<>();

        solution.add(graphe.getSommetDepart());
        sommetsParcourus.add(graphe.getSommetDepart());
        Sommet sommetSuivant = graphe.getSommetDepart();

        while (sommetsParcourus.size() != graphe.getAdjacence().keySet().size()) {
            int sommeQuantite = 0;
            while (sommetSuivant != null && sommeQuantite < graphe.getCapacite()) {
                List<Arc> arcs = graphe.getAdjacence().get(sommetSuivant);
                sommetSuivant = Outils.getVoisinProche(arcs, sommetsParcourus);
                if (sommetSuivant != null && sommeQuantite + sommetSuivant.getQuantite() < graphe.getCapacite()) {
                    sommeQuantite += sommetSuivant.getQuantite();
                    solution.add(sommetSuivant);
                    sommetsParcourus.add(sommetSuivant);
                } else {
                    break;
                }
            }
            solution.add(graphe.getSommetDepart());
            sommetSuivant = graphe.getSommetDepart();
        }
        return solution;
    }

    public static List<Sommet> cleanSolution(List<Sommet> solution){
        List<Sommet> res = new ArrayList<>();
        Sommet sommetPrev = new Sommet(Integer.MAX_VALUE,0,0,0);
        for(Sommet sommet : solution){
            if(!(sommetPrev.getIndex() == 0 && sommet.getIndex() == 0)){
                res.add(sommet);
            }
            sommetPrev = sommet;
        }
        return res;
    }
}
