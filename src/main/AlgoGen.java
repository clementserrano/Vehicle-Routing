package main;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

public class AlgoGen {

    private final int ITERATION_MAX = 10;

    public String findSolution(Graphe graphe) {
        SolutionGen solutionFound = new SolutionGen();
        List<SolutionGen> population = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            SolutionGen solution = new SolutionGen(findSolutionX0(graphe));
            population.add(solution);
        }

        for (SolutionGen solution : population) {
            System.out.println(solution.getListeSommets().stream().map(sommet -> sommet.toString()).collect(joining(";")));
        }

        for (int i = 0; i < ITERATION_MAX; i++) {

            //1 - Reproduction
            int cpt = 0;
            List<Float> fitnessList = new ArrayList<>();
            for (int j = 0; j < population.size(); j++) {
                float fitness;
                if (Outils.capaciteRespectee(graphe, population.get(j).getListeSommets()))
                    fitness = Outils.distanceTotale(population.get(j).getListeSommets());
                else
                    fitness = (float) 1000000;
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
            for (int j = 0; j < population.size(); j++) {
                double rand = Math.random();
                //System.out.println(rand);
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
            /*for (SolutionGen solution : population) {
                System.out.println(solution.getListeSommets().stream().map(sommet -> sommet.toString()).collect(joining(";")));
            }*/

            //2 - Croisement



            /*
            List<SolutionGen> first = new ArrayList<>(population.subList(0, population.size() / 2));
            List<SolutionGen> second = new ArrayList<>(population.subList(population.size() / 2, population.size()));

            population.clear();

            for (int j = 0; j < first.size(); j++) {
                int indexCroisement = Outils.getRandomBetween(0, first.get(j).getListeSommets().size());
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
            */


            //3 - Mutation
            for (SolutionGen solution : population) {
                double rand = Math.random();
                if (rand < 0.1) {
                    int a = Outils.getRandomBetween(0, solution.getListeSommets().size());
                    int b = Outils.getRandomBetween(0, solution.getListeSommets().size());
                    Collections.swap(solution.getListeSommets(), a, b);
                }
            }


            System.out.println();
            for (SolutionGen solution : population) {
                System.out.println(solution.getListeSommets().stream().map(sommet -> sommet.toString()).collect(joining(";")) + " " + Outils.distanceTotale(solution.getListeSommets()));
            }
        }


        solutionFound = population.get(0);
        for (SolutionGen solution : population) {
            if (Outils.distanceTotale(solutionFound.getListeSommets()) > Outils.distanceTotale(solution.getListeSommets())) {
                solutionFound = solution;
            }
        }


        graphe.startDraw();
        for (int j = 0; j < solutionFound.getListeSommets().size() - 1; j++) {
            graphe.dessine(solutionFound.getListeSommets().get(j).getX() * 5, solutionFound.getListeSommets().get(j).getY() * 5, solutionFound.getListeSommets().get(j + 1).getX() * 5, solutionFound.getListeSommets().get(j + 1).getY() * 5);
        }

        System.out.println("Meilleur : ");
        System.out.println(solutionFound.getListeSommets().stream().map(sommet -> sommet.toString()).collect(joining(";")) + " " + Outils.distanceTotale(solutionFound.getListeSommets()));
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

        return solution_tmp;
    }
}
