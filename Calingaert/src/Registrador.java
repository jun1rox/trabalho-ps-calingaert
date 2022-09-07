/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author gusta
 */
public class Registrador {
    private int posicao;
    private Memoria memoria;
    
    public Registrador(Memoria mem, int pos){
        this.posicao = pos;
        this.memoria = mem;
    }
    
    public void setRegistrador(String a) {
        this.memoria.setMemoriaPosicao(posicao,a);
     
    }
    
    public String getRegistrador() {
        return this.memoria.getMemoriaPosicao(posicao);
    }
    
    public int getRegistradorInt() {
        return this.memoria.getMemoriaPosicaoInt(posicao);
    }
    
    public void add(int numero) {
        int reg = FuncoesUteis.binaryStringToInt(this.getRegistrador());       // pega o valor do registrador e converte pra int
        this.setRegistrador(FuncoesUteis.intToBinaryString(reg + numero, 16)); // soma o valor com a entrada, converte pra string binario e seta o valor novo
    }
    
    public void add(String binario) {
        this.add(FuncoesUteis.binaryStringToInt(binario));
    }
}
