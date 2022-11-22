package br.edu.ifes.si.tpa.model.design;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
//alterar
public class In {
    public static Digrafo lerDigrafo(String pathToFile) { // atualizado

        String path = pathToFile;
        BufferedReader in = null;
        Digrafo digrafo = null;
        try {
            in = new BufferedReader(new FileReader(path));
            int vertices = Integer.parseInt(in.readLine());
            int arestas = Integer.parseInt(in.readLine());

            digrafo = new Digrafo(vertices);
            for (int i = 0; i < vertices; i++) {
                StringTokenizer st = new StringTokenizer(in.readLine(), " ");
                String vertice = st.nextToken().trim(); // verticeInicial
                int donoDoVertice = Integer.parseInt(st.nextToken().trim()); // verticeFinal
                // System.out.println("Na linha " + i + 3 + " : " + vertice + " - " + donoDoVertice);
                digrafo.addVertice(vertice, donoDoVertice);
            }

            // System.out.println("\nArestas: " + arestas);
            for (int i = 0; i < arestas; i++) {
                StringTokenizer st = new StringTokenizer(in.readLine(), " ");
                int de = Integer.parseInt(st.nextToken().trim()); // arestaInicial
                int para = Integer.parseInt(st.nextToken().trim()); // arestaFinal
                // System.out.println(i + 1 + " : " + de + " - " + para);
                digrafo.addAresta(de, para);
            }
        } catch (Exception e) {
            System.out.println("Error while reading a file.");
        } finally {
            // fechar o arquivo
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                    Logger.getLogger(In.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return digrafo;
    }
}
