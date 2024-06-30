package com.emploverse.backend.util.mapper.employee;

import com.emploverse.backend.dto.employee.EmployeeDTO;
import com.emploverse.backend.model.employee.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

        EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

        @Mapping(source = "manager.id", target = "managerId")
        @Mapping(source = "user.id", target = "userId")
        @Mapping(source = "department.id", target = "departmentId")
        @Mapping(source = "salary.id", target = "salaryId")
        @Mapping(source = "attendances", target = "attendanceIds", qualifiedByName = "attendanceIds")
        @Mapping(source = "projects", target = "projectIds", qualifiedByName = "projectIds")
        @Mapping(source = "tasks", target = "taskIds", qualifiedByName = "taskIds")
        @Mapping(source = "subordinates", target = "subordinateIds", qualifiedByName = "subordinateIds")
        EmployeeDTO employeeToEmployeeDTO(Employee employee);

        @Mapping(source = "managerId", target = "manager.id")
        @Mapping(source = "userId", target = "user.id")
        @Mapping(source = "departmentId", target = "department.id")
        @Mapping(source = "salaryId", target = "salary.id")
        @Mapping(source = "attendanceIds", target = "attendances", qualifiedByName = "attendanceEntities")
        @Mapping(source = "projectIds", target = "projects", qualifiedByName = "projectEntities")
        @Mapping(source = "taskIds", target = "tasks", qualifiedByName = "taskEntities")
        @Mapping(source = "subordinateIds", target = "subordinates", qualifiedByName = "employeeEntities")
        Employee employeeDTOToEmployee(EmployeeDTO employeeDTO);

        @Named("attendanceIds")
        default Set<Long> mapAttendancesToIds(Set<Attendance> attendances) {
                return attendances.stream().map(Attendance::getId).collect(Collectors.toSet());
        }

        @Named("attendanceEntities")
        default Set<Attendance> mapIdsToAttendances(Set<Long> ids) {
                return ids.stream().map(id -> {
                        Attendance attendance = new Attendance();
                        attendance.setId(id);
                        return attendance;
                }).collect(Collectors.toSet());
        }

        @Named("projectIds")
        default Set<Long> mapProjectsToIds(Set<Project> projects) {
                return projects.stream().map(Project::getId).collect(Collectors.toSet());
        }

        @Named("projectEntities")
        default Set<Project> mapIdsToProjects(Set<Long> ids) {
                return ids.stream().map(id -> {
                        Project project = new Project();
                        project.setId(id);
                        return project;
                }).collect(Collectors.toSet());
        }

        @Named("taskIds")
        default Set<Long> mapTasksToIds(Set<Task> tasks) {
                return tasks.stream().map(Task::getId).collect(Collectors.toSet());
        }

        @Named("taskEntities")
        default Set<Task> mapIdsToTasks(Set<Long> ids) {
                return ids.stream().map(id -> {
                        Task task = new Task();
                        task.setId(id);
                        return task;
                }).collect(Collectors.toSet());
        }

        @Named("subordinateIds")
        default Set<Long> mapSubordinatesToIds(Set<Employee> subordinates) {
                return subordinates.stream().map(Employee::getId).collect(Collectors.toSet());
        }

        @Named("employeeEntities")
        default Set<Employee> mapIdsToEmployees(Set<Long> ids) {
                return ids.stream().map(id -> {
                        Employee employee = new Employee();
                        employee.setId(id);
                        return employee;
                }).collect(Collectors.toSet());
        }
}
