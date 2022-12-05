package br.edu.ifes.si.tpa.model.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.edu.ifes.si.tpa.model.design.Aresta;
import br.edu.ifes.si.tpa.model.design.Digrafo;

public class AlgoritmoDFSDigrafo {

     private boolean[] marcado; // marcado[v1] = existe um caminho do vértice origem vo->v1?
     private int[] arestaPara; // arestaPara[v1] = última aresta no menor caminho vértice origem vo->v1
     private final int vo;
     private boolean[] noCaminho;
     private Pilha<Integer> caminho;
     private int numeroDeCaminhos;

     // guardar todos os caminhos
     private List <List<Integer>> caminhos = new ArrayList<>();

     public AlgoritmoDFSDigrafo(Digrafo G, int vo, int vd) {
          this.vo = vo;
          arestaPara = new int[G.nVertices()];
          marcado = new boolean[G.nVertices()];
          noCaminho = new boolean[G.nVertices()];
          caminho = new Pilha<Integer>();
          dfs2(G, vo, vd);
     }

     public int numeroDeCaminhos() {
          return numeroDeCaminhos;
     }

     private void dfs2(Digrafo G, int v, int vd) {
          caminho.empilha(v);
          noCaminho[v] = true;

          // encontrado caminho de v para vd (vértice destino)
          if (v == vd) {
               addCaminho();
               //imprimeCaminhoAtual();
               numeroDeCaminhos++;
          }
          // considerar todos os vizinhos que continuariam o caminho
          else {
               for (Aresta a : G.getVertice(v).getAllAdj()) {
                    int x = a.getV2();
                    if (!noCaminho[x])
                         dfs2(G, x, vd);
               }
          }
          // feita a exploração de v, então o remove do caminho
          caminho.desempilha();
          noCaminho[v] = false;
     }

     private void addCaminho() {
          List<Integer> lista = new ArrayList<>();
          for (int i : caminho) {
               lista.add(i);
          }
          Collections.reverse(lista);
          caminhos.add(lista);
     }

     public boolean temCaminhoPara(int v) {
          return marcado[v];
     }

     public Iterable<Integer> caminhoPara(int v) {
          if (!temCaminhoPara(v))
               return null;
          Pilha<Integer> caminho = new Pilha<Integer>();
          for (int x = v; x != vo; x = arestaPara[x])
               caminho.empilha(x);
          caminho.empilha(vo);
          return caminho;
     }


     public List<List<Integer>> getCaminhos(){
          return caminhos;
     }
}
