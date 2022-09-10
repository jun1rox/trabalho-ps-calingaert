package trabalho_ps;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import static trabalho_ps.FuncoesUteis.intToBinaryString;

/**
 *
 * @author gusta
 */
public class Memoria {

    private String[] memoria;
    private final int TAMANHO_PILHA = 5;
    private final int TAMANHO_MEMORIA = 512;
    private int ponteiroPilha;
    private final int INICIO_INS_DADOS = 7;

    /**
     *
     * @param registradores
     */
    public Memoria() {
        this.memoria = new String[this.TAMANHO_MEMORIA];
        this.ponteiroPilha = 3;

        Arrays.fill(this.memoria, "0000000000000000");
        this.memoria[1] = "0000000000000101"; //segunda posiçaõ da memória (5 = tamanho da pilha)

    }

    public String[] getMemoria() { //ve a memoria inteira
        return this.memoria;
    }

    //Pode ser usado para setar o primeiro valor de PC
    public int getINICIO_INS_DADOS() {
        return this.INICIO_INS_DADOS;
    }

    //escolhe uma posição da memória(int) e atualiza ela com uma novo valor(string)
    public void setMemoriaPosicao(int local, String valor) {
        this.memoria[local] = valor;
    }

    //Retorna uma posição da memória
    public String getMemoriaPosicao(int local) {
        return this.memoria[local];
    }

    // Retorna uma posição da memória, convertido para inteiro
    public int getMemoriaPosicaoInt(int local) {
        return FuncoesUteis.binaryStringToInt(this.getMemoriaPosicao(local));
    }

    public int getTAMANHO_MEMORIA() {
        return TAMANHO_MEMORIA;
    }

    public void carregaPrograma(String caminho) throws FileNotFoundException, IOException {
        BufferedReader buffRead = new BufferedReader(new FileReader(caminho));
        String linha = buffRead.readLine();
        int i = this.INICIO_INS_DADOS;
        while (linha != null) {
            for (String s : linha.split(" ")) {
                int val = Integer.parseInt(s);
                this.memoria[i] = intToBinaryString(val, 16);
                i++;
            }
            linha = buffRead.readLine();
        }
    }
    
    public int getMemoriaDiretaInt(int local) {
        int address = this.getMemoriaPosicaoInt(local);
        return this.getMemoriaPosicaoInt(address);
    }
    
    public int getMemoriaIndiretaInt(int local) {
        int address = this.getMemoriaPosicaoInt(local);
        address = this.getMemoriaPosicaoInt(address);
        return this.getMemoriaPosicaoInt(address);
    }
    
    public int getMemoriaImediataInt(int local) {
        return this.getMemoriaPosicaoInt(local);
    }
}
