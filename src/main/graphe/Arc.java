package main.graphe;

public class Arc {

    private Sommet sommetA;
    private Sommet sommetB;
    private double distance;

    public Arc(Sommet a, Sommet b, double d){
        sommetA = a;
        sommetB = b;
        distance = d;
    }

    public Sommet getSommetA() {
        return sommetA;
    }

    public void setSommetA(Sommet sommetA) {
        this.sommetA = sommetA;
    }

    public Sommet getSommetB() {
        return sommetB;
    }

    public void setSommetB(Sommet sommetB) {
        this.sommetB = sommetB;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return sommetA.getIndex() + " ==> "+ sommetB.getIndex() +"("+Math.floor(distance * 100) / 100+")";
    }
}
