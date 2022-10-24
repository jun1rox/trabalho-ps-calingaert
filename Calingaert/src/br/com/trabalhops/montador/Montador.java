package br.com.trabalhops.montador;

import br.com.trabalhops.errors.ErroMontador;
import br.com.trabalhops.errors.TipoErro;
import br.com.trabalhops.montador.Instrucao.ModosEnderecamento;
import static br.com.trabalhops.montador.Instrucao.ModosEnderecamento.*;
import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
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
    private final List<ErroMontador> erros = new ArrayList<>();
    private final List<String> resultado = new ArrayList<>();
    private int posInstrucao;
    private int contadorLinha = 0;
    private int posicao = 0;
    private BufferedReader buffRead;
    private String linha;
    private Matcher findNumeros;
    
    private BufferedWriter buffWriterObj, buffWriterLst;
    
    private final String saidaObj = "./src/arquivos/file.obj";
    private final String saidaLst = "./src/arquivos/file.lst";

    public boolean monta() throws IOException {
        buffRead = new BufferedReader(new FileReader(this.caminho));
        
        this.buffWriterObj = new BufferedWriter(new FileWriter(saidaObj));
        this.buffWriterLst = new BufferedWriter(new FileWriter(saidaLst));
        
        linha = buffRead.readLine();
        while (buffRead.ready()) {
            this.linhaPrimeiraPassagem();
        }
        buffRead.close();
        
        for (Simbolo s : simbolos) {
            if(!s.isDefinido()) erros.add(new ErroMontador(contadorLinha, TipoErro.SIMBOLO_NAO_DEFINIDO));
            System.out.println(s.getRotulo());
            System.out.println(s.getEndereco());
            System.out.println(s.isDefinido());  
        }
             
        // segunda passagem
        if(this.erros.isEmpty()) {
            this.contadorLinha = 0;
            this.posicao = 0;
            buffRead = new BufferedReader(new FileReader(this.caminho));
            linha = buffRead.readLine();
            while (buffRead.ready()) {
                this.linhaSegundaPassagem();
            }
            buffRead.close();
            
            buffWriterObj.append("* file.obj\n");
            for (String string : this.resultado) {
                buffWriterObj.append(string+"\n");
            }
            buffWriterObj.append("TAMANHO\n" + resultado.size());
        }
                
        if(erros.isEmpty()) {
            buffWriterLst.append("CÓDIGO COMPILADO COM SUCESSO.");
        } else {
            buffWriterLst.append("LINHA INSTRUÇÃO\n");
            for (ErroMontador e: erros) {
                buffWriterLst.append(e.getLinha() + " " + e.getTipo().getDescricao() + "\n");
            }
        }
        
        this.buffWriterObj.flush();
        this.buffWriterLst.flush();
        this.buffWriterObj.close();        
        this.buffWriterLst.close();
        
        return erros.isEmpty();
    }

    private void linhaPrimeiraPassagem() throws IOException {
        List<String> palavras = trataLinha(linha);
        if ("".equals(palavras.get(0))) return;
        
        Instrucao ins = instrucoes.getInstrucao(palavras.get(0));
        if (ins != null) {
            // primeira palavra é uma instrução
            posInstrucao = 0;
        } else {
            ins = instrucoes.getInstrucao(palavras.get(1));
            if (ins == null) { // testa se a segunda palavra é uma instrução
                erros.add(new ErroMontador(contadorLinha, TipoErro.INSTRUCAO_INVALIDA));
                linha = buffRead.readLine();
                return;
            }
            
            if (!existeSimbolo(palavras.get(0))) {
                // testa se a primeira palavra é um simbolo ja definido
                simbolos.add(new Simbolo(palavras.get(0), posicao, true));
            } else {
                // se já existe, verifica se o simbolo está indefinido
                boolean flag_indefinido = false;
                for(Simbolo s: simbolos) {
                    if(s.getRotulo().equals(palavras.get(0)) && !s.isDefinido()) {
                        flag_indefinido = true;
                        s.setDefinido(true);
                        s.setEndereco(posicao);
                    }
                }
                if(!flag_indefinido) {
                    // ERRO: simbolo redefinido (2x no lado esquerdo)
                    linha = buffRead.readLine();
                    erros.add(new ErroMontador(contadorLinha, TipoErro.SIMBOLO_REDEFINIDO));
                    return;
                }
            }

            posInstrucao = 1;
        }
        
        int numOperandos = ins.getNumOperandos();
        int operandoAmount = palavras.size() - numOperandos;

        if (operandoAmount > posInstrucao + 1) {
            // ERRO: INSTRUÇÃO TEM OPERANDOS DEMAIS
            erros.add(new ErroMontador(contadorLinha, TipoErro.INSTRUCAO_INVALIDA));
            System.out.println(1);
            linha = buffRead.readLine();
            return;
        }
        if (operandoAmount < posInstrucao + 1) {
            // ERRO: INSTRUÇÃO TEM POUCOS OPERANDOS
            erros.add(new ErroMontador(contadorLinha, TipoErro.INSTRUCAO_INVALIDA));
            linha = buffRead.readLine();
            return;
        }
        if(ins.isPseudoInstrucao()) {
            switch(ins.getNome()) {
                case "SPACE" -> {
                    posicao += 3; // pula 3 palavras
                }
                case "CONST" -> {
                    posicao += 1;
                    String op_1 = palavras.get(posInstrucao + 1);
                    ModosEnderecamento modo_op1 = verificaNumero(op_1);
                    if(modo_op1 != DIRETO) { // ACEITAR APENAS NÚMEROS SEM CARACTERES
                        // ERRO: OPERANDO INVÁLIDO
                        erros.add(new ErroMontador(contadorLinha, TipoErro.INSTRUCAO_INVALIDA));                    }
                }
                case "END" -> {
                    // ??
                }
                case "START" -> {
                    // variavel posição inicial?
                    // POSICAO = OPERANDO?
                }
                /* TABELAS DEFINIÇÃO/USO
                case "EXTR" -> {
                    
                }
                case "EXTDEF" -> {
                    
                }
                */
                default -> {
                    erros.add(new ErroMontador(contadorLinha, TipoErro.INSTRUCAO_INVALIDA));
                }
            }
            linha = buffRead.readLine();
            return;
        }
        if(numOperandos > 0) {
            posicao += 1;
            String op_1 = palavras.get(posInstrucao + 1);
            ModosEnderecamento modo_op1;
            if (!trataSimbolo(op_1)) {
                modo_op1 = verificaNumero(op_1);
            } else {
                modo_op1 = DIRETO;
            }
            
            // FALTA: erro valor fora do limite
            
            if(ins.getNome().equals("COPY")) {
                posicao +=1;
                String op_2 = palavras.get(posInstrucao + 2);
                ModosEnderecamento modo_op2;
                if (!trataSimbolo(op_2)) {
                    modo_op2 = verificaNumero(op_2);
                } else {
                    modo_op2 = DIRETO;
                }
                
                if(instrucoes.trataCopy(modo_op1, modo_op2) == -1) {
                    // ERRO: OPERANDOS/COMBINAÇÃO DE OPERANDOS INVÁLIDA
                    erros.add(new ErroMontador(contadorLinha, TipoErro.SIMBOLO_REDEFINIDO));
                    linha = buffRead.readLine();
                    return;
                }       
            } else {
                if (!ins.getModosPermitidos().contains(modo_op1)) {
                    // ERRO: MODO DE ENDEREÇAMENTO NÃO PERMITIDO
                    erros.add(new ErroMontador(contadorLinha, TipoErro.INSTRUCAO_INVALIDA));
                    linha = buffRead.readLine();
                    return;
                }
            }
        }
        
        posicao +=1;
        linha = buffRead.readLine();
    }

     private void linhaSegundaPassagem() throws IOException {
        List<String> palavras = trataLinha(linha);
        if ("".equals(palavras.get(0))) return;
        
        Instrucao ins = instrucoes.getInstrucao(palavras.get(0));
        if (ins != null) {
            posInstrucao = 0;
        } else {
            ins = instrucoes.getInstrucao(palavras.get(1));
            posInstrucao = 1;
        }
        
        int numOperandos = ins.getNumOperandos();
        if(ins.isPseudoInstrucao()) {
            switch(ins.getNome()) {
                case "SPACE" -> {
                    for(int i = 0; i < 3; ++i) {
                        resultado.add("000");
                    }
                }
                case "CONST" -> {
                    //bota direto pq ja verificou o operando
                    resultado.add(preencheZeros(palavras.get(posInstrucao + 1)));
                }
                case "END" -> {
                    // nao faz nada aqui eu acho
                }
                case "START" -> {
                    // tmb nao faz nada eu acho
                }
                /* TABELAS DEFINIÇÃO/USO
                case "EXTR" -> {
                    
                }
                case "EXTDEF" -> {
                    
                }
                */
            }
        } else {
            if(numOperandos > 0) {
                boolean flagIsSimbolo_1 = false;
                posicao += 1;
                String op_1 = palavras.get(posInstrucao + 1);
                ModosEnderecamento modo_op1;
                if (!trataSimbolo(op_1)) {
                    modo_op1 = verificaNumero(op_1);
                } else {
                    flagIsSimbolo_1 = true;
                    modo_op1 = DIRETO;
                }

                if(ins.getNome().equals("COPY")) {
                    // se for COPY pega o segundo operando (apenas COPY tem dois operandos)
                    // e 6 combinações de operandos
                    posicao +=1;
                    String op_2 = palavras.get(posInstrucao + 2);
                    ModosEnderecamento modo_op2;
                    if (!existeSimbolo(op_2)) {
                        modo_op2 = verificaNumero(op_2);
                    } else {
                        modo_op2 = DIRETO; // simbolo é um endereço direto
                    }
                    // escreve o código do copy conforme os operandos
                    resultado.add(preencheZeros(instrucoes.trataCopy(modo_op1, modo_op2).toString()));
                } else {
                    // NÃO É O COPY SEGUIR NORMALMENTE
                    // escreve o código da instrução conforme o modo de endereçamento do operando
                    resultado.add(preencheZeros(ins.getCodigoMontado(modo_op1)));
                    if(flagIsSimbolo_1) {
                        Simbolo s = getSimbolo(op_1);
                        // escreve o endereço do operando simbolo
                        resultado.add(preencheZeros(s.getEnderecoString()));
                    } else {
                        // FALTA: se for imediato não vai para o mapa de relocação
                        // escreve o valor do operando
                        resultado.add(preencheZeros(getValorNumero(op_1)));
                    }
                }
            } else {
                // não tem operandos pega o primeiro (e unico) código
                resultado.add(preencheZeros(ins.getCodigos().get(0).toString()));
            }
        }
              
        linha = buffRead.readLine();
     }
     
    private boolean existeSimbolo(String nome) {
        for (Simbolo s : this.simbolos) {
            if (s.getRotulo().equals(nome)) {
                return true;
            }
        }
        return false;
    }
    
    private Simbolo getSimbolo(String nome) {
        for (Simbolo s : this.simbolos) {
            if (s.getRotulo().equals(nome)) {
                return s;
            }
        }
        return null;
    }
    
    public String getValorNumero(String operando) {
        // remove caracteres especiais dos numeros
        // "#65" -> "65"     "87,I" -> "87"
        Pattern prefixo = Pattern.compile("[@#](\\d+)");
        Matcher findPrefixo = prefixo.matcher(operando);
        if(findPrefixo.find()) operando = findPrefixo.group(1);
        
        Pattern sufixo = Pattern.compile("(\\d+),");
        Matcher findSufixo = sufixo.matcher(operando);
        if(findSufixo.find()) operando = findSufixo.group(0);
        
        return operando;
    }
    
    public String preencheZeros(String palavra) {
        // adiciona zero a esquerda nas palavras (até 3 caracteres)
        // "8" -> "008"     "10" -> "010"
        String zeros = "";
        if(palavra.length() == 1) {
            zeros = "00";
        } else if(palavra.length() == 2) {
            zeros = "0";
        }
        return zeros + palavra;
    }
    
    public List<String> trataLinha(String linha) {
        this.contadorLinha += 1;
        linha = linha.split("\\*")[0];
        
        if (linha.length() > 80) {
            erros.add(new ErroMontador(contadorLinha, TipoErro.LINHA_LONGA));
        }
        linha = linha.replaceAll("\\s+", " ");
        linha = linha.trim();
        return Arrays.asList(linha.split(" "));
    }
    
    public boolean trataSimbolo(String palavra) {
        // verifica a sintaxe de nome para simbolos
        // e busca se o simbolo ja existe na tabela
        findNumeros = numeros.matcher(palavra);
        if (!findNumeros.find()) {       // VERIFICAR NOME DO SIMBOLO (PODE TER NUMEROS)
            if (!existeSimbolo(palavra)) {
                simbolos.add(new Simbolo(palavra, 0));
            } else {
                // MAPA DE RELOCAÇÃO? O SIMBOLO JA TA DEFINIDO
            }
            return true;
        } else {
            return false;
        }
    }
            
    private ModosEnderecamento verificaNumero(String palavra) {
        // retorna o modo de endereçamento de um operando
        // "64" -> direto
        // "#89" -> imediato
        // "97,I" -> indireto
        Pattern letras = Pattern.compile("[a-zA-Z]", Pattern.CASE_INSENSITIVE);
        Pattern caracteres = Pattern.compile("^[a-zA-Z0-9!$%^&*()_+\\-=\\[\\]{};':\"\\\\|.<>\\/?]*$", Pattern.CASE_INSENSITIVE);
        Pattern indireto = Pattern.compile("^\\d+,I");
        Pattern imediato = Pattern.compile("^#\\d+");

        Matcher findIndireto = imediato.matcher(palavra);
        Matcher findImediato = indireto.matcher(palavra);
        Matcher findLetras = letras.matcher(palavra);
        Matcher findCaracteres = caracteres.matcher(palavra);
        if (!findLetras.find() || !findCaracteres.find()) {
            return DIRETO;
        }
        else if (findIndireto.find()) {
            return INDIRETO;
        }
        else if (findImediato.find()) {
            return IMEDIATO;
        }
        return null;
    }

}
