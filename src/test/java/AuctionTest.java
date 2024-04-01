import models.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;

class AuctionTest {
    private Clock fixedClock;

    @BeforeEach
    void setUp() {
        Auction.resetLastId();

        fixedClock = Clock.fixed(Instant.parse("2022-01-01T10:00:00Z"), ZoneId.of("UTC"));
    }

    @Test
    void auctionInitializesCorrectly() throws BusinessException {
        Auction auction = new Auction("Art", 10, 100, "owner", fixedClock);

        assertEquals("Art", auction.getSymbol());
        assertEquals(100, auction.getMinimumPrice());
        assertTrue(auction.isOpen());
        assertNotNull(auction.getClosingTime()); // Closing time should be null until the auction is closed.
    }

    @Test
    void auctionInitializationFailsForInvalidInput() {
        BusinessException thrown;

        thrown = assertThrows(BusinessException.class, () -> new Auction("Art", -1, 100, "owner", fixedClock));
        assertEquals(BusinessException.ErrorCode.INVALID_INPUT, thrown.getErrorCode());

        thrown = assertThrows(BusinessException.class, () -> new Auction("Art", 10, -100, "owner", fixedClock));
        assertEquals(BusinessException.ErrorCode.INVALID_INPUT, thrown.getErrorCode());
    }

    @Test
    void closeAuctionUpdatesStateCorrectly() throws BusinessException {
        Auction auction = new Auction("Art", 10, 100, "owner", fixedClock);
        auction.closeAuction();

        assertFalse(auction.isOpen());
        assertEquals(Instant.parse("2022-01-01T10:00:00Z"), auction.getClosingTime());
    }

    @Test
    void closeAuctionCalculatesWinningBidsCorrectly() throws BusinessException {
        Auction auction = new Auction("Art", 10, 100, "owner", fixedClock);

        auction.getBids().add(new Bid("bidder1", "Art", auction.getId(), 150, 5, Instant.now(fixedClock)));
        auction.getBids().add(new Bid("bidder2", "Art", auction.getId(), 200, 5, Instant.now(fixedClock).plusSeconds(10)));

        auction.closeAuction();

        assertEquals(2, auction.getWinningBids().size());
        assertEquals(350.0, auction.getTotalRevenue());
        assertEquals(10, auction.getTotalSoldQuantity());
    }
}
