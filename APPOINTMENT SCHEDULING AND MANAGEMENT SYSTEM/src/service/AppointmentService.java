package service;

import dao.AppointmentDAO;
import exception.AppointmentConflictException;
import model.Appointment;

import java.util.List;

public class AppointmentService {
    private final AppointmentDAO appointmentDAO;

    public AppointmentService(AppointmentDAO appointmentDAO) {
        this.appointmentDAO = appointmentDAO;
    }

    public boolean bookAppointment(Appointment appointment) throws AppointmentConflictException {
        // Check for conflicts or conditions before booking
        List<Appointment> existingAppointments = appointmentDAO.getAppointmentsByDoctor(appointment.getDoctorId());
        for (Appointment appt : existingAppointments) {
            if (appt.getDate().equals(appointment.getDate())) {
                throw new AppointmentConflictException("Appointment conflict detected");
            }
        }
        return appointmentDAO.createAppointment(appointment);
    }

    public boolean cancelAppointment(int appointmentId) {
        return appointmentDAO.deleteAppointment(appointmentId);
    }

    public List<Appointment> getAppointmentsByDoctor(int doctorId) {
        return appointmentDAO.getAppointmentsByDoctor(doctorId);
    }
}
