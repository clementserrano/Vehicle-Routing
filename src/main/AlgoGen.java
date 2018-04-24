package main;

import java.util.*;
import java.util.stream.Collectors;

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

            List<SolutionGen> populationTemp = new ArrayList<>();
            for (int j = 0; j < population.size(); j++) {
                double rand = Math.random();
                System.out.println(rand);
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
