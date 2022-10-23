package br.com.trabalhops.macros;

import java.io.BufferedReader;
import java.io.FileReader;
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
    private BufferedReader reader;
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
    
    public boolean processMacros(String path) throws IOException {
        this.path = path;
        this.reader = new BufferedReader(new FileReader(this.path));
        
        this.definitionLevelCounter = 0;
        this.expansionLevelCounter = 0;
        this.previousOpcode = null;
        this.newMacroDefinition = null;
        
        this.readLineInput();
        
        while (this.line != null) {
            if (this.words.get(1) == "MACRO") { // CASE opcode is "MACRO"
                this.definitionLevelCounter += 1;
                if (this.definitionLevelCounter == 1){
                    //allocate new macro definition
                }
                else{
                    //write line to new macro definition
                }
            }
            else if (this.previousOpcode == "MACRO"){  // CASE opcode is PROTOTYPE
                if (this.expansionLevelCounter == 0){
                    //push ith formal parameter and (d,i) on formal-parameter stack
                }
                if (this.definitionLevelCounter > 0){
                    //write line to new macro definition
                }
            }
            else if (this.macroTable.containsKey(this.words.get(1))){ // CASE opcode is MACRO NAME
                if (this.definitionLevelCounter == 0){
                    this.expansionLevelCounter += 1;
                    //prepare actual-parameter list
                    //push actual-parameter list on actual-parameter stack
                }
                if ((this.definitionLevelCounter > 0) && (this.expansionLevelCounter == 0)){
                    //replace each formal parameter by topmost corresponding '#(k,i)' from formal-parameter stack
                }
                if (this.definitionLevelCounter > 0){
                    //write line to new macro definition
                }
            }
            else if (this.words.get(1) == "MEND"){ // CASE opcode is "MEND"
                if (this.definitionLevelCounter == 0){
                    //pop actual-parameter list (level of expansionLevelCounter) from actual parameter stack
                    this.expansionLevelCounter -= 1;
                }
                else{ //definition mode
                    if (this.expansionLevelCounter == 0){
                        //pop formal-parameter stack (level definitionLevelCounter)
                        this.definitionLevelCounter -= 1;
                        //write line to new macro definition
                    }
                }
            }
            else{
                if ((this.expansionLevelCounter == 0) && (this.definitionLevelCounter > 0)){

                }
                if(this.definitionLevelCounter == 0){
                    //write line to output
                }
                else{
                    //write line to new macro definition
                }
            }

            if (this.expansionLevelCounter > 0){
                //read line from old macro definition named in current macro call
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
