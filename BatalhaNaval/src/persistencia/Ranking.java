package persistencia;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Ranking {
    private static final String RANK_FILE = "ranking.txt";

    /** Grava pontuação e devolve a posição do jogador depois de ordenado. */
    public static int registrar(String nome, int pontos) {
        List<String> linhas = new ArrayList<>();
        try {
            if (Files.exists(Path.of(RANK_FILE)))
                linhas = Files.readAllLines(Path.of(RANK_FILE));
        } catch (IOException ignore) { }

        linhas.add(nome + "," + pontos);          // adiciona nova linha
        // Converte para pares (nome, pontos)
        List<String[]> pares = new ArrayList<>();
        for (String l : linhas) pares.add(l.split(","));
        // Ordena por pontos desc.
        pares.sort((a, b) -> Integer.compare(
                Integer.parseInt(b[1]), Integer.parseInt(a[1])));

        // Regrava o arquivo já ordenado
        try (PrintWriter pw = new PrintWriter(new FileWriter(RANK_FILE))) {
            for (String[] p : pares)
                pw.println(p[0] + "," + p[1]);
        } catch (IOException ignore) { }

        // Procura a posição (1-based) do jogador que acabou de jogar
        for (int i = 0; i < pares.size(); i++)
            if (pares.get(i)[0].equals(nome) &&
                Integer.parseInt(pares.get(i)[1]) == pontos)
                return i + 1;
        return pares.size(); // fallback
    }
}
