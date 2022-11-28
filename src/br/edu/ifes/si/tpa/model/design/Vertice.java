package br.edu.ifes.si.tpa.model.design;

import java.util.ArrayList;
import java.util.List;

public class Vertice {
    private int ID;
    private Autor dono;
    protected ArrayList<Aresta> adj;

    Vertice(int ID, Autor autor) {
        this.ID = ID;
        this.dono = autor;
        this.adj = new ArrayList<Aresta>();
    }

    public Autor getAutor() {
        return dono;
    }

    public int getID() {
        return ID;
    }

    public List<Aresta> getAllAdj() {
        return adj;
    }

    void addAdj(Aresta a) {
        adj.add(a);
    }
}
