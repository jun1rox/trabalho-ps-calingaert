package br.com.trabalhops.montador;

import java.awt.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author junio
 */
public class Montador {

    private String caminho;
    private Instrucoes instrucoes;
    private ArrayList<Simbolo> simbolos;

    public Montador() {
        this.instrucoes = new Instrucoes();
        this.caminho = "../teste.asm";
        this.simbolos = new ArrayList();
    }

    public boolean validaSimbolo(String nome) {
        for (Simbolo s : this.simbolos) {
            if (s.getRotulo().equals(nome)) {
                return false;
            }
        }
        return true;
    }

    public boolean verificaNumero(String palavra) {
        Pattern letras = Pattern.compile("[a-zA-Z]", Pattern.CASE_INSENSITIVE);
        Pattern caracteres = Pattern.compile("^[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]*$", Pattern.CASE_INSENSITIVE);
        Matcher findLetras = letras.matcher(palavra);
        Matcher findCaracteres = caracteres.matcher(palavra);
        if (findLetras.find()) {
            return false; // HEXA???
        } else {
            if (!findCaracteres.find()) {
                return true;
            } else {
                if (palavra.startsWith("#")) {
                    return true;
                } else if (palavra.startsWith("@")) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }

    public boolean monta() throws IOException {
        BufferedReader buffRead = new BufferedReader(new FileReader(caminho));
        String linha = buffRead.readLine();
        int posInstrucao;
        int contadorLinha = 0;
        int posicao = 0;
        String[] palavras;
        Pattern numeros = Pattern.compile("\\d", Pattern.CASE_INSENSITIVE);
        Matcher findNumeros;

        while (linha != null) {
            contadorLinha += 1;
            linha = linha.split("\\*")[0];

            if (linha.length() > 80) {
                System.out.println("Ta muito grande, bb");
            } else {
                linha = linha.replaceAll("\\s+", " ");
                linha = linha.trim();
                palavras = linha.split(" ");
                linha = buffRead.readLine();
                if ("".equals(palavras[0])) continue;

                if (instrucoes.checkInstrucao(palavras[0])) {
                    posInstrucao = 0;
                } else {
                    if (palavras.length < 2) {
                        // testa se existe segunda palavra na linha 
                        // ERROR, TOO FEW ARGUMENTS (SO TEM UMA LABEL NA LINHA)
                        // RETURN
                    }
                    if (!instrucoes.checkInstrucao(palavras[1])) {
                        // ERROR, THERE'S NO INSTRUCTION IN THE LINE
                        // RETURN
                    }
                    if (validaSimbolo(palavras[0])) {
                        simbolos.add(new Simbolo(palavras[0], posicao, true));
                    } else {
                        System.out.println(contadorLinha + " - Erro: simbolo redefinido " + palavras[0]);
                    }

                    posInstrucao = 1;
                }

                posicao += 1; // JA PASSOU A LABEL PODE SOMAR A POSICAO (POSICAO DA INSTRUÇÃO)
                
                
                // 
                int instrucao_e_opcodes[] = instrucoes.getInstrucao(palavras[posInstrucao]);
                if (instrucao_e_opcodes == null) continue;
                
                int operandoAmount = palavras.length - instrucao_e_opcodes[1];

                if (operandoAmount > posInstrucao + 1) {
                    // ERROR, TOO MANY ARGUMENTS
                } else if (operandoAmount < posInstrucao + 1) {
                    // ERROR, TOO FEW ARGUMENTS
                } else {
                    for(int i = posInstrucao + 1; i < palavras.length; i++) {
                        findNumeros = numeros.matcher(palavras[i]); // VERIFICA SE É UM NOME VALIDO PRA SIMBOLO
                        if(!findNumeros.find()) { 
                            if (validaSimbolo(palavras[i])) {
                                // ATUALIZAR POSICOES !!!!!!!!!!!!!
                                simbolos.add(new Simbolo(palavras[i], posicao));
                            } else {
                                // MAPA DE RELOCAÇÃO? O SIMBOLO JA TA DEFINIDO
                            }
                        } else {
                            if(!verificaNumero(palavras[i])) { // VE SE É UM NUMERO
                                System.out.println("Erro: numero");
                                // NUMERO NO FORMATO CORRETO?
                                // NUMERO GRANDE DEMAIS?
                            } else {
                                // VERIFICAR QUE TIPO DE NUMERO É
                                // SE A OPERAÇÃO PERMITE ex: existe ADD direto imediato?
                            }
                        }
                        
                        
                    }
                }
            }
            linha = buffRead.readLine();
        }
        return true;
    }

}
