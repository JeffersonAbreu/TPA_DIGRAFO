package br.edu.ifes.si.tpa.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.edu.ifes.si.tpa.model.design.Autor;
import br.edu.ifes.si.tpa.model.design.Digrafo;
import br.edu.ifes.si.tpa.model.design.In;

public class TopArtigos {
     public static void main(String[] args) {
          In in = new In(new File("_dados/Digrafo1.txt").getAbsolutePath());
          Digrafo digrafo = new Digrafo(in);
          System.out.println("Top Artigos");
          System.out.println(TopArtigos.run(digrafo));
     }

     public static String run(Digrafo digrafo) {
          String retorno = "";
          // Getting Collection of values from HashMap
          Collection<Autor> values = digrafo.getAutores().values();

          // Creating an ArrayList of values
          List<Autor> autores = new ArrayList<>(values);

          autores.sort(Comparator.comparing(Autor::getTotalObras)); // ordena
          Collections.reverse(autores); // inverte a ordem

          for (Autor autor : autores) {
               retorno += autor.getID() + ": " + autor.getTotalObras() + "\n";
          }
          return retorno;
     }
}
