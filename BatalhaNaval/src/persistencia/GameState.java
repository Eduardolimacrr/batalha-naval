package persistencia;

import java.io.Serializable;
import jogo.Mapa;
import jogo.Jogador;

/** Tudo que precisamos para recriar uma partida depois */
public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;

    public Mapa mapa;               // contém mapaInterno + mapaVisivel
    public Jogador jogadorLocal;    // nome + pontuação
    public Jogador jogadorRemoto;
    public boolean vezDoLocal;      // de quem é a vez
    public int abatidasLocal;
    public int abatidasRemoto;
}
