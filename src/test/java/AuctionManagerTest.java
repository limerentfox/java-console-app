import console.AuctionMenuManager;
import models.AuctionManager;
import models.BusinessException;
import models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.Clock;
import java.util.Map;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuctionMenuManagerTest {
    @Mock
    private AuctionManager auctionManager;
    @Mock
    private User currentUser;

    private ByteArrayOutputStream outContent;
    private PrintStream originalOut;
    private AuctionMenuManager auctionMenuManager;

    @BeforeEach
    void setUp() {
        originalOut = System.out;
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut); // Restore original System.out
    }

    private void setupMenuManagerWithInput(String input) {
        Scanner scanner = new Scanner(input);
        auctionMenuManager = new AuctionMenuManager(scanner, System.out, auctionManager);
        auctionMenuManager.setCurrentUser(currentUser);
    }

    @Test
    void testCreatingAuction() {
        String input = "1\nArt\n10\n100.0\n7\n"; // Sequence to create an auction and exit
        setupMenuManagerWithInput(input);
        auctionMenuManager.displayAuctionMenu();

        verify(auctionManager, times(1)).createAuction(anyString(), anyInt(), anyDouble(), eq(currentUser));
        assertTrue(outContent.toString().contains("Auction created successfully."));
    }

    @Test
    void testViewingMyAuctions() throws BusinessException {
        String input = "2\n7\n"; // Sequence to view auctions and exit
        setupMenuManagerWithInput(input);

        when(auctionManager.getAuctions()).thenReturn(Map.of(
                1, new Auction("Art", 10, 100.0, currentUser.getUsername(), Clock.systemDefaultZone())
        ));
        when(currentUser.getUsername()).thenReturn("testUser");

        auctionMenuManager.displayAuctionMenu();

        assertTrue(outContent.toString().contains("Your Auctions"));
        assertTrue(outContent.toString().contains("Art"));
    }

    @Test
    void testPlacingBid() {
        String input = "4\n1\nArt\n50.0\n2\n7\n"; // Sequence to place a bid and exit
        setupMenuManagerWithInput(input);

        when(auctionManager.isAuctionOwnedByCurrentUser(anyInt(), eq(currentUser))).thenReturn(false);

        auctionMenuManager.displayAuctionMenu();

        verify(auctionManager, times(1)).placeBid(eq(currentUser.getUsername()), anyString(), anyInt(), anyDouble(), anyInt());
    }

    private static class Auction extends models.Auction {
        public Auction(String symbol, int quantity, double minimumPrice, String owner, Clock clock) throws BusinessException {
            super(symbol, quantity, minimumPrice, owner, clock);
        }
    }
}

