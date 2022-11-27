package br.com.trabalhops.calingaert;

import br.com.trabalhops.gui.Tela;
import br.com.trabalhops.ligador.Ligador;
import br.com.trabalhops.macros.MacroProcessor;
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
        
        Tela tela = new Tela(registradores, memoria);

        ImageIcon img = new ImageIcon("../calingas.png");
        tela.setIconImage(img.getImage());
        tela.setTitle("Calingaert - g²");
        tela.preencheTabela(memoria);
        tela.preencheTabelaRegistradores(registradores);
        tela.setResizable(false);
        tela.getContentPane().setBackground(new Color(204, 153, 255));
        tela.setVisible(true);
    }

}
