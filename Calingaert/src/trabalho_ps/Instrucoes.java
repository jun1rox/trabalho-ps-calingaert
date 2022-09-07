package trabalho_ps;

import static trabalho_ps.FuncoesUteis.binaryStringToInt;

/**
 *
 * @author Franco
 */
public class Instrucoes {
    
    public String getInstrucao(Registrador PC, Memoria memoria) {
        int op1, op2, op3;
        int pos_instrucao = PC.getValorInt();
        int instrucao = binaryStringToInt(memoria.getMemoriaPosicao(pos_instrucao));
        switch(instrucao) {
            case 13 -> { 
                op1 = binaryStringToInt(memoria.getMemoriaPosicao(pos_instrucao));
                op2 = binaryStringToInt(memoria.getMemoriaPosicao(pos_instrucao));
                PC.add(3);
                return "COPY " + op1 + " " + op2;
            }
            default -> {
                PC.add(2);
                return "UNDEFINED";
            }
        }
    }
}
