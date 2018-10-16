package notif;


import java.time.LocalDateTime;

public class Client {
    private long clientId;

    private String reservationName;
    private String phoneNumber;
    private LocalDateTime timeOfCreation;

    private LocalDateTime timeOfDeletion;

    public Client(String reservationName, String phoneNumber, LocalDateTime timeOfCreation, LocalDateTime timeOfDeletion){
        this.reservationName = reservationName;
        this.phoneNumber = phoneNumber;
        this.timeOfCreation = timeOfCreation;
        this.timeOfDeletion = timeOfDeletion;
    }
    public Client(){}

    public Client(long clientId, String reservationName, String phoneNumber, LocalDateTime timeOfCreation, LocalDateTime timeOfDeletion){
        this(reservationName, phoneNumber, timeOfCreation, timeOfDeletion);
        this.clientId = clientId;
    }

    public long getClientId() {
        return clientId;
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

    public void setTimeOfCreation(LocalDateTime timeOfCreation) {
        this.timeOfCreation = timeOfCreation;
    }

    @Override
    public String toString(){
        return "Client[clientId="+this.getClientId()+", reservationName="+this.getReservationName()+", phoneNumber="+this.getPhoneNumber()+
                ",\nreservationDateAndTime="+this.timeOfCreation+ ", deletionDateAndTime="+this.timeOfDeletion+"]";
    }

}
