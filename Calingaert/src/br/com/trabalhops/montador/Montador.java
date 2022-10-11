package br.com.trabalhops.montador;

import br.com.trabalhops.errors.ErroMontador;
import br.com.trabalhops.errors.TipoErro;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author junio
 */
public class Montador {

    private final String caminho = "../teste.asm";
    private final Instrucoes instrucoes = new Instrucoes();
    private final List<Simbolo> simbolos = new ArrayList<>();
    private final Pattern numeros = Pattern.compile("\\d", Pattern.CASE_INSENSITIVE);
    private List<ErroMontador> erros = new ArrayList<>();
    private List<String> palavras;
    private int posInstrucao;
    private int contadorLinha = 0;
    private int posicao = 0;
    private BufferedReader buffRead;
    private String linha;
    private Matcher findNumeros;

    public Montador() {
    }

    public boolean monta() throws IOException {
        buffRead = new BufferedReader(new FileReader(this.caminho));
        linha = buffRead.readLine();
        while (buffRead.ready()) {
            this.leLinha();
        }
        return true;
    }

    private void leLinha() throws IOException {
        contadorLinha += 1;
        linha = linha.split("\\*")[0];

        if (linha.length() > 80) {
            erros.add(new ErroMontador(contadorLinha, TipoErro.LINHA_LONGA));
            buffRead.readLine();
            return;
        }
        linha = linha.replaceAll("\\s+", " ");
        linha = linha.trim();
        palavras = Arrays.asList(linha.split(" "));
        buffRead.readLine();
        if ("".equals(palavras.get(0))) {
            return;
        }

        if (instrucoes.checkInstrucao(palavras.get(0))) {
            posInstrucao = 0;
        } else {
            if (palavras.size() < 2) {
                // testa se existe segunda palavra na linha 
                // ERROR, TOO FEW ARGUMENTS (SO TEM UMA LABEL NA LINHA)
                // RETURN
            }
            if (!instrucoes.checkInstrucao(palavras.get(1))) {
                // ERROR, THERE'S NO INSTRUCTION IN THE LINE
                // RETURN
            }
            if (validaSimbolo(palavras.get(0))) {
                simbolos.add(new Simbolo(palavras.get(0), posicao, true));
            } else {
                erros.add(new ErroMontador(contadorLinha, TipoErro.SIMBOLO_REDEFINIDO));
                System.out.println(contadorLinha + " - Erro: simbolo redefinido " + palavras.get(0));
            }

            posInstrucao = 1;
        }

        posicao += 1; // JA PASSOU A LABEL PODE SOMAR A POSICAO (POSICAO DA INSTRUÇÃO)

        int instrucaoEOpcodes[] = instrucoes.getInstrucao(palavras.get(posInstrucao));
        if (instrucaoEOpcodes == null) {
            return;
        }

        int operandoAmount = palavras.size() - instrucaoEOpcodes[1];

        if (operandoAmount > posInstrucao + 1) {
            // ERROR, TOO MANY ARGUMENTS
            return;
        }
        if (operandoAmount < posInstrucao + 1) {
            // ERROR, TOO FEW ARGUMENTS
            return;
        }
        for (int i = posInstrucao + 1; i < palavras.size(); i++) {
            findNumeros = numeros.matcher(palavras.get(i)); // VERIFICA SE É UM NOME VALIDO PRA SIMBOLO
            if (!findNumeros.find()) {
                if (validaSimbolo(palavras.get(i))) {
                    // ATUALIZAR POSICOES !!!!!!!!!!!!!
                    simbolos.add(new Simbolo(palavras.get(i), posicao));
                } else {
                    // MAPA DE RELOCAÇÃO? O SIMBOLO JA TA DEFINIDO
                }
            } else {
                if (!verificaNumero(palavras.get(i))) { // VE SE É UM NUMERO
                    System.out.println("Erro: numero");
                    // NUMERO NO FORMATO CORRETO?
                    // NUMERO GRANDE DEMAIS?
                } else {
                    // VERIFICAR QUE TIPO DE NUMERO É
                    // SE A OPERAÇÃO PERMITE ex: existe ADD direto imediato?
                }
            }

        }
        buffRead.readLine();
    }

    private boolean validaSimbolo(String nome) {
        for (Simbolo s : this.simbolos) {
            if (s.getRotulo().equals(nome)) {
                return false;
            }
        }
        return true;
    }

    private boolean verificaNumero(String palavra) {
        Pattern letras = Pattern.compile("[a-zA-Z]", Pattern.CASE_INSENSITIVE);
        Pattern caracteres = Pattern.compile("^[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]*$", Pattern.CASE_INSENSITIVE);
        Matcher findLetras = letras.matcher(palavra);
        Matcher findCaracteres = caracteres.matcher(palavra);
        if (findLetras.find() || findCaracteres.find()) {
            return false;
        }
        if (palavra.startsWith("#") || palavra.startsWith("@")) {
            return true;
        }
        return false;
    }

}
