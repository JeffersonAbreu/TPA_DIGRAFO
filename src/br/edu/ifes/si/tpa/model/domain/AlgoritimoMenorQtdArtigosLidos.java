package br.edu.ifes.si.tpa.model.domain;

import java.io.File;

import br.edu.ifes.si.tpa.model.design.Digrafo;
import br.edu.ifes.si.tpa.model.design.In;

public class AlgoritimoMenorQtdArtigosLidos {
     public static void main(String[] args) {
          In in = new In(new File("_dados/Digrafo1.txt").getAbsolutePath());
          Digrafo digrafo = new Digrafo(in);
     }
}
