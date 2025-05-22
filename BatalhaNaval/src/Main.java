import conexao.Servidor;
import conexao.Cliente;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Menu ---");
            System.out.println("[s] Servidor");
            System.out.println("[c] Cliente");
            System.out.println("[r] Ver ranking");
            System.out.println("[x] Sair");
            System.out.print("Escolha uma opção: ");
            String escolha = scanner.nextLine().trim().toLowerCase();

            try {
                switch (escolha) {
                    case "s":
                        Servidor.iniciar();
                        break;
                    case "c":
                        Cliente.iniciar();
                        break;
                    case "r":
                        exibirRanking();
                        break;
                    case "x":
                        System.out.println("Encerrando o programa.");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void exibirRanking() {
        System.out.println("\n--- Ranking ---");
        try {
            Path caminho = Path.of("ranking.txt");
            if (!Files.exists(caminho)) {
                System.out.println("Nenhum dado de ranking encontrado.");
                return;
            }

            List<String> linhas = Files.readAllLines(caminho);
            int posicao = 1;
            for (String linha : linhas) {
                String[] partes = linha.split(",");
                if (partes.length >= 2) {
                    String nome = partes[0];
                    String pontos = partes[1];
                    System.out.println(posicao + "º - " + nome + " - " + pontos + " pontos");
                    posicao++;
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o ranking.");
        }
    }
}
