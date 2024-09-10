package com.training_system.entity.dto;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import com.training_system.entity.Course;
import com.training_system.entity.Enrollment;
import com.training_system.entity.Payment;
import com.training_system.entity.Person;
import com.training_system.entity.enums.EnrollmentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentDto {
	private Long id;
	private Long student_id;
	private Long course_id;
	private Long payment_id;
	private LocalDate enrollmentDate;
	private EnrollmentStatus enrollment_status;
	
	public static EnrollmentDto fromEntityToDto(Enrollment enrollment) {
		return new EnrollmentDto(
				enrollment.getId(), 
				enrollment.getStudent().getId(),
				enrollment.getCourse().getId(),
				enrollment.getPayment().getId(),
				enrollment.getEnrollmentDate(),
				enrollment.getEnrollment_status()
			);
	}
	
	public static Set<EnrollmentDto> fromEntitiesToDtos(Set<Enrollment> enrollments){
		return enrollments.stream().map(enrollment -> fromEntityToDto(enrollment)).collect(Collectors.toSet());
	}
}
