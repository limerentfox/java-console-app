package console;

import models.*;
import java.io.PrintStream;
import java.util.Scanner;

public class AuctionMenuManager {
    private final Scanner scanner;
    private final PrintStream out;
    private final AuctionManager auctionManager;

    public AuctionMenuManager(Scanner scanner, PrintStream out, AuctionManager auctionManager) {
        this.scanner = scanner;
        this.out = out;
        this.auctionManager = auctionManager;
    }

    public void displayAuctionMenu() {
        boolean running = true;
        while (running) {
            out.println("\n=== Auction Management Menu ===");
            out.println("1. Create an auction");
            out.println("2. View your auctions");
            out.println("3. Close an auction");
            out.println("4. Place a bid");
            out.println("5. View won bids");
            out.println("6. View lost bids");
            out.println("7. Go back");
            out.print("Enter option: ");
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    createAuction();
                    break;
                case "2":
                    viewMyAuctions();
                    break;
                case "3":
                    closeAuction();
                    break;
                case "4":
                    placeBid();
                    break;
                case "5":
                    viewWonBids();
                    break;
                case "6":
                    viewLostBids();
                    break;
                case "7":
                    running = false;
                    break;
                default:
                    out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    private void createAuction() {
        out.print("Enter symbol: ");
        String symbol = scanner.nextLine();
        out.print("Enter quantity: ");
        int quantity = Integer.parseInt(scanner.nextLine());
        out.print("Enter minimum price: ");
        double minimumPrice = Double.parseDouble(scanner.nextLine());
        auctionManager.createAuction(symbol, quantity, minimumPrice);
        out.println("Auction created successfully.");
    }

    private void viewMyAuctions() {
        out.println("\n" + auctionManager.getCurrentUser().getUsername());
        out.println("\t=> Your Auctions\n===================================");
        auctionManager.getAuctions().values().stream()
                .filter(auction -> auction.getOwner().equals(auctionManager.getCurrentUser().getUsername()))
                .forEach(auction -> {
                    out.println("Id: " + auction.getId() + "\nSymbol: " + auction.getSymbol() + "\nStatus: " + (auction.isOpen() ? "OPENED" : "CLOSED"));
                    auction.getBids()
                            .forEach(bid -> out.println(" - Bidder: " + bid.getBidder() + ", Price: " + bid.getPrice() + ", Quantity: " + bid.getQuantity()));
                });
        out.println("\n===================================\n");
    }

    private void closeAuction() {
        out.print("Enter the id of the auction to close: ");
        int auctionId = Integer.parseInt(scanner.nextLine());
        auctionManager.closeAuction(auctionId);
        Auction closedAuction = auctionManager.getAuctions().get(auctionId);
        AuctionSummary auctionSummary = closedAuction.getAuctionSummary();
        out.println(auctionSummary.summarize());
    }

    private void placeBid() {
        out.println("\n" + auctionManager.getCurrentUser().getUsername());
        out.print("Enter auction id: ");
        int auctionId = Integer.parseInt(scanner.nextLine());

        if (auctionManager.isAuctionOwnedByCurrentUser(auctionId)) {
            out.println("Cannot place bid on your own auction. Please enter auction id of an auction you do not own.");
            out.print("Enter auction id: ");
            auctionId = Integer.parseInt(scanner.nextLine());
        }

        out.print("Enter auction symbol: ");
        String auctionSymbol = scanner.nextLine();
        out.print("Enter bid amount: ");
        double price = Double.parseDouble(scanner.nextLine());
        out.print("Enter quantity: ");
        int quantity = Integer.parseInt(scanner.nextLine());

        auctionManager.placeBid(getCurrentUser().getUsername(), auctionSymbol,auctionId, price, quantity);
    }


    private void viewWonBids() {
        auctionManager.getWonBids().stream().forEach(
                bid -> out.println("\nAuctionLot: " + bid.getAuctionId() + " " + "Symbol: " + bid.getAuctionSymbol() + " " + "Quantity: " + bid.getQuantity() + " " + "Price: " + bid.getPrice())
        );
    }

    private void viewLostBids() {
        auctionManager.getLostBids().stream().forEach(
                bid -> out.println("\nAuctionLot: " + bid.getAuctionId() + " " + "Symbol: " + bid.getAuctionSymbol() + " " + "Quantity: " + bid.getQuantity() + " " + "Price: " + bid.getPrice())
        );
    }

    public void setCurrentUser(User currentUser) {
        auctionManager.setCurrentUser(currentUser);
    }

    public User getCurrentUser() {
       return auctionManager.getCurrentUser();
    }
}
