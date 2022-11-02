package br.com.trabalhops.ligador;

import br.com.trabalhops.montador.Simbolo;

/**
 *
 * @author aluno
 */
public class Tabela {
    private Simbolo simbolo;
    private int endereco;

    public Tabela() {
    }

    public Tabela(Simbolo simbolo, int endereco) {
        this.simbolo = simbolo;
        this.endereco = endereco;
    }

    public Simbolo getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(Simbolo simbolo) {
        this.simbolo = simbolo;
    }

    public int getEndereco() {
        return endereco;
    }

    public void setEndereco(int endereco) {
        this.endereco = endereco;
    }
    
}
