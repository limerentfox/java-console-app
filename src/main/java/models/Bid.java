package models;

public class Bid {
    private final User user;
    private final double price;
    private final int quanity;
    private final String symbol;
    private final Boolean isClosed;

    public Bid (User user, double price, int quanity, String symbol, Boolean isClosed) {
        this.user = user;
        this.price = price;
        this.quanity = quanity;
        this.symbol = symbol;
    }

    public double getPrice() {
        return price;
    }

    public int getQuanity() {
        return quanity;
    }

    public String getSymbol() {
        return symbol;
    }

    public User getUser() {
        return user;
    }

    public Boolean getIsClosed() {
        return isClosed;
    }
}
