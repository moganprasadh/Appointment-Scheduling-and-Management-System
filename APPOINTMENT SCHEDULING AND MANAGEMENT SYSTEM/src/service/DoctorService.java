package service;

import dao.DoctorDAO;
import model.Doctor;

public class DoctorService {
    private final DoctorDAO doctorDAO;

    public DoctorService(DoctorDAO doctorDAO) {
        this.doctorDAO = doctorDAO;
    }

    public Doctor getDoctorById(int doctorId) {
        return doctorDAO.getDoctorById(doctorId);
    }
}
