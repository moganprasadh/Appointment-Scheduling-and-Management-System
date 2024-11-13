package controller;

import model.Doctor;
import service.DoctorService;

public class DoctorController {
    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    public Doctor getDoctorById(int doctorId) {
        return doctorService.getDoctorById(doctorId);
    }
}
