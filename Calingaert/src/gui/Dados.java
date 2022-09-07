/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import trabalho_ps.Memoria;

/**
 *
 * @author junio
 */
public class Dados {
    // RECEBE UM ARRAY DE STRING E MANDA COM O INDICE PARA A TABELA
    
    private Memoria memoria;
    
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
