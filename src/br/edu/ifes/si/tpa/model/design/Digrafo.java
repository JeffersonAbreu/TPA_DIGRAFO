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

    public void addVertice(String vert, int autor) {
        vertices.add(new Vertice(vert, getAutor(autor)));
    }

    public List<Vertice> getVertices() {
        return vertices;
    } 
}
