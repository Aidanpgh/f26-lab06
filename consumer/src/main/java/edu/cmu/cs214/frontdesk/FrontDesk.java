package edu.cmu.cs214.frontdesk;

import edu.cmu.cs214.booking.Booking;
import edu.cmu.cs214.booking.BookingApi;
import java.util.ArrayList;
import java.util.List;

/**
 * Walk-in front desk. Owned by another team, built on {@link BookingApi}.
 *
 * <p>Nothing here knows how bookings are stored. It knows the API and the
 * javadoc that came with it.
 */
public class FrontDesk {

    private final BookingApi api;

    public FrontDesk(BookingApi api) {
        this.api = api;
    }

    /**
     * Books a guest standing at the desk. A walk-in wants the room now or not
     * at all, so this never waitlists. Returns null when the room is taken.
     */
    public Booking bookWalkIn(String roomId, long startMinute, long endMinute) {
        return api.createBooking(roomId, startMinute, endMinute, null);
    }

    /** Puts a guest on the waitlist for a room that is already spoken for. */
    public Booking joinWaitlist(String roomId, long startMinute, long endMinute,
                                String guestName) {
        return api.createBooking(roomId, startMinute, endMinute, guestName);
    }

    /** One display line per booking on the room, in schedule order. */
    public List<String> displaySchedule(String roomId) {
        List<String> lines = new ArrayList<>();
        for (Booking booking : api.listBookings(roomId)) {
            lines.add(clock(booking.getStartMinute()) + "-" + clock(booking.getEndMinute())
                    + "  " + label(booking));
        }
        return lines;
    }

    /** Cancels and lets the next guest in line have the room. */
    public boolean cancelAndOfferToWaitlist(long bookingId) {
        return api.cancelBooking(bookingId, true);
    }

    /** Cancels without waking anyone up, for desk corrections and typos. */
    public boolean cancelQuietly(long bookingId) {
        return api.cancelBooking(bookingId, false);
    }

    private static String label(Booking booking) {
        if (booking.getWaitlistKey() == null) {
            return booking.getStatus().toString();
        }
        return booking.getStatus() + " (" + booking.getWaitlistKey() + ")";
    }

    private static String clock(long minuteOfDay) {
        return String.format("%02d:%02d", minuteOfDay / 60, minuteOfDay % 60);
    }
}
