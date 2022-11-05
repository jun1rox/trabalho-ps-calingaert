package br.com.trabalhops.ligador;

/**
 *
 * @author aluno
 */
public class TabelaUso {
    public enum Sinal {
        POSITIVO, NEGATIVO;
    }
    
    private Sinal sinal; //positivo -> true & negativo -> false
    private String rotulo;
    private String posicao;
    
    public TabelaUso() {
    }

    public TabelaUso(String rotulo, String posicao, Sinal sinal) {
        this.sinal = sinal;
        this.posicao = posicao;
        this.rotulo = rotulo;
    }

    public String getRotulo() {
        return rotulo;
    }

    public void setRotulo(String rotulo) {
        this.rotulo = rotulo;
    }

    public String getPosicao() {
        return posicao;
    }

    public void setPosicao(String posicao) {
        this.posicao = posicao;
    }

    public Sinal isSinal() {
        return sinal;
    }

    public void setSinal(Sinal sinal) {
        this.sinal = sinal;
    }
    
}
