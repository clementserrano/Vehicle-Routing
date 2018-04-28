package main.tabou;

import java.util.ArrayList;
import java.util.List;

public class Historique extends ArrayList<Historique.Line> {

    class Line {
        private float distance;
        private List<Permutation> listeTabou;

        public Line(float distance, List<Permutation> listeTabou) {
            this.distance = distance;
            this.listeTabou = listeTabou;
        }

        public float getDistance() {
            return distance;
        }

        public void setDistance(float distance) {
            this.distance = distance;
        }

        public List<Permutation> getListeTabou() {
            return listeTabou;
        }

        public void setListeTabou(List<Permutation> listeTabou) {
            this.listeTabou = listeTabou;
        }
    }

    public boolean contains(float distance, List<Permutation> listeTabou) {
        Line line = new Line(distance, listeTabou);
        return this.contains(line);
    }

    public void add(float distance, List<Permutation> listeTabou) {
        this.add(new Line(distance, listeTabou));
    }
}
