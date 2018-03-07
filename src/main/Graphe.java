package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

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

                while(input.hasNextLine()){
                    line = input.nextLine();
                    System.out.println(line);
                    String[] data = line.split(";");

                    Sommet sommet = new Sommet(
                            Integer.valueOf(data[1]),
                            Integer.valueOf(data[2]),
                            Integer.valueOf(data[3]));

                    adjacence.put(sommet, new ArrayList<>());
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
