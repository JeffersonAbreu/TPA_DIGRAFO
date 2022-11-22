package br.edu.ifes.si.tpa.model.design;

public class Autor implements Comparable<Autor> {
    private int ID;

        public Autor(int id) {
            this.ID = id;
        }

        public int getID() {
            return ID;
        }

        @Override
        public int compareTo(Autor autor) {
            if (this.ID == autor.getID())
                return 1;
            return 0;
        }
}
