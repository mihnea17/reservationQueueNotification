import java.time.LocalDate;

public class Client {
    private String reservationName;
    private long reservationId;
    private String phoneNumber;
    private LocalDate timeOfCreation;
    private int numberOfReservationBeforeHim;

    public Client(long reservationId, String reservationName, String phoneNumber, LocalDate timeOfCreation){
        this.reservationId = reservationId;
        this.reservationName = reservationName;
        this.phoneNumber = phoneNumber;
        this.timeOfCreation = timeOfCreation;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getTimeOfCreation() {
        return timeOfCreation;
    }

    public void setTimeOfCreation(LocalDate timeOfCreation) {
        this.timeOfCreation = timeOfCreation;
    }

    public int getNumberOfReservationBeforeHim() {
        return numberOfReservationBeforeHim;
    }

    public void setNumberOfReservationBeforeHim(int numberOfReservationBeforeHim) {
        this.numberOfReservationBeforeHim = numberOfReservationBeforeHim;
    }

    public long getReservationId() {
        return reservationId;
    }
    public void setReservationId(long reservationId) {
        this.reservationId = reservationId;
    }
    public String getReservationName() {
        return reservationName;
    }
    public void setReservationName(String reservationName) {
        this.reservationName = reservationName;
    }



}
