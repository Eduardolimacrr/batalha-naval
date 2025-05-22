package jogo;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Scanner;

import embarcacoes.Embarcacao;
import persistencia.GameState;
import persistencia.Persistencia;
import persistencia.Ranking;

public class Jogo {
    private Jogador jogadorLocal;
    private String nomeOponente;
    private Mapa mapa;
    private PrintWriter out;
    private BufferedReader in;
    private Scanner scanner;
    private boolean ehServidor;
    private int embarcacoesAbatidas;
    private int embarcacoesAbatidasOponente = 0;

    public Jogo(String nomeJogadorLocal, String nomeOponente, PrintWriter out, BufferedReader in, boolean ehServidor) {
        this.jogadorLocal = new Jogador(nomeJogadorLocal);
        this.nomeOponente = nomeOponente;
        this.out = out;
        this.in = in;
        this.ehServidor = ehServidor;
        this.mapa = new Mapa(20, 20);
        this.mapa.alocarTodasEmbarcacoes();
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        boolean vezDoJogador = ehServidor;

        GameState salvo = Persistencia.carregar();
        if (salvo != null) {
            restaurarEstado(salvo);
            vezDoJogador = salvo.vezDoLocal;
        }

        while (embarcacoesAbatidas < 5 && embarcacoesAbatidasOponente < 5) {
            mapa.exibirMapaVisivel();
            System.out.println("Sua pontuação atual: " + jogadorLocal.getPontuacao());

            if (vezDoJogador) {
                System.out.println("Sua vez de atacar! Informe 'linha coluna' ou digite 'sair':");
                String entrada = scanner.nextLine().trim();

                if (entrada.equalsIgnoreCase("sair")) {
                    System.out.println("Você saiu do jogo.");
                    out.println("sair");
                    int pos = Ranking.registrar(jogadorLocal.getNome(), jogadorLocal.getPontuacao());
                    System.out.println("Sua pontuação foi registrada no ranking na posição: " + pos);
                    Persistencia.salvar(capturarEstado(vezDoJogador));
                    break;
                }

                String[] partes = entrada.split("\\s+|,");
                if (partes.length < 2) {
                    System.out.println("Entrada inválida.");
                    continue;
                }

                int linha, coluna;
                try {
                    linha = Integer.parseInt(partes[0]);
                    coluna = Integer.parseInt(partes[1]);
                } catch (NumberFormatException e) {
                    System.out.println("Coordenadas inválidas.");
                    continue;
                }

                out.println(linha + "," + coluna);

                try {
                    String resposta = in.readLine(); // recebe resultado do oponente                    
                    if (resposta == null || resposta.equalsIgnoreCase("sair")) {
                        System.out.println("O oponente desconectou ou saiu do jogo.");
                        int pos = Ranking.registrar(jogadorLocal.getNome(), jogadorLocal.getPontuacao());
                        System.out.println("Sua pontuação foi registrada no ranking na posição: " + pos);
                        Persistencia.salvar(capturarEstado(vezDoJogador));
                        System.exit(0);
                    } else if (resposta.equalsIgnoreCase("fim")) {
                        System.out.println("O jogo foi encerrado.");
                        int pos = Ranking.registrar(jogadorLocal.getNome(), jogadorLocal.getPontuacao());
                        System.out.println("Você ficou em " + pos + "º no ranking!");
                        jogadorLocal.exibirPontuacao();
                        Persistencia.limpar();
                        return; // encerra corretamente
                    }
                
                    System.out.println("Resultado do ataque: " + resposta);

                    if (resposta.startsWith("acertou:")) {
                        char simbolo = resposta.charAt(resposta.length() - 1);
                        jogadorLocal.registrarAcerto(Character.toLowerCase(simbolo));
                        mapa.getMapaVisivel()[linha][coluna] = simbolo;
                        embarcacoesAbatidas++;

                        String nome = Embarcacao.getNomeEmbarcacao(simbolo);
                        System.out.println("Você acertou um(a) " + nome + "!");

                    } else {
                        mapa.getMapaVisivel()[linha][coluna] = 'X';
                    }
                } catch (Exception e) {
                    System.err.println("Erro ao receber resposta do oponente.");
                    break;
                }

            } else {
                System.out.println("Esperando jogada do oponente...");
                try {
                    String coordenadas = in.readLine();
                    if (coordenadas == null || coordenadas.equalsIgnoreCase("sair")) {
                        System.out.println("O oponente encerrou a partida.");
                        int pos = Ranking.registrar(jogadorLocal.getNome(), jogadorLocal.getPontuacao());
                        System.out.println("Sua pontuação foi registrada no ranking na posição: " + pos);
                        Persistencia.salvar(capturarEstado(vezDoJogador));
                        break;
                    }

                    String[] partes = coordenadas.split(",");
                    int linha = Integer.parseInt(partes[0]);
                    int coluna = Integer.parseInt(partes[1]);

                    char simbolo = mapa.getMapaInterno()[linha][coluna];
                    if (simbolo != 'V') {
                        mapa.getMapaInterno()[linha][coluna] = 'Y';
                        out.println("acertou:" + simbolo);
                        embarcacoesAbatidasOponente++;
                    } else {
                        out.println("errou");
                    }

                } catch (Exception e) {
                    System.err.println("Erro ao receber jogada do oponente.");
                    break;
                }
            }

            Persistencia.salvar(capturarEstado(vezDoJogador));
            vezDoJogador = !vezDoJogador;
        }

        Persistencia.limpar();
        int pos = Ranking.registrar(jogadorLocal.getNome(), jogadorLocal.getPontuacao());
        System.out.println("Você ficou em " + pos + "º no ranking!");
        jogadorLocal.exibirPontuacao();
        out.println("fim");
    }

    private GameState capturarEstado(boolean vezDoLocal) {
        GameState gs = new GameState();
        gs.mapa = mapa;
        gs.jogadorLocal = jogadorLocal;
        gs.jogadorRemoto = new Jogador(nomeOponente);
        gs.vezDoLocal = vezDoLocal;
        gs.abatidasLocal = embarcacoesAbatidas;
        gs.abatidasRemoto = embarcacoesAbatidasOponente;
        return gs;
    }

    private void restaurarEstado(GameState gs) {
        this.mapa = gs.mapa;
        this.jogadorLocal = gs.jogadorLocal;
        this.nomeOponente = gs.jogadorRemoto.getNome();
        this.embarcacoesAbatidas = gs.abatidasLocal;
        this.embarcacoesAbatidasOponente = gs.abatidasRemoto;
    }
}
