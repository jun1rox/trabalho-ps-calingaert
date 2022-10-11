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
        int posInstrucao;
        String[] palavras;
        
        ArrayList<Simbolo> simbolos = new ArrayList();
        
        while (linha != null) {
            linha = linha.split("\\*")[0];
            
            if (linha.length() > 80) {
                System.out.println("Ta muito grande, bb");
            } else {
                linha = linha.replaceAll("\\s+", " ");
                linha = linha.trim();
                palavras = linha.split(" ");
                
                if (instrucoes.checkInstrucao(palavras[0])) // se ele entrar nesse if é pq a primeira palavra é a instrucao
                {
                    posInstrucao = 0; 
                }
                else
                {
                    if (palavras.length < 2) // testa se existe segunda palavra na linha
                    {
                        // ERROR, TOO FEW ARGUMENTS (SO TEM UMA LABEL NA LINHA)
                        // RETURN
                    }
                    if (!instrucoes.checkInstrucao(palavras[1]))
                    {
                        // ERROR, THERE'S NO INSTRUCTION IN THE LINE
                        // RETURN
                    }

                    posInstrucao = 1;
                }
                // adendo, tem que fazer mais testes nos operandos
                int instrucao_e_opcodes[] = instrucoes.getInstrucao(palavras[posInstrucao]);
                int operandoAmount = palavras.length - instrucao_e_opcodes[1];

                if(operandoAmount > posInstrucao + 1)
                {
                    // ERROR, TOO MANY ARGUMENTS
                }
                else if (operandoAmount < posInstrucao + 1)
                {
                    // ERROR, TOO FEW ARGUMENTS
                }
                else
                {
                    // tudo certo na parte de operandos
                }
            }
            linha = buffRead.readLine();
        }
        return true;
    }
    
}
