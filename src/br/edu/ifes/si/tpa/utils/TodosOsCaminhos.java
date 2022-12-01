package br.edu.ifes.si.tpa.utils;

import java.io.File;

import br.edu.ifes.si.tpa.model.design.Digrafo;
import br.edu.ifes.si.tpa.model.design.In;

public class TodosOsCaminhos {
     public static void main(String[] args) {
          In in = new In(new File("_dados/Digrafo1.txt").getAbsolutePath());
          Digrafo digrafo = new Digrafo(in);
          System.out.println("Todos Os Caminhos");
          System.out.println(TodosOsCaminhos.run(digrafo, 13, 0));
     }

     public static String run(Digrafo digrafo, int origem, int destino) {
          String retorno = "";

          return retorno;
     }
}
