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
    }
}
