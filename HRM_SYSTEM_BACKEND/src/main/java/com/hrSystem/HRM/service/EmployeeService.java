package com.hrSystem.HRM.service;

import com.hrSystem.HRM.dto.EmployeeRequestDTO;
import com.hrSystem.HRM.dto.EmployeeResponseDTO;
import com.hrSystem.HRM.entity.Employee;
import com.hrSystem.HRM.exception.ResourceNotFoundException;
import com.hrSystem.HRM.exception.ValidationException;
import com.hrSystem.HRM.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO requestDTO) {
        // Validate employee ID uniqueness if provided
        if (requestDTO.getEmployeeId() != null && !requestDTO.getEmployeeId().trim().isEmpty()) {
            if (employeeRepository.existsByEmployeeId(requestDTO.getEmployeeId())) {
                throw new ValidationException("Employee ID already exists: " + requestDTO.getEmployeeId());
            }
        }

        Employee employee = convertToEntity(requestDTO);
        Employee savedEmployee = employeeRepository.save(employee);
        return convertToDTO(savedEmployee);
    }

    public EmployeeResponseDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", id));
        return convertToDTO(employee);
    }

    public EmployeeResponseDTO getEmployeeByEmployeeId(String employeeId) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", employeeId));
        return convertToDTO(employee);
    }

    public List<EmployeeResponseDTO> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public EmployeeResponseDTO updateEmployee(Long id, EmployeeRequestDTO requestDTO) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", id));

        // Validate employee ID uniqueness if provided and different from current
        if (requestDTO.getEmployeeId() != null && !requestDTO.getEmployeeId().trim().isEmpty()) {
            if (employeeRepository.existsByEmployeeIdAndIdNot(requestDTO.getEmployeeId(), id)) {
                throw new ValidationException("Employee ID already exists: " + requestDTO.getEmployeeId());
            }
        }

        updateEmployeeFields(employee, requestDTO);
        Employee updatedEmployee = employeeRepository.save(employee);
        return convertToDTO(updatedEmployee);
    }

    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employee", id);
        }
        employeeRepository.deleteById(id);
    }

    private Employee convertToEntity(EmployeeRequestDTO dto) {
        Employee employee = new Employee();
        employee.setEmployeeId(dto.getEmployeeId() != null && !dto.getEmployeeId().trim().isEmpty() 
                ? dto.getEmployeeId() : null);
        employee.setName(dto.getName());
        employee.setDepartment(dto.getDepartment());
        employee.setRole(dto.getRole());
        employee.setSkills(dto.getSkills());
        employee.setSkillLevel(dto.getSkillLevel() != null ? dto.getSkillLevel() : 1);
        employee.setExperience(dto.getExperience() != null ? dto.getExperience() : 0.0);
        employee.setCategory(dto.getCategory() != null ? dto.getCategory() : "Full-time");
        employee.setAvailability(dto.getAvailability() != null ? dto.getAvailability() : "Available");
        employee.setPerformanceRating(dto.getPerformanceRating() != null ? dto.getPerformanceRating() : 0.0);
        employee.setStatus(dto.getStatus() != null ? dto.getStatus() : "Present");
        return employee;
    }

    private EmployeeResponseDTO convertToDTO(Employee employee) {
        EmployeeResponseDTO dto = new EmployeeResponseDTO();
        dto.setId(employee.getId());
        dto.setEmployeeId(employee.getEmployeeId());
        dto.setName(employee.getName());
        dto.setDepartment(employee.getDepartment());
        dto.setRole(employee.getRole());
        dto.setSkills(employee.getSkills());
        dto.setSkillLevel(employee.getSkillLevel());
        dto.setExperience(employee.getExperience());
        dto.setCategory(employee.getCategory());
        dto.setAvailability(employee.getAvailability());
        dto.setPerformanceRating(employee.getPerformanceRating());
        dto.setStatus(employee.getStatus());
        dto.setCreatedAt(employee.getCreatedAt());
        dto.setUpdatedAt(employee.getUpdatedAt());
        return dto;
    }

    private void updateEmployeeFields(Employee employee, EmployeeRequestDTO dto) {
        if (dto.getEmployeeId() != null) {
            employee.setEmployeeId(dto.getEmployeeId().trim().isEmpty() ? null : dto.getEmployeeId());
        }
        if (dto.getName() != null) {
            employee.setName(dto.getName());
        }
        if (dto.getDepartment() != null) {
            employee.setDepartment(dto.getDepartment());
        }
        if (dto.getRole() != null) {
            employee.setRole(dto.getRole());
        }
        if (dto.getSkills() != null) {
            employee.setSkills(dto.getSkills());
        }
        if (dto.getSkillLevel() != null) {
            employee.setSkillLevel(dto.getSkillLevel());
        }
        if (dto.getExperience() != null) {
            employee.setExperience(dto.getExperience());
        }
        if (dto.getCategory() != null) {
            employee.setCategory(dto.getCategory());
        }
        if (dto.getAvailability() != null) {
            employee.setAvailability(dto.getAvailability());
        }
        if (dto.getPerformanceRating() != null) {
            employee.setPerformanceRating(dto.getPerformanceRating());
        }
        if (dto.getStatus() != null) {
            employee.setStatus(dto.getStatus());
        }
    }
}

