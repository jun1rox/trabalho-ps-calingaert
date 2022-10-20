package br.com.trabalhops.montador;
  
import static br.com.trabalhops.montador.Instrucao.ModosEnderecamento.*;
import java.util.Arrays;

/**
 *
 * @author junio
 */
public enum Instrucoes {
    
    
    ADD(new Instrucao("ADD", 1, Arrays.asList(2, 34, 130), Arrays.asList(DIRETO, INDIRETO, IMEDIATO)));
    
    private final Instrucao instrucao;
    
    private Instrucoes(Instrucao instrucao) {
        this.instrucao = instrucao;
    }

    public Instrucao getInstrucao() {
        return instrucao;
    }
    
    public String getNome() {
        return this.instrucao.getNome();
    }
    
    public Instrucoes getInstrucao(String nome) {
        switch(nome) {
            case "add" -> {
                return ADD;
            }
            
            default -> {
                return null;
            }
        }
    }
}
