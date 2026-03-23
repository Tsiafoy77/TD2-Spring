package com.td2.students;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StudentController {

    private final List<Student> students = new ArrayList<>();

    // A) GET /welcome?name=...
    @GetMapping("/welcome")
    public String welcome(@RequestParam String name) {
        return "Welcome " + name;
    }

    @PostMapping("/students")
    public List<Student> addStudents(@RequestBody List<Student> newStudents) {

        students.addAll(newStudents);


        StringBuilder noms = new StringBuilder();
        for (Student s : students) {
            noms.append(s.getFirstName())
                    .append(" ")
                    .append(s.getLastName())
                    .append(", ");
        }

        String nomsEnChaine = noms.toString().replaceAll(", $", "");  // supprime la dernière virgule


        System.out.println("Noms des étudiants enregistrés : " + nomsEnChaine);

        return new ArrayList<>(students);   // réponse JSON attendue
    }

    // C) GET /students avec Accept header
    @GetMapping("/students")
    public ResponseEntity<?> getStudents(@RequestHeader("Accept") String accept) {
        if ("text/plain".equals(accept)) {
            StringBuilder sb = new StringBuilder();
            for (Student s : students) {
                sb.append(s.getFirstName()).append(" ").append(s.getLastName()).append("\n");
            }
            return ResponseEntity.ok(sb.toString());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body("Format non supporté");
        }
    }
}
