package models;

import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Auction {
    private static int lastId = 0;
    private final int id;
    private final String symbol;
    private final int quantity;
    private  final double minimumPrice;
    private boolean isOpen;
    private Instant closingTime;
    private final String owner;
    private final List<Bid> bids;

    private AuctionSummary auctionSummary;
    private final List<Bid> winningBids = new ArrayList<>();
    private double totalRevenue = 0;
    private int totalSoldQuantity = 0;
    private final Clock clock;

    public Auction(String symbol, int quantity, double minimumPrice, String owner, Clock clock) throws BusinessException {
        if (minimumPrice <= 0) {
            throw new BusinessException("Minimum cannot be zero or negative.", BusinessException.ErrorCode.INVALID_INPUT);
        }
        if (quantity < 0) {
            throw new BusinessException("Quantity cannot be negative.", BusinessException.ErrorCode.INVALID_INPUT);
        }

        this.id = ++lastId; // Increment and assign the next ID
        this.symbol = symbol;
        this.quantity = quantity;
        this.minimumPrice = minimumPrice;
        this.owner = owner;
        this.isOpen = true;
        this.bids = new ArrayList<>();
        this.clock = clock;
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
        if (!this.isOpen) return; // feedback to user
        // business exception class

        this.isOpen = false;
        this.closingTime = clock.instant();

        List<Bid> sortedBids = bids.stream()
                .filter(bid -> bid.getPrice() >= minimumPrice)
                .sorted(Comparator.comparing(Bid::getPrice).reversed()
                        .thenComparing(Bid::getSubmissionTime))
                .toList();

        int remainingQuantity = quantity;
        for (Bid bid : sortedBids) {
            if (remainingQuantity == 0) break;

            if (bid.getQuantity() <= remainingQuantity) {
                winningBids.add(bid);
                totalRevenue += bid.getPrice() * bid.getQuantity();
                totalSoldQuantity += bid.getQuantity();
                remainingQuantity -= bid.getQuantity();
            } else {
                try {
                    Bid newBid = new Bid(bid.getBidder(), symbol, id, bid.getPrice(), remainingQuantity, bid.getSubmissionTime());
                    winningBids.add(newBid);
                } catch (BusinessException e) {
                    System.err.println("Error creating partial bid: " + e.getMessage());
                }
                totalRevenue += bid.getPrice() * remainingQuantity;
                totalSoldQuantity += remainingQuantity;
                remainingQuantity = 0;
            }
        }


       this.auctionSummary = new AuctionSummary(id, symbol, winningBids, totalRevenue, totalSoldQuantity);
    }

    public AuctionSummary getAuctionSummary() {
        return this.auctionSummary;
    }

    public static void resetLastId() {
        lastId = 0;
    }
}