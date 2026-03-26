package com.td3.students.service;
import com.td3.students.model.Student;
import com.td3.students.validator.StudentValidator;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    private final List<Student> studentsInMemory = new ArrayList<>();

    public List<Student> addStudents(List<Student> newStudents) {
        for (Student s : newStudents) {
            StudentValidator.validate(s);
        }
        studentsInMemory.addAll(newStudents);
        return new ArrayList<>(studentsInMemory);
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(studentsInMemory);
    }
}