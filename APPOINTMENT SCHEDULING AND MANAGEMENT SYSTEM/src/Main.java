
import model.*;
import service.*;
import utility.DBConnection;
import dao.*;

import java.sql.Connection;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Connection connection = DBConnection.getConnection();

        // Initializing DAOs
        AppointmentDAO appointmentDAO = new AppointmentDAOImpl(connection);
        PatientDAO patientDAO = new PatientDAOImpl(connection);
        DoctorDAO doctorDAO = new DoctorDAOImpl(connection);

        // Initializing Services
        AppointmentService appointmentService = new AppointmentService(appointmentDAO);
        PatientService patientService = new PatientService(patientDAO);
        DoctorService doctorService = new DoctorService(doctorDAO);

        try (Scanner scanner = new Scanner(System.in)) {
			while (true) {
			    System.out.println("Appointment Scheduling System:");
			    System.out.println("1. Register Patient");
			    System.out.println("2. Add Doctor");
			    System.out.println("3. Book Appointment");
			    System.out.println("4. Cancel Appointment");
			    System.out.println("5. View Appointments by Doctor");
			    System.out.println("6. Exit");
			    System.out.print("Choose an option: ");
			    int choice = scanner.nextInt();

			    switch (choice) {
			        case 1:
			            System.out.print("Enter Patient ID: ");
			            int patientId = scanner.nextInt();
			            System.out.print("Enter Patient Name: ");
			            String patientName = scanner.next();
			            System.out.print("Enter Patient Age: ");
			            int patientAge = scanner.nextInt();

			            Patient patient = new Patient(patientId, patientName, patientAge);
			            try {
			                patientService.registerPatient(patient);
			                System.out.println("Patient registered successfully.");
			            } catch (Exception e) {
			                System.out.println("Error: " + e.getMessage());
			            }
			            break;

			        case 2:
			            System.out.print("Enter Doctor ID: ");
			            int doctorId = scanner.nextInt();
			            System.out.print("Enter Doctor Name: ");
			            String doctorName = scanner.next();
			            System.out.print("Enter Specialization: ");
			            String specialization = scanner.next();
			            Doctor doctor = new Doctor(doctorId, doctorName, specialization);
			            doctorDAO.addDoctor(doctor);
			            System.out.println("Doctor added successfully.");
			            break;

			        case 3:
			            System.out.print("Enter Appointment ID: ");
			            int appointmentId = scanner.nextInt();
			            System.out.print("Enter Appointment Date (yyyy-mm-dd): ");
			            String date = scanner.next();
			            System.out.print("Enter Appointment Type (InPerson/Virtual): ");
			            String type = scanner.next();
			            System.out.print("Enter Doctor ID: ");
			            int apptDoctorId = scanner.nextInt();
			            System.out.print("Enter Patient ID: ");
			            int apptPatientId = scanner.nextInt();

			            Appointment appointment;
			            if (type.equalsIgnoreCase("InPerson")) {
			                System.out.print("Enter Location: ");
			                String location = scanner.next();
			                appointment = new InPersonAppointment(appointmentId, new Date(), apptDoctorId, apptPatientId, location);
			            } else {
			                System.out.print("Enter Platform: ");
			                String platform = scanner.next();
			                appointment = new VirtualAppointment(appointmentId, new Date(), apptDoctorId, apptPatientId, platform);
			            }

			            try {
			                appointmentService.bookAppointment(appointment);
			                System.out.println("Appointment booked successfully.");
			            } catch (Exception e) {
			                System.out.println("Error: " + e.getMessage());
			            }
			            break;

			        case 4:
			            System.out.print("Enter Appointment ID to Cancel: ");
			            int cancelAppointmentId = scanner.nextInt();
			            boolean isCanceled = appointmentService.cancelAppointment(cancelAppointmentId);
			            if (isCanceled) {
			                System.out.println("Appointment canceled successfully.");
			            } else {
			                System.out.println("Error: Appointment not found.");
			            }
			            break;

			        case 5:
			            System.out.print("Enter Doctor ID: ");
			            int docId = scanner.nextInt();
			            System.out.println("Appointments for Doctor ID " + docId + ":");
			            for (Appointment appt : appointmentService.getAppointmentsByDoctor(docId)) {
			                System.out.println("Appointment ID: " + appt.getId() + ", Date: " + appt.getDate() + ", Type: " + appt.getType());
			            }
			            break;

			        case 6:
			            System.out.println("Exiting...");
			            System.exit(0);
			            break;

			        default:
			            System.out.println("Invalid choice. Please try again.");
			    }
			}
		}
    }
}
