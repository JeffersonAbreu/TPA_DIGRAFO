package br.edu.ifes.si.tpa.model.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.edu.ifes.si.tpa.model.design.Digrafo;

public class AlgoritmoBFSDigrafo {

    private static final int INFINITY = Integer.MAX_VALUE;
    private boolean[] marcado; // marcado[v1] = existe um caminho do vértice origem vo->v1?
    private int[] arestaPara; // arestaPara[v1] = última aresta no menor caminho vértice origem vo->v1
    private int[] distanciaPara; // distanciaPara[v1] = tamanho do menor caminho vértice origem vo->v1

    /**
     * Verifica o menor caminho de um vértice origem vo e todos os demais vértices
     * do dígrafo
     * 
     * @param G  o dígrafo
     * @param vo o vértice origem
     */
    public AlgoritmoBFSDigrafo(Digrafo G, int vo) {
        marcado = new boolean[G.nVertices()];
        distanciaPara = new int[G.nVertices()];
        arestaPara = new int[G.nVertices()];
        for (int v = 0; v < G.nVertices(); v++) {
            distanciaPara[v] = INFINITY;
        }
        bfs(G, vo);
    }

    /**
     * Método algoritmoBFS para um vértice origem
     * 
     * @param G  o dígrafo
     * @param vo o vértice origem
     */
    private void bfs(Digrafo G, int vo) {
        Fila<Integer> f = new Fila<Integer>();
        f.enfileira(vo);
        marcado[vo] = true;
        distanciaPara[vo] = 0;
        while (!f.isEmpty()) {
            int v = f.desenfileira();
            for (Aresta a : G.getVertice(v).getAllAdj()) {
                int x = a.getV2();
                if (!marcado[x]) {
                    arestaPara[x] = v;
                    distanciaPara[x] = distanciaPara[v] + 1;
                    marcado[x] = true;
                    f.enfileira(x);
                }
            }
        }
    }

    /**
     * Existe um caminho direcionado do vértice atual para o vértice v
     * 
     * @param v o vértice
     * @return verdadeiro se existir um caminho direcionado, ou falso, caso
     *         contrário
     */
    public boolean temCaminhoPara(int v) {
        return marcado[v];
    }

    /**
     * Retorna o número de arestas no menor caminho do vértice origem v
     * 
     * @param v o vértice
     * @return o número de arestas no menor caminho
     */
    public int distanciaPara(int v) {
        return distanciaPara[v];
    }

    /**
     * Retorna um caminho do vértice origem para o vértice v ou null se não existir
     * caminho
     * 
     * @param v o vértice
     * @return a sequência de vértices no menor caminho, como iterable
     */
    public Iterable<Integer> caminhoPara(int v) {
        if (!temCaminhoPara(v)) {
            return null;
        }
        Pilha<Integer> caminho = new Pilha<Integer>();
        int x;
        for (x = v; distanciaPara[x] != 0; x = arestaPara[x]) {
            caminho.empilha(x);
        }
        caminho.empilha(x);
        return caminho;
    }

    public List<Integer> getCaminhoPara(int v) {
        List<Integer> lista = new ArrayList<>();
        if (caminhoPara(v) != null) {
            for (Integer i : caminhoPara(v)) {
                lista.add(i);
            }
        }
        return lista;
    }
}