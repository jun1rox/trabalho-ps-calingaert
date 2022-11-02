package br.com.trabalhops.ligador;

import br.com.trabalhops.montador.Simbolo;

/**
 *
 * @author aluno
 */
public class TabelaUso extends Tabela {
    private boolean sinal; //positivo -> true & negativo -> false

    public TabelaUso() {
    }

    public TabelaUso(Simbolo simbolo, int endereco, boolean sinal) {
        super(simbolo, endereco);
        this.sinal = sinal;
    }

    public boolean isSinal() {
        return sinal;
    }

    public void setSinal(boolean sinal) {
        this.sinal = sinal;
    }
    
}
