package edu.cmu.cs214.booking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

/** The producer's own suite. It never touches the consumer module. */
class InMemoryBookingServiceTest {

    private final BookingApi api = new InMemoryBookingService();

    @Test
    void freeRoomGivesConfirmedBooking() {
        Booking booking = api.createBooking(BookingRequest.builder("R1", 540, 600).build());

        assertEquals(BookingStatus.CONFIRMED, booking.getStatus());
        assertEquals(540, booking.getStartMinute());
    }

    @Test
    void conflictWithoutKeyReturnsNull() {
        api.createBooking(BookingRequest.builder("R1", 540, 600).build());

        assertNull(api.createBooking(BookingRequest.builder("R1", 570, 630).build()));
        assertEquals(1, api.listBookings("R1").size());
    }

    @Test
    void conflictWithKeyGoesOnWaitlist() {
        api.createBooking(BookingRequest.builder("R1", 540, 600).build());

        Booking queued = api.createBooking(BookingRequest.builder("R1", 570, 630)
                .waitlistKey("party-of-four").build());

        assertEquals(BookingStatus.WAITLISTED, queued.getStatus());
        assertEquals("party-of-four", queued.getWaitlistKey());
    }

    @Test
    void touchingRangesDoNotConflict() {
        api.createBooking(BookingRequest.builder("R1", 540, 600).build());

        Booking next = api.createBooking(BookingRequest.builder("R1", 600, 660).build());

        assertEquals(BookingStatus.CONFIRMED, next.getStatus());
    }

    @Test
    void cancelWithNotifyPromotesTheWaitlistedBooking() {
        Booking held = api.createBooking(BookingRequest.builder("R1", 540, 600).build());
        Booking queued = api.createBooking(BookingRequest.builder("R1", 570, 630)
                .waitlistKey("party-of-four").build());

        assertTrue(api.cancelBooking(held.getId(), true));

        assertEquals(BookingStatus.CONFIRMED, queued.getStatus());
        List<Booking> schedule = api.listBookings("R1");
        assertEquals(1, schedule.size());
        assertEquals(queued.getId(), schedule.get(0).getId());
    }
}
