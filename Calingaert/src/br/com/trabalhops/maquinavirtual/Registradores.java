package br.com.trabalhops.maquinavirtual;

/**
 *
 * @author Luzo
 */
public class Registradores {
    
    private int PC;
    private int ACC;
    private int SP;
    private int MOP;
    private int RI;
    private int RE;

    public Registradores() {
        this.PC = 0;
        this.ACC = 0;
        this.SP = 0;
        this.MOP = 0;
        this.RI = 0;
        this.RE = 0;
    }

    public void resetRegistradores(int valor_pc) {
        this.PC = valor_pc;
        this.ACC = 0;
        this.SP = 0;
        this.RI = 0;
        this.RE = 0;
    }
    
    public int getPC() {
        return PC;
    }

    public void setPC(int PC) {
        this.PC = PC;
    }

    public int getACC() {
        return ACC;
    }

    public void setACC(int ACC) {
        this.ACC = ACC;
    }

    public int getSP() {
        return SP;
    }

    public void setSP(int SP) {
        this.SP = SP;
    }

    public int getMOP() {
        return MOP;
    }

    public void setMOP(int MOP) {
        this.MOP = MOP;
    }

    public int getRI() {
        return RI;
    }

    public void setRI(int RI) {
        this.RI = RI;
    }

    public int getRE() {
        return RE;
    }

    public void setRE(int RE) {
        this.RE = RE;
    }

    public void addPC(int valor) {
        this.PC += valor;
    }
    
    
}
