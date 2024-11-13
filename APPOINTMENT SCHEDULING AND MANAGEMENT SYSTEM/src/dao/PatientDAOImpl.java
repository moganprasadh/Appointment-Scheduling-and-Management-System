package dao;

import model.Patient;
import utility.DBConnection;

import java.sql.*;

public class PatientDAOImpl implements PatientDAO {
    private final Connection conn;

    public PatientDAOImpl() {
        this.conn = DBConnection.getConnection();
    }

    public PatientDAOImpl(Connection connection) {
		// TODO Auto-generated constructor stub
    	this.conn=connection;
    	
	}

	@Override
    public boolean addPatient(Patient patient) {
        String query = "INSERT INTO Patient (name, age) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, patient.getName());
            ps.setInt(2, patient.getAge());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Patient getPatientById(int patientId) {
        String query = "SELECT * FROM Patient WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, patientId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Patient(rs.getInt("id"), rs.getString("name"), rs.getInt("age"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
