package entities;

import java.time.LocalDate;
import entities.CreditCard;

public class CreditCard {
    private CardType cardType;
    private String PAN;
    private String expirationMonth;
    private String expirationYear;
    private String cardHolder;
    private double balance;

    public CreditCard(CardType cardType, String PAN, String expirationMonth, String expirationYear, String cardHolder) {
        this.cardType = cardType;
        this.PAN = PAN;
        this.expirationMonth = expirationMonth;
        this.expirationYear = expirationYear;
        this.cardHolder = cardHolder;
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
}
