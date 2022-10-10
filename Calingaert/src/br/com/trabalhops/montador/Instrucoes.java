package br.com.trabalhops.montador;
  
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author junio
 */
public class Instrucoes {
    private final Map<String,int[]> mapa;

    public Instrucoes() {
        this.mapa = new HashMap<>();
        mapa.put( "ADD", new int[] {2,1});
        mapa.put( "BR", new int[] {0,1});
        mapa.put( "BRNEG", new int[] {5,1});
        mapa.put( "BRPOS", new int[] {1,1});
        mapa.put( "BRZERO", new int[] {4,1});
        mapa.put( "CALL", new int[] {15,1});
        mapa.put( "COPY", new int[] {13,2});
        mapa.put( "DIVIDE", new int[] {10,1});
        mapa.put( "LOAD", new int[] {3,1});
        mapa.put( "MULT", new int[] {14,1});
        mapa.put( "READ", new int[] {12,1});
        mapa.put( "RET", new int[] {16,0});
        mapa.put( "STOP", new int[] {11,0});
        mapa.put( "STORE", new int[] {7,1});
        mapa.put( "SUB", new int[] {6,1});
        mapa.put( "WRITE", new int[] {8,1});
    }
    
    public boolean checkInstrucao(String instrucao) {
        instrucao = instrucao.toUpperCase();
        return mapa.get(instrucao) != null;
    }
    
    public int[] getInstrucao(String instrucao) { 
        instrucao = instrucao.toUpperCase();
        return mapa.get(instrucao);
    }
}
