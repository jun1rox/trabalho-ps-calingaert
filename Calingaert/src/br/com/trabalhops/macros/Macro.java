package br.com.trabalhops.macros;

/**
 *
 * @author Luzo
 */
public class Macro {
    private String[] parameters;
    private String body;
    
    public Macro(String[] parameters){
        this.parameters = parameters;
        this.body = "";
    }

    public String[] getParameters() {
        return parameters;
    }

    public void setParameters(String[] parameters) {
        this.parameters = parameters;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
    
    public void writeBody(String line){
        this.body.concat(line);
    }
}
