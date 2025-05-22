package jogo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import embarcacoes.Embarcacao;

public class Jogador implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nome;
    private int pontuacao;
    private transient Map<Character, Integer> pontosPorEmbarcacao;

    public Jogador(String nome) {
        this.nome = nome;
        this.pontuacao = 0;
        inicializarPontuacoes();
    }

    private void inicializarPontuacoes() {
        pontosPorEmbarcacao = new HashMap<>();
        pontosPorEmbarcacao.put('a', 10); // Porta-Aviões
        pontosPorEmbarcacao.put('d', 8);  // Destroyer
        pontosPorEmbarcacao.put('s', 6);  // Submarino
        pontosPorEmbarcacao.put('f', 4);  // Fragata
        pontosPorEmbarcacao.put('b', 2);  // Bote
    }

    public void registrarAcerto(char simbolo) {
        if (pontosPorEmbarcacao == null) {
            inicializarPontuacoes();  // para caso de desserialização
        }
        if (pontosPorEmbarcacao.containsKey(simbolo)) {
            pontuacao += pontosPorEmbarcacao.get(simbolo);
            System.out.println(nome + " acertou uma embarcação '" + simbolo + "' e ganhou " + pontosPorEmbarcacao.get(simbolo) + " pontos!");
        } else {
            System.out.println("Acerto inválido ou símbolo desconhecido: " + simbolo);
        }
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public String getNome() {
        return nome;
    }

    public void exibirPontuacao() {
        System.out.println("Pontuação de " + nome + ": " + pontuacao + " pontos.");
    }

    
}
