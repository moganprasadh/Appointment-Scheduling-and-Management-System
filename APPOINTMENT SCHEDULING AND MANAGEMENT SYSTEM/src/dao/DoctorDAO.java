package dao;

import model.Doctor;
import java.util.List;

public interface DoctorDAO {
    boolean addDoctor(Doctor doctor);
    Doctor getDoctorById(int doctorId);
    List<Doctor> getAllDoctors();
    boolean deleteDoctor(int doctorId);
}
