public class Player implements java.io.Serializable{

    private String playerName;
    private int numberOfCredits;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getNumberOfCredits() {
        return numberOfCredits;
    }

    public void setNumberOfCredits(int numberOfCredits) {
        this.numberOfCredits = numberOfCredits;
    }

    public Player(String playerName, int numberOfCredits) {
        this.playerName = playerName;
        this.numberOfCredits = numberOfCredits;
    }

    public Player() {
    }

    public Player(String playerName) {
        this.playerName = playerName;
        numberOfCredits = 100;
    }

    public String info() {
        return (getPlayerName() + " (" + getNumberOfCredits() + ")");
    }
}
