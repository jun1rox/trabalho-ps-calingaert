package calingaert;

import java.awt.BorderLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import trabalho_ps.FuncoesUteis;
import javax.swing.JTextPane;
import trabalho_ps.Memoria;
import trabalho_ps.Registrador;

/**
 *
 * @author gusta
 */
public class Calingaert {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList<Registrador> registradores = new ArrayList<>();
        Memoria memoria = new Memoria(registradores);
        String caminho = "../programa.txt";
        try {
            memoria.carregaPrograma(caminho);
        } catch (IOException ex) {
            Logger.getLogger(Calingaert.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        JFrame frame = new JFrame("Calingaert");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        JTextPane painel = new JTextPane();
        frame.getContentPane().add(BorderLayout.CENTER, painel);
        
        try {
            FuncoesUteis.imprimeInterfaceFinal(painel, caminho, memoria.getMemoria());
            // TODO code application logic here
        } catch (IOException ex) {
            Logger.getLogger(Calingaert.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
