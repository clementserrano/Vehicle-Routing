package main.tabou;

import javafx.application.Platform;
import main.Outils;
import main.graphe.Graphe;
import main.graphe.Sommet;
import main.gui.Algo;
import main.gui.Res;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.joining;

public class Tabou implements Algo {

    private int ITERATION_MAX = 100000;
    private int TAILLE_VOISINAGE = 200;
    private int TAILLE_LISTE_TABOU = 100;
    private int TAILLE_MAX_PERMUTATION;

    public String findSolution(Graphe graphe, Res controller) {
        // Création d'une solution X0
        List<Sommet> solution = Outils.findFirstSolution(graphe);
        List<Sommet> bestSolution = solution;
        float distanceTotaleMin = Outils.distanceTotale(solution);

        // Méthode tabou
        TAILLE_MAX_PERMUTATION = (graphe.getAdjacence().keySet().size() - 2) / 3;

        List<Permutation> listeTabou = new ArrayList<>();

        Voisin voisinPrev = new Voisin(solution, distanceTotaleMin, null);
        int i = 0;

        print(bestSolution, distanceTotaleMin, listeTabou, i, controller);

        while (i < ITERATION_MAX) {
            Voisinage voisinage = new Voisinage();
            List<Permutation> permutations = new ArrayList<>();
            while (voisinage.size() < TAILLE_VOISINAGE) {
                Voisin voisin = permutation(voisinPrev.getSolution(), graphe);
                if (!permutations.contains(voisin.getPermutation())) {
                    voisinage.add(voisin);
                    permutations.add(voisin.getPermutation());
                }
            }

            Voisin voisinNext = voisinage.getBest(listeTabou);

            if (voisinNext != null) {
                if (voisinNext.getDistance() > voisinPrev.getDistance()) {
                    if (listeTabou.size() >= TAILLE_LISTE_TABOU) {
                        listeTabou.remove(0);
                    }
                    listeTabou.add(voisinNext.getPermutation());
                }

                if (voisinNext.getDistance() < distanceTotaleMin) {
                    bestSolution = voisinNext.getSolution();
                    distanceTotaleMin = voisinNext.getDistance();

                    print(bestSolution, distanceTotaleMin, listeTabou, i, controller);
                }

                voisinPrev = voisinNext;
            } else {
                break;
            }

            i++;
            printIter(i, controller);
        }

        print(bestSolution, distanceTotaleMin, listeTabou, i, controller);
        return bestSolution.stream().map(sommet -> sommet.toString()).collect(joining(";"));
    }

    @Override
    public String getNom() {
        return "Tabou";
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
            indexA = Outils.getRandomBetween(1, solution.size() - 3);
            int tailleMaxA = solution.size() - 3 - indexA;
            int tailleA = Outils.getRandomBetween(0, tailleMaxA > TAILLE_MAX_PERMUTATION ? TAILLE_MAX_PERMUTATION : tailleMaxA);
            indexAFin = indexA + tailleA;

            indexB = Outils.getRandomBetween(indexAFin, solution.size() - 2);
            int tailleMaxB = solution.size() - 2 - indexB;
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

    public void print(List<Sommet> bestSolution, float distanceTotaleMin, List<Permutation> listeTabou, int i, Res controller) {
        final String res = "-------------------------------------------------------------------------------------------------------"
                + "\nItération : " + i + "       Distance minimale : " + distanceTotaleMin + "       Taille tabou : " + listeTabou.size()
                + "\n" + bestSolution.stream().map(sommet -> sommet.toString()).collect(joining(";"));
        System.out.println(res);
        Platform.runLater(() -> controller.updateConsole(res));
    }

    public void printIter(int i, Res controller) {
        Platform.runLater(() -> controller.updateIter(i));
    }

    public void setITERATION_MAX(int ITERATION_MAX) {
        this.ITERATION_MAX = ITERATION_MAX;
    }

    public void setTAILLE_VOISINAGE(int TAILLE_VOISINAGE) {
        this.TAILLE_VOISINAGE = TAILLE_VOISINAGE;
    }

    public void setTAILLE_LISTE_TABOU(int TAILLE_LISTE_TABOU) {
        this.TAILLE_LISTE_TABOU = TAILLE_LISTE_TABOU;
    }
}
