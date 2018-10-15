package notif;

import java.time.LocalDateTime;

public class Client {
    private String reservationName;
    private String phoneNumber;
    private LocalDateTime timeOfCreation;

    public Client(String reservationName, String phoneNumber, LocalDateTime timeOfCreation){
        this.reservationName = reservationName;
        this.phoneNumber = phoneNumber;
        this.timeOfCreation = timeOfCreation;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public LocalDateTime getTimeOfCreation() {
        return timeOfCreation;
    }

    public String getReservationName() {
        return reservationName;
    }



}
