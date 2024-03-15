package models;

import java.time.Instant;


public class Bid {
    private final String bidder;
    private final String auctionSymbol;
    private final Integer auctionId;
    private final double price;
    private final int quantity;
    private final Instant submissionTime;

    public Bid(String bidder, String auctionSymbol, int auctionId, double price, int quantity, Instant submissionTime) {
        if (price <= 0 || quantity <= 0) throw new IllegalArgumentException("Invalid bid parameters.");
        this.bidder = bidder;
        this.auctionSymbol = auctionSymbol;
        this.price = price;
        this.quantity = quantity;
        this.auctionId = auctionId;
        this.submissionTime = submissionTime;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getAuctionSymbol() {
        return auctionSymbol;
    }

    public Integer getAuctionId() {
        return auctionId;
    }

    public String getBidder() {
        return bidder;
    }

    public double getPrice() {
        return price;
    }

    public Instant getSubmissionTime() {
        return submissionTime;
    }
}

