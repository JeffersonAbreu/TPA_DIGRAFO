package br.edu.ifes.si.tpa.model.design;

import java.util.HashMap;
import java.util.StringTokenizer;

public class Digrafo {
    private HashMap<Integer, Autor> autores = new HashMap<>();
    private int nVertices, nArestas;
    private Vertice[] listVertices;

    public Digrafo(Vertice[] listVertices, HashMap<Integer, Autor> autores) {
        this.autores = autores;
        this.listVertices = listVertices;
    }

    public Digrafo(In in) {
        nVertices = Integer.parseInt(in.readLine());
        nArestas = Integer.parseInt(in.readLine());
        listVertices = new Vertice[nVertices];

        // lendo vertices e arestas do arquivo
        for (int i = 0; i < nVertices; i++) {
            StringTokenizer st = new StringTokenizer(in.readLine(), " ");
            int vertice = Integer.parseInt(st.nextToken().trim()); // verticeInicial
            int autor = Integer.parseInt(st.nextToken().trim()); // verticeFinal
            listVertices[i] = new Vertice(vertice, getAutor(autor));
            autores.get(autor).addObra(vertice);
        }

        // System.out.println("\nArestas: " + arestas);
        for (int i = 0; i < nArestas; i++) {
            StringTokenizer st = new StringTokenizer(in.readLine(), " ");
            int orig = Integer.parseInt(st.nextToken().trim()); // arestaInicial
            int dest = Integer.parseInt(st.nextToken().trim()); // arestaFinal
            listVertices[orig].addAdj(new Aresta(orig, dest));
        }
    }

    public Vertice[] getListVertices() {
        return listVertices;
    }

    // metodos publicos
    public Autor getAutor(int ID) {
        if (!autores.containsKey((Integer) ID)) {
            autores.put((Integer) ID, new Autor(ID));
        }
        return autores.get((Integer) ID);
    }

    public int nVertices() {
        return nVertices;
    }

    public int nArestas() {
        return nArestas;
    }

    public Vertice getVertice(int id) {
        return listVertices[id];
    }

    public HashMap<Integer, Autor> getAutores() {
        return autores;
    }
}
