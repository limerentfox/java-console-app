package models;

import java.util.ArrayList;
import java.util.List;

public class AuctionSummary {
    private final int auctionId;
    private final String symbol;
    private final List<Bid> winningBids;
    private final double totalRevenue;
    private final int totalSoldQuantity;

    public AuctionSummary(int auctionId, String symbol, List<Bid> winningBids, double totalRevenue, int totalSoldQuantity) {
        this.auctionId = auctionId;
        this.symbol = symbol;
        this.winningBids = new ArrayList<>(winningBids); // Ensure immutability by copying
        this.totalRevenue = totalRevenue;
        this.totalSoldQuantity = totalSoldQuantity;
    }

    public List<String> getWonBidsInfo() {
        List<String> wonBidsInfo = new ArrayList<>();
        for (Bid bid : winningBids) {
            String info = String.format("User: %s, Quantity: %d, Price: %.2f", bid.getBidder(), bid.getQuantity(), bid.getPrice());
            wonBidsInfo.add(info);
        }
        return wonBidsInfo;
    }

    // Getters
    public int getAuctionId() {
        return auctionId;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public int getTotalSoldQuantity() {
        return totalSoldQuantity;
    }

    public List<Bid> getWinningBids() {
        return new ArrayList<>(winningBids);
    }

    // Method to summarize auction results for display
    // move the responsibility of the strings into a helper class
    public String summarize() {
        StringBuilder summary = new StringBuilder();
        summary.append("Auction ID: ").append(auctionId)
                .append("\nSymbol: ").append(symbol)
                .append("\nTotal Revenue: $").append(String.format("%.2f", totalRevenue))
                .append("\nTotal Sold Quantity: ").append(totalSoldQuantity)
                .append("\nWinning Bids:\n");

        getWonBidsInfo().forEach(bidInfo -> summary.append("  ").append(bidInfo).append("\n"));

        return summary.toString();
    }
}
