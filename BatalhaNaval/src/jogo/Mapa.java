package jogo;

import java.io.Serializable;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Mapa implements Serializable {
    private static final long serialVersionUID = 1L;

    private final int linhas;
    private final int colunas;
    private final char[][] mapaVisivel;
    private final char[][] mapaInterno;
    private transient final Random random = new Random();  // <--- transient aqui!

    private static final char OCULTO = 'O';
    private static final char VAZIO = 'V';

    public Mapa(int linhas, int colunas) {
        this.linhas = linhas;
        this.colunas = colunas;
        this.mapaVisivel = new char[linhas][colunas];
        this.mapaInterno = new char[linhas][colunas];
        inicializarMapas();
    }

    private void inicializarMapas(){
    
        for (int i = 0; i < linhas; i++){
            for (int j = 0; j < colunas; j++){
                mapaInterno[i][j] = VAZIO;
                mapaVisivel[i][j] = OCULTO;
            }
        }
    }

    public void exibirMapaVisivel(){
        for (int i = 0; i < linhas; i++){
            for (int j = 0; j < colunas; j++){
                System.out.print(mapaVisivel[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void exibirMapaInterno(){
        for (int i = 0; i < linhas; i++){
            for (int j = 0; j < colunas; j++){
                System.out.print(mapaInterno[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    
    public char[][] getMapaInterno() {
        return mapaInterno;
    }
    
    public char[][] getMapaVisivel() {
        return mapaVisivel;
    }

    //////////////////////////////////////////////////////////////////////////// alocação no mapa
    

    private boolean podeAlocar(int linha, int coluna, int tamanho, String orientacao) {
        int dx = 0, dy = 0;
        switch (orientacao) {
            case "horizontal": dy = 1; break;
            case "vertical": dx = 1; break;
            case "diagonal": dx = 1; dy = 1; break;
        }
    
        for (int i = -1; i <= tamanho; i++) {
            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    int nx = linha + dx * i + x;
                    int ny = coluna + dy * i + y;
                    if (nx >= 0 && ny >= 0 && nx < linhas && ny < colunas) {
                        if (i >= 0 && i < tamanho) {
                            if (mapaInterno[nx][ny] != VAZIO) return false;
                        } else {
                            if (mapaInterno[nx][ny] != VAZIO && mapaInterno[nx][ny] != OCULTO) return false;
                        }
                    } else if (i >= 0 && i < tamanho) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
 
    private List<String> distribuirOrientacoesProporcionalmente(int quantidade) {
        List<String> orientacoes = new ArrayList<>();
        int base = quantidade / 3;
        int resto = quantidade % 3;
    
        for (int i = 0; i < base; i++) {
            orientacoes.add("horizontal");
            orientacoes.add("vertical");
            orientacoes.add("diagonal");
        }
    
        String[] todas = {"horizontal", "vertical", "diagonal"};
        for (int i = 0; i < resto; i++) {
            orientacoes.add(todas[i]);
        }
    
        return orientacoes;
    }
    
    
    private void alocarEmbarcacao(int linha, int coluna, int tamanho, String orientacao, char simbolo) {
        int dx = 0, dy = 0;
        switch (orientacao) {
            case "horizontal": dy = 1; break;
            case "vertical": dx = 1; break;
            case "diagonal": dx = 1; dy = 1; break;
        }
    
        for (int i = 0; i < tamanho; i++) {
            mapaInterno[linha + dx * i][coluna + dy * i] = simbolo;
        }
    }
    

    private void alocarEmbarcacoes(int quantidade, int tamanho, char simbolo) {
        List<String> orientacoes = distribuirOrientacoesProporcionalmente(quantidade);
        Collections.shuffle(orientacoes, random);
    
        for (String orientacao : orientacoes) {
            boolean alocado = false;
            while (!alocado) {
                int linha = random.nextInt(linhas);
                int coluna = random.nextInt(colunas);
    
                if (podeAlocar(linha, coluna, tamanho, orientacao)) {
                    alocarEmbarcacao(linha, coluna, tamanho, orientacao, simbolo);
                    alocado = true;
                }
            }
        }
    }  


    public void alocarTodasEmbarcacoes() {
        alocarEmbarcacoes(2, 8, 'A'); // Porta-aviões
        alocarEmbarcacoes(3, 5, 'D'); // Destroyers
        alocarEmbarcacoes(4, 4, 'S'); // Submarinos
        alocarEmbarcacoes(5, 3, 'F'); // Fragatas
        alocarEmbarcacoes(6, 2, 'B'); // Botes
    }

    
}