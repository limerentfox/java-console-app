import models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class AuctionSummaryTest {
    private AuctionSummary summary;
    private Instant time1, time2;

    @BeforeEach
    void setUp() throws BusinessException {
        time1 = Instant.now();
        time2 = time1.plusSeconds(60); // Assuming bid2 happens a minute after bid1
        Bid bid1 = new Bid("user1", "SYM", 1, 100.0, 2, time1);
        Bid bid2 = new Bid("user2", "SYM", 1, 150.0, 3, time2);
        List<Bid> winningBids = List.of(bid1, bid2);
        summary = new AuctionSummary(1, "SYM", winningBids, 550.0, 5);
    }

    @Test
    void testAuctionSummaryConstructor() {
        assertEquals(1, summary.auctionId());
        assertEquals("SYM", summary.symbol());
        assertEquals(550.0, summary.totalRevenue());
        assertEquals(5, summary.totalSoldQuantity());
        assertEquals(2, summary.winningBids().size());
    }

    @Test
    void testWinningBidsDetails() {
        List<Bid> winningBids = summary.winningBids();
        assertNotNull(winningBids);
        assertEquals(2, winningBids.size());

        Bid firstBid = winningBids.getFirst();
        assertEquals("user1", firstBid.getBidder());
        assertEquals(100.0, firstBid.getPrice());
        assertEquals(2, firstBid.getQuantity());
        assertEquals(time1, firstBid.getSubmissionTime());

        Bid secondBid = winningBids.get(1);
        assertEquals("user2", secondBid.getBidder());
        assertEquals(150.0, secondBid.getPrice());
        assertEquals(3, secondBid.getQuantity());
        assertEquals(time2, secondBid.getSubmissionTime());
    }

    @Test
    void testImmutabilityOfWinningBids() {
        List<Bid> originalWinningBids = new ArrayList<>(summary.winningBids());
        try {

            summary.winningBids().add(new Bid("user3", "SYM", 1, 200.0, 1, Instant.now()));
            fail("Expected UnsupportedOperationException for modifying unmodifiable list");
        } catch (UnsupportedOperationException | BusinessException ignored) {
        }

        assertEquals(originalWinningBids, summary.winningBids());
    }
}
