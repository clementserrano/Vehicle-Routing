package main;

import java.util.List;
import java.util.Random;
import java.util.Set;

public class Outils {

    public static double distance(Sommet a, Sommet b){
        return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
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
}
