package models;


import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AuctionManager {
    private final Map<Integer, Auction> auctions = new HashMap<>();
    private final List<Bid> allBids = new ArrayList<>();
    private User currentUser;

    public AuctionManager(User currentUser) {
        this.currentUser = currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public boolean isAuctionOwnedByCurrentUser(int auctionId) {
        Auction auction = auctions.get(auctionId);
        return auction != null && auction.getOwner().equals(currentUser.getUsername());
    }
    public void createAuction(String symbol, int quantity, double minimumPrice) {
        Auction newAuction = new Auction(symbol, quantity, minimumPrice, this.currentUser.getUsername());
        Integer auctionId = newAuction.getId();
        auctions.put(auctionId, newAuction);
    }

    public Map<Integer, Auction> getAuctions() {
        return auctions;
    }

    public void closeAuction(int auctionId) {
        Auction auction = auctions.get(auctionId);

        if (auction != null && auction.getOwner().equals(currentUser.getUsername()) && auction.isOpen()) {
            auction.closeAuction();
        }
    }

    public void placeBid(String bidder, String auctionSymbol, int auctionId, double price, int quantity) {
        Auction auction = auctions.get(auctionId);
        if (auction != null && auction.isOpen() && !auction.getOwner().equals(bidder)) {
            Bid newBid = new Bid(bidder, auctionSymbol, auctionId, price, quantity, Instant.now());
            auction.getBids().add(newBid);
            allBids.add(newBid);
        }
    }



    public List<Bid> getWonBids() {
        return allBids.stream()
                .filter(bid -> bid.getBidder().equals(currentUser.getUsername()) && auctions.get(bid.getAuctionId()).getWinningBids().contains(bid))
                .collect(Collectors.toList());
    }

    public List<Bid> getLostBids() {
        return allBids.stream()
                .filter(bid -> bid.getBidder().equals(currentUser.getUsername()) && !auctions.get(bid.getAuctionId()).getWinningBids().contains(bid))
                .collect(Collectors.toList());
    }
}

