package br.com.trabalhops.ligador;

import br.com.trabalhops.montador.Simbolo;

/**
 *
 * @author aluno
 */
public class TabelaUso extends Tabela {
    public enum Sinal {
        POSITIVO, NEGATIVO;
    }
    
    private Sinal sinal; //positivo -> true & negativo -> false

    public TabelaUso() {
    }

    public TabelaUso(Simbolo simbolo, int endereco, Sinal sinal) {
        super(simbolo, endereco);
        this.sinal = sinal;
    }

    public Sinal isSinal() {
        return sinal;
    }

    public void setSinal(Sinal sinal) {
        this.sinal = sinal;
    }
    
}
