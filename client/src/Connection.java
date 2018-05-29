import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Connection {
    private Player player;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Card card;

    public Player getPlayer() {
        return player;
    }

    public Card getCard() {
        try {
            System.out.println("Waiting for a card...");
            card = (Card) in.readObject();
            System.out.println("Received card(" + card.printCard() + ")");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace(); //connection
        }
        return card;
    }

    public void sendCard(Card card) {
        try {
            out.writeObject(card);
            System.out.println("Sending card (" + card.printCard() + ")");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Connection(String ip, String port, String playerName) {
        player = new Player(playerName);
        try {
            socket = new Socket(ip, Integer.parseInt(port));
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            out.writeObject(player);
        } catch (IOException e) {
            System.err.println("Unknown host");
            System.exit(-1);
        }
    }
}
