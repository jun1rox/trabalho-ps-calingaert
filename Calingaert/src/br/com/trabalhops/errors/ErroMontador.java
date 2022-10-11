package br.com.trabalhops.errors;

/**
 *
 * @author gsutavo
 */
public class ErroMontador {
    
    private int linha;
    private TipoErro tipo;

    public ErroMontador(int linha, TipoErro erro) {
        this.linha = linha;
        this.tipo = erro;
    }

    public int getLinha() {
        return linha;
    }

    public void setLinha(int linha) {
        this.linha = linha;
    }

    public TipoErro getTipo() {
        return tipo;
    }

    public void setTipo(TipoErro erro) {
        this.tipo = erro;
    }
    
}
