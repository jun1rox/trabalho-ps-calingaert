package br.com.trabalhops.montador;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Franco
 */
public class Instrucao {
    public enum ModosEnderecamento {
        DIRETO,
        INDIRETO,
        IMEDIATO,
    };
    private boolean pseudoInstrucao = false;
    private String nome;
    private int numOperandos;
    private List<Integer> codigos = new ArrayList<>();
    private List<ModosEnderecamento> modosPermitidos = new ArrayList<>();

    public Instrucao(String nome, int numOperandos, List<Integer> codigos, List<ModosEnderecamento> modosPermitidos, boolean pseudoInstrucao) {
        this(nome, numOperandos, codigos, modosPermitidos);
        this.pseudoInstrucao = pseudoInstrucao;
    }
    
    public Instrucao(String nome, int numOperandos, List<Integer> codigos, List<ModosEnderecamento> modosPermitidos) {
        this.numOperandos = numOperandos;
        this.nome = nome;
        this.codigos = codigos;
        this.modosPermitidos = modosPermitidos;
    }
    
    public Instrucao(String nome, int numOperandos, List<Integer> codigos) {
        this.nome = nome;
        this.numOperandos = numOperandos;
        this.codigos = codigos;
    }
    
    public boolean isPseudoInstrucao() {
        return pseudoInstrucao;
    }

    public String getNome() {
        return nome;
    }

    public int getNumOperandos() {
        return numOperandos;
    }

    public List<Integer> getCodigos() {
        return codigos;
    }
    
    public Integer getCodigo(int index) {
        return codigos.get(index);
    }

    public List<ModosEnderecamento> getModosPermitidos() {
        return modosPermitidos;
    }
    
    public ModosEnderecamento getModoPermitido(int index) {
        return modosPermitidos.get(index);
    }

    public void setPseudoInstrucao(boolean pseudoInstrucao) {
        this.pseudoInstrucao = pseudoInstrucao;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setNumOperandos(int numOperandos) {
        this.numOperandos = numOperandos;
    }

    public void setCodigos(List<Integer> codigos) {
        this.codigos = codigos;
    }

    public void setModosPermitidos(List<ModosEnderecamento> modosPermitidos) {
        this.modosPermitidos = modosPermitidos;
    }
    
    public void addCodigos(int codigo) {
        this.codigos.add(codigo);
    }
    
    public void addPermitidos(ModosEnderecamento modo) {
        this.modosPermitidos.add(modo);
    }
    
    public String getCodigoMontado(ModosEnderecamento modo) {
        int index = modosPermitidos.indexOf(modo);
        return codigos.get(index).toString();
    }
            
}
