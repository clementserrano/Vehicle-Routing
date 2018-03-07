package main;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static java.util.stream.Collectors.joining;

public class Graphe {

    private Map<Sommet, List<Arc>> adjacence;
    private Sommet sommetDepart;
    private int capacite;

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
                    if(sommetDepart == null){
                        sommetDepart = sommet;
                    }
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
                        Arc arc1 = new Arc(sommetA, sommetB, distance);
                        adjacence.get(sommetA).add(arc1);
                        Arc arc2 = new Arc(sommetB, sommetA, distance);
                        adjacence.get(sommetB).add(arc2);
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Sommet getSommetDepart() {
        return sommetDepart;
    }

    public Map<Sommet, List<Arc>> getAdjacence() {
        return adjacence;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public void dessine() {
        JFrame fenetre = new JFrame();
        JPanel panel = new JPanel();
        panel.setLayout(null);

        for(Sommet sommet : adjacence.keySet()){
            JLabel label = new JLabel(sommet.toString());
            label.setLocation(sommet.getX()*5, sommet.getY()*5);
            label.setSize(20, 20);
            panel.add(label);
        }

        fenetre.add(panel);
        fenetre.setSize(1000,1000);
        fenetre.setVisible(true);
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
