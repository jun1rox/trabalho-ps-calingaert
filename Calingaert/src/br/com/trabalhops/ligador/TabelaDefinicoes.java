package br.com.trabalhops.ligador;

/**
 *
 * @author aluno
 */
public class TabelaDefinicoes {
    private Modo modo;
    private String rotulo;
    private String endereco;
    
    public TabelaDefinicoes() {
    }

    public TabelaDefinicoes(String rotulo, String endereco, Modo modo) {
        this.modo = modo;
        this.rotulo = rotulo;
        this.endereco = endereco;
    }

    public Modo getModo() {
        return modo;
    }

    public String getRotulo() {
        return rotulo;
    }

    public void setRotulo(String rotulo) {
        this.rotulo = rotulo;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setModo(Modo modo) {
        this.modo = modo;
    }
}
