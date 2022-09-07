package trabalho_ps;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
    private final int QUANT_REGISTRADORES = 6;

    /**
     *
     * @param registradores
     */
    public Memoria(ArrayList<Registrador> registradores) {
        this.memoria = new String[this.TAMANHO_MEMORIA];
        this.ponteiroPilha = 3;

        this.memoria[0] = "0000000000000000"; //primeira posição da memória
        this.memoria[1] = "0000000000001010"; //segunda posiçaõ da memória (10 = tamanho da pilha)
        for (int i = 2;
                i < this.TAMANHO_PILHA + 2;
                i++) {
            this.memoria[i] = "0000000000000000";
        }
        for (int i = this.TAMANHO_PILHA + 2; // Área do programa
                i < this.TAMANHO_MEMORIA - this.QUANT_REGISTRADORES;
                i++) {
            this.memoria[i] = "0000000000000000";
        }
        
        for (int i = this.TAMANHO_MEMORIA - this.QUANT_REGISTRADORES, c = 0;
                i < this.TAMANHO_MEMORIA;
                i++, c++) {
            registradores.add(new Registrador(this, i));
            this.memoria[i] = "0000000000000000";
        }
          
        registradores.get(0).setRegistrador("0000000000001100"); // PC
        registradores.get(1).setRegistrador("0000000000000010"); // SP
        //2 ACC
        //3 OPM
        //4 IR
        //5 IM

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
}
