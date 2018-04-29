package main.génétique;

import main.Outils;
import main.graphe.Graphe;
import main.graphe.Sommet;

import java.util.*;

import static java.util.stream.Collectors.joining;

public class AlgoGen {

    private final int ITERATION_MAX = 100000;
    private final int POPULATION_SIZE = 150;

    public String findSolution(Graphe graphe) {
        System.out.println("-------------------------------------------------------------------------------------------------------");
        SolutionGen solutionFound = new SolutionGen();
        List<SolutionGen> population = new ArrayList<>();
        for (int i = 0; i < POPULATION_SIZE; i++) {
            SolutionGen solution = new SolutionGen(findSolutionX0(graphe));
            population.add(solution);
        }

        for (int i = 0; i < ITERATION_MAX; i++) {
            Collections.sort(population, (s1, s2) -> {
                if (Outils.distanceTotale(s1.getListeSommets()) > Outils.distanceTotale(s2.getListeSommets()))
                    return 1;
                if (Outils.distanceTotale(s1.getListeSommets()) < Outils.distanceTotale(s2.getListeSommets()))
                    return -1;
                return 0;
            });

            //1 - Reproduction
            int cpt = 0;
            List<Float> fitnessList = new ArrayList<>();
            for (SolutionGen solution : population.subList(0, population.size() / 2)) {
                float fitness;
                fitness = (float) Math.pow(Outils.distanceTotale(solution.getListeSommets()), 2);
                fitnessList.add(fitness);
                cpt += fitness;
            }

            //Pondération
            float cpt2 = 0;
            for (int j = 0; j < fitnessList.size(); j++) {
                float proba = fitnessList.get(j) / cpt;
                cpt2 += 1 - proba;
                fitnessList.set(j, 1 - proba);
            }

            //Inversion pondération
            for (int j = 0; j < fitnessList.size(); j++) {
                float proba = fitnessList.get(j) / cpt2;
                if (j > 0) {
                    fitnessList.set(j, fitnessList.get(j - 1) + proba);
                } else {
                    fitnessList.set(j, proba);
                }
            }

            //Choix reproduction
            List<SolutionGen> populationTemp = new ArrayList<>();
            for (int j = 0; j < POPULATION_SIZE; j++) {
                double rand = Math.random();
                int index = 0;
                for (Float fitness : fitnessList) {
                    if (rand < fitness) {
                        populationTemp.add(new SolutionGen(population.get(index).getListeSommets()));
                        break;
                    }
                    index++;
                }
            }
            population = populationTemp;

            //2 - Croisement

            List<SolutionGen> first = new ArrayList<>(population.subList(0, population.size() / 2));
            List<SolutionGen> second = new ArrayList<>(population.subList(population.size() / 2, population.size()));

            population.clear();

            for (int j = 0; j < first.size(); j++) {
                int indexCroisement = Outils.getRandomBetween(0, first.get(j).getListeSommets().size());
                SolutionGen solutionFirstPart = new SolutionGen(first.get(j).getListeSommets());
                SolutionGen solutionSecondPart = new SolutionGen(second.get(j).getListeSommets());
                List<Sommet> fragment1 = new ArrayList<>(solutionFirstPart.getListeSommets().subList(0, indexCroisement));
                List<Sommet> fragment2 = new ArrayList<>(solutionFirstPart.getListeSommets().subList(indexCroisement, solutionFirstPart.getListeSommets().size()));

                List<Sommet> fragment3 = new ArrayList<>(solutionSecondPart.getListeSommets().subList(0, indexCroisement));
                List<Sommet> fragment4 = new ArrayList<>(solutionSecondPart.getListeSommets().subList(indexCroisement, solutionSecondPart.getListeSommets().size()));

                List<Sommet> newSolution1 = new ArrayList<>(fragment4);
                List<Sommet> newSolution2 = new ArrayList<>(fragment2);

                //Supprime si un sommet existe dans les fragment 1 et 4
                List<Sommet> toRemoveFragment1 = new ArrayList<>();
                for (Sommet sommet : fragment1) {
                    if (newSolution1.contains(sommet)) {
                        toRemoveFragment1.add(sommet);
                    }
                }

                //Supprime si un sommet existe dans les fragment 2 et 3
                List<Sommet> toRemoveFragment3 = new ArrayList<>();
                for (Sommet sommet : fragment3) {
                    if (newSolution2.contains(sommet)) {
                        toRemoveFragment3.add(sommet);
                    }
                }

                for (Sommet sommet : toRemoveFragment1) {
                    fragment1.remove(sommet);
                }
                for (Sommet sommet : toRemoveFragment3) {
                    fragment3.remove(sommet);
                }

                fragment3.addAll(toRemoveFragment1);
                fragment1.addAll(toRemoveFragment3);

                newSolution1.addAll(0, fragment1);
                newSolution2.addAll(0, fragment3);

                if (Outils.capaciteRespectee(graphe, newSolution1) && Outils.capaciteRespectee(graphe, newSolution2)
                        && newSolution1.get(0).getIndex() == 0 && newSolution1.get(newSolution1.size() - 1).getIndex() == 0
                        && newSolution2.get(0).getIndex() == 0 && newSolution2.get(newSolution2.size() - 1).getIndex() == 0) {
                    population.add(new SolutionGen(newSolution1));
                    population.add(new SolutionGen(newSolution2));
                } else {
                    population.add(solutionFirstPart);
                    population.add(solutionSecondPart);
                }
            }

            //3 - Mutation
            for (SolutionGen solution : population) {
                double rand = Math.random();
                if (rand < 0.05) {
                    int a = Outils.getRandomBetween(1, solution.getListeSommets().size());
                    int b = Outils.getRandomBetween(1, solution.getListeSommets().size());
                    Collections.swap(solution.getListeSommets(), a, b);
                }
            }

            if (i % 1000 == 0) {
                System.out.println();
                for (SolutionGen solution : population) {
                    System.out.println(solution.getListeSommets().stream().map(Sommet::toString).collect(joining(";")) + " " + Outils.distanceTotale(solution.getListeSommets()));
                }
            }
        }


        solutionFound = population.get(0);
        for (SolutionGen solution : population) {
            if (Outils.distanceTotale(solutionFound.getListeSommets()) > Outils.distanceTotale(solution.getListeSommets())) {
                solutionFound = solution;
            }
        }

        /*
        graphe.startDraw();
        for (int j = 0; j < solutionFound.getListeSommets().size() - 1; j++) {
            graphe.dessine(solutionFound.getListeSommets().get(j).getX() * 5, solutionFound.getListeSommets().get(j).getY() * 5, solutionFound.getListeSommets().get(j + 1).getX() * 5, solutionFound.getListeSommets().get(j + 1).getY() * 5);
        }*/

        System.out.println("Meilleur : ");
        System.out.println(solutionFound.getListeSommets().stream().map(sommet -> sommet.toString()).collect(joining(";")) + " " + Outils.distanceTotale(solutionFound.getListeSommets()));
        return solutionFound.getListeSommets().stream().map(sommet -> sommet.toString()).collect(joining(";"));
    }

    private List<Sommet> findSolutionX0(Graphe graphe) {
        List<Sommet> solution_tmp = Outils.findFirstSolution(graphe);
        List<Sommet> sommet_list = new ArrayList<>();

        boolean solutionOK = false;

        while (!solutionOK) {
            Collections.shuffle(solution_tmp.subList(1, solution_tmp.size() - 1));
            solutionOK = Outils.capaciteRespectee(graphe, solution_tmp);
        }

        return solution_tmp;
    }
}
