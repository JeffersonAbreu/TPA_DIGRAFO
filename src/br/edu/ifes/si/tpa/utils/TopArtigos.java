package br.edu.ifes.si.tpa.utils;

import java.io.File;

import br.edu.ifes.si.tpa.model.design.Digrafo;
import br.edu.ifes.si.tpa.model.design.In;
import br.edu.ifes.si.tpa.model.design.Vertice;
import br.edu.ifes.si.tpa.model.domain.Aresta;

public class TopArtigos {
     public static void main(String[] args) {
          In in = new In(new File("_dados/Digrafo1.txt").getAbsolutePath());
          Digrafo digrafo = new Digrafo(in);
          System.out.println("Top Artigos");
          System.out.println(TopArtigos.run(digrafo));
     }

     public static String run(Digrafo digrafo) {
          String retorno = "";
          int[] citacoes_cada_artigo = new int[digrafo.nVertices()];
          for (Vertice artigo : digrafo.getListVertices()) {
               for ( Aresta aresta : artigo.getAllAdj()) {
                    int referencia = aresta.getV2();
                    citacoes_cada_artigo[referencia]++;
               }
          } 

          for (Vertice artigo : digrafo.getListVertices()) {
               retorno += artigo.getID() + ": " + citacoes_cada_artigo[artigo.getID()] + "\n";
          }
          return retorno;
     }
}
