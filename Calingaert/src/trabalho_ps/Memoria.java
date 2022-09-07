package trabalho_ps;


import java.util.ArrayList;

/**
 *
 * @author gusta
 */
public class Memoria {
    private ArrayList<String> memoria;
    private final int TAMANHO_PILHA = 5;
    private final int TAMANHO_MEMORIA = 512;
    private int ponteiroPilha;
    private final int INICIO_INS_DADOS = 12;
    private final int QUANT_REGISTRADORES = 6;


    public Memoria(Registrador[] registradores) {
        this.memoria = new ArrayList();
        this.ponteiroPilha = 3;
        
        this.memoria.add("0000000000000000"); //primeira posição da memória
        this.memoria.add("0000000000001010"); //segunda posiçaõ da memória (10 = tamanho da pilha)
        for(int i = 2;
                i < this.TAMANHO_PILHA + 2;
                i++){
           this.memoria.add("Pilha");
        }
        for(int i = this.TAMANHO_PILHA + 2;
                i < this.TAMANHO_MEMORIA - this.QUANT_REGISTRADORES; 
                i++){
            this.memoria.add("-");
        }
        for(int i = this.TAMANHO_MEMORIA - this.QUANT_REGISTRADORES, c=0;
                i < this.TAMANHO_MEMORIA;
                i++, c++){
            registradores[c] = new Registrador(this,i);
        }
        
        this.memoria.add("0000000000001100");//PC
        this.memoria.add("0000000000000010"); //SP
        this.memoria.add("0000000000000000"); //ACC
        this.memoria.add("xx"); //OPM
        this.memoria.add("0000000000000000"); //IR
        this.memoria.add("0000000000000000");//IM
        
        
    }
    
    public ArrayList<String> getMemoria() { //ve a memoria inteira
        return memoria;
    }
    
    //Pode ser usado para setar o primeiro valor de PC
    public int getINICIO_INS_DADOS() {
        return this.INICIO_INS_DADOS;
    }

    //escolhe uma posição da memória(int) e atualiza ela com uma novo valor(string)
    public void setMemoriaPosicao(int local, String valor) {
        this.memoria.set(local, valor);
    }

    //Retorna uma posição da memória
    public String getMemoriaPosicao(int local) {
        return this.memoria.get(local);
    }
    
    // Retorna uma posição da memória, convertido para inteiro
    public int getMemoriaPosicaoInt(int local) {
        return FuncoesUteis.binaryStringToInt(this.getMemoriaPosicao(local));
    }

    public int getTAMANHO_MEMORIA() {
        return TAMANHO_MEMORIA;
    }
}