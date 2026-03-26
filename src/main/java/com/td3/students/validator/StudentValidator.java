package com.td3.students.validator;
import com.td3.students.model.Student;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class StudentValidator {

    public static void validate(Student student) {
        if (student.getReference() == null || student.getReference().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "NewStudent.reference cannot be null");
        }
        if (student.getFirstName() == null || student.getFirstName().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "NewStudent.firstName cannot be null");
        }
        if (student.getLastName() == null || student.getLastName().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "NewStudent.lastName cannot be null");
        }
    }
}