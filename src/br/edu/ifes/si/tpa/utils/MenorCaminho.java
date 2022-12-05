package br.edu.ifes.si.tpa.utils;

import java.io.File;
import java.util.List;

import br.edu.ifes.si.tpa.model.design.Digrafo;
import br.edu.ifes.si.tpa.model.design.In;
import br.edu.ifes.si.tpa.model.domain.AlgoritmoBFSDigrafo;

public class MenorCaminho {
    public static void main(String[] args) {
        In in = new In(new File("_dados/Digrafo1.txt").getAbsolutePath());
        Digrafo digrafo = new Digrafo(in);
        int origem = 13;
        int destino = 1;
        List<Integer> caminho = run(digrafo, origem, destino);
        if (caminho.size() > 0) {
            System.out.printf("%d para %d:  \n", origem, destino);
            System.out.println(caminhoToString(caminho));
        } else {
            System.out.printf("%d para %d (-):  não conectado\n", origem, destino);
        }
    }

    public static List<Integer> run(Digrafo digrafo, int origem, int destino) {
        AlgoritmoBFSDigrafo algoritmoBFS = new AlgoritmoBFSDigrafo(digrafo, origem);
        return algoritmoBFS.getCaminhoPara(destino);
    }

    public static String caminhoToString(List<Integer> caminho) {
        String resultado = String.valueOf(caminho.get(0));
        caminho.remove(0);
        for (Integer i : caminho) {
            resultado += " => " + String.format("%2d", i);
        }
        return resultado;
    }
}
