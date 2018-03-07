package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static java.util.stream.Collectors.joining;

public class Graphe {

    private Map<Sommet, List<Arc>> adjacence;

    public Graphe(String filename){
        try {
            File file = new File("src/data/"+filename);
            Scanner input = new Scanner(file);

            adjacence = new HashMap<>();

            if(input.hasNextLine()){
                input.nextLine();

                String line = "";

                // Construction des sommets
                while(input.hasNextLine()){
                    line = input.nextLine();
                    //System.out.println(line);
                    String[] data = line.split(";");

                    Sommet sommet = new Sommet(
                            Integer.valueOf(data[0]),
                            Integer.valueOf(data[1]),
                            Integer.valueOf(data[2]),
                            Integer.valueOf(data[3]));

                    adjacence.put(sommet, new ArrayList<>());
                }
            }

            // Construction des arcs
            ArrayList<Sommet> listeSommet = new ArrayList();
            listeSommet.addAll(adjacence.keySet());

            for(int i = 0; i < listeSommet.size(); i++){
                for(int j = i + 1; j < listeSommet.size(); j++){
                    Sommet sommetA = listeSommet.get(i);
                    Sommet sommetB = listeSommet.get(j);
                    double distance = Outils.distance(listeSommet.get(i),listeSommet.get(j));

                    if(distance < 25){
                        Arc arc = new Arc(sommetA, sommetB, distance);
                        adjacence.get(sommetA).add(arc);
                        adjacence.get(sommetB).add(arc);
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        StringBuilder toStringBuilder = new StringBuilder();
        toStringBuilder.append("Graph \n");
        adjacence.forEach((node, arcs) -> {
            toStringBuilder.append("[=")
                    .append(node.toString())
                    .append(" : [");

            final String arcToString =
                    arcs.stream()
                            .map(arc -> new StringBuilder()
                                    .append("[")
                                    .append(arc.toString())
                                    .append("]"))
                            .collect(joining(", "));

            toStringBuilder.append(arcToString);
            toStringBuilder.append(("]\n"));
        });

        return toStringBuilder.toString();
    }
}
