package edu.cmu.cs214.booking;

/** Lifecycle state of a {@link Booking}. */
public enum BookingStatus {

    /** The room is held for this booking. */
    CONFIRMED,

    /** The room was busy and this booking is queued behind the conflict. */
    WAITLISTED,

    /** The booking was cancelled and no longer holds or queues for the room. */
    CANCELLED
}
