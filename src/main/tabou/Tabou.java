package main.tabou;

import main.graphe.Graphe;
import main.Outils;
import main.graphe.Sommet;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.joining;

public class Tabou {

    private static final int ITERATION_MAX = 1000000;
    private static final int TAILLE_VOISINAGE = 800;
    private static final int TAILLE_LISTE_TABOU = 200;

    private static int TAILLE_MAX_PERMUTATION;

    public String findSolution(Graphe graphe) {
        // Création d'une solution X0
        List<Sommet> solution = Outils.findFirstSolution(graphe);
        List<Sommet> bestSolution = solution;
        float distanceTotaleMin = Outils.distanceTotale(solution);

        // Méthode tabou
        TAILLE_MAX_PERMUTATION = (graphe.getAdjacence().keySet().size() - 2) / 3;

        List<Permutation> listeTabou = new ArrayList<>();
        Historique historique = new Historique();
        historique.add(distanceTotaleMin,listeTabou);

        Voisin voisinPrev = new Voisin(solution, distanceTotaleMin, null);
        int i = 0;

        System.out.println("-------------------------------------------------------------------------------------------------------");
        System.out.println(i + " : " + distanceTotaleMin + "    tabou : " + listeTabou.size());
        System.out.println(bestSolution.stream().map(sommet -> sommet.toString()).collect(joining(";")));

        while (i < ITERATION_MAX) {
            Voisinage voisinage = new Voisinage();
            List<Permutation> permutations = new ArrayList<>();
            while (voisinage.size() < TAILLE_VOISINAGE) {
                Voisin voisin = permutation(voisinPrev.getSolution(), graphe);
                if(!permutations.contains(voisin.getPermutation())){
                    voisinage.add(voisin);
                    permutations.add(voisin.getPermutation());
                }
            }

            Voisin voisinNext = voisinage.getBest(listeTabou);

            if(voisinNext != null) {
                if (voisinNext.getDistance() > voisinPrev.getDistance()) {
                    if (listeTabou.size() >= TAILLE_LISTE_TABOU) {
                        listeTabou.remove(0);
                    }
                    listeTabou.add(voisinNext.getPermutation());
                }

                if (voisinNext.getDistance() < distanceTotaleMin) {
                    bestSolution = voisinNext.getSolution();
                    distanceTotaleMin = voisinNext.getDistance();
                }

                voisinPrev = voisinNext;
            }else{
                System.out.println("---------------------------------------TABOU END-------------------------------------------------------");
                System.out.println(i + " : " + distanceTotaleMin + "    tabou : " + listeTabou.size());
                break;
            }

            i++;
            if (i % 10000 == 0) {
                System.out.println("-------------------------------------------------------------------------------------------------------");
                System.out.println(i + " : " + distanceTotaleMin + "    tabou : " + listeTabou.size());
                System.out.println(bestSolution.stream().map(sommet -> sommet.toString()).collect(joining(";")));
            }

            /*if(historique.contains(voisinNext.getDistance(),listeTabou)){
                System.out.println("Loop detected");
                break;
            }else{
                historique.add(voisinNext.getDistance(),listeTabou);
            }*/
        }
        /*graphe.startDraw();
        for (int j = 0; j < bestSolution.size() - 1; j++) {
            graphe.dessine(bestSolution.get(j).getX() * 5, bestSolution.get(j).getY() * 5, bestSolution.get(j + 1).getX() * 5, bestSolution.get(j + 1).getY() * 5);
        }*/
        return bestSolution.stream().map(sommet -> sommet.toString()).collect(joining(";"));
    }

    private Voisin permutation(List<Sommet> solution, Graphe graphe) {
        List<Sommet> solutionPermutee = new ArrayList<>();

        List<Sommet> fragmentA = new ArrayList<>();
        List<Sommet> fragmentB = new ArrayList<>();

        Permutation permutation = new Permutation(0, 0, 0, 0);

        int indexA = 0;
        int indexAFin = 0;
        int indexB = 0;
        int indexBFin = 0;

        boolean solutionOK = false;

        while (!solutionOK) {
            solutionPermutee.clear();
            fragmentA.clear();
            fragmentB.clear();
            //A VOIR: Les fragments ont souvent une taille de seulement 1.
            indexA = Outils.getRandomBetween(1, solution.size() - 2);
            int tailleMaxA = solution.size() - 2 - indexA;
            int tailleA = Outils.getRandomBetween(0, tailleMaxA > TAILLE_MAX_PERMUTATION ? TAILLE_MAX_PERMUTATION : tailleMaxA);
            indexAFin = indexA + tailleA;

            indexB = Outils.getRandomBetween(indexAFin, solution.size() - 1);
            int tailleMaxB = solution.size() - 1 - indexB;
            int tailleB = Outils.getRandomBetween(0, tailleMaxB > TAILLE_MAX_PERMUTATION ? TAILLE_MAX_PERMUTATION : tailleMaxB);
            indexBFin = indexB + tailleB;

            permutation = new Permutation(indexA, indexAFin, indexB, indexBFin);

            for (int i = 0; i < solution.size(); i++) {
                if (i >= indexA && i <= indexAFin) {
                    fragmentA.add(solution.get(i));
                } else if (i >= indexB && i <= indexBFin) {
                    fragmentB.add(solution.get(i));
                } else {
                    solutionPermutee.add(solution.get(i));
                }
            }

            if (solutionPermutee.size() < indexA) {
                solutionPermutee.addAll(fragmentB);
            } else {
                solutionPermutee.addAll(indexA, fragmentB);
            }

            if (solutionPermutee.size() < indexB) {
                solutionPermutee.addAll(fragmentA);
            } else {
                solutionPermutee.addAll(indexB, fragmentA);
            }
            //System.out.println(Outils.getQuantiteTotale(graphe, solutionPermutee));
            solutionOK = !solution.equals(solutionPermutee) && Outils.capaciteRespectee(graphe, solutionPermutee);
        }

        //System.out.println(solutionPermutee.stream().map(sommet -> sommet.toString()).collect(joining(";")));
        return new Voisin(Outils.cleanSolution(solutionPermutee), Outils.distanceTotale(solutionPermutee), permutation);
    }


}
