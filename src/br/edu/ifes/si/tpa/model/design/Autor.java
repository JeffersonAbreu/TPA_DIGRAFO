package br.edu.ifes.si.tpa.model.design;

public class Autor implements Comparable {
    private int ID;

        public Autor(int id) {
            this.ID = id;
        }

        public int getID() {
            return ID;
        }

        @Override
        public int compareTo(Object o) {
            if (this.ID == ((Autor) o).getID())
                return 1;
            return 0;
        }
}
