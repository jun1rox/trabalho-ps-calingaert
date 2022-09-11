package br.com.trabalhops.maquinavirtual;

import br.com.trabalhops.utils.Utils;
import br.com.trabalhops.gui.Tela;

/**
 *
 * @author Franco
 */
public class Instrucoes implements InstrucoesInterface {

    private final Utils utils = new Utils();

    @Override
    public int getInstrucao(Tela tela) {
        Memoria memoria = tela.memoria;
        Registradores registradores = tela.registradores;
        int op1;
        int pos_instrucao = registradores.getPC();
        int instrucao = utils.binaryStringToInt(memoria.getMemoriaPosicao(pos_instrucao));
        registradores.setRI(instrucao);
        switch (instrucao) {

            // BR
            case 0 -> { // direto
                int val_1 = memoria.getMemoriaPosicaoInt(pos_instrucao + 1);
                registradores.setPC(val_1);
                return 1;
            }

            case 32 -> { // indireto
                int val_1 = memoria.getMemoriaDiretaInt(pos_instrucao + 1);
                registradores.setPC(val_1);
                return 1;
            }

            // BRPOS
            case 1 -> { // direto
                int val_1 = memoria.getMemoriaPosicaoInt(pos_instrucao + 1);
                if (registradores.getACC() > 0) {
                    registradores.setPC(val_1);
                } else {
                    registradores.addPC(2);
                }
                return 1;
            }

            case 33 -> { // indireto
                int val_1 = memoria.getMemoriaDiretaInt(pos_instrucao + 1);
                if (registradores.getACC() > 0) {
                    registradores.setPC(val_1);
                } else {
                    registradores.addPC(2);
                }
                return 1;
            }

            // ADD
            case 2 -> { // direto
                int val_1 = memoria.getMemoriaDiretaInt(pos_instrucao + 1);
                int acc = registradores.getACC();
                registradores.setACC(acc + val_1);
                registradores.addPC(2);
                return 1;
            }

            case 34 -> { // indireto
                int val_1 = memoria.getMemoriaIndiretaInt(pos_instrucao + 1);
                int acc = registradores.getACC();
                registradores.setACC(acc + val_1);
                registradores.addPC(2);
                return 1;
            }

            case 130 -> { // imediato
                int val_1 = memoria.getMemoriaImediataInt(pos_instrucao + 1);
                int acc = registradores.getACC();
                registradores.setACC(acc + val_1);
                registradores.addPC(2);
                return 1;
            }

            // LOAD
            case 3 -> { // direto
                int val_1 = memoria.getMemoriaDiretaInt(pos_instrucao + 1);
                registradores.setACC(val_1);
                registradores.addPC(2);
                return 1;
            }

            case 35 -> { // indireto
                int val_1 = memoria.getMemoriaIndiretaInt(pos_instrucao + 1);
                registradores.setACC(val_1);
                registradores.addPC(2);
                return 1;
            }

            case 131 -> { // imediato
                int val_1 = memoria.getMemoriaImediataInt(pos_instrucao + 1);
                registradores.setACC(val_1);
                registradores.addPC(2);
                return 1;
            }

            // BRZERO
            case 4 -> { // direto
                int val_1 = memoria.getMemoriaPosicaoInt(pos_instrucao + 1);
                if (registradores.getACC() == 0) {
                    registradores.setPC(val_1);
                } else {
                    registradores.addPC(2);
                }
                return 1;
            }

            case 36 -> { // indireto
                int val_1 = memoria.getMemoriaDiretaInt(pos_instrucao + 1);
                if (registradores.getACC() == 0) {
                    registradores.setPC(val_1);
                } else {
                    registradores.addPC(2);
                }
                return 1;
            }

            // BRNEG
            case 5 -> { // direto
                op1 = memoria.getMemoriaPosicaoInt(pos_instrucao + 1);
                if (registradores.getACC() == 0) {
                    registradores.setPC(op1);
                }
                registradores.addPC(2);
                return 1;
            }

            case 37 -> { // indireto
                op1 = memoria.getMemoriaDiretaInt(pos_instrucao + 1);
                if (registradores.getACC() == 0) {
                    registradores.setPC(op1);
                }
                registradores.addPC(2);
                return 1;
            }

            // SUB
            case 6 -> { // direto
                int val_1 = memoria.getMemoriaDiretaInt(pos_instrucao + 1);
                int acc = registradores.getACC();
                registradores.setACC(acc - val_1);
                registradores.addPC(2);
                return 1;
            }

            case 38 -> { // indireto
                int val_1 = memoria.getMemoriaIndiretaInt(pos_instrucao + 1);
                int acc = registradores.getACC();
                registradores.setACC(acc - val_1);
                registradores.addPC(2);
                return 1;
            }

            case 134 -> { // imediato
                int val_1 = memoria.getMemoriaImediataInt(pos_instrucao + 1);
                int acc = registradores.getACC();
                registradores.setACC(acc - val_1);
                registradores.addPC(2);
                return 1;
            }

            // STORE
            case 7 -> { // direto
                op1 = memoria.getMemoriaPosicaoInt(pos_instrucao + 1);
                int acc = registradores.getACC();
                memoria.setMemoriaPosicao(op1, utils.intToBinaryString(acc, 16));
                registradores.addPC(2);
                return 1;
            }

            case 39 -> { // indireto
                op1 = memoria.getMemoriaDiretaInt(pos_instrucao + 1);
                int acc = registradores.getACC();
                memoria.setMemoriaPosicao(op1, utils.intToBinaryString(acc, 16));
                registradores.addPC(2);
                return 1;
            }

            // WRITE
            case 8 -> { // direto
                op1 = memoria.getMemoriaDiretaInt(pos_instrucao + 1);
                tela.escreveConsole(op1);
                registradores.addPC(2);
                return 1;
            }

            case 40 -> { // indireto
                op1 = memoria.getMemoriaIndiretaInt(pos_instrucao + 1);
                tela.escreveConsole(op1);
                registradores.addPC(2);
                return 1;
            }

            case 136 -> { // imediato
                op1 = memoria.getMemoriaImediataInt(pos_instrucao + 1);
                tela.escreveConsole(op1);
                registradores.addPC(2);
                return 1;
            }

            // RET
            case 9 -> {
                registradores.setPC(registradores.getSP());
                return 1;
            }

            // DIVIDE
            case 10 -> { // direto
                int val_1 = memoria.getMemoriaDiretaInt(pos_instrucao + 1);
                int acc = registradores.getACC();
                registradores.setACC(acc / val_1);
                registradores.addPC(2);
                return 1;
            }

            case 42 -> { // indireto
                int val_1 = memoria.getMemoriaIndiretaInt(pos_instrucao + 1);
                int acc = registradores.getACC();
                registradores.setACC(acc / val_1);
                registradores.addPC(2);
                return 1;
            }

            case 138 -> { // imediato
                int val_1 = memoria.getMemoriaImediataInt(pos_instrucao + 1);
                int acc = registradores.getACC();
                registradores.setACC(acc / val_1);
                registradores.addPC(2);
                return 1;
            }

            // STOP
            case 11 -> {
                return 0;
            }

            // READ
            case 12 -> { // direto
                op1 = memoria.getMemoriaPosicaoInt(pos_instrucao + 1);
                int input = tela.lerTeclado();
                memoria.setMemoriaPosicao(op1, utils.intToBinaryString(input, 16));
                registradores.addPC(2);
                return 1;
            }

            case 44 -> { // indireto
                op1 = memoria.getMemoriaDiretaInt(pos_instrucao + 1);
                int input = tela.lerTeclado();
                memoria.setMemoriaPosicao(op1, utils.intToBinaryString(input, 16));
                registradores.addPC(2);
                return 1;
            }

            // COPY
            case 13 -> { // op1 direto op2 direto
                op1 = memoria.getMemoriaPosicaoInt(pos_instrucao + 1);
                int val_2 = memoria.getMemoriaDiretaInt(pos_instrucao + 2);
                memoria.setMemoriaPosicao(op1, utils.intToBinaryString(val_2, 16));
                registradores.addPC(3);
                return 1;
            }

            case 77 -> { // op1 direto op2 indireto
                op1 = memoria.getMemoriaPosicaoInt(pos_instrucao + 1);
                int val_2 = memoria.getMemoriaIndiretaInt(pos_instrucao + 2);
                memoria.setMemoriaPosicao(op1, utils.intToBinaryString(val_2, 16));
                registradores.addPC(3);
                return 1;
            }

            case 141 -> { // op1 direto op2 imediato
                op1 = memoria.getMemoriaPosicaoInt(pos_instrucao + 1);
                int val_2 = memoria.getMemoriaImediataInt(pos_instrucao + 2);
                memoria.setMemoriaPosicao(op1, utils.intToBinaryString(val_2, 16));
                registradores.addPC(3);
                return 1;
            }

            case 45 -> { // op1 indireto op2 direto
                op1 = memoria.getMemoriaDiretaInt(pos_instrucao + 1);
                int val_2 = memoria.getMemoriaDiretaInt(pos_instrucao + 2);
                memoria.setMemoriaPosicao(op1, utils.intToBinaryString(val_2, 16));
                registradores.addPC(3);
                return 1;
            }

            case 109 -> { // op1 indireto op2 indireto
                op1 = memoria.getMemoriaDiretaInt(pos_instrucao + 1);
                int val_2 = memoria.getMemoriaIndiretaInt(pos_instrucao + 2);
                memoria.setMemoriaPosicao(op1, utils.intToBinaryString(val_2, 16));
                registradores.addPC(3);
                return 1;
            }

            case 173 -> { // op1 indireto op2 imediato
                op1 = memoria.getMemoriaDiretaInt(pos_instrucao + 1);
                int val_2 = memoria.getMemoriaImediataInt(pos_instrucao + 2);
                memoria.setMemoriaPosicao(op1, utils.intToBinaryString(val_2, 16));
                registradores.addPC(3);
                return 1;
            }

            // MULT
            case 14 -> { // direto
                int val_1 = memoria.getMemoriaDiretaInt(pos_instrucao + 1);
                int acc = registradores.getACC();
                registradores.setACC(acc * val_1);
                registradores.addPC(2);
                return 1;
            }

            case 46 -> { // indireto
                int val_1 = memoria.getMemoriaIndiretaInt(pos_instrucao + 1);
                int acc = registradores.getACC();
                registradores.setACC(acc * val_1);
                registradores.addPC(2);
                return 1;
            }

            case 142 -> { // imediato
                int val_1 = memoria.getMemoriaImediataInt(pos_instrucao + 1);
                int acc = registradores.getACC();
                registradores.setACC(acc * val_1);
                registradores.addPC(2);
                return 1;
            }

            // CALL
            case 15 -> { // direto
                int val_1 = memoria.getMemoriaDiretaInt(pos_instrucao + 1);
                registradores.setSP(registradores.getPC());
                registradores.setPC(val_1);
                return 1;
            }

            case 47 -> { // indireto
                int val_1 = memoria.getMemoriaDiretaInt(pos_instrucao + 1);
                registradores.setSP(registradores.getPC());
                registradores.setPC(val_1);
                return 1;
            }

            default -> {
                return 0;
            }
        }
    }
}
