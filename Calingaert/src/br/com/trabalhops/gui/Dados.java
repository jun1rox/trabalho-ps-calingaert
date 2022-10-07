package br.com.trabalhops.gui;

import br.com.trabalhops.utils.Utils;
import br.com.trabalhops.maquinavirtual.Memoria;
import br.com.trabalhops.maquinavirtual.Registradores;

/**
 * RECEBE UM ARRAY DE STRING E MANDA COM O INDICE PARA A TABELA
 *
 * @author junio
 */
public class Dados {

    public boolean decimal;
    private final Utils utils = new Utils();
    
    public Dados() {
        this.decimal = false;
    }

    public void toggleDecimal() {
        decimal = !decimal;
    }
    
    public String[][] criaMatriz(Memoria memoria) {
        String[][] dados = new String[512][2];

        int tamanho = memoria.getTAMANHO_MEMORIA();

        for (int posicao = 0; posicao < tamanho; posicao++) {
            String valor = memoria.getMemoriaPosicao(posicao);
            dados[posicao][0] = Integer.toString(posicao);
            if (decimal){
                dados[posicao][1] = Integer.toString(utils.binaryStringToInt(valor));
            } else {
                dados[posicao][1] = valor;
            }
        }

        return dados;
    }

    public String[][] criaMatrizRegistradores(Registradores registradores) {
        String[][] dados = new String[6][2];

        dados[0][0] = "PC";
        dados[0][1] = Integer.toString(registradores.getPC());
        dados[1][0] = "ACC";
        dados[1][1] = Integer.toString(registradores.getACC());
        dados[2][0] = "SP";
        dados[2][1] = Integer.toString(registradores.getSP());
        dados[3][0] = "MOP";
        dados[3][1] = Integer.toString(registradores.getMOP());
        dados[4][0] = "RI";
        dados[4][1] = Integer.toString(registradores.getRI());
        dados[5][0] = "RE";
        dados[5][1] = Integer.toString(registradores.getRE());

        return dados;
    }
    
}
