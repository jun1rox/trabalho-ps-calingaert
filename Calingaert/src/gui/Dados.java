package gui;

import trabalho_ps.Memoria;

/**
 *
 * @author junio
 */
public class Dados {
    // RECEBE UM ARRAY DE STRING E MANDA COM O INDICE PARA A TABELA
    
    public Dados() {
        
    }
    
    public String[][] criaMatriz(Memoria memoria) {
        
        String[][] dados = new String[512][2];
        
        int tamanho = memoria.getTAMANHO_MEMORIA();
        
        for (int posicao = 0; posicao < tamanho; posicao++) {
            // getposi
            
            dados[posicao][0] = Integer.toString(posicao);
            dados[posicao][1] = memoria.getMemoriaPosicao(posicao);
        }
        
        return dados;
    }   
}
