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
    private List<String> words = new ArrayList<>(List.of("", "", ""));  // [label, opcode/macroName, ...]
    private HashMap<String, Macro> macroTable = new HashMap<>();
    private Stack<Parameters> formalParameterStack = new Stack<>();
    private Stack<ArrayList<String>> actualParameterStack = new Stack<>();
    private String previousOpcode;
    private String newMacroDefinition;
    private String currentMacroCall;

    public boolean processMacros(String path) throws IOException {
        System.out.println("===========MACRO LOGS============");
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
            System.out.print(this.definitionLevelCounter);
            System.out.print(" ");
            System.out.println(this.expansionLevelCounter);
            //System.out.println(this.words.get(1));
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
                        this.formalParameterStack.push(new Parameters(parameters[i], this.definitionLevelCounter, i));
                    }
                }
                if (this.definitionLevelCounter > 1){
                    //write line to new macro definition
                    this.macroTable.get(this.newMacroDefinition).writeBody(this.line);
                }
            } else if (this.macroTable.containsKey(this.words.get(1))) { // CASE opcode is MACRO NAME
                if (this.definitionLevelCounter == 0) {
                    this.currentMacroCall = this.words.get(1);
                    this.expansionLevelCounter += 1;

                    //prepare actual-parameter list
                    //push actual-parameter list on actual-parameter stack
                    String[] parameters = this.words.get(2).split(",");
                    ArrayList<String> auxList = new ArrayList<String>();
                    auxList.add(Integer.toString(this.lineCounter));
                    for (int i = 0; i < parameters.length; i++){
                        auxList.add(parameters[i]);
                    }
                    this.actualParameterStack.push(auxList);
                }
                if ((this.definitionLevelCounter > 0) && (this.expansionLevelCounter == 0)) {
                    //replace each formal parameter by topmost corresponding '#(k,i)' from formal-parameter stack
                }
                if (this.definitionLevelCounter > 0) {
                    //write line to new macro definition
                    this.macroTable.get(this.newMacroDefinition).writeBody(this.line);
                }
            }
            else if (this.words.get(1).equals("MEND")){ // CASE opcode is "MEND"
                if (this.definitionLevelCounter == 0){
                    this.expansionLevelCounter -= 1;

                    //pop actual-parameter list (level of expansionLevelCounter) from actual parameter stack
                } else { //definition mode
                    if (this.expansionLevelCounter == 0) {
                        //pop formal-parameter stack (level definitionLevelCounter)
                        while(!this.formalParameterStack.isEmpty() && this.formalParameterStack.peek().getLevel() == this.definitionLevelCounter){
                            this.formalParameterStack.pop();
                        }
                    }
                    if (this.definitionLevelCounter > 1){
                        this.macroTable.get(this.newMacroDefinition).writeBody(this.line);
                    }
                    //this.macroTable.get(this.newMacroDefinition).writeBody(this.line);
                    this.definitionLevelCounter -= 1;
                }
            }
            else{
                if ((this.expansionLevelCounter == 0) && (this.definitionLevelCounter > 0)){
                    //replace each formal parameter
                    //by topmost corresponging '#(k,i)'
                    //from formal-parameter stack
                }
                if (this.definitionLevelCounter == 0) {
                    //write line to output
                    System.out.println(this.line);
                    if (this.line.length() > 0){
                        this.line = this.line.substring(1);
                    }
                    this.writer.append(this.line + "\n");
                }
                else{
                    //write line to new macro definition
                    this.macroTable.get(this.newMacroDefinition).writeBody(this.line);
                }
            }

            if (this.expansionLevelCounter > 0) {
                //read line from old macro definition named in current macro call
                //System.out.println(this.currentMacroCall);
                this.line = this.macroTable.get(this.currentMacroCall).getLine();
                
                if (this.line != null){
                    this.previousOpcode = this.words.get(1);
        
                    this.words = Arrays.asList(this.line.split(" "));
                }else{
                    this.readLineInput();
                }
                
                //replace '#(k, i)' by
                //if k=1 then actual-parameter list[i] from actual-parameter stack
                //else '#(k-1, i)'
            } else {
                this.readLineInput();
            }
            
            //macro dubug
//            if (this.macroTable.get("A") != null){
//                List<String> teste = this.macroTable.get("A").getBody();
//                for(int i = 0; i<teste.size(); i++){
//                    System.out.println(teste.get(i));
//                }
//            }
//            
//            if (this.macroTable.get("B") != null){
//                List<String> teste = this.macroTable.get("B").getBody();
//                for(int i = 0; i<teste.size(); i++){
//                    System.out.println(teste.get(i));
//                }
//            }
        }
        this.writer.close();
        System.out.println("===========MACRO LOGS============");
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

            this.words = Arrays.asList(this.line.split(" "));
        }else {
            this.line = "";
            this.previousOpcode = this.words.get(1);
            this.words = List.of("", "", "");
        }
        
    }
}
