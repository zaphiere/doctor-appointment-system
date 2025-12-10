package com.andrew.doctor_appointment_system.util.mapper;

import com.andrew.doctor_appointment_system.model.DoctorSchedule;
import com.andrew.doctor_appointment_system.model.dto.DoctorScheduleDTO;

public class DoctorScheduleMapper {
	
	public static DoctorScheduleDTO toDTO(DoctorSchedule doctorSchedule) {

		DoctorScheduleDTO dto = new DoctorScheduleDTO();
		dto.setId(doctorSchedule.getId());
		dto.setDateAvailable(doctorSchedule.getDateAvailable());
		dto.setStartTime(doctorSchedule.getTimeFrom());
		dto.setEndTime(doctorSchedule.getTimeTo());
		
		return dto;
	}

}
