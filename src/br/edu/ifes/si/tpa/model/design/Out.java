package br.edu.ifes.si.tpa.model.design;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Out {
    private final String pathToFile;
    private BufferedWriter out = null;

    public String getPathToFile() {
        return pathToFile;
    }

    public Out(String pathToFile) {
        this.pathToFile = pathToFile;
        if (pathToFile == null)
            throw new NullPointerException("argument is null");
        try {
            out = new BufferedWriter(new FileWriter(pathToFile));
        } catch (IOException e) {
            System.out.println("Error while reading a file " + pathToFile);
        }
    }

    /**
     * Reads and returns the next line in this input stream.
     *
     * @return the next line in this input stream; <tt>null</tt> if no such line
     */
    public void writeLine(String line) {
        try {
            out.write(line);
            out.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void newLine() {
        try {
            out.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
