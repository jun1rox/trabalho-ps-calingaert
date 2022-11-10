/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.trabalhops.ligador;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Franco
 */
public class Ligador {
    private List<TabelaUso> tabelaUso = new ArrayList<>();
    private List<TabelaDefinicoes> tabelaSimbolosGlobais = new ArrayList<>();
    private BufferedWriter buffWriter;
    private BufferedReader buffRead, buffRead_2;
    
    public Ligador() {}
    
    public void Liga(String caminho_1, String caminho_2) throws FileNotFoundException, IOException {
        buffRead = new BufferedReader(new FileReader(caminho_1));
       
        String linha = buffRead.readLine();
        while (linha != null) {
            // preencher Tabelas
        }
        
        buffRead_2 = new BufferedReader(new FileReader(caminho_2));
        linha = buffRead_2.readLine();
        while (linha != null) {
            // preencher Tabelas
        }
        
    }
}
