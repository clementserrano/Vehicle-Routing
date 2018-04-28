package main;

import main.graphe.Graphe;
import main.graphe.Sommet;

import java.util.*;

import static java.util.stream.Collectors.joining;

public class AlgoGen {

    private final int ITERATION_MAX = 1;

    public String findSolution(Graphe graphe) {
        SolutionGen solutionFound = new SolutionGen();
        SolutionGen solution1 = new SolutionGen(findSolutionX0(graphe));
        SolutionGen solution2 = new SolutionGen(findSolutionX0(graphe));
        SolutionGen solution3 = new SolutionGen(findSolutionX0(graphe));
        SolutionGen solution4 = new SolutionGen(findSolutionX0(graphe));

        List<SolutionGen> population = new ArrayList<>();
        population.add(solution1);
        population.add(solution2);
        population.add(solution3);
        population.add(solution4);

        for (int i = 0; i < ITERATION_MAX; i++) {
            int cpt = 0;
            List<Float> fitnessList = new ArrayList<>();
            for (SolutionGen solution : population) {
                fitnessList.add(Outils.distanceTotale(solution.getListeSommets()));
                cpt += Outils.distanceTotale(solution.getListeSommets());
            }
            for (int j = 0; j < fitnessList.size(); j++) {
                Float proba = fitnessList.get(j);
                proba /= cpt;
                if (j > 0) {
                    fitnessList.set(j, fitnessList.get(j - 1) + proba);
                } else {
                    fitnessList.set(j, proba);
                }
            }
            System.out.println(fitnessList);
            System.out.print("\n");

            List<SolutionGen> populationTemp = new ArrayList<>();
            for (int j = 0; j < population.size(); j++) {
                double rand = Math.random();
                //System.out.println(rand);
                int index = 0;
                for (Float fitness : fitnessList) {
                    if (rand < fitness) {
                        populationTemp.add(population.get(index));
                        break;
                    }
                    index++;
                }
            }
            population = populationTemp;
            for (SolutionGen solution : population) {
                System.out.println(solution.getListeSommets().stream().map(sommet -> sommet.toString()).collect(joining(";")));
            }

            List<SolutionGen> first = new ArrayList<>(population.subList(0, population.size()/2));
            List<SolutionGen> second = new ArrayList<>(population.subList(population.size()/2, population.size()));

            population.clear();

            for (int j = 0; j < first.size(); j++) {
                int indexCroisement = Outils.getRandomBetween(0, first.get(j).getListeSommets().size());
                System.out.println(indexCroisement);
                SolutionGen solutionFirstPart = first.get(j);
                SolutionGen solutionSecondPart = second.get(j);
                List<Sommet> fragment1 = solutionFirstPart.getListeSommets().subList(0, indexCroisement);
                List<Sommet> fragment2 = solutionFirstPart.getListeSommets().subList(indexCroisement, solutionFirstPart.getListeSommets().size());

                List<Sommet> fragment3 = solutionSecondPart.getListeSommets().subList(0, indexCroisement);
                List<Sommet> fragment4 = solutionSecondPart.getListeSommets().subList(indexCroisement, solutionSecondPart.getListeSommets().size());

                List<Sommet> newSolution1 = new ArrayList<>();
                newSolution1.addAll(fragment1);
                newSolution1.addAll(fragment4);
                List<Sommet> newSolution2 = new ArrayList<>();
                newSolution2.addAll(fragment3);
                newSolution2.addAll(fragment2);

                population.add(new SolutionGen(newSolution1));
                population.add(new SolutionGen(newSolution2));
            }

            System.out.println();
            for (SolutionGen solution : population) {
                System.out.println(solution.getListeSommets().stream().map(sommet -> sommet.toString()).collect(joining(";")));
            }

        }


        solutionFound = solution1;
        return solutionFound.getListeSommets().stream().map(sommet -> sommet.toString()).collect(joining(";"));
    }

    private List<Sommet> findSolutionX0(Graphe graphe) {
        List<Sommet> solution_tmp = Outils.findFirstSolution(graphe);
        List<Sommet> sommet_list = new ArrayList<>();

        boolean solutionOK = false;

        while (!solutionOK) {
            Collections.shuffle(solution_tmp);
            solutionOK = Outils.capaciteRespectee(graphe, solution_tmp);
        }
        System.out.println(solution_tmp.stream().map(sommet -> sommet.toString()).collect(joining(";")));

        return solution_tmp;
    }
}
