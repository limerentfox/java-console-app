import models.*;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class BidTest {

    @Test
    void successfulBidCreation() {
        Instant now = Instant.now();
        assertDoesNotThrow(() -> new Bid("bidder", "XYZ", 1, 100.0, 10, now));
    }

    @Test
    void bidCreationThrowsBusinessExceptionForNegativePrice() {
        Instant now = Instant.now();
        BusinessException thrown = assertThrows(BusinessException.class, () -> new Bid("bidder", "XYZ", 1, -100.0, 10, now));
        assertEquals(BusinessException.ErrorCode.INVALID_INPUT, thrown.getErrorCode());
        assertTrue(thrown.getMessage().contains("Price cannot be zero or negative"));
    }

    @Test
    void bidCreationThrowsBusinessExceptionForZeroQuantity() {
        Instant now = Instant.now();
        BusinessException thrown = assertThrows(BusinessException.class, () -> new Bid("bidder", "XYZ", 1, 100.0, 0, now));
        assertEquals(BusinessException.ErrorCode.INVALID_INPUT, thrown.getErrorCode());
        assertTrue(thrown.getMessage().contains("Quantity cannot be zero or negative"));
    }

    @Test
    void gettersReturnCorrectValues() throws BusinessException {
        Instant submissionTime = Instant.now();
        Bid bid = new Bid("bidder", "XYZ", 1, 50.0, 5, submissionTime);

        assertEquals("bidder", bid.getBidder());
        assertEquals("XYZ", bid.getAuctionSymbol());
        assertEquals(1, bid.getAuctionId());
        assertEquals(50.0, bid.getPrice());
        assertEquals(5, bid.getQuantity());
        assertEquals(submissionTime, bid.getSubmissionTime());
    }
}
