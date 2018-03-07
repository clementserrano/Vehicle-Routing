package main;

public class Outils {

    public static double distance(Sommet a, Sommet b){
        return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
    }
}
