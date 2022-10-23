package br.com.trabalhops.macros;

/**
 *
 * @author Luzo
 */
public class Parameters {
    
    private String name;
    private int level;
    private int position;
    
    public Parameters(String name, int level, int position) {
        this.name = name;
        this.level = level;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
