package br.edu.ifes.si.tpa.utils;

import java.io.File;
import java.util.List;

import br.edu.ifes.si.tpa.model.design.Digrafo;
import br.edu.ifes.si.tpa.model.design.In;
import br.edu.ifes.si.tpa.model.domain.AlgoritmoDFSDigrafo;

public class TodosOsCaminhos {

    public static void main(String[] args) {
        In in = new In(new File("_dados/Digrafo1.txt").getAbsolutePath());
        Digrafo digrafo = new Digrafo(in);
        List<List<Integer>> caminhos = TodosOsCaminhos.run(digrafo, 13, 1);

        if (caminhos.size() > 0) {
            System.out.printf("tem %d caminhos\n",caminhos.size());
            for(List<Integer> caminho: caminhos){
                System.out.println(caminhoToString(caminho));
            }
        }
    }

    public static String caminhoToString(List<Integer> caminho) {
        String resultado = String.valueOf(caminho.get(0));
        caminho.remove(0);
        for (Integer i : caminho) {
            resultado += " => " + String.format("%2d", i);
        }
        return resultado;
    }

    public static List<List<Integer>> run(Digrafo digrafo, int origem, int destino) {
        AlgoritmoDFSDigrafo dfs = new AlgoritmoDFSDigrafo(digrafo, origem, destino);
        return dfs.getCaminhos();
    }

}