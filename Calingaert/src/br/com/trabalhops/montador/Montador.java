package br.com.trabalhops.montador;

import java.awt.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 *
 * @author junio
 */
public class Montador {
    
    private String caminho;
    private Instrucoes instrucoes;
    
    public Montador() {
        this.instrucoes = new Instrucoes();
        this.caminho = "../teste.asm";
    }
    
    public boolean monta() throws IOException {
        BufferedReader buffRead = new BufferedReader(new FileReader(caminho));
        String linha = buffRead.readLine();
        int i = 0;
        
        ArrayList<Simbolo> simbolos = new ArrayList();
        
        while (linha != null) {
            if (linha.length() > 80) {
                System.out.println("Ta muito grande, bb");
            } else {
                linha = linha.replaceAll("\\s+", " ");
                linha = linha.trim();
                for (String s : linha.split(" ")) {
                    System.out.print(s);
                    System.out.println(instrucoes.checkInstrucao(s));
                    
                }
            }
            linha = buffRead.readLine();
        }
        return true;
    }
    
}
