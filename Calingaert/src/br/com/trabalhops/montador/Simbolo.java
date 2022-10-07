package br.com.trabalhops.montador;

/**
 *
 * @author junio
 */
public class Simbolo {
    
    private String rotulo;
    private int endereco;
    private boolean definido;
    
    public Simbolo(String rotulo, int endereco, boolean definido) {
        this(rotulo, endereco);
        this.definido = definido;
    }
    
    public Simbolo(String rotulo, int endereco) {
        this.rotulo = rotulo;
        this.endereco = endereco;
        this.definido = false;
    }

    public void setRotulo(String rotulo) {
        this.rotulo = rotulo;
    }

    public void setEndereco(int endereco) {
        this.endereco = endereco;
    }

    public void setDefinido(boolean definido) {
        this.definido = definido;
    }

    public String getRotulo() {
        return rotulo;
    }

    public int getEndereco() {
        return endereco;
    }

    public boolean isDefinido() {
        return definido;
    } 
}
