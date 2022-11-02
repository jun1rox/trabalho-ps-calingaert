package br.com.trabalhops.ligador;

/**
 *
 * @author aluno
 */
public class TabelaSimbolosGlobais {
    private Modo modo;

    public TabelaSimbolosGlobais() {
    }

    public TabelaSimbolosGlobais(Modo modo) {
        this.modo = modo;
    }

    public Modo getModo() {
        return modo;
    }

    public void setModo(Modo modo) {
        this.modo = modo;
    }
    
}
