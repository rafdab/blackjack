import javax.swing.*;

public class Game {
    private Player player;
    private int playersPoints;
    private int dealersPoints;
    private int bet;

    public Game(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getPlayersPoints() {
        return playersPoints;
    }

    public void setPlayersPoints(int playersPoints) {
        this.playersPoints = playersPoints;
    }

    public int getDealersPoints() {
        return dealersPoints;
    }

    public void setDealersPoints(int dealersPoints) {
        this.dealersPoints = dealersPoints;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public void newGame(){
        playersPoints = 0;
        dealersPoints = 0;
        placeBet();
    }
    public void placeBet(){
        bet = player.getNumberOfCredits() + 1;
        while (bet > player.getNumberOfCredits()) bet = Integer.parseInt(JOptionPane.showInputDialog("How much are you willing to bet?", null));
    }

    public String hit(Card card){
        playersPoints += card.getCardPoints();
        return "" + playersPoints;
    }

    public String hitDealer(Card card){
        dealersPoints += card.getCardPoints();
        return "" + dealersPoints;
    }

    public void endGame(){
        if (playersPoints > 21){
            bet *= -1;
        } else if (dealersPoints > 21){
            bet *= 2;
        }
        if (playersPoints < 21 && dealersPoints < 21){
            if (dealersPoints >= playersPoints){
                bet *= -1;
            } else {
                bet *= 2;
            }
        }
        int tmp =  player.getNumberOfCredits() + bet;
        player.setNumberOfCredits(tmp);
        newGame();
    }
}
