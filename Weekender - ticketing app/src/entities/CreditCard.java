package entities;

public class CreditCard {
    private CardType cardType;
    private String PAN;
    private String expirationMonth;
    private String expirationYear;
    private String cardHolder;
    private int CardId;
    private static int max = 1;
    private double balance;

    public CreditCard(int id, CardType cardType, String PAN, String expirationMonth, String expirationYear, String cardHolder) {
        this.CardId = id;
        max = Math.max(id,max) + 1;
        this.cardType = cardType;
        this.PAN = PAN;
        this.expirationMonth = expirationMonth;
        this.expirationYear = expirationYear;
        this.cardHolder = cardHolder;
    }
    public CreditCard(CardType cardType, String PAN, String expirationMonth, String expirationYear, String cardHolder) {
        this.CardId = max ++;
        this.cardType = cardType;
        this.PAN = PAN;
        this.expirationMonth = expirationMonth;
        this.expirationYear = expirationYear;
        this.cardHolder = cardHolder;
    }

    public int getCardId() {
        return CardId;
    }
    public void setBalance(double balance)
    {
        this.balance = balance;
    }
    public double getBalance(){return balance;}
    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public String getPAN() {
        return PAN;
    }

    public void setPAN(String PAN) {
        this.PAN = PAN;
    }

    public String getExpirationMonth() {
        return expirationMonth;
    }

    public void setExpirationMonth(String expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    public String getExpirationYear() {
        return expirationYear;
    }

    public void setExpirationYear(String expirationYear) {
        this.expirationYear = expirationYear;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "cardType=" + cardType +
                ", PAN='" + PAN + '\'' +
                ", expirationMonth='" + expirationMonth + '\'' +
                ", expirationYear='" + expirationYear + '\'' +
                ", cardHolder='" + cardHolder + '\'' +
                '}';
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
