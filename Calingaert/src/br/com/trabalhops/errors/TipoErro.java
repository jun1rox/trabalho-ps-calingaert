package br.com.trabalhops.errors;

/**
 *
 * @author gsutavo
 */
public enum TipoErro {
    
    CARACTER_INVALIDO("Caracter inválido."),
    LINHA_LONGA("Linha muito Longa."),
    DIGITO_INVALIDO("Dígito inválido."),
    ESPACO_ESPERADO("Espaço ou final de linha esperado."),
    FORA_DOS_LIMITES("Valor fora dos limites."),
    ERRO_SINTAXE("Erro de sintaxe."),
    SIMBOLO_REDEFINIDO("Símbolo redefinido."),
    SIMBOLO_NAO_DEFINIDO("Símbolo não definido."),
    INSTRUCAO_INVALIDA("Instrução inválida."),
    FALTA_END("Falta diretiva END.");
    
    private final String descricao;
    
    private TipoErro(String descricao) {
        this.descricao = descricao;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
}
