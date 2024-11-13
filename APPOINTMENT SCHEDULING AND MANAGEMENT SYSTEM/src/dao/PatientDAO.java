package dao;

import model.Patient;

public interface PatientDAO {
    boolean addPatient(Patient patient);
    Patient getPatientById(int patientId);
}
