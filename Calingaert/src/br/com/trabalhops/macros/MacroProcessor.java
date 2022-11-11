package br.com.trabalhops.macros;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Stack;

/**
 *
 * @author Luzo
 */
public class MacroProcessor {

    private String path;  // setted by processMacros with "../test_macros.asm" for testing porpouses
    private final String outputFile = "./src/arquivos/MASMAPRG.ASM";
    private BufferedReader reader;
    private BufferedWriter writer;
    private String line;
    private int lineCounter = 0;
    private int definitionLevelCounter;
    private int expansionLevelCounter;
    private List<String> words = new ArrayList<>(List.of("", "", ""));  // [label, opcode/macroName, argumentos]
    private HashMap<String, Macro> macroTable = new HashMap<>();
    private Stack<Parameters> formalParameterStack = new Stack<>();
    private Stack<ArrayList<String>> actualParameterStack = new Stack<>();
    private String previousOpcode;
    private String newMacroDefinition;
    private Stack<String> macroCallStack = new Stack<>();

    public boolean processMacros(String path) throws IOException {
        this.path = path;
        this.reader = new BufferedReader(new FileReader(this.path));
        this.writer = new BufferedWriter(new FileWriter(this.outputFile));

        this.definitionLevelCounter = 0;
        this.expansionLevelCounter = 0;
        this.previousOpcode = null;
        this.newMacroDefinition = null;

        this.readLineInput();

        while (this.line != null) {
            if (this.words.get(1).equals("MACRO")) { // CASE opcode is "MACRO"
                this.definitionLevelCounter += 1;
                if (this.definitionLevelCounter == 1) {
                    //allocate new macro definition
                } else {
                    //write line to new macro definition
                    this.macroTable.get(this.newMacroDefinition).writeBody(this.line);
                }
            }
            else if (this.previousOpcode.equals("MACRO")){  // CASE opcode is PROTOTYPE
                String[] parameters = this.words.get(2).split(",");
                if (this.definitionLevelCounter == 1){
                    //allocate new macro definition
                    this.macroTable.put(this.words.get(1), new Macro(parameters));  //allocation must happen here because name of macro is only known now
                    this.newMacroDefinition = this.words.get(1);
                }
                if (this.expansionLevelCounter == 0){
                    //push ith formal parameter and (d,i) on formal-parameter stack
                    for(int i = 0; i < parameters.length; i++){
                        this.formalParameterStack.push(new Parameters(parameters[i], this.definitionLevelCounter, i + 1));
                    }
                }
                if (this.definitionLevelCounter > 1){
                    //write line to new macro definition
                    this.macroTable.get(this.newMacroDefinition).writeBody(this.line);
                }
            } else if (this.macroTable.containsKey(this.words.get(1))) { // CASE opcode is MACRO NAME
                if (this.definitionLevelCounter == 0) {
                    this.macroCallStack.add(this.words.get(1));
                    this.expansionLevelCounter += 1;

                    //prepare actual-parameter list
                    //push actual-parameter list on actual-parameter stack
                    String[] parameters = this.words.get(2).split(",");
                    ArrayList<String> auxList = new ArrayList<String>();
                    if (this.expansionLevelCounter > 1){
                        auxList.add(Integer.toString(this.macroTable.get(this.macroCallStack.peek()).getCurrentLine()));
                    }else{
                        auxList.add(Integer.toString(this.lineCounter));
                    }
                    //auxList.add(Integer.toString(this.lineCounter));
                    for (int i = 0; i < parameters.length; i++){
                        auxList.add(parameters[i]);
                    }
                    this.actualParameterStack.push(auxList);
                }
                if ((this.definitionLevelCounter > 0) && (this.expansionLevelCounter == 0)) {
                    String[] parameters = this.words.get(2).split(",");
                    Parameters newParameter = null;
                    String loopParameter;
                    //replace each formal parameter by topmost corresponding '#(k,i)' from formal-parameter stack
                    for (int i = 0; i < parameters.length; i++){
                        loopParameter = parameters[i];
                        newParameter = null;
                        for (int j = this.formalParameterStack.size() - 1; j > -1; j--){
                            if(this.formalParameterStack.get(j).getName().equals(loopParameter)){
                                newParameter = this.formalParameterStack.get(j);
                                break;
                            }
                        }
                        
                        if (newParameter != null){
                            parameters[i] = "#(" + newParameter.getLevel() + ";" + (newParameter.getPosition() + 1) + ")";
                        }
                    }
                }
                if (this.definitionLevelCounter > 0) {
                    //write line to new macro definition
                    this.macroTable.get(this.newMacroDefinition).writeBody(this.line);
                }
            }
            else if (this.words.get(1).equals("MEND")){ // CASE opcode is "MEND"
                if (this.definitionLevelCounter == 0){
                    //pop actual-parameter list (level of expansionLevelCounter) from actual parameter stack
                    this.actualParameterStack.pop();
                    this.expansionLevelCounter -= 1;
                    this.macroCallStack.pop();
                } else { //definition mode
                    if (this.expansionLevelCounter == 0) {
                        //pop formal-parameter stack (level definitionLevelCounter)
                        while(!this.formalParameterStack.isEmpty() && this.formalParameterStack.peek().getLevel() == this.definitionLevelCounter){
                            this.formalParameterStack.pop();
                        }
                    }
                    this.macroTable.get(this.newMacroDefinition).writeBody(this.line);
                    this.definitionLevelCounter -= 1;
                }
            }
            else{
                String[] parameters = this.words.get(2).split(",");
                Parameters newParameter = null;
                String loopParameter;
                if ((this.expansionLevelCounter == 0) && (this.definitionLevelCounter > 0)){
                    //replace each formal parameter
                    //by topmost corresponging '#(k,i)'
                    //from formal-parameter stack
                    for (int i = 0; i < parameters.length; i++){
                        loopParameter = parameters[i];
                        newParameter = null;
                        for (int j = this.formalParameterStack.size() - 1; j > -1; j--){
                            if(this.formalParameterStack.get(j).getName().equals(loopParameter)){
                                newParameter = this.formalParameterStack.get(j);
                                break;
                            }
                        }
                        
                        if (newParameter != null){
                            parameters[i] = "#(" + newParameter.getLevel() + ";" + (newParameter.getPosition() + 1) + ")";
                        }
                    }
                }
                if (this.definitionLevelCounter == 0) {
                    //write line to output
                    this.words.set(2, String.join(",", parameters));
                    this.line = String.join(" ", this.words);
                    
                    if (this.words.get(0).isEmpty()){
                        this.line = this.line.substring(1);
                    }
                    this.writer.append(this.line + "\n");
                }
                else{
                    //write line to new macro definition
                    this.words.set(2, String.join(",", parameters));
                    this.line = String.join(" ", this.words);
                    this.line = this.line.trim();
                    this.macroTable.get(this.newMacroDefinition).writeBody(this.line);
                }
            }

            if (this.expansionLevelCounter > 0) {
                //read line from old macro definition named in current macro call
                
                do{
                    this.line = this.macroTable.get(this.macroCallStack.peek()).getLine();
                    if (this.line != null){
                        break;
                    }
                } while(!this.macroCallStack.isEmpty());
                
                if (this.line != null){
                    this.previousOpcode = this.words.get(1);
        
                    List<String> var = Arrays.asList(this.line.split(" "));
                    if (var.size() == 1){
                        this.words.set(0, "");
                        this.words.set(1, this.line.split(" ")[0]);
                        this.words.set(2, "");
                    }
                    else if(var.size() == 2){            
                        this.words.set(0, "");
                        this.words.set(1, this.line.split(" ")[0]);
                        this.words.set(2, this.line.split(" ")[1]);
                    } else{
                        this.words.set(0, this.line.split(" ")[0]);
                        this.words.set(1, this.line.split(" ")[1]);
                        this.words.set(2, this.line.split(" ")[2]);
                    }
                }else{
                    this.readLineInput();
                }
                
                //replace '#(k, i)' by
                //if k=1 then actual-parameter list[i] from actual-parameter stack
                //else '#(k-1, i)'
                if (this.words.get(2).contains("#")){
                    String[] parameters = this.words.get(2).split(",");

                    for (int i = 0; i < parameters.length; i++){
                        if (parameters[i].contains("#")){
                            char k = parameters[i].charAt(2);
                            int i2 = Character.getNumericValue(parameters[i].charAt(4));
                            if (k == '1'){
                                parameters[i] = this.actualParameterStack.peek().get(i2 - 1);
                            }else {
                                parameters[i] = parameters[i].substring(0, 2) +
                                        (Integer.parseInt(parameters[i].substring(2, 3)) - 1) +
                                        parameters[i].substring(3, 6);
                            }
                        }
                    }

                    this.words.set(2, String.join(",", parameters));   
                }
                
            } else {
                this.readLineInput();
            }
            
            //macro dubug
//            if (this.macroTable.get("MULTSC") != null){
//                List<String> teste = this.macroTable.get("MULTSC").getBody();
//                System.out.println("Macro MULTSC =====");
//                for(int i = 0; i<teste.size(); i++){
//                    System.out.println(teste.get(i));
//                }
//                System.out.println("=====");
//            } 
//            if (this.macroTable.get("DIVSC") != null){
//                List<String> teste = this.macroTable.get("DIVSC").getBody();
//                System.out.println("Macro DIVSC =====");
//                for(int i = 0; i<teste.size(); i++){
//                    System.out.println(teste.get(i));
//                }
//                System.out.println("=====");
//            }
//            if (this.macroTable.get("SCALE") != null){
//                List<String> teste = this.macroTable.get("SCALE").getBody();
//                System.out.println("Macro SCALE =====");
//                for(int i = 0; i<teste.size(); i++){
//                    System.out.println(teste.get(i));
//                }
//                System.out.println("=====");
//            }
//            if (this.macroTable.get("DISCR") != null){
//                List<String> teste = this.macroTable.get("DISCR").getBody();
//                System.out.println("Macro DISCR =====");
//                for(int i = 0; i<teste.size(); i++){
//                    System.out.println(teste.get(i));
//                }
//                System.out.println("=====");
//            }
//            if(this.actualParameterStack.size() > 1){
//            System.out.println("Stack Parameters =======");
//            for(int i = 0; i < this.actualParameterStack.size();i ++){
//                System.out.println(i+"=======");
//                for(int j = 0; j < this.actualParameterStack.get(i).size(); j++){
//                    System.out.println(this.actualParameterStack.get(i).get(j));
//                }
//            }
//            System.out.println("=======");
//            //System.out.println("stack size: " + this.actualParameterStack.size());   
//            }
        }
        this.writer.close();
        return true;
    }

    public void readLineInput() throws IOException {
        this.line = this.reader.readLine();
        this.lineCounter += 1;

        if (line == null) {
            return;
        }

        // discard comments
        if (this.line.split("\\*").length != 0){
            this.line = this.line.split("\\*")[0];
            // replace multiple spaces, tabs ... with single space
            this.line = this.line.replaceAll("\\s+", " ");

            this.previousOpcode = this.words.get(1);
            
            
            List<String> var = Arrays.asList(this.line.split(" "));
            if (var.size() == 1){
                this.words.set(0, "");
                this.words.set(1, this.line.split(" ")[0]);
                this.words.set(2, "");
            }
            else if(var.size() == 2){            
                this.words.set(0, "");
                this.words.set(1, this.line.split(" ")[0]);
                this.words.set(2, this.line.split(" ")[1]);
            } else{
                this.words.set(0, this.line.split(" ")[0]);
                this.words.set(1, this.line.split(" ")[1]);
                this.words.set(2, this.line.split(" ")[2]);
            }

            //this.words = Arrays.asList(this.line.split(" "));
        }else {
            this.line = "";
            this.previousOpcode = this.words.get(1);
            this.words.set(0, "");
            this.words.set(1, "");
            this.words.set(2, "");
        }
        
    }
}
