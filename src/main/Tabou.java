package main;

import java.util.*;

import static java.util.stream.Collectors.joining;

public class Tabou {

    private final int ITERATION_MAX = 10000;
    private final int TAILLE_MAX = 3;

    public String findSolution(Graphe graphe) {
        // Création d'une solution X0
        List<Sommet> solution = findSolutionX0(graphe);
        float distanceTotaleMin = Outils.distanceTotale(solution);
        System.out.println(solution.stream().map(sommet -> sommet.toString()).collect(joining(";")));

        // Méthode tabou
        List<Permutation> listeTabou = new ArrayList<>();
        int i = 0;
        System.out.println(i + " : " + distanceTotaleMin);
        while (i < ITERATION_MAX) {
            List<Sommet> solutionPermutee = permutation(solution, listeTabou, distanceTotaleMin, graphe);
            float distanceSolutionPermutee = Outils.distanceTotale(solutionPermutee);
            if (distanceSolutionPermutee < distanceTotaleMin) {
                solution = solutionPermutee;
                distanceTotaleMin = distanceSolutionPermutee;
            }
            i++;
            System.out.println(i + " : " + distanceTotaleMin);
        }

        return solution.stream().map(sommet -> sommet.toString()).collect(joining(";"));
    }

    private List<Sommet> permutation(List<Sommet> solution, List<Permutation> listeTabou, float distanceTotaleMin, Graphe graphe) {
        List<Sommet> solutionPermutee = new ArrayList<>();

        List<Sommet> fragmentA = new ArrayList<>();
        List<Sommet> fragmentB = new ArrayList<>();

        Permutation permutation = new Permutation(0, 0, 0, 0);

        int indexA = 0;
        int indexAFin = 0;
        int indexB = 0;
        int indexBFin = 0;

        boolean indexOK = false;
        boolean solutionOK = false;

        while(!solutionOK) {
            solutionPermutee.clear();
            fragmentA.clear();
            fragmentB.clear();
            indexOK = false;
            while (!indexOK) {
                //A VOIR: Les fragments ont souvent une taille de seulement 1.
                indexA = Outils.getRandomBetween(1, solution.size() - 2);
                int tailleMaxA = solution.size() - 2 - indexA;
                int tailleA = Outils.getRandomBetween(0, tailleMaxA > TAILLE_MAX ? TAILLE_MAX : tailleMaxA);
                if (tailleA == 0) {
                    indexAFin = indexA;
                } else {
                    indexAFin = Outils.getRandomBetween(indexA, indexA + tailleA);
                }

                indexB = Outils.getRandomBetween(indexAFin, solution.size() - 1);
                int tailleMaxB = solution.size() - 1 - indexB;
                int tailleB = Outils.getRandomBetween(0, tailleMaxB > TAILLE_MAX ? TAILLE_MAX : tailleMaxB);
                if (tailleB == 0) {
                    indexBFin = indexB;
                } else {
                    indexBFin = Outils.getRandomBetween(indexB, indexB + tailleB);
                }

                permutation = new Permutation(indexA, indexAFin, indexB, indexBFin);

                if (!listeTabou.contains(permutation)) {
                    indexOK = true;
                }
            }

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
            System.out.println(Outils.getQuantiteTotale(graphe, solutionPermutee));
            solutionOK = Outils.capaciteRespectee(graphe, solutionPermutee);
        }

        if(Outils.distanceTotale(solutionPermutee) > distanceTotaleMin){
            if(listeTabou.size() >= 10){
                listeTabou.remove(0);
            }
            listeTabou.add(permutation);
        }

        System.out.println(solutionPermutee.stream().map(sommet -> sommet.toString()).collect(joining(";")));
        return solutionPermutee;
    }

    private List<Sommet> findSolutionX0(Graphe graphe) {
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


}
