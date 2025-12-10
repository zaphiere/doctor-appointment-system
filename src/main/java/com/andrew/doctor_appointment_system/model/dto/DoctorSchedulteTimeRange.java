package com.andrew.doctor_appointment_system.model.dto;

import java.time.LocalTime;

public interface DoctorSchedulteTimeRange {
	
	LocalTime getStartTime();
	LocalTime getEndTime();
}
