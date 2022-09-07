package calingaert;

import java.awt.BorderLayout;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import trabalho_ps.FuncoesUteis;
import javax.swing.JTextPane;

/**
 *
 * @author gusta
 */
public class Calingaert {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Calingaert");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //frame.setUndecorated(true);
        frame.setVisible(true);

        JTextPane painel = new JTextPane();
        frame.getContentPane().add(BorderLayout.CENTER, painel);
        String caminho = "../programa.txt";
        try {
            FuncoesUteis.imprimeInterfaceFinal(painel, caminho);

            // TODO code application logic here
        } catch (IOException ex) {
            Logger.getLogger(Calingaert.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
