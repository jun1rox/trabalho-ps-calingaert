package calingaert;

import gui.Tela;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import trabalho_ps.Memoria;
import trabalho_ps.FuncoesUteis;
import trabalho_ps.Registradores;

/**
 *
 * @author gusta
 */
public class Calingaert {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Memoria memoria = new Memoria();
        int valor_pc = memoria.getINICIO_INS_DADOS();
        Registradores registradores = new Registradores();
        registradores.setPC(valor_pc);
        String caminho = "../programa.txt";

        try {
            memoria.carregaPrograma(caminho);
        } catch (IOException ex) {
            Logger.getLogger(Calingaert.class.getName()).log(Level.SEVERE, null, ex);
        }

        Tela tela = new Tela(registradores, memoria);

        tela.setTitle("Calingaert - g²");
        tela.preencheTabela(memoria);
        tela.preencheTabelaRegistradores(registradores);
        tela.setVisible(true);

    }

}
