package model;


public class Schedule {
	private int doctorId;
    private String availableDays;
    private String availableHours;
	public Schedule(int doctorId, String availableDays, String availableHours) {
		super();
		this.doctorId = doctorId;
		this.availableDays = availableDays;
		this.availableHours = availableHours;
	}
	public int getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}
	public String getAvailableDays() {
		return availableDays;
	}
	public void setAvailableDays(String availableDays) {
		this.availableDays = availableDays;
	}
	public String getAvailableHours() {
		return availableHours;
	}
	public void setAvailableHours(String availableHours) {
		this.availableHours = availableHours;
	}
    
    
}
