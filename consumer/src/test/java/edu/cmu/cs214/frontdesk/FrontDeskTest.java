package edu.cmu.cs214.frontdesk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.cmu.cs214.booking.Booking;
import edu.cmu.cs214.booking.BookingStatus;
import edu.cmu.cs214.booking.InMemoryBookingService;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * The contract gate. This suite belongs to the front desk team and checks what
 * they rely on from the booking API.
 */
class FrontDeskTest {

    private final FrontDesk desk = new FrontDesk(new InMemoryBookingService());

    @Test
    void walkInOnAFreeRoomIsConfirmed() {
        Booking booking = desk.bookWalkIn("Oak", 540, 600);

        assertEquals(BookingStatus.CONFIRMED, booking.getStatus());
        assertEquals("Oak", booking.getRoomId());
    }

    @Test
    void walkInOnABusyRoomIsTurnedAway() {
        desk.bookWalkIn("Oak", 540, 600);

        assertNull(desk.bookWalkIn("Oak", 570, 630));
        assertEquals(1, desk.displaySchedule("Oak").size());
    }

    @Test
    void guestWithANameGoesOnTheWaitlist() {
        desk.bookWalkIn("Oak", 540, 600);

        Booking queued = desk.joinWaitlist("Oak", 570, 630, "Ramirez");

        assertEquals(BookingStatus.WAITLISTED, queued.getStatus());
    }

    @Test
    void cancellingWithAnOfferPromotesTheWaitedGuest() {
        Booking held = desk.bookWalkIn("Oak", 540, 600);
        Booking queued = desk.joinWaitlist("Oak", 570, 630, "Ramirez");

        assertTrue(desk.cancelAndOfferToWaitlist(held.getId()));

        assertEquals(BookingStatus.CONFIRMED, queued.getStatus());
        assertEquals(List.of("09:30-10:30  CONFIRMED (Ramirez)"),
                desk.displaySchedule("Oak"));
    }

    @Test
    void quietCancelLeavesTheWaitlistWhereItWas() {
        Booking held = desk.bookWalkIn("Oak", 540, 600);
        Booking queued = desk.joinWaitlist("Oak", 570, 630, "Ramirez");

        assertTrue(desk.cancelQuietly(held.getId()));

        assertEquals(BookingStatus.WAITLISTED, queued.getStatus());
        assertEquals(List.of("09:30-10:30  WAITLISTED (Ramirez)"),
                desk.displaySchedule("Oak"));
    }

    @Test
    void scheduleIsOrderedByStartAndHidesCancelledBookings() {
        desk.bookWalkIn("Oak", 660, 720);
        desk.bookWalkIn("Oak", 540, 600);
        Booking correction = desk.bookWalkIn("Oak", 600, 660);
        desk.cancelQuietly(correction.getId());

        List<String> lines = desk.displaySchedule("Oak");

        assertEquals(2, lines.size());
        assertEquals("09:00-10:00  CONFIRMED", lines.get(0));
        assertEquals("11:00-12:00  CONFIRMED", lines.get(1));
    }

    @Test
    void waitlistedGuestsAreShownWithTheirName() {
        desk.bookWalkIn("Oak", 540, 600);
        desk.joinWaitlist("Oak", 570, 630, "Ramirez");

        List<String> lines = desk.displaySchedule("Oak");

        assertEquals(List.of("09:00-10:00  CONFIRMED", "09:30-10:30  WAITLISTED (Ramirez)"),
                lines);
    }
}
