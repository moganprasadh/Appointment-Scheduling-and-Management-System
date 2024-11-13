package model;

import java.util.Date;

public class Appointment {
	private int id;
    private Date date;
    private String type;
    private int doctorId;
    private int patientId;
	public Appointment(int id, java.util.Date date, String type, int doctorId, int patientId) {
		super();
		this.id = id;
		this.date = date;
		this.type = type;
		this.doctorId = doctorId;
		this.patientId = patientId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}
	public int getPatientId() {
		return patientId;
	}
	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}
	
}
