package trabalho_ps;

import static trabalho_ps.FuncoesUteis.binaryStringToInt;
import static trabalho_ps.FuncoesUteis.intToBinaryString;

/**
 *
 * @author Franco
 */
public class Instrucoes {

    public static int getInstrucao(Registradores registradores, Memoria memoria) {
        int op1;
        int pos_instrucao = registradores.getPC();
        int instrucao = binaryStringToInt(memoria.getMemoriaPosicao(pos_instrucao));
        registradores.setRI(instrucao);
        switch (instrucao) {
            case 0 -> { // BR
                int val_1 = memoria.getMemoriaPosicaoInt(pos_instrucao + 1);
                registradores.setPC(val_1);
                return 1;
            }

            case 1 -> { // BRPOS
                int val_1 = memoria.getMemoriaPosicaoInt(pos_instrucao + 1);
                if (registradores.getACC() > 0) {
                    registradores.setPC(val_1);
                } else {
                    registradores.addPC(2);
                }
                return 1;
            }

            case 2 -> { // ADD
                int val_1 = memoria.getMemoriaDiretaInt(pos_instrucao + 1);
                int acc = registradores.getACC();
                registradores.setACC(acc + val_1);
                registradores.addPC(2);
                return 1;
            }

            case 3 -> { // LOAD
                int val_1 = memoria.getMemoriaDiretaInt(pos_instrucao + 1);
                registradores.setACC(val_1);
                registradores.addPC(2);
                return 1;
            }

            case 4 -> { // BRZERO
                int val_1 = memoria.getMemoriaPosicaoInt(pos_instrucao + 1);
                if (registradores.getACC() == 0) {
                    registradores.setPC(val_1);
                } else {
                    registradores.addPC(2);
                }
                return 1;
            }

            case 5 -> { // BRNEG
                op1 = memoria.getMemoriaPosicaoInt(pos_instrucao + 1);
                if (registradores.getACC() == 0) {
                    registradores.setPC(op1);
                }
                registradores.addPC(2);
                return 1;
            }

            case 6 -> { // SUB
                int val_1 = memoria.getMemoriaDiretaInt(pos_instrucao + 1);
                int acc = registradores.getACC();
                registradores.setACC(acc - val_1);
                registradores.addPC(2);
                return 1;
            }

            case 7 -> { // STORE
                op1 = memoria.getMemoriaPosicaoInt(pos_instrucao + 1);
                int acc = registradores.getACC();
                memoria.setMemoriaPosicao(op1, intToBinaryString(acc, 16));
                registradores.addPC(2);
                return 1;
            }

            case 8 -> { // WRITE
                System.out.println(memoria.getMemoriaDiretaInt(pos_instrucao + 1));
                registradores.addPC(2);
                return 1;
            }

            case 9 -> { // RET
                registradores.setPC(registradores.getSP());
                return 1;
            }

            case 10 -> { // DIVIDE
                int val_1 = memoria.getMemoriaDiretaInt(pos_instrucao + 1);
                int acc = registradores.getACC();
                registradores.setACC(acc / val_1);
                registradores.addPC(2);
                return 1;
            }

            case 11 -> { // STOP
                return 0;
            }

            case 12 -> { // READ
                // LER DO TECLADO
                registradores.addPC(2);
                return 1;
            }

            case 13 -> { // COPY 
                op1 = binaryStringToInt(memoria.getMemoriaPosicao(pos_instrucao + 1));
                int val_2 = memoria.getMemoriaDiretaInt(pos_instrucao + 2);
                memoria.setMemoriaPosicao(op1, intToBinaryString(val_2, 16));
                registradores.addPC(3);
                return 1;
            }

            case 14 -> { // MULT
                int val_1 = memoria.getMemoriaDiretaInt(pos_instrucao + 1);
                int acc = registradores.getACC();
                registradores.setACC(acc * val_1);
                registradores.addPC(2);
                return 1;
            }

            case 15 -> { // CALL
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
