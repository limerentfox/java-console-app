package models;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Auction {
    private static int lastId = 0;
    private final int id;
    private final String symbol;
    private final int quantity;
    private  final double minimumPrice;
    private boolean isOpen;

    // use a different type
    private Instant closingTime;
    private final String owner;
    private final List<Bid> bids;

    private AuctionSummary auctionSummary;
    private final List<Bid> winningBids = new ArrayList<>();
    private double totalRevenue = 0;
    private int totalSoldQuantity = 0;

    public Auction(String symbol, int quantity, double minimumPrice, String owner) {
        if (quantity < 0 || minimumPrice <= 0) throw new IllegalArgumentException("Invalid auction parameters.");
        this.id = ++lastId; // Increment and assign the next ID
        this.symbol = symbol;
        this.quantity = quantity;
        this.minimumPrice = minimumPrice;
        this.owner = owner;
        this.isOpen = true;
        this.bids = new ArrayList<>();
    }

    public String getSymbol() {
        return symbol;
    }

    public int getId() {
        return id;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public double getMinimumPrice() {
        return minimumPrice;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getTotalSoldQuantity() {
        return totalSoldQuantity;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public List<Bid> getWinningBids() {
        return winningBids;
    }

    public Instant getClosingTime() {
        return closingTime;
    }

    public String getOwner() {
        return owner;
    }

    public void closeAuction() {
        if (!this.isOpen) return;

        this.isOpen = false;
        this.closingTime = Instant.now();

        List<Bid> sortedBids = bids.stream()
                .filter(bid -> bid.getPrice() >= this.minimumPrice)
                .sorted(Comparator.comparing(Bid::getPrice).reversed()
                        .thenComparing(Bid::getSubmissionTime))
                .collect(Collectors.toList());

        int remainingQuantity = this.quantity;
        for (Bid bid : sortedBids) {
            if (remainingQuantity == 0) break;

            if (bid.getQuantity() <= remainingQuantity) {
                winningBids.add(bid);
                totalRevenue += bid.getPrice() * bid.getQuantity();
                totalSoldQuantity += bid.getQuantity();
                remainingQuantity -= bid.getQuantity();
            } else {
                winningBids.add(new Bid(bid.getBidder(), this.symbol, this.id, bid.getPrice(), remainingQuantity, bid.getSubmissionTime()));
                totalRevenue += bid.getPrice() * remainingQuantity;
                totalSoldQuantity += remainingQuantity;
                remainingQuantity = 0;
            }
        }

        this.setAuctionSummary(this.id, this.symbol, winningBids, totalRevenue, totalSoldQuantity);

    }


    private void setAuctionSummary(int id, String symbol, List<Bid> winningBids, double totalRevenue, int totalSoldQuantity) {
       this.auctionSummary = new AuctionSummary(id, symbol, winningBids, totalRevenue, totalSoldQuantity);
    }

    public AuctionSummary getAuctionSummary() {
        return this.auctionSummary;
    }
}