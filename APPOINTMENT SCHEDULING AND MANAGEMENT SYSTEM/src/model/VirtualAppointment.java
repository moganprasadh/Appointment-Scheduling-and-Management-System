package model;

import java.util.Date;

public class VirtualAppointment extends Appointment {
    private String platform;

    public VirtualAppointment(int id, Date date, int doctorId, int patientId, String platform) {
        super(id, date, "Virtual", doctorId, patientId);
        this.platform = platform;
    }

    public String getPlatform() {
        return platform;
    }
}
