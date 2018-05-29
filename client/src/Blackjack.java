import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Blackjack implements ActionListener {
    Connection connection;
    private JPanel contentPanel;
    private JPanel dealersPanel;
    private JPanel playersPanel;
    private JPanel buttonsPanel;
    private JPanel playerInfoPanel;
    private JPanel playerGamePanel;
    private JPanel dealersGamePanel;
    private JPanel dealersInfoPanel;

    private JButton hitButton;
    private JButton standButton;

    private JLabel dealersPointsLabel;
    private JLabel playersPointsLabel;
    private JLabel creditsLabel;

    private JTextPane playersHandPane;
    private JTextPane dealersHandPane;

    private Card card;
    private Game game;

    public Blackjack() {
    }

    public Blackjack(Connection connection) {
        JFrame gameFrame = new JFrame("Blackjack");
        gameFrame.setContentPane(contentPanel);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        hitButton.addActionListener(this);
        standButton.addActionListener(this);
        gameFrame.setSize(500, 400);
        gameFrame.setVisible(true);

        playersPointsLabel.setText("");
        playersHandPane.setText("");
        playersHandPane.setEnabled(false);

        creditsLabel.setText("100");
        dealersHandPane.setText("");
        dealersHandPane.setEnabled(false);
        dealersPointsLabel.setText("");

        game = new Game(connection.getPlayer());
        this.connection = connection;
        card = connection.getCard();

        game.newGame();
        card.setValue(game.getBet());
        card.setSuit("bet");
        connection.sendCard(card);

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == hitButton){
            System.out.println("Hit!");
            onHit();
        }
        if (actionEvent.getSource() == standButton){
            System.out.println("Stand!");
            onStand();
        }
    }

    private void onHit(){
        connection.sendCard(new Card("Hit!", 1));  //send card request
        card = connection.getCard();
        String tmp = playersHandPane.getText() + "\n" + card.printCard();
        playersHandPane.setText(tmp);
        playersPointsLabel.setText(game.hit(card));

        if (game.getPlayersPoints() > 20){
            System.out.println("Cannot draw more cards");
        }

    }

    private void onStand(){
        disableButtons();
        connection.sendCard(new Card("Stand!", 1)); //send stand signal

        ///dealer's turn
        card = connection.getCard();
        while (!card.getSuit().equals("dStand")){
            String tmp = dealersHandPane.getText() + "\n" + card.printCard();
            dealersHandPane.setText(tmp);
            dealersPointsLabel.setText(game.hitDealer(card));
            card = connection.getCard();
        }

        //endgame
        game.endGame();
        connection.sendCard(new Card("bet", game.getBet()));
        clearFields();

        enableButtons();
    }

    private void disableButtons(){
        hitButton.setEnabled(false);
        standButton.setEnabled(false);
    }

    private void enableButtons(){
        hitButton.setEnabled(true);
        standButton.setEnabled(true);
    }

    private void clearFields(){
        playersPointsLabel.setText("");
        playersHandPane.setText("");

        creditsLabel.setText("" + game.getPlayer().getNumberOfCredits());
        dealersHandPane.setText("");
        dealersPointsLabel.setText("");

    }
}
