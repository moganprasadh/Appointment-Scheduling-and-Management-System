package service;

import dao.PatientDAO;
import exception.PatientAlreadyRegisteredException;
import model.Patient;

public class PatientService {
    private final PatientDAO patientDAO;

    public PatientService(PatientDAO patientDAO) {
        this.patientDAO = patientDAO;
    }

    public boolean registerPatient(Patient patient) throws PatientAlreadyRegisteredException {
        Patient existingPatient = patientDAO.getPatientById(patient.getId());
        if (existingPatient != null) {
            throw new PatientAlreadyRegisteredException("Patient is already registered.");
        }
        return patientDAO.addPatient(patient);
    }

    public Patient getPatientById(int patientId) {
        return patientDAO.getPatientById(patientId);
    }
}
