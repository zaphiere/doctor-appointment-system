package com.andrew.doctor_appointment_system.util.mapper;

import com.andrew.doctor_appointment_system.model.Appointment;
import com.andrew.doctor_appointment_system.model.dto.AppointmentDTO;

public class ConsultationBookingMapper {
	
	public static AppointmentDTO toDTO(Appointment appointment) {
		
		AppointmentDTO dto = new AppointmentDTO();
		
		dto.setAppointmentId(appointment.getId());
		dto.setPatientName(
				appointment.getPatient().getFirstname() + 
				" " + appointment.getPatient().getLastname());
		dto.setDoctorName(
				appointment.getDoctor().getFirstname() + 
				" " + appointment.getDoctor().getLastname());
		dto.setDoctorSchedule(DoctorScheduleMapper.toDTO(appointment.getDoctorSchedule()));
		dto.setConsultationDate(appointment.getDate());
		dto.setConsultationTime(appointment.getTime());
		dto.setConsultationDetails(appointment.getConsultationDetails());
		dto.setStatus(appointment.getStatus().name());
		
		return dto;
	}

}
