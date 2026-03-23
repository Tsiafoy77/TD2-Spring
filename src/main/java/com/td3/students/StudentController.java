package com.td3.students;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StudentController {

    private final List<Student> students = new ArrayList<>();

    // A) a) GET /welcome avec ResponseEntity + vérification du paramètre
    @GetMapping("/welcome")
    public ResponseEntity<String> welcome(@RequestParam(required = false) String name) {
        if (name == null || name.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Paramètre 'name' manquant ou vide");
        }
        return ResponseEntity.ok("Welcome " + name);
    }

    // A) b) POST /students avec 201 Created + gestion erreur 500
    @PostMapping("/students")
    public ResponseEntity<List<Student>> addStudents(@RequestBody List<Student> newStudents) {
        try {
            students.addAll(newStudents);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ArrayList<>(students));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    // A) c) GET /students avec Accept header + codes 400 / 501 / 500
    @GetMapping("/students")
    public ResponseEntity<?> getStudents(@RequestHeader(value = "Accept", required = false) String accept) {
        try {
            if (accept == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Entête Accept manquante");
            }

            if (!"text/plain".equals(accept) && !"application/json".equals(accept)) {
                return ResponseEntity.status(501)
                        .body("Format non supporté");
            }

            if ("text/plain".equals(accept)) {
                String noms = students.stream()
                        .map(s -> s.getFirstName() + " " + s.getLastName())
                        .reduce((a, b) -> a + ", " + b)
                        .orElse("");
                return ResponseEntity.ok(noms);
            } else {
                return ResponseEntity.ok(new ArrayList<>(students));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur serveur");
        }
    }
}