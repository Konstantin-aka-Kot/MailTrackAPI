package com.akshentsev.mailtrackapi.model;

public enum MailStatus {
    REGISTERED,
    IN_TRANSIT,
    ARRIVED_AT_TRANSIT,
    DEPARTURE_FROM_POSTAL_OFFICE,
    ARRIVED_AT_DESTINATION,
    DELIVERED,
    FAILED_DELIVERY
}
