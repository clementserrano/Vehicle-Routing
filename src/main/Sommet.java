package main;

public class Sommet {

    private int index;
    private int x;
    private int y;
    private int quantite;

    public Sommet(int _index, int _x, int _y, int _quantite){
        index = _index;
        x = _x;
        y = _y;
        quantite = _quantite;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    @Override
    public String toString() {
        return Integer.toString(index);
    }
}
