package dao;

import model.Appointment;

import java.util.List;

public interface AppointmentDAO {
    boolean createAppointment(Appointment appointment);
    boolean deleteAppointment(int appointmentId);
    List<Appointment> getAppointmentsByDoctor(int doctorId);
}
