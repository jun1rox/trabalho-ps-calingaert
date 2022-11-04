package br.com.trabalhops.macros;

import java.util.ArrayList;

/**
 *
 * @author Luzo
 */
public class Macro {
    private String[] parameters;
    private ArrayList <String> body;
    private int currentLine;
    
    public Macro(String[] parameters){
        this.parameters = parameters;
        this.body = new ArrayList<>();
        this.currentLine = 0;
    }

    public String[] getParameters() {
        return parameters;
    }

    public void setParameters(String[] parameters) {
        this.parameters = parameters;
    }

    public ArrayList<String> getBody() {
        return body;
    }

    public void setBody(ArrayList<String> body) {
        this.body = body;
    }
    
    public void writeBody(String line){
        this.body.add(line);
//        for(int i = 0; i<this.body.size(); i++){;;
//            System.out.println(this.body.get(i));
//        }
    }
    
    public String getLine(){
        String line;
        if (currentLine < this.body.size()){
            line = this.body.get(this.currentLine);
            this.currentLine += 1;
        }else{
            line = null;
        }
        return line;
    }
}
