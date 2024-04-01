package models;


import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class AuctionManager {
    private final Map<Integer, Auction> auctions = new HashMap<>();
    private final List<Bid> allBids = new ArrayList<>();

    public AuctionManager() {
    }

    public boolean isAuctionOwnedByCurrentUser(int auctionId, User currentUser) {
        Auction auction = auctions.get(auctionId);
        return auction != null && auction.getOwner().equals(currentUser.getUsername());
    }
    public void createAuction(String symbol, int quantity, double minimumPrice, User currentUser) {
        try {
            Clock clock = Clock.systemDefaultZone();
            Auction newAuction = new Auction(symbol, quantity, minimumPrice, currentUser.getUsername(), clock);
            Integer auctionId = newAuction.getId();
            auctions.put(auctionId, newAuction);
        } catch (BusinessException e) {
            System.err.println("Error creating new auction: " + e.getMessage());
        }

    }

    public Map<Integer, Auction> getAuctions() {
        return auctions;
    }

    public void closeAuction(int auctionId, User currentUser) {
        Auction auction = auctions.get(auctionId);

        if (auction != null && auction.getOwner().equals(currentUser.getUsername()) && auction.isOpen()) {
            auction.closeAuction();
        }
    }

    public void placeBid(String bidder, String auctionSymbol, int auctionId, double price, int quantity) {
        Auction auction = auctions.get(auctionId);
        if (auction != null && auction.isOpen() && !auction.getOwner().equals(bidder)) {
            try {
                Bid newBid = new Bid(bidder, auctionSymbol, auctionId, price, quantity, Instant.now());
                auction.getBids().add(newBid);
                allBids.add(newBid);
            } catch (BusinessException e) {
                System.err.println("Error creating new bid: " + e.getMessage());
            }

        }
    }




    public Stream<Bid> getWonBids(User currentUser) {
        return allBids.stream()
                .filter(bid -> bid.getBidder().equals(currentUser.getUsername()) && auctions.get(bid.getAuctionId()).getWinningBids().contains(bid));
    }

    public Stream<Bid> getLostBids(User currentUser) {
        return allBids.stream()
                .filter(bid -> bid.getBidder().equals(currentUser.getUsername()) && !auctions.get(bid.getAuctionId()).getWinningBids().contains(bid));
    }
}

