CREATE DATABASE AppointmentSchedulingSystem;
USE AppointmentSchedulingSystem;
CREATE TABLE Patient (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL
);

CREATE TABLE Doctor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    specialization VARCHAR(100)
);
CREATE TABLE Appointment (
    id INT AUTO_INCREMENT PRIMARY KEY,
    date DATE NOT NULL,
    type ENUM('InPerson', 'Virtual') NOT NULL,
    doctorId INT,
    patientId INT,
    status ENUM('Scheduled', 'Completed', 'Cancelled') DEFAULT 'Scheduled',
    FOREIGN KEY (doctorId) REFERENCES Doctor(id) ON DELETE SET NULL,
    FOREIGN KEY (patientId) REFERENCES Patient(id) ON DELETE SET NULL
);

CREATE TABLE Schedule (
    doctorId INT NOT NULL,
    availableDate DATE NOT NULL,
    availableTime TIME NOT NULL
);

-- storedProcedures

DELIMITER //

CREATE PROCEDURE ScheduleAppointment (
    IN p_date DATE,
    IN p_type ENUM('InPerson', 'Virtual'),
    IN p_doctorId INT,
    IN p_patientId INT
)
BEGIN
    DECLARE doctor_available INT;

    -- Check if the doctor is available on the given date
    SELECT COUNT(*) INTO doctor_available
    FROM Schedule
    WHERE doctorId = p_doctorId AND availableDate = p_date;

    IF doctor_available > 0 THEN
        INSERT INTO Appointment (date, type, doctorId, patientId, status)
        VALUES (p_date, p_type, p_doctorId, p_patientId, 'Scheduled');
    ELSE
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Doctor is not available on the chosen date';
    END IF;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE CancelAppointment (
    IN p_appointmentId INT
)
BEGIN
    UPDATE Appointment
    SET status = 'Cancelled'
    WHERE id = p_appointmentId;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE CompleteAppointment (
    IN p_appointmentId INT
)
BEGIN
    UPDATE Appointment
    SET status = 'Completed'
    WHERE id = p_appointmentId;
END //

DELIMITER ;



-- triggers

DELIMITER //
CREATE TRIGGER NotifyDoctorOnCancel
AFTER UPDATE ON Appointment
FOR EACH ROW
BEGIN
    IF OLD.status = 'Scheduled' AND NEW.status = 'Cancelled' THEN
        -- Assuming a Notifications table to store notifications for doctors
        INSERT INTO Notifications (doctorId, message)
        VALUES (NEW.doctorId, CONCAT('Appointment ', NEW.id, ' has been cancelled.'));
    END IF;
END //
DELIMITER ;
DELIMITER //
CREATE TRIGGER UpdateScheduleOnCompletion
AFTER UPDATE ON Appointment
FOR EACH ROW
BEGIN
    IF OLD.status = 'Scheduled' AND NEW.status = 'Completed' THEN
        DELETE FROM Schedule
        WHERE doctorId = NEW.doctorId AND availableDate = NEW.date;
    END IF;
END //
DELIMITER ;


-- indexes.sql

CREATE INDEX idx_appointment_doctorId ON Appointment (doctorId);
CREATE INDEX idx_appointment_patientId ON Appointment (patientId);
CREATE INDEX idx_appointment_date ON Appointment (date);

CREATE INDEX idx_schedule_doctorId ON Schedule (doctorId);
CREATE INDEX idx_schedule_availableDate ON Schedule (availableDate);

-- views.sql

CREATE VIEW UpcomingAppointments AS
SELECT a.id AS appointmentId, a.date, a.type, a.status, d.name AS doctorName, p.name AS patientName
FROM Appointment a
JOIN Doctor d ON a.doctorId = d.id
JOIN Patient p ON a.patientId = p.id
WHERE a.status = 'Scheduled' AND a.date >= CURDATE();

CREATE VIEW DoctorAppointmentSummary AS
SELECT d.id AS doctorId, d.name AS doctorName, COUNT(a.id) AS totalAppointments,
       SUM(CASE WHEN a.status = 'Completed' THEN 1 ELSE 0 END) AS completedAppointments,
       SUM(CASE WHEN a.status = 'Cancelled' THEN 1 ELSE 0 END) AS cancelledAppointments
FROM Doctor d
LEFT JOIN Appointment a ON d.id = a.doctorId
GROUP BY d.id;

CREATE VIEW PatientAppointmentHistory AS
SELECT p.id AS patientId, p.name AS patientName, a.date, a.type, a.status, d.name AS doctorName
FROM Patient p
JOIN Appointment a ON p.id = a.patientId
JOIN Doctor d ON a.doctorId = d.id;
