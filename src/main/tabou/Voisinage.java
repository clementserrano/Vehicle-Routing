package main.tabou;

import java.util.ArrayList;
import java.util.List;

public class Voisinage extends ArrayList<Voisin> {

    public Voisin getBest(List<Permutation> listeTabou) {
        Voisin best = this.get(0);
        boolean isTabou = listeTabou.contains(best.getPermutation());
        for (Voisin voisin : this) {
            if (voisin.getDistance() < best.getDistance() && !listeTabou.contains(voisin.getPermutation())) {
                best = voisin;
                isTabou = false;
            }
        }

        if(isTabou) return null;
        return best;
    }
}
