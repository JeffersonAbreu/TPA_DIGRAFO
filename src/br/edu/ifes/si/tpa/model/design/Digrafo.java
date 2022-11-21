package br.edu.ifes.si.tpa.model.design;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Digrafo {
    private HashMap<Integer, Autor> autores;
    private List<Vertice> vertices;
    
    public Digrafo(int nVertices) {
        autores = new HashMap<>();
        vertices = new ArrayList<>(nVertices);
    }

    // metodos publicos
    public Autor getAutor(int ID) {
        if (!autores.containsKey((Integer) ID)) {
            autores.put((Integer) ID, new Autor(ID));
        }
        return autores.get((Integer) ID);
    }

    public void addAresta(int de, int para) {
        getVertices().get(de).addAdj(new Aresta(de, para));
    }

    public void addVertice(int vert, int autor) {
        vertices.add(new Vertice(vert, getAutor(autor)));
    }

    public List<Vertice> getVertices() {
        return vertices;
    }

    // Classes Internas

    private class Aresta {
        int de, para;

        public Aresta(int de, int para) {
            this.de = de;
            this.para = para;
        }
    }

    /**
     * Autor
     */
    private class Autor implements Comparable {
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

    /**
     * Vertice
     */
    private class Vertice {
        private int ID;
        private Autor dono;
        protected ArrayList<Aresta> adj;

        Vertice(int ID, Autor autor) {
            this.ID = ID;
            this.dono = autor;
            this.adj = new ArrayList<>();
        }

        Autor getAutor() {
            return dono;
        }

        int getID() {
            return ID;
        }

        List<Aresta> getAllAdj() {
            return adj;
        }

        void addAdj(Aresta a) {
            adj.add(0, a);
        }
    }
}
