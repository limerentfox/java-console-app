package models;

import java.time.LocalDateTime;

public class Bid {
    private String bidder;
    private String auctionSymbol;
    private Integer auctionId;
    private double price;
    private int quantity;
    private LocalDateTime submissionTime;

    public Bid(String bidder, String auctionSymbol, int auctionId, double price, int quantity, LocalDateTime submissionTime) {
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

    public LocalDateTime getSubmissionTime() {
        return submissionTime;
    }
}

