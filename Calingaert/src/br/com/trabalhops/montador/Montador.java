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
    private Instrucoes instrucoes;
    private final List<Simbolo> simbolos = new ArrayList<>();
    private final Pattern numeros = Pattern.compile("\\d", Pattern.CASE_INSENSITIVE);
    private List<ErroMontador> erros = new ArrayList<>();
    private List<String> resultado = new ArrayList<>();
    private int posInstrucao;
    private int contadorLinha = 0;
    private int posicao = 0;
    private BufferedReader buffRead;
    private String linha;
    private Matcher findNumeros;
    
    private BufferedWriter buffWriterObj, buffWriterLst;
    
    //Dentro desses arrays, colocar as palavras para serem gravadas nos arquivos .obj e .lst
        
    private ArrayList<String> obj  = new ArrayList();
    private ArrayList<String> lst  = new ArrayList();
    
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
        }
        
        
        buffWriterObj.append("* file.obj\n");
        for (String string : this.obj) {
            buffWriterObj.append(string+"\n");
        }
        buffWriterObj.append("TAMANHO\n" + obj.size());
        
        buffWriterLst.append("LINHA CÓDIGO INSTRUÇÃO\n");
        for (int i = 0; i < this.lst.size(); i++) {
            buffWriterLst.append(i + " " + obj.get(i) + " " + lst.get(i)+ "\n");
        }
        
        this.buffWriterObj.flush();
        this.buffWriterLst.flush();
        this.buffWriterObj.close();        
        this.buffWriterLst.close();
        
        return true;
    }

    private void linhaPrimeiraPassagem() throws IOException {
        List<String> palavras = trataLinha(linha);
        if ("".equals(palavras.get(0))) return;
        
        Instrucao ins = instrucoes.getInstrucao(palavras.get(0));
        if (ins != null) {
            posInstrucao = 0;
        } else {
            ins = instrucoes.getInstrucao(palavras.get(1));
            if (ins == null) {
                // LABEL OP1 OP2
                // ERROR, THERE'S NO INSTRUCTION IN THE LINE
                // RETURN
            }
            
            if (!existeSimbolo(palavras.get(0))) {
                simbolos.add(new Simbolo(palavras.get(0), posicao, true));
            } else {
                boolean flag_indefinido = false;
                for(Simbolo s: simbolos) {
                    if(s.getRotulo().equals(palavras.get(0)) && !s.isDefinido()) {
                        flag_indefinido = true;
                        s.setDefinido(true);
                        s.setEndereco(posicao);
                    }
                }
                if(!flag_indefinido) erros.add(new ErroMontador(contadorLinha, TipoErro.SIMBOLO_REDEFINIDO));
            }

            posInstrucao = 1;
        }
        
        int numOperandos = ins.getNumOperandos();
        int operandoAmount = palavras.size() - numOperandos;

        if (operandoAmount > posInstrucao + 1) {
            // ERROR, TOO MANY ARGUMENTS
            return;
        }
        if (operandoAmount < posInstrucao + 1) {
            // ERROR, TOO FEW ARGUMENTS
            return;
        }
        if(ins.isPseudoInstrucao()) {
            /// SPACE -> PULA POSICOES
            /// CONST -> SÓ ESCREVE OQ VEM DPS
            /// END N FAZ NADA ETC
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
            
            // erro valor fora do limite
            if (!ins.getModosPermitidos().contains(modo_op1)) {
                // ERRO
            }
            
            if(ins.getNome().equals("COPY")) {
                posicao +=1;
                String op_2 = palavras.get(posInstrucao + 2);
                ModosEnderecamento modo_op2;
                if (!trataSimbolo(op_2)) {
                    modo_op2 = verificaNumero(op_2);
                } else {
                    modo_op2 = DIRETO;
                }
                
                // trataCopy(modo_op1, modo_op2) -> codígo ou -1 
                // DIRETO, INDIRETO -> CÓDIGO 
                // IMEDIATO, INDIRETO -> -1
                // tratar COPY
            }
        }
        
                
        this.buffWriterObj.append(palavras.get(0));
        

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
            ///
            ///
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
                    posicao +=1;
                    String op_2 = palavras.get(posInstrucao + 2);
                    ModosEnderecamento modo_op2;
                    if (!existeSimbolo(op_2)) {
                        modo_op2 = verificaNumero(op_2);
                    } else {
                        modo_op2 = DIRETO;
                    }
                    // tratar COPY
                    //  CASO DIRETO/DIRETO ->
                    //  CASO DIRETO/IMEDIATO -> etc...
                } else {
                    // NÃO É O COPY SEGUIR NORMALMENTE
                    resultado.add(ins.getCodigoMontado(modo_op1)); // para instrucao atual pega o código apropriado para o modo do operando
                    if(flagIsSimbolo_1) {
                        Simbolo s = getSimbolo(op_1);
                        resultado.add(s.getEnderecoString());
                    } else {
                        // extrair valor numérico do operando
                    }
                    // ou ext
                }
            } else {
                resultado.add(ins.getCodigos().get(0).toString());
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
