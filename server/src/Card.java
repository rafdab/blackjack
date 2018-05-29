public class Card implements java.io.Serializable{
    private String suit;
    private int value;

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setFields(String suit, int value){
        this.suit = suit;
        this.value = value;
    }

    public Card() {
    }

    public Card(String suit, int value) {
        this.suit = suit;
        this.value = value;
    }

    public String printCard(){
        String tmp;
        switch (value){
            case 11:
                tmp = "Ace of " + suit;
                break;
            case 12:
                tmp = "Jack of " + suit;
                break;
            case 13:
                tmp = "Queen of " + suit;
                break;
            case 14:
                tmp = "King of " + suit;
                break;
            default:
                tmp = value + " of " + suit;
                break;
        }
        return tmp;
    }

    public int getCardPoints(){
        if (value > 11) return 10;
        return value;
    }
}
