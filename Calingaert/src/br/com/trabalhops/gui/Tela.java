package br.com.trabalhops.gui;

import br.com.trabalhops.ligador.Ligador;
import br.com.trabalhops.macros.MacroProcessor;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import br.com.trabalhops.maquinavirtual.Instrucoes;
import br.com.trabalhops.maquinavirtual.Memoria;
import br.com.trabalhops.maquinavirtual.Registradores;
import br.com.trabalhops.montador.Montador;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author junio
 */
public class Tela extends javax.swing.JFrame {

    private int stepDuration = 1000;
    public final Instrucoes instrucoes = new Instrucoes();
    public Registradores registradores;
    public Memoria memoria;
    public Dados dados;

    public String caminho1 = "";
    public String caminho2 = "";

    public Tela(Registradores registradores, Memoria memoria) {
        this.registradores = registradores;
        this.memoria = memoria;
        this.dados = new Dados();

        initComponents();
        console.setEditable(false);
        selectStepDuration.setVisible(false);
    }

    public int lerTeclado() {
        String input = JOptionPane.showInputDialog(this, "Input");
        return Integer.parseInt(input);
    }

    public void escreveConsole(int valor) {
        console.append(Integer.toString(valor) + "\n");
    }

    public void preencheTabela(Memoria memoria) {
        String colunas[] = {"Endereço", "Valor"};

        DefaultTableModel model = new DefaultTableModel(dados.criaMatriz(memoria), colunas);
        tabelaMemoria.setModel(model);
    }

