package br.com.trabalhops.ligador;

import static br.com.trabalhops.ligador.Modo.*;
import static br.com.trabalhops.ligador.TabelaUso.Sinal.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Franco
 */
public class Ligador {

    private List<TabelaUso> tabelaUso = new ArrayList<>();
    private List<TabelaDefinicoes> tabelaSimbolosGlobais = new ArrayList<>();
    private List<String> resultado = new ArrayList<>();
    private List<String> palavras = new ArrayList<>();
    int tamanho_1 = 0, tamanho_2 = 0, posicao = 0, valor;
    private String mapa_1 = "", mapa_2 = "";
    private BufferedWriter buffWriter;
    private BufferedReader buffRead;

    public Ligador() {
    }

    private String getSimboloGlobal(String rotulo_busca) {
        for (TabelaDefinicoes t : tabelaSimbolosGlobais) {
            if (t.getRotulo().equals(rotulo_busca)) {
                return t.getEndereco();
            }
        }
        return null;
    }

    public void liga(String caminho_1) throws FileNotFoundException, IOException {
        this.leArquivo_1(caminho_1);

        if (!tabelaUso.isEmpty()) {
            return;
        }

        String nome_arquivo = "./src/arquivos/output.txt";

        File output_arquivo = new File(nome_arquivo);
        output_arquivo.createNewFile();
        this.buffWriter = new BufferedWriter(new FileWriter(output_arquivo));
        this.buffWriter.append(mapa_1 + "\n");
        for (String s : this.resultado) {
            this.buffWriter.append(s + "\n");
        }
        this.buffWriter.flush();
        this.buffWriter.close();
    }

    public void liga(String caminho_1, String caminho_2) throws FileNotFoundException, IOException {

        // INICIO DA PRIMEIRA PASSAGEM
        this.leArquivo_1(caminho_1);
        this.leArquivo_2(caminho_2);

        // FIM DA PRIMEIRA PASSAGEM, LEITURA DOS ARQUIVOS E UNI??O DAS TABELAS
        // Verificar simbolo redefinido? 2x na tabela de simbolos globais
        for (TabelaUso t : tabelaUso) {
            String rotulo = t.getRotulo();
            int pos = Integer.parseInt(t.getPosicao());
            String endereco = this.getSimboloGlobal(rotulo);
            if (endereco != null) {
                resultado.set(pos, endereco);
            } else {
                System.out.println("Erro: Simbolo externo indefinido");
            }
        }

        String nome_arquivo = "./src/arquivos/output.txt";

        File output_arquivo = new File(nome_arquivo);
        output_arquivo.createNewFile();
        this.buffWriter = new BufferedWriter(new FileWriter(output_arquivo));
        this.buffWriter.append(mapa_1.concat(mapa_2) + "\n");
        for (String s : this.resultado) {
            this.buffWriter.append(String.format("%03d", Integer.parseInt(s)) + "\n");
        }
        this.buffWriter.flush();
        this.buffWriter.close();
    }

    public void leArquivo_1(String caminho) throws FileNotFoundException, IOException {
        buffRead = new BufferedReader(new FileReader(caminho));

        String linha = buffRead.readLine();
        while (linha != null) {
            if (linha.equals("TAMANHO")) {
                linha = buffRead.readLine();
                tamanho_1 = Integer.parseInt(linha);
            } else if (linha.equals("MAPA")) {
                linha = buffRead.readLine();
                mapa_1 = linha;
            } else if (linha.equals("TABELA_USO")) {
                linha = buffRead.readLine();
                while (!linha.equals("***")) {
                    palavras = Arrays.asList(linha.split(" "));
                    tabelaUso.add(new TabelaUso(palavras.get(0), palavras.get(1), POSITIVO));
                    linha = buffRead.readLine();
                }
            } else if (linha.equals("TABELA_DEFINICAO")) {
                linha = buffRead.readLine();
                while (!linha.equals("***")) {
                    palavras = Arrays.asList(linha.split(" "));
                    tabelaSimbolosGlobais.add(new TabelaDefinicoes(palavras.get(0), palavras.get(1), RELATIVO));
                    linha = buffRead.readLine();
                }
            } else {
                resultado.add(linha);
            }

            linha = buffRead.readLine();
        }
    }

    private void leArquivo_2(String caminho) throws FileNotFoundException, IOException {
        buffRead = new BufferedReader(new FileReader(caminho));
        
        String linha = buffRead.readLine();
        while (linha != null) {
            if (linha.equals("TAMANHO")) {
                linha = buffRead.readLine();
                System.out.println(linha);
                tamanho_2 = Integer.parseInt(linha);
            } else if (linha.equals("MAPA")) {
                linha = buffRead.readLine();
                mapa_2 = linha;
            } else if (linha.equals("TABELA_USO")) {
                linha = buffRead.readLine();
                while (!linha.equals("***")) {
                    palavras = Arrays.asList(linha.split(" "));
                    valor = Integer.parseInt(palavras.get(1)) + tamanho_1;
                    tabelaUso.add(new TabelaUso(palavras.get(0), Integer.toString(valor), POSITIVO));
                    linha = buffRead.readLine();
                }
            } else if (linha.equals("TABELA_DEFINICAO")) {
                linha = buffRead.readLine();
                while (!linha.equals("***")) {
                    palavras = Arrays.asList(linha.split(" "));
                    valor = Integer.parseInt(palavras.get(1)) + tamanho_1;
                    tabelaSimbolosGlobais.add(new TabelaDefinicoes(palavras.get(0), Integer.toString(valor), RELATIVO));
                    linha = buffRead.readLine();
                }
            } else {
                if (linha.equals("XXX")) {
                    resultado.add(linha);
                } else {
                    valor = Integer.parseInt(linha); // Se for um endere??o, desloca pelo tamanho do m??dulo 1
                    if (mapa_2.charAt(posicao) == '1') {
                        valor += tamanho_1;
                    }
                    resultado.add(Integer.toString(valor));
                }
                posicao += 1;
            }

            linha = buffRead.readLine();
        }
    }

}
