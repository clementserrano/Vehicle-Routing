package main.tabou;

public class Permutation {

    private Couple coupleA;
    private Couple coupleB;

    public Permutation(Couple a, Couple b){
        coupleA = a;
        coupleB = b;
    }

    public Permutation(int aDebut, int aFin, int bDebut, int bFin){
        coupleA = new Couple(aDebut, aFin);
        coupleB = new Couple(bDebut, bFin);
    }

    public Couple getCoupleA() {
        return coupleA;
    }

    public void setCoupleA(Couple coupleA) {
        this.coupleA = coupleA;
    }

    public Couple getCoupleB() {
        return coupleB;
    }

    public void setCoupleB(Couple coupleB) {
        this.coupleB = coupleB;
    }

    @Override
    public boolean equals(Object obj) {
        Permutation permutation = (Permutation) obj;
        if((coupleA.equals(permutation.getCoupleA()) && coupleB.equals(permutation.getCoupleB()))
            || coupleA.equals(permutation.getCoupleB()) && coupleB.equals(permutation.getCoupleA())){
            return true;
        }
        return false;
    }
}
