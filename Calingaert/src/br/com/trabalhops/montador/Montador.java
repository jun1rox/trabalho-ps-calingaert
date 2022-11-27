package br.com.trabalhops.montador;

import br.com.trabalhops.errors.ErroMontador;
import br.com.trabalhops.errors.TipoErro;
import static br.com.trabalhops.ligador.Modo.*;
import br.com.trabalhops.ligador.TabelaDefinicoes;
import br.com.trabalhops.ligador.TabelaUso;
import static br.com.trabalhops.ligador.TabelaUso.Sinal.*;
import br.com.trabalhops.montador.Instrucao.ModosEnderecamento;
import static br.com.trabalhops.montador.Instrucao.ModosEnderecamento.*;
import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
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
    private final Instrucoes instrucoes = new Instrucoes();
    private final List<Simbolo> simbolos = new ArrayList<>();
    private final Pattern numeros = Pattern.compile("\\d", Pattern.CASE_INSENSITIVE);
    private final List<ErroMontador> erros = new ArrayList<>();
    private final List<String> resultado = new ArrayList<>();
    private final List<String> simbolosExternos = new ArrayList<>();
    private final List<TabelaUso> tabelaUso = new ArrayList<>();
    private final List<TabelaDefinicoes> tabelaDefinicoes = new ArrayList<>();
    private String mapa_relocacao = "";
    private int posInstrucao;
    private int contadorLinha = 0;
    private int posicao = 0;
    private BufferedReader buffRead;
    private String linha;
    private Matcher findNumeros;
    private boolean faltaEnd = true;

    private BufferedWriter buffWriterObj, buffWriterLst;

    public boolean monta(String caminho) throws IOException {
        reset();
        
        String caminhoSemExtensao = caminho.split(".asm")[0];
        String[] nomes = caminhoSemExtensao.split("/");
        String nome = nomes[nomes.length - 1];
        String saidaObj = "./src/arquivos/" + nome + ".obj";
        String saidaLst = "./src/arquivos/" + nome + ".lst";
        
        File objFile = new File(saidaObj);
        objFile.createNewFile();
        File lstFile = new File(saidaLst);
        lstFile.createNewFile();
        
        buffRead = new BufferedReader(new FileReader(caminho));
        this.buffWriterObj = new BufferedWriter(new FileWriter(saidaObj));
        this.buffWriterLst = new BufferedWriter(new FileWriter(saidaLst));

        linha = buffRead.readLine();
        while (linha != null && faltaEnd) {
            this.linhaPrimeiraPassagem();
        }
        buffRead.close();

        for (TabelaUso s : tabelaUso) {
            System.out.println(s.getRotulo());
            System.out.println(s.getPosicao());
        }
        
        for (Simbolo s : simbolos) {
            if (!s.isDefinido()) {
                erros.add(new ErroMontador(contadorLinha, TipoErro.SIMBOLO_NAO_DEFINIDO));
                System.out.println(s.getRotulo());
                
            }
        }
        
        for (TabelaDefinicoes s : tabelaDefinicoes) {
            System.out.println(s.getRotulo());
            System.out.println(s.getEndereco());
        }
        System.out.println("FIM");
        if (faltaEnd) {
            erros.add(new ErroMontador(contadorLinha, TipoErro.FALTA_END));
        }

        // segunda passagem
        if (this.erros.isEmpty()) {
            this.contadorLinha = 0;
            this.posicao = 0;
            buffRead = new BufferedReader(new FileReader(caminho));
            linha = buffRead.readLine();
            faltaEnd = true;
            while (linha != null && faltaEnd) {
                this.linhaSegundaPassagem();
            }
            buffRead.close();

            
            buffWriterObj.append("TAMANHO\n");
            buffWriterObj.append(this.resultado.size() + "\n");
            buffWriterObj.append("MAPA\n");
            buffWriterObj.append(mapa_relocacao + "\n");
            buffWriterObj.append("TABELA_USO\n");
            for (TabelaUso tab : this.tabelaUso) {
                buffWriterObj.append(tab.getRotulo() + " " + tab.getPosicao() + "\n");
            }
            buffWriterObj.append("***\n");
            buffWriterObj.append("TABELA_DEFINICAO\n");
            for (TabelaDefinicoes tab : this.tabelaDefinicoes) {
                buffWriterObj.append(tab.getRotulo() + " " + tab.getEndereco() + "\n");
            }
            buffWriterObj.append("***\n");
            for (String string : this.resultado) {
                buffWriterObj.append(string + "\n");
            }
        }

        if (erros.isEmpty()) {
            buffWriterLst.append("CÓDIGO COMPILADO COM SUCESSO.");
        } else {
            buffWriterLst.append("LINHA INSTRUÇÃO\n");
            for (ErroMontador e : erros) {
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
        if ("".equals(palavras.get(0))) {
            return;
        }

        Instrucao ins = instrucoes.getInstrucao(palavras.get(0));
        if (contadorLinha == 0) {
            if (palavras.size() == 2) {
                if ("START".equals(ins.getNome())) {
                    ins = instrucoes.getInstrucao(palavras.get(1));
                    if (ins != null) {
                        linha = buffRead.readLine();
                        return;
                    }
                }
            }
            erros.add(new ErroMontador(contadorLinha, TipoErro.ERRO_SINTAXE));
        }
        if (ins != null) {
            // primeira palavra é uma instrução
            posInstrucao = 0;
        } else {
            ins = instrucoes.getInstrucao(palavras.get(1));
            if (ins == null) { // testa se a segunda palavra não é uma instrução
                erros.add(new ErroMontador(contadorLinha, TipoErro.INSTRUCAO_INVALIDA));
                linha = buffRead.readLine();
                return;
            }
            if (ins.getNome().equals("EXTR")) {
                // encontrou o EXTR adiciona na lista de simbolos externos
                // ao encontrar um simbolo verificar a lista de simbolos externos
                simbolosExternos.add(palavras.get(0));
                linha = buffRead.readLine();
                return;
            }
            if (!existeSimbolo(palavras.get(0))) {
                // testa se a primeira palavra é um simbolo ja definido
                if(!inSimbolosExternos(palavras.get(0))) {
                    simbolos.add(new Simbolo(palavras.get(0), posicao, true));
                }
            } else {
                // se já existe, verifica se o simbolo está indefinido
                boolean flag_indefinido = false;
                for (Simbolo s : simbolos) {
                    if (s.getRotulo().equals(palavras.get(0)) && !s.isDefinido()) {
                        flag_indefinido = true;
                        s.setDefinido(true);
                        s.setEndereco(posicao);
                        for(TabelaDefinicoes t : tabelaDefinicoes) {
                            if (s.getRotulo().equals(t.getRotulo())) {
                                t.setEndereco(Integer.toString(posicao));
                            }
                        }
                    }
                }
                if (!flag_indefinido) {
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
        if (operandoAmount > posInstrucao + 1 || operandoAmount < posInstrucao + 1) {
            // ERRO: INSTRUÇÃO TEM OPERANDOS DEMAIS
            erros.add(new ErroMontador(contadorLinha, TipoErro.ERRO_SINTAXE));
            linha = buffRead.readLine();
            return;
        }

        if (ins.isPseudoInstrucao()) {
            switch (ins.getNome()) {
                case "SPACE" -> {
                    posicao += 3; // pula 3 palavras
                }
                case "CONST" -> {
                    posicao += 1;
                    String op_1 = palavras.get(posInstrucao + 1);
                    ModosEnderecamento modo_op1 = verificaNumero(op_1);
                    if (modo_op1 != DIRETO) { // ACEITAR APENAS NÚMEROS SEM CARACTERES
                        // ERRO: OPERANDO INVÁLIDO
                        erros.add(new ErroMontador(contadorLinha, TipoErro.ERRO_SINTAXE));
                    }
                }
                case "END" -> {
                    this.faltaEnd = false;
                }
                case "START" -> {
                    if(contadorLinha > 0) erros.add(new ErroMontador(contadorLinha, TipoErro.ERRO_SINTAXE));
                }
                case "EXTR" -> {
                    // EXTR é tratado especialmente antes, então se chegar aqui (com posInstrucao = 0) é erro
                    erros.add(new ErroMontador(contadorLinha, TipoErro.ERRO_SINTAXE));
                }
                case "EXTDEF" -> {
                    if(posInstrucao != 0 || palavras.size() != 2) {
                        erros.add(new ErroMontador(contadorLinha, TipoErro.ERRO_SINTAXE));
                    } else {
                        // vai na tabela de simbolos igual
                        String op_1 = palavras.get(posInstrucao + 1);
                        tabelaDefinicoes.add(new TabelaDefinicoes(op_1, "0", RELATIVO));
                        if (!trataSimbolo(op_1)) { // verifica o nome do simbolo e adiciona se não existe
                            erros.add(new ErroMontador(contadorLinha, TipoErro.ERRO_SINTAXE));
                        }
                    }
                }
                default -> {
                    erros.add(new ErroMontador(contadorLinha, TipoErro.ERRO_SINTAXE));
                }
            }
            linha = buffRead.readLine();
            return;
        }
        if (numOperandos > 0) {
            posicao += 1;
            String op_1 = palavras.get(posInstrucao + 1);
            int valor_op = 0;
            ModosEnderecamento modo_op1;
            boolean ext_flag = inSimbolosExternos(op_1);
            if (!trataSimbolo(op_1) && !ext_flag) { // nao é um simbolo interno/externo
                modo_op1 = verificaNumero(op_1);
                valor_op = Integer.parseInt(getValorNumero(op_1));
            } else {
                modo_op1 = DIRETO;
                if(ext_flag) { // é um simbolo externo
                   tabelaUso.add(new TabelaUso(op_1, Integer.toString(posicao), POSITIVO));
                }
            }

            if(modo_op1 == IMEDIATO) {
                // 2 ^ 16 | 0 a 65535
                if(valor_op < 0 || valor_op > 65535) erros.add(new ErroMontador(contadorLinha, TipoErro.FORA_DOS_LIMITES));
            } else if(modo_op1 == DIRETO) {
                // 512 posições 0 a 511
                if(valor_op < 0 || valor_op > 511) erros.add(new ErroMontador(contadorLinha, TipoErro.FORA_DOS_LIMITES));
            }
            
            if (ins.getNome().equals("COPY")) {
                posicao += 1;
                String op_2 = palavras.get(posInstrucao + 2);
                ModosEnderecamento modo_op2;
                ext_flag = inSimbolosExternos(op_2);
                if (!ext_flag && !trataSimbolo(op_2)) {
                    modo_op2 = verificaNumero(op_2);
                } else {
                    modo_op2 = DIRETO;
                    if(ext_flag) { // é um simbolo externo
                        tabelaUso.add(new TabelaUso(op_2, Integer.toString(posicao), POSITIVO));
                    }
                }

                if (instrucoes.trataCopy(modo_op1, modo_op2) == -1) {
                    // ERRO: OPERANDOS/COMBINAÇÃO DE OPERANDOS INVÁLIDA
                    erros.add(new ErroMontador(contadorLinha, TipoErro.ERRO_SINTAXE));
                    linha = buffRead.readLine();
                    return;
                }
            } else {
                if (!ins.getModosPermitidos().contains(modo_op1)) {
                    // ERRO: MODO DE ENDEREÇAMENTO NÃO PERMITIDO
                    erros.add(new ErroMontador(contadorLinha, TipoErro.ERRO_SINTAXE));
                    linha = buffRead.readLine();
                    return;
                }
            }
        }

        posicao += 1;
        linha = buffRead.readLine();
    }

    private void linhaSegundaPassagem() throws IOException {
        List<String> palavras = trataLinha(linha);
        if ("".equals(palavras.get(0))) {
            return;
        }

        Instrucao ins = instrucoes.getInstrucao(palavras.get(0));
        if (ins != null) {
            posInstrucao = 0;
        } else {
            ins = instrucoes.getInstrucao(palavras.get(1));
            posInstrucao = 1;
        }

        int numOperandos = ins.getNumOperandos();
        if (ins.isPseudoInstrucao()) {
            switch (ins.getNome()) {
                case "SPACE" -> {
                    for (int i = 0; i < 3; ++i) {
                        resultado.add("000");
                    }
                    mapa_relocacao += "000";
                }
                case "CONST" -> {
                    //bota direto pq ja verificou o operando
                    mapa_relocacao += "0";
                    resultado.add(preencheZeros(palavras.get(posInstrucao + 1)));
                }
                case "END" -> {
                    faltaEnd = false;
                }
                case "EXTR" -> {}
                case "EXTDEF" -> {}
                default -> {}
            }
        } else {
            mapa_relocacao += "0"; // instrução
            if (numOperandos > 0) {
                Simbolo s;
                boolean flagIsSimbolo_1 = false;
                boolean ext_flag_1 = false;
                boolean ext_flag_2 = false;
                boolean flagIsSimbolo_2 = false;
                posicao += 1;
                String op_1 = palavras.get(posInstrucao + 1);
                ModosEnderecamento modo_op1 = DIRETO;
                
                if(inSimbolosExternos(op_1)) {
                    ext_flag_1 = true;
                } else if (!existeSimbolo(op_1)) {
                    modo_op1 = verificaNumero(op_1);
                } else {
                    flagIsSimbolo_1 = true;
                }
                if(modo_op1 == IMEDIATO) mapa_relocacao += "0";
                else mapa_relocacao += "1";

                if (ins.getNome().equals("COPY")) {
                    // se for COPY pega o segundo operando (apenas COPY tem dois operandos)
                    // e 6 combinações de operandos
                    posicao += 1;
                    String op_2 = palavras.get(posInstrucao + 2);
                    ModosEnderecamento modo_op2 = DIRETO;
                    if(inSimbolosExternos(op_2)) {
                        ext_flag_2 = true;
                    } else if (!existeSimbolo(op_2)) {
                        modo_op2 = verificaNumero(op_2);
                    } else {
                        flagIsSimbolo_2 = true;
                    }
                    if(modo_op2 == IMEDIATO) mapa_relocacao += "0";
                    else mapa_relocacao += "1";
                    // escreve o código do copy conforme os operandos
                    resultado.add(preencheZeros(instrucoes.trataCopy(modo_op1, modo_op2).toString()));
                    if (flagIsSimbolo_1) {
                        s = getSimbolo(op_1);
                        resultado.add(preencheZeros(s.getEnderecoString()));
                    } else {
                        if(ext_flag_1) {
                            resultado.add("XXX");
                        } else {
                            resultado.add(preencheZeros(getValorNumero(op_1)));
                        }
                    }
                    if (flagIsSimbolo_2) {
                        s = getSimbolo(op_2);
                        resultado.add(preencheZeros(s.getEnderecoString()));
                    } else {
                        if(ext_flag_2) {
                            resultado.add("XXX");
                        } else {
                            resultado.add(preencheZeros(getValorNumero(op_2)));
                        }
                    }

                } else {
                    // NÃO É O COPY SEGUIR NORMALMENTE
                    // escreve o código da instrução conforme o modo de endereçamento do operando
                    resultado.add(preencheZeros(ins.getCodigoMontado(modo_op1)));
                    if (flagIsSimbolo_1) {
                        s = getSimbolo(op_1);
                        // escreve o endereço do operando simbolo
                        resultado.add(preencheZeros(s.getEnderecoString()));
                    } else {
                        // FALTA: se for imediato não vai para o mapa de relocação
                        // escreve o valor do operando
                        if(ext_flag_1) {
                            resultado.add("XXX");
                        } else {
                            resultado.add(preencheZeros(getValorNumero(op_1)));
                        }
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
        if (findPrefixo.find()) {
            operando = findPrefixo.group(1);
        }

        Pattern sufixo = Pattern.compile("(\\d+),");
        Matcher findSufixo = sufixo.matcher(operando);
        if (findSufixo.find()) {
            operando = findSufixo.group(0);
        }

        return operando;
    }

    public String preencheZeros(String palavra) {
        // adiciona zero a esquerda nas palavras (até 3 caracteres)
        // "8" -> "008"     "10" -> "010"
        String zeros = "";
        if (palavra.length() == 1) {
            zeros = "00";
        } else if (palavra.length() == 2) {
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
            if (!existeSimbolo(palavra) && !inSimbolosExternos(palavra)) {
                simbolos.add(new Simbolo(palavra, 0));
            } 
            return true;
        } else {
            return false;
        }
    }

    private boolean inSimbolosExternos(String nome) {
        for(String s : simbolosExternos) {
            if(s.equals(nome)) return true;
        }
        return false;
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
        } else if (findIndireto.find()) {
            return INDIRETO;
        } else if (findImediato.find()) {
            return IMEDIATO;
        }
        return null;
    }

    public void reset() {
        this.contadorLinha = 0;
        this.erros.clear();
        this.simbolos.clear();
        this.simbolosExternos.clear();
        this.tabelaUso.clear();
        this.tabelaDefinicoes.clear();
        this.faltaEnd = true;
        this.mapa_relocacao = "";
        this.posicao = 0;
        this.resultado.clear();
        
    }
}
