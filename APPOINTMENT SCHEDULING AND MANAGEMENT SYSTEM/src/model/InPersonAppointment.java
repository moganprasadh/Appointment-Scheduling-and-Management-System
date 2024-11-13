package model;

import java.util.Date;

public class InPersonAppointment extends Appointment {
    private String location;

    public InPersonAppointment(int id, Date date, int doctorId, int patientId, String location) {
        super(id, date, "InPerson", doctorId, patientId);
        this.location = location;
    }

    public String getLocation() {
        return location;
    }
}
