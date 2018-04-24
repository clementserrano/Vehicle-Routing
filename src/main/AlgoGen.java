package main;

import java.util.*;

import static java.util.stream.Collectors.joining;

public class AlgoGen {

    private final int ITERATION_MAX = 100;

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
            List<Sommet> bestSolution;
            for (SolutionGen solution : population) {
                cpt += Outils.distanceTotale(solution.getListeSommets());
            }
        }
        solutionFound = solution1;

        System.out.println(solutionFound.getListeSommets().stream().map(sommet -> sommet.toString()).collect(joining(";")));

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
