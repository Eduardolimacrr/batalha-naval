package conexao;

import jogo.Jogo;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cliente {
    public static void iniciar() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o IP do servidor: ");
        String ip = scanner.nextLine();

        Socket socket = new Socket(ip, 12345);

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        System.out.print("Digite seu nome: ");
        String nomeLocal = scanner.nextLine();
        out.println(nomeLocal); // envia nome ao servidor

        String nomeRecebido = in.readLine(); // recebe nome do servidor
        System.out.println("Conectado com " + nomeRecebido);

        Jogo jogo = new Jogo(nomeLocal, nomeRecebido, out, in, false);
        jogo.iniciar();

        socket.close();
        scanner.close();
    }
}
