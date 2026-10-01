package edu.cmu.cs214.booking;

/**
 * Everything needed to create one booking, passed to
 * {@link BookingApi#createBooking(BookingRequest)}.
 *
 * <p>Build one with {@link #builder(String, long, long)}, which takes the
 * required fields, then set the optional ones by name:
 *
 * <pre>{@code
 * BookingRequest request = BookingRequest.builder("R1", 540, 600)
 *         .waitlistKey("party-of-four")
 *         .notes("needs a projector")
 *         .build();
 * }</pre>
 *
 * <p>A request is immutable. It carries values only; the rules for what they
 * mean, and which values are rejected, live on {@link BookingApi}.
 */
public final class BookingRequest {

    private final String roomId;
    private final long startMinute;
    private final long endMinute;
    private final String waitlistKey;
    private final String notes;

    private BookingRequest(Builder builder) {
        this.roomId = builder.roomId;
        this.startMinute = builder.startMinute;
        this.endMinute = builder.endMinute;
        this.waitlistKey = builder.waitlistKey;
        this.notes = builder.notes;
    }

    /**
     * Starts a request for the half-open range {@code [startMinute, endMinute)}.
     * The waitlist key and notes default to null.
     */
    public static Builder builder(String roomId, long startMinute, long endMinute) {
        return new Builder(roomId, startMinute, endMinute);
    }

    public String getRoomId() {
        return roomId;
    }

    public long getStartMinute() {
        return startMinute;
    }

    public long getEndMinute() {
        return endMinute;
    }

    /** The caller's waitlist key, or null to decline waitlisting. */
    public String getWaitlistKey() {
        return waitlistKey;
    }

    /** Free-text notes for the booking, or null for none. */
    public String getNotes() {
        return notes;
    }

    /** Builder for {@link BookingRequest}. */
    public static final class Builder {

        private final String roomId;
        private final long startMinute;
        private final long endMinute;
        private String waitlistKey;
        private String notes;

        private Builder(String roomId, long startMinute, long endMinute) {
            this.roomId = roomId;
            this.startMinute = startMinute;
            this.endMinute = endMinute;
        }

        /** Waitlist on conflict under this key; null (the default) declines. */
        public Builder waitlistKey(String waitlistKey) {
            this.waitlistKey = waitlistKey;
            return this;
        }

        /** Free-text notes to attach; null (the default) means none. */
        public Builder notes(String notes) {
            this.notes = notes;
            return this;
        }

        public BookingRequest build() {
            return new BookingRequest(this);
        }
    }
}
