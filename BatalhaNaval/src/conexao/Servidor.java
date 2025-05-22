package conexao;

import jogo.Jogo;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Servidor {
    public static void iniciar() throws IOException {
        ServerSocket servidor = new ServerSocket(12345);
        System.out.println("Aguardando conexão...");

        Socket socket = servidor.accept();
        System.out.println("Cliente conectado!");

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite seu nome: ");
        String nomeLocal = scanner.nextLine();
        out.println(nomeLocal); // envia nome para o cliente

        String nomeRecebido = in.readLine(); // recebe nome do cliente
        System.out.println("Conectado com " + nomeRecebido);

        Jogo jogo = new Jogo(nomeLocal, nomeRecebido, out, in, true);
        jogo.iniciar();

        socket.close();
        servidor.close();
        scanner.close();
    }
}
