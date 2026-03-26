package com.td3.students.controller;

import com.td3.students.model.Student;
import com.td3.students.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class StudentController {

    private final StudentService studentService;
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // A) GET /welcome
    @GetMapping("/welcome")
    public ResponseEntity<String> welcome(@RequestParam(required = false) String name) {
        if (name == null || name.isBlank()) {
            return ResponseEntity.badRequest().body("Paramètre 'name' manquant ou vide");
        }
        return ResponseEntity.ok("Welcome " + name);
    }

    // B) POST /students
    @PostMapping("/students")
    public ResponseEntity<List<Student>> createStudents(@RequestBody List<Student> newStudents) {
        try {
            List<Student> result = studentService.addStudents(newStudents);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .header("Content-Type", "application/json")
                    .body(result);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .header("Content-Type", "text/plain")
                    .body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // C) GET /students
    @GetMapping("/students")
    public ResponseEntity<?> getStudents(@RequestHeader(value = "Accept", required = false) String accept) {

        return ResponseEntity.ok("GET /students non implémenté pour le moment");
    }
}