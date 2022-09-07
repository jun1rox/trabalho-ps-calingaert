package calingaert;

import gui.Tela;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import trabalho_ps.Memoria;
import trabalho_ps.Registrador;
import trabalho_ps.FuncoesUteis;

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
        String valor_pc = FuncoesUteis.intToBinaryString(memoria.getINICIO_INS_DADOS(), 16);
        Registrador PC = new Registrador(valor_pc);
        Registrador ACC = new Registrador();
        Registrador SP = new Registrador();
        Registrador MOP = new Registrador();
        Registrador RI = new Registrador();
        Registrador RE = new Registrador();
        String caminho = "../programa.txt";
        
        Tela tela = new Tela();
        
        
        try {
            memoria.carregaPrograma(caminho);
        } catch (IOException ex) {
            Logger.getLogger(Calingaert.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        tela.preencheTabela(memoria);
        tela.setVisible(true);

    }

}
