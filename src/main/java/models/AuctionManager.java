package models;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AuctionManager {
    private Map<Integer, Auction> auctions = new HashMap<>();
    private List<Bid> allBids = new ArrayList<>();
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
        String symbol = auction.getSymbol();

        if (auction != null && auction.getOwner().equals(currentUser.getUsername()) && auction.isOpen()) {
            auction.closeAuction();
        } else {
            System.out.println("Auction closing failed. It may not exist, be already closed, or belong to another user.");
        }
    }

    public void placeBid(String bidder, String auctionSymbol, int auctionId, double price, int quantity) {
        Auction auction = auctions.get(auctionId);
        if (auction != null && auction.isOpen() && !auction.getOwner().equals(bidder)) {
            Bid newBid = new Bid(bidder, auctionSymbol, auctionId, price, quantity, LocalDateTime.now());
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

