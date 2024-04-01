package models;

import java.util.ArrayList;
import java.util.List;

public record AuctionSummary(int auctionId, String symbol, List<Bid> winningBids, double totalRevenue,
                             int totalSoldQuantity) {
    public AuctionSummary(int auctionId, String symbol, List<Bid> winningBids, double totalRevenue, int totalSoldQuantity) {
        this.auctionId = auctionId;
        this.symbol = symbol;
        this.winningBids = new ArrayList<>(winningBids); // Ensure immutability by copying
        this.totalRevenue = totalRevenue;
        this.totalSoldQuantity = totalSoldQuantity;
    }

    @Override
    public List<Bid> winningBids() {
        return new ArrayList<>(winningBids);
    }
}
