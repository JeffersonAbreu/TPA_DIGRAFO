package br.edu.ifes.si.tpa.model.design;

import java.util.ArrayList;
import java.util.List;

public class Vertice {
    private String ID;
        private Autor dono;
        protected ArrayList<Aresta> adj;

        Vertice(String ID, Autor autor) {
            this.ID = ID;
            this.dono = autor;
            this.adj = new ArrayList<>();
        }

        public Autor getAutor() {
            return dono;
        }

        public String getID() {
            return ID;
        }

        public List<Aresta> getAllAdj() {
            return adj;
        }

        void addAdj(Aresta a) {
            adj.add(0, a);
        }
}