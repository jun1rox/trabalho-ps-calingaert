/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.trabalhops.gui;
import java.util.TimerTask;

/**
 *
 * @author Franco
 */
public class TaskDepuracao extends TimerTask {

    private final Tela tela;
    private int status;
    
    public TaskDepuracao(Tela tela) {
        this.tela = tela;
        status = 1;
    }
    @Override
    public void run() {
        status = tela.instrucoes.getInstrucao(tela);
        if(status == 0) this.cancel();
        tela.preencheTabelaRegistradores(tela.registradores);
        tela.preencheTabela(tela.memoria);
    }
    
}