    public void preencheTabelaRegistradores(Registradores registradores) {
        String colunas[] = {"Registrador", "Valor"};

        DefaultTableModel model = new DefaultTableModel(dados.criaMatrizRegistradores(registradores), colunas);

        tabelaRegistradores.setModel(model);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        modoOperacao = new javax.swing.ButtonGroup();
        jFileChooser1 = new javax.swing.JFileChooser();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jFileChooser2 = new javax.swing.JFileChooser();
        jButton1 = new javax.swing.JButton();
        botaoExecutar = new javax.swing.JButton();
        tabelaMemoriaScroll = new javax.swing.JScrollPane();
        tabelaMemoria = new javax.swing.JTable();
        tabelaRegistradoresScroll = new javax.swing.JScrollPane();
        tabelaRegistradores = new javax.swing.JTable();
        modoStep = new javax.swing.JRadioButton();
        modoContinuo = new javax.swing.JRadioButton();
        modoIntervalo = new javax.swing.JRadioButton();
        consoleScroll = new javax.swing.JScrollPane();
        console = new javax.swing.JTextArea();
        resetButton = new javax.swing.JButton();
        toggleModoTabela = new javax.swing.JToggleButton();
        selectStepDuration = new javax.swing.JComboBox<>();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        jButton1.setText("jButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(204, 102, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        botaoExecutar.setText("Prox Instrução");
        botaoExecutar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoExecutarActionPerformed(evt);
            }
        });

        tabelaMemoria.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"", null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Endereço", "Valor"
            }
        ));
        tabelaMemoriaScroll.setViewportView(tabelaMemoria);

        tabelaRegistradores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"", null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Registradores", "Valor"
            }
        ));
        tabelaRegistradoresScroll.setViewportView(tabelaRegistradores);

        modoStep.setBackground(new java.awt.Color(135, 135, 255));
        modoOperacao.add(modoStep);
        modoStep.setSelected(true);
        modoStep.setText("Manual");
        modoStep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modoStepActionPerformed(evt);
            }
        });

        modoContinuo.setBackground(new java.awt.Color(135, 135, 255));
        modoOperacao.add(modoContinuo);
        modoContinuo.setText("Contínuo");
        modoContinuo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modoContinuoActionPerformed(evt);
            }
        });

        modoIntervalo.setBackground(new java.awt.Color(135, 135, 255));
        modoOperacao.add(modoIntervalo);
        modoIntervalo.setText("Depuração");
        modoIntervalo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modoIntervaloActionPerformed(evt);
            }
        });

        console.setColumns(20);
        console.setRows(5);
        consoleScroll.setViewportView(console);

        resetButton.setText("Reset");
        resetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetButtonActionPerformed(evt);
            }
        });

        toggleModoTabela.setText("Decimal");
        toggleModoTabela.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                toggleModoTabelaStateChanged(evt);
            }
        });
        toggleModoTabela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toggleModoTabelaActionPerformed(evt);
            }
        });

        selectStepDuration.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "5", "10" }));
        selectStepDuration.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectStepDurationActionPerformed(evt);
            }
        });

        jButton2.setText("Arquivo 1");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Compilar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Arquivo 2");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(42, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(toggleModoTabela)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(modoStep, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(modoContinuo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE))
                                            .addGap(101, 101, 101))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addComponent(botaoExecutar, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(26, 26, 26)))
                                    .addComponent(tabelaRegistradoresScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(resetButton, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(selectStepDuration, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(consoleScroll, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addComponent(modoIntervalo, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(45, 45, 45)
                        .addComponent(tabelaMemoriaScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(44, 44, 44))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(resetButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton2)
                            .addComponent(jButton4))
                        .addGap(18, 18, 18)
                        .addComponent(jButton3)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(tabelaRegistradoresScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(modoStep)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(modoContinuo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(modoIntervalo)
                                    .addComponent(selectStepDuration, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(botaoExecutar)))
                        .addGap(53, 53, 53)
                        .addComponent(consoleScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(tabelaMemoriaScroll, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(toggleModoTabela)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botaoExecutarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoExecutarActionPerformed
        int mop = this.registradores.getMOP();

        switch (mop) {
            case 0 -> {
                instrucoes.getInstrucao(this);
                this.preencheTabelaRegistradores(registradores);
                this.preencheTabela(this.memoria);
            }
            case 1 -> {
                while (instrucoes.getInstrucao(this) != 0) {
                }
                this.preencheTabelaRegistradores(registradores);
                this.preencheTabela(this.memoria);
            }
            case 2 -> {
                Timer timer = new Timer();
                timer.schedule(new TaskDepuracao(this), 0, stepDuration);//wait 0 ms before doing the action and do it evry 1000ms (1second)
            }

            default -> {
                return;
            }
        }
    }//GEN-LAST:event_botaoExecutarActionPerformed

    private void modoStepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modoStepActionPerformed
        selectStepDuration.setVisible(false);
        this.registradores.setMOP(0);
        this.preencheTabelaRegistradores(registradores);
        botaoExecutar.setText("Prox Instrução");
    }//GEN-LAST:event_modoStepActionPerformed

    private void modoContinuoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modoContinuoActionPerformed
        selectStepDuration.setVisible(false);
        this.registradores.setMOP(1);
        this.preencheTabelaRegistradores(registradores);
        botaoExecutar.setText("Executar");
    }//GEN-LAST:event_modoContinuoActionPerformed

    private void modoIntervaloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modoIntervaloActionPerformed
        selectStepDuration.setVisible(true);
        this.registradores.setMOP(2);
        this.preencheTabelaRegistradores(registradores);
        botaoExecutar.setText("Executar");
    }//GEN-LAST:event_modoIntervaloActionPerformed

    private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetButtonActionPerformed
        memoria.resetMemoria();
        registradores.resetRegistradores(memoria.getINICIO_INS_DADOS());
        try {
            memoria.carregaPrograma(memoria.programaCarregado);
        } catch (IOException ex) {
            Logger.getLogger(Tela.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.preencheTabelaRegistradores(registradores);
        this.preencheTabela(this.memoria);
        console.setText("");
    }//GEN-LAST:event_resetButtonActionPerformed

    private void toggleModoTabelaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_toggleModoTabelaStateChanged

    }//GEN-LAST:event_toggleModoTabelaStateChanged

    private void selectStepDurationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectStepDurationActionPerformed
        String tempoSegundos = selectStepDuration.getSelectedItem().toString();
        stepDuration = Integer.parseInt(tempoSegundos) * 1000;
    }//GEN-LAST:event_selectStepDurationActionPerformed

    private void toggleModoTabelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toggleModoTabelaActionPerformed
        dados.toggleDecimal();
        this.preencheTabela(this.memoria);

        if (dados.decimal == true) {
            toggleModoTabela.setText("Binário");
        } else {
            toggleModoTabela.setText("Decimal");
        }
    }//GEN-LAST:event_toggleModoTabelaActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivo 1", "txt");

        fileChooser.setFileFilter(filter);
        int retorno = fileChooser.showOpenDialog(this);

        if (retorno == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            caminho1 = file.getPath();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        MacroProcessor macroProcessor = new MacroProcessor();
        Montador montador = new Montador();
        Ligador ligador = new Ligador();
        try {
            if (caminho1 != "") {
                macroProcessor.processMacros(caminho1, "./src/arquivos/MASMAPRG1.asm");
                montador.monta("./src/arquivos/MASMAPRG1.asm");
                
                if (caminho2 != "") {
                    macroProcessor.processMacros(caminho2, "./src/arquivos/MASMAPRG2.asm");
                    montador.monta("./src/arquivos/MASMAPRG2.asm");
                    ligador.liga("./src/arquivos/MASMAPRG1.obj", "./src/arquivos/MASMAPRG2.obj");
                } else {
                    ligador.liga("./src/arquivos/MASMAPRG1.obj");
                }
            }
            memoria.carregaPrograma("./src/arquivos/output.txt");
        } catch (IOException ex) {
            Logger.getLogger(Tela.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivo 2", "txt");

        fileChooser.setFileFilter(filter);
        int retorno = fileChooser.showOpenDialog(this);

        if (retorno == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            caminho2 = file.getPath();
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Tela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new Tela().setVisible(true);
//            }
//        });

        //</editor-fold>

        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new Tela().setVisible(true);
//            }
//        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoExecutar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JTextArea console;
    private javax.swing.JScrollPane consoleScroll;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JFileChooser jFileChooser2;
    private javax.swing.JRadioButton modoContinuo;
    private javax.swing.JRadioButton modoIntervalo;
    private javax.swing.ButtonGroup modoOperacao;
    private javax.swing.JRadioButton modoStep;
    private javax.swing.JButton resetButton;
    private javax.swing.JComboBox<String> selectStepDuration;
    private javax.swing.JTable tabelaMemoria;
    private javax.swing.JScrollPane tabelaMemoriaScroll;
    private javax.swing.JTable tabelaRegistradores;
    private javax.swing.JScrollPane tabelaRegistradoresScroll;
    private javax.swing.JToggleButton toggleModoTabela;
    // End of variables declaration//GEN-END:variables
}
