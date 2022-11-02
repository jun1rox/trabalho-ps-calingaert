package br.com.trabalhops.ligador;

/**
 *
 * @author aluno
 */
public class TabelaDefinicoes {
    private Modo modo;

    public TabelaDefinicoes() {
    }

    public TabelaDefinicoes(Modo modo) {
        this.modo = modo;
    }

    public Modo getModo() {
        return modo;
    }

    public void setModo(Modo modo) {
        this.modo = modo;
    }
}
