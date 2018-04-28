package main.tabou;

public class Couple {

    private int indexDebut;
    private int indexFin;

    public Couple(int debut, int fin){
        indexDebut = debut;
        indexFin = fin;
    }

    public int getIndexDebut() {
        return indexDebut;
    }

    public void setIndexDebut(int indexDebut) {
        this.indexDebut = indexDebut;
    }

    public int getIndexFin() {
        return indexFin;
    }

    public void setIndexFin(int indexFin) {
        this.indexFin = indexFin;
    }

    @Override
    public boolean equals(Object obj) {
        Couple couple = (Couple) obj;
        if(indexDebut == couple.getIndexDebut() && indexFin == couple.getIndexFin()){
            return true;
        }
        return false;
    }
}
