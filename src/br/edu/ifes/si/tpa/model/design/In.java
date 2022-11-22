package br.edu.ifes.si.tpa.model.design;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class In {
    private BufferedReader in = null;
    private String nomeFile;

    public In(String pathToFile) {
        if (pathToFile == null)
            throw new NullPointerException("argument is null");
        try {
            nomeFile = pathToFile;
            in = new BufferedReader(new FileReader(pathToFile));
        } catch (IOException e) {
            System.out.println("Error while reading a file " + pathToFile);
        }
    }

    /**
     * Reads and returns the next line in this input stream.
     *
     * @return the next line in this input stream; <tt>null</tt> if no such line
     */
    public String readLine() {
        String line;
        try {
            line = in.readLine();
        } catch (IOException e) {
            line = null;
        }
        return line;
    }

    public String getPathName() {
        return nomeFile;
    }

    /**
     * Returns true if this input stream exists.
     *
     * @return <tt>true</tt> if this input stream exists; <tt>false</tt> otherwise
     */
    public boolean exists() {
        boolean exists = in != null;
        if (!exists) {
            System.err.println("Arquivo não existe!\n" + nomeFile);
        }
        return exists;
    }

    /**
     * Closes this input stream.
     */
    public void close() {
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
