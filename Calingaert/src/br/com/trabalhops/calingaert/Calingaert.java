package br.com.trabalhops.calingaert;

import br.com.trabalhops.gui.Tela;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import br.com.trabalhops.maquinavirtual.Memoria;
import br.com.trabalhops.maquinavirtual.Registradores;
import br.com.trabalhops.montador.Montador;
import java.awt.Color;
import javax.swing.ImageIcon;

/**
 *
 * @author gusta
 */
public class Calingaert {

    public static void main(String[] args) throws IOException {
        Memoria memoria = new Memoria();
        int valor_pc = memoria.getINICIO_INS_DADOS();
        Registradores registradores = new Registradores();
        registradores.setPC(valor_pc);
        String caminho = "../fibonacci.txt";

        Montador montador = new Montador();
        montador.monta();

        memoria.carregaPrograma(caminho);

        Tela tela = new Tela(registradores, memoria);

        ImageIcon img = new ImageIcon("../calingas.png");
        tela.setIconImage(img.getImage());
        tela.setTitle("Calingaert - g²");
        tela.preencheTabela(memoria);
        tela.preencheTabelaRegistradores(registradores);
        tela.setResizable(false);
        tela.getContentPane().setBackground(new Color(51, 204, 255));
        tela.setVisible(true);
    }

}
