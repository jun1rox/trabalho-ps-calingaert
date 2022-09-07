package trabalho_ps;

import static trabalho_ps.FuncoesUteis.binaryStringToInt;

/**
 *
 * @author gusta
 */
public class Registrador {

    private String valor;

    public Registrador() {
        this.valor = "0000000000000000";
    }

    public Registrador(String valor) {
        this.valor = valor;
    }

    public void set(String a) {
        this.valor = a;
    }

    public String get() {
        return this.valor;
    }

    public int getValorInt() {
        return binaryStringToInt(this.valor);
    }

    public void add(int numero) {
        int reg = FuncoesUteis.binaryStringToInt(this.get());       // pega o valor do registrador e converte pra int
        this.set(FuncoesUteis.intToBinaryString(reg + numero, 16)); // soma o valor com a entrada, converte pra string binario e seta o valor novo
    }

    public void add(String binario) {
        this.add(FuncoesUteis.binaryStringToInt(binario));
    }
}
