package br.edu.ifes.si.tpa.model.design;

import java.util.ArrayList;
import java.util.List;

public class Autor implements Comparable<Autor> {
    private int ID;
    private List<Integer> vertices = new ArrayList<>();

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

    public void addObra(int vertice) {
        vertices.add(vertice);
    }

    public List<Integer> getObras() {
        return vertices;
    }

    public int getTotalObras() {
        return vertices.size();
    }
}
