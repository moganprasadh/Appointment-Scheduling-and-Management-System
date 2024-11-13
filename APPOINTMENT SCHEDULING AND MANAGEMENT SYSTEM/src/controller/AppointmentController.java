package controller;

import model.Appointment;
import service.AppointmentService;

import java.util.List;

import exception.AppointmentConflictException;
import exception.AppointmentNotFoundException;

public class AppointmentController {
    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    public boolean bookAppointment(Appointment appointment) throws AppointmentConflictException {
        return appointmentService.bookAppointment(appointment);
    }

    public boolean cancelAppointment(int appointmentId) throws AppointmentNotFoundException {
        return appointmentService.cancelAppointment(appointmentId);
    }

    public List<Appointment> getAppointmentsByDoctor(int doctorId) {
        return appointmentService.getAppointmentsByDoctor(doctorId);
    }
}
