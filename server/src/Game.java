public class Game {
    private Player player;
    private int playersPoints;
    private int dealersPoints;
    private int bet;

    public int getPlayersPoints() {
        return playersPoints;
    }

    public int getDealersPoints() {
        return dealersPoints;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
        System.out.println(player.info() + " bet " + bet + " credits");
    }

    public Player getPlayer(){
        return player;
    }

    public void addPlayersPoints(int value) { playersPoints += value; }
    public void addDealersPoints(int value) { dealersPoints += value; }

    public Game(Player player) {
        this.player = player;
    }

    public Player updatePlayer() {
        return player;
    }

    public void newRound(){
        playersPoints = 0;
        dealersPoints = 0;
        bet = 0;
    }

    public Boolean checkPoints(){
        if (playersPoints > dealersPoints) return true;
        return false;
    }

    public void checkWinner(){
        if (playersPoints > 21){
            bet *= -1;
            System.out.println("Dealer("+player.info()+") wins");
        } else if (dealersPoints > 21){
            bet *= 2;
            System.out.println("Player("+player.info()+") wins");
        }
        else{
            if (dealersPoints >= playersPoints){
                bet *= -1;
                System.out.println("Dealer("+player.info()+") wins");
            } else {
                bet *= 2;
                System.out.println("Player("+player.info()+") wins");
            }
        }
        int tmp =  player.getNumberOfCredits() + bet;
        player.setNumberOfCredits(tmp);
    }
}
