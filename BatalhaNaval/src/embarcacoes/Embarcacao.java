package embarcacoes;

import java.util.Map;

public class Embarcacao {
    protected int tamanho;
    protected char simbolo;
    protected String orientacao;
    protected String nome;

    public Embarcacao(int tamanho, char simbolo, String nome){
        this.tamanho = tamanho;
        this.simbolo = simbolo;
        this.nome = nome;
    }

    public int getTamanho(){
        return tamanho;
    }
    
    public char getSimbolo(){
        return simbolo;
    }
    
    public String getOrientacao(){
        return orientacao;
    }
    
    public String getNome(){
        return nome;
    }

    public void setOrientacao(String orientacao){
        this.orientacao = orientacao;
    }

    private static final Map<Character, String> nomesEmbarcacoes = Map.of(
    'A', "Porta-aviões",
    'D', "Destroyer",
    'S', "Submarino",
    'F', "Fragata",
    'B', "Bote"
    );

    public static String getNomeEmbarcacao(char simbolo) {
        return nomesEmbarcacoes.getOrDefault(simbolo, "Embarcação desconhecida");
    }


}
