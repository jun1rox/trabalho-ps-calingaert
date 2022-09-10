package trabalho_ps;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author gusta
 */
public class FuncoesUteis {

    public static int binaryStringToInt(String binario) {
        if (binario.charAt(0) == '1') // numero negativo -> aplicar complemento de 2
        // inverte bits -> soma 1 -> poe sinal negativo
        {
            return -(Integer.parseInt(inverteBits(binario), 2) + 1);
        } else {
            return Integer.parseInt(binario, 2);
        }
    }

    public static String intToBinaryString(int numero, int tamanho) {
        String saida = Integer.toBinaryString(numero); // essa função já trata o complemento de dois
        tamanho -= saida.length();
        if (tamanho > 0) {
            // preenche com 0s à esquerda
            char[] zeros = new char[tamanho];
            for (int i = 0; i < tamanho; i++) {
                zeros[i] = '0';
            }
            return (new String(zeros)).concat(saida);
        } else if (tamanho == 0) {
            return saida;
        } else {
            return saida.substring(0 - tamanho);
        }
    }

//    public static String registradorDisplay(Registrador reg) {;
//        Integer valor = FuncoesUteis.binaryStringToInt(reg.get());
//        return valor.toString();
//
//    }

    private static String inverteBits(String binario) {
        char[] inverso = new char[binario.length()];

        for (int i = 0; i < inverso.length; i++) {
            inverso[i] = (binario.charAt(i) == '1') ? '0' : '1'; // se for 0, vira 1, e vice-versa
        }
        return new String(inverso);
    }

    public static void imprimeInterfaceFinal(javax.swing.JTextPane painel, String caminho, String[] memoria) throws FileNotFoundException, IOException {
        ArrayList<String> binarios = new ArrayList<>();
        BufferedReader buffRead = new BufferedReader(new FileReader(caminho));
        String linha = buffRead.readLine();
        while (linha != null) {
            for (String s : linha.split(" ")) {
                int val = Integer.parseInt(s);
                binarios.add(intToBinaryString(val, 16));
            }
            painel.setText(painel.getText() + "\n" + linha);
            linha = buffRead.readLine();
        }
        painel.setText(painel.getText() + "\n\n" + String.join("\n", binarios));

    }
}