package br.com.trabalhops.maquinavirtual;

import br.com.trabalhops.utils.Utils;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/**
 *
 * @author gusta
 */
public class Memoria {

    private final Utils utils = new Utils();

    private final String[] dados;
    private final String TAMANHO_PILHA = "0000000000000101";
    private final int TAMANHO_MEMORIA = 512;
    private final int INICIO_INS_DADOS = 7;

    public Memoria() {
        this.dados = new String[this.TAMANHO_MEMORIA];
//        this.ponteiroPilha = 3;

        Arrays.fill(this.dados, "0000000000000000");
        this.dados[1] = this.TAMANHO_PILHA; //segunda posição da memória
    }

    public String[] getDados() { //ve a dados inteira
        return this.dados;
    }

    //Pode ser usado para setar o primeiro valor de PC
    public int getINICIO_INS_DADOS() {
        return this.INICIO_INS_DADOS;
    }

    //escolhe uma posição da memória(int) e atualiza ela com uma novo valor(string)
    public void setMemoriaPosicao(int local, String valor) {
        this.dados[local] = valor;
    }

    //Retorna uma posição da memória
    public String getMemoriaPosicao(int local) {
        return this.dados[local];
    }

    // Retorna uma posição da memória, convertido para inteiro
    public int getMemoriaPosicaoInt(int local) {
        return utils.binaryStringToInt(this.getMemoriaPosicao(local));
    }

    public int getTAMANHO_MEMORIA() {
        return this.TAMANHO_MEMORIA;
    }

    public void carregaPrograma(String caminho) throws FileNotFoundException, IOException {
        BufferedReader buffRead = new BufferedReader(new FileReader(caminho));
        String linha = buffRead.readLine();
        int i = this.INICIO_INS_DADOS;
        while (linha != null) {
            for (String s : linha.split(" ")) {
                int val = Integer.parseInt(s);
                this.dados[i] = utils.intToBinaryString(val, 16);
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
