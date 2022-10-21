package br.com.trabalhops.montador;
  
import static br.com.trabalhops.montador.Instrucao.ModosEnderecamento.*;
import java.util.Arrays;

/**
 *
 * @author junio
 */
public class Instrucoes {
    
    
    public final Instrucao ADD = new Instrucao("ADD", 1, Arrays.asList(2, 34, 130), Arrays.asList(DIRETO, INDIRETO, IMEDIATO));
    public final Instrucao BR = new Instrucao("BR", 1, Arrays.asList(0, 34), Arrays.asList(DIRETO, INDIRETO));
    public final Instrucao BRNEG = new Instrucao("BRNEG", 1, Arrays.asList(5, 34), Arrays.asList(DIRETO, INDIRETO));
    public final Instrucao BRPOS = new Instrucao("BRPOS", 1, Arrays.asList(1, 34), Arrays.asList(DIRETO, INDIRETO));
    public final Instrucao BRZERO = new Instrucao("BRZERO", 1, Arrays.asList(4, 34), Arrays.asList(DIRETO, INDIRETO));
    public final Instrucao CALL = new Instrucao("CALL", 1, Arrays.asList(15, 34), Arrays.asList(DIRETO, INDIRETO));
    public final Instrucao DIVIDE = new Instrucao("DIVIDE", 1, Arrays.asList(10, 34, 130), Arrays.asList(DIRETO, INDIRETO, IMEDIATO));
    public final Instrucao LOAD = new Instrucao("LOAD", 1, Arrays.asList(3, 34, 130), Arrays.asList(DIRETO, INDIRETO, IMEDIATO));
    public final Instrucao MULT = new Instrucao("MULT", 1, Arrays.asList(14, 34, 130), Arrays.asList(DIRETO, INDIRETO, IMEDIATO));
    public final Instrucao READ = new Instrucao("READ", 1, Arrays.asList(12, 34), Arrays.asList(DIRETO, INDIRETO));
    public final Instrucao RET = new Instrucao("RET", 0, Arrays.asList(16));
    public final Instrucao STOP = new Instrucao("STOP", 0, Arrays.asList(11));
    public final Instrucao STORE = new Instrucao("STORE", 1, Arrays.asList(7, 34), Arrays.asList(DIRETO, INDIRETO));
    public final Instrucao SUB = new Instrucao("SUB", 1, Arrays.asList(6, 34, 130), Arrays.asList(DIRETO, INDIRETO, IMEDIATO));
    public final Instrucao WRITE = new Instrucao("WRITE", 1, Arrays.asList(8, 34, 130), Arrays.asList(DIRETO, INDIRETO, IMEDIATO));
    
    public final Instrucao CONST = new Instrucao("CONST", 1, Arrays.asList(-1), Arrays.asList(DIRETO), true);
    public final Instrucao END = new Instrucao("END", 0, Arrays.asList(-1), Arrays.asList(DIRETO), true);
    public final Instrucao EXTDEF = new Instrucao("EXTDEF", 1, Arrays.asList(-1), Arrays.asList(DIRETO), true);
    public final Instrucao EXTR = new Instrucao("EXTR", 0, Arrays.asList(-1), Arrays.asList(DIRETO), true);
    public final Instrucao SPACE = new Instrucao("SPACE", 0, Arrays.asList(-1), Arrays.asList(DIRETO), true);
    public final Instrucao START = new Instrucao("START", 1, Arrays.asList(-1), Arrays.asList(DIRETO),true);
    
    public Instrucao getInstrucao(String nome) {
        switch(nome.toLowerCase()) {
            case "add" -> {
                return ADD;
            }
            
            case "br" -> {
                return BR;
            }
            
            case "brneg" -> {
                return BRNEG;
            }
            
            case "brpos" -> {
                return BRPOS;
            }
            
            case "brzero" -> {
                return BRZERO;
            }
            
            case "call" -> {
                return CALL;
            }
            
            case "divide" -> {
                return DIVIDE;
            }
            
            case "load" -> {
                return LOAD;
            }
            
            case "mult" -> {
                return MULT;
            }
            
            case "read" -> {
                return READ;
            }
            
            case "ret" -> {
                return RET;
            }
            
            case "stop" -> {
                return STOP;
            }
            
            case "store" -> {
                return STORE;
            }
            
            case "sub" -> {
                return SUB;
            }
            
            case "write" -> {
                return WRITE;
            }
            
            case "const" -> {
                return CONST;
            }
            
            case "end" -> {
                return END;
            }
            
            case "extdef" -> {
                return EXTDEF;
            }
            
            case "extr" -> {
                return EXTR;
            }
            
            case "space" -> {
                return SPACE;
            }
            
            case "start" -> {
                return START;
            }
            
            default -> {
                return null;
            }
        }
    }
}
