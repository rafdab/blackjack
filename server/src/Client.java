import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;

//this class handles connection with client app and prepares it to game
public class Client extends Thread {
    private Player player;
    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private ArrayList<Card> deck;
    private Card card;
    private Game game;

    //where game is happening
    public void run(){
        game = new Game(player);
        System.out.println("New player connected, and his name is " + player.info());

        while (true) {
            card.setValue(1);

            while (card.getValue() > 0){
                System.out.println(player.info() + " is starting a new round");
                getCard();
                deck = createDeck();
                game.newRound();
                game.setBet(card.getValue());

                if (card.getValue() > 0 && !card.getSuit().equals("dStand")){
                    getCard();
                    while (card.getValue() == 1) {
                        if (card.getValue() == 1 && card.getSuit().equals("Hit!")){
                            System.out.println("" + player.info() + " hits");

                            card = deal();
                            sendCard();

                            game.addPlayersPoints(card.getCardPoints());
                            System.out.println("" + player.info() + " has " + game.getPlayersPoints() + " points");
                        }
                        getCard();
                        if (card.getValue() == 1 && card.getSuit().equals("Stand!")){
                            onStand();
                            break;
                        }
                    }
                } else { break; }
            }

            if (card.getValue() == 0){
                //save player's score to file

                break;
            } else if (card.getValue() < 0){
                //send a file

            }
        }
        System.out.println(player.info() + " left");
    }

    void onStand(){
        System.out.println("" + player.info() + " stands");
        while (game.getDealersPoints() < 17 && game.checkPoints()){
            System.out.println("Dealer playing with " + player.info() + " hits");

            card = deal();
            sendCard();

            game.addDealersPoints(card.getCardPoints());
            System.out.println("Dealer playing with " + player.info() + " has " + game.getDealersPoints() + " points");
        }
        System.out.println("Dealer playing with " + player.info() + " stands");
        card = new Card("dStand", 1);
        game.checkWinner();
        player.setNumberOfCredits(game.getPlayer().getNumberOfCredits());
        sendCard();
    }

    public Client(Socket clientSocket) {
        card = new Card(null, 0);
        this.socket = clientSocket;
        try {
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
            player = (Player) inputStream.readObject();
            card.setFields(player.getPlayerName(), 0);
            sendCard();

        } catch (IOException e) { //i/o streams
            System.err.println("Connection to client failed");
        } catch (ClassNotFoundException e) { // type conversion
            System.err.println("Wrong class exception");
        }
    }

    private static ArrayList<Card> createDeck(){
        ArrayList<Card> deck = new ArrayList<>();
        deck.clear();
        for (int i = 2; i <=14; ++i){
            deck.add(new Card("Diamonds", i));
            deck.add(new Card("Clubs", i));
            deck.add(new Card("Hearts", i));
            deck.add(new Card("Spades", i));
        }
        Collections.shuffle(deck);
        return deck;
    }

    private Card deal(){
        Card tmp = deck.get(deck.size()-1);
        deck.remove(deck.size()-1);
        return tmp;
    }

    void sendCard() {
        try {
            System.out.println("\tSending card (" + card.printCard() + ") to " + player.info());
            outputStream.writeObject(card);
        } catch (IOException e) {
            System.err.println("Could not send a card. Connection error");
        }
    }

    void getCard(){
        try {
            System.out.println("\tWaiting for a card from " + player.info());
            card = (Card) inputStream.readObject();
            System.out.println("\tReceived a card (" + card.printCard() + ") from " + player.info());
        } catch (EOFException e) {
            System.err.println("Player " + player.info() + " disconnected");
            card.setFields(null, 0);
        } catch (IOException e) {
            System.err.println("Connection failed");
            e.printStackTrace();
            card.setFields(null, 0);
        } catch (ClassNotFoundException e) {
            System.err.println("Received something else than a Card");
            card.setFields(null, 0);
        }
    }
}
