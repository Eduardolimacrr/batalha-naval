package persistencia;

import java.io.*;

public class Persistencia {
    private static final String SAVE_FILE = "gamestate.dat";

    public static void salvar(GameState estado) {
        try (ObjectOutputStream oos =
                 new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {
            oos.writeObject(estado);
        } catch (IOException e) {
            System.err.println("Falha ao salvar partida: " + e.getMessage());
        }
    }

    public static GameState carregar() {
        try (ObjectInputStream ois =
                 new ObjectInputStream(new FileInputStream(SAVE_FILE))) {
            return (GameState) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;            // não existe save ou corrompido
        }
    }

    /** Apaga o checkpoint quando a partida termina. */
    public static void limpar() {
        new File(SAVE_FILE).delete();
    }
}
