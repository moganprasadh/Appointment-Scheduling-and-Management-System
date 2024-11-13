package dao;

import model.Appointment;
import utility.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAOImpl implements AppointmentDAO {
    private final Connection conn;

    public AppointmentDAOImpl() {
        this.conn = DBConnection.getConnection();
    }

    public AppointmentDAOImpl(Connection connection) {
        this.conn = connection; 
    }
    
	@Override
    public boolean createAppointment(Appointment appointment) {
        String query = "INSERT INTO Appointment (date, type, doctorId, patientId) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setDate(1, new Date(appointment.getDate().getTime()));
            ps.setString(2, appointment.getType());
            ps.setInt(3, appointment.getDoctorId());
            ps.setInt(4, appointment.getPatientId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteAppointment(int appointmentId) {
        String query = "DELETE FROM Appointment WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, appointmentId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Appointment> getAppointmentsByDoctor(int doctorId) {
        List<Appointment> appointments = new ArrayList<>();
        String query = "SELECT * FROM Appointment WHERE doctorId = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, doctorId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                appointments.add(new Appointment(
                    rs.getInt("id"),
                    rs.getDate("date"),
                    rs.getString("type"),
                    rs.getInt("doctorId"),
                    rs.getInt("patientId")
                ));
            }

            if (appointments.isEmpty()) {
                System.out.println("Sorry, no data found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

}
