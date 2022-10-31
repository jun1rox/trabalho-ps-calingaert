package br.com.trabalhops.macros;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
    private List<String> words;  // [label, opcode/macroName, ...]
    private HashMap<String, Macro> macroTable = new HashMap<>();
    private Stack<Parameters> formalParameterStack = new Stack<>();
    // actualParameterStack / to be defined /
    private String previousOpcode;
    private String newMacroDefinition;
    private String currentMacroCall;
    
    public boolean processMacros(String path) throws IOException {
        this.path = path;
        this.reader = new BufferedReader(new FileReader(this.path));
        this.writer = new BufferedWriter(new FileWriter(this.outputFile));
        
        this.definitionLevelCounter = 0;
        this.expansionLevelCounter = 0;
        this.previousOpcode = null;
        this.newMacroDefinition = null;
        this.currentMacroCall = null;
        
        this.readLineInput();
        
        while (this.line != null) {
            if (this.words.get(1) == "MACRO") { // CASE opcode is "MACRO"
                this.definitionLevelCounter += 1;
                if (this.definitionLevelCounter == 1){
                    //allocate new macro definition
                }
                else{
                    //write line to new macro definition
                    this.macroTable.get(this.newMacroDefinition).writeBody(this.line);
                }
            }
            else if (this.previousOpcode == "MACRO"){  // CASE opcode is PROTOTYPE
                String[] parameters = this.words.get(1).split(",");
                this.macroTable.put(this.words.get(1), new Macro(parameters));  //allocation must happen here because name of macro is only known now
                if (this.expansionLevelCounter == 0){
                    //push ith formal parameter and (d,i) on formal-parameter stack
                    for(int i = 0; i <= parameters.length; i++){
                        this.formalParameterStack.push(new Parameters(parameters[i], this.definitionLevelCounter, i));
                    }
                }
                if (this.definitionLevelCounter > 0){
                    //write line to new macro definition
                    this.macroTable.get(this.newMacroDefinition).writeBody(this.line);
                }
            }
            else if (this.macroTable.containsKey(this.words.get(1))){ // CASE opcode is MACRO NAME
                if (this.definitionLevelCounter == 0){
                    this.currentMacroCall = this.words.get(1);
                    this.expansionLevelCounter += 1;
                    
                    //prepare actual-parameter list
                    //push actual-parameter list on actual-parameter stack
                }
                if ((this.definitionLevelCounter > 0) && (this.expansionLevelCounter == 0)){
                    //replace each formal parameter by topmost corresponding '#(k,i)' from formal-parameter stack
                }
                if (this.definitionLevelCounter > 0){
                    //write line to new macro definition
                    this.macroTable.get(this.newMacroDefinition).writeBody(this.line);
                }
            }
            else if (this.words.get(1) == "MEND"){ // CASE opcode is "MEND"
                if (this.definitionLevelCounter == 0){
                    this.expansionLevelCounter -= 1;
                    
                    //pop actual-parameter list (level of expansionLevelCounter) from actual parameter stack
                }
                else{ //definition mode
                    if (this.expansionLevelCounter == 0){
                        //pop formal-parameter stack (level definitionLevelCounter)
                        while(this.formalParameterStack.peek().getLevel() == this.definitionLevelCounter){
                            this.formalParameterStack.pop();
                        }
                    }
                    this.definitionLevelCounter -= 1;
                    //write line to new macro definition
                    this.macroTable.get(this.newMacroDefinition).writeBody(this.line);
                }
            }
            else{
                if ((this.expansionLevelCounter == 0) && (this.definitionLevelCounter > 0)){

                }
                if(this.definitionLevelCounter == 0){
                    //write line to output
                    this.writer.append(this.line);
                }
                else{
                    //write line to new macro definition
                    this.macroTable.get(this.newMacroDefinition).writeBody(this.line);
                }
            }

            if (this.expansionLevelCounter > 0){
                //read line from old macro definition named in current macro call
                this.line = this.macroTable.get(this.currentMacroCall).getLine();
                //replace '#(k, i)' by
                //if k=1 then actual-parameter list[i] from actual-parameter stack
                //else '#(k-1, i)'
            }
            else{
                this.readLineInput();
            }
        }
        return true;
    }
    
    public void readLineInput() throws IOException{
        this.line = this.reader.readLine();
        this.lineCounter += 1;
        
        if (line == null){
            return;
        }
        
        // discard comments
        this.line = this.line.split("\\*")[0];
        // replace multiple spaces, tabs ... with single space
        this.line = this.line.replaceAll("\\s+", " ");

        this.previousOpcode = this.words.get(1);
        
        this.words = Arrays.asList(this.line.split(" "));
        // need to verify errors ??????
    }
}
