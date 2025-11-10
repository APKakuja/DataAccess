package com.ra2.jdbctemplate_h2.controller;

import com.ra2.jdbctemplate_h2.model.Customer;
import com.ra2.jdbctemplate_h2.Services.CustomerService;
import com.ra2.jdbctemplate_h2.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerController {

    @Autowired
    private CustomerRepository repository;

    @Autowired
    private CustomerService service;

    // Inicialitzar la BD i inserir dades de mostra
    @PostMapping("/init-db")
    public ResponseEntity<String> initDb() {
        repository.createTable();
        return ResponseEntity.ok("Taula creadas.");
    }

    // Llistar tots els clients
    @GetMapping("/users")
    public List<Customer> findAllCustomers() {
        return repository.findAll();
    }

    // Buscar client per ID
    @GetMapping("/users/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Customer customer = service.findCustomerById(id);
        if (customer == null) {
            return ResponseEntity.status(404).body("Client no trobat.");
        }
        return ResponseEntity.ok(customer);
    }

    // Crear client
    @PostMapping("/users")
    public ResponseEntity<String> createCustomer(@RequestBody Customer customer) {
        String result = service.createCustomer(customer);
        if (result.contains("mínim")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.status(201).body(result);
    }

    // Actualitzar client
    @PutMapping("/users")
    public ResponseEntity<String> updateCustomer(@RequestBody Customer customer) {
        repository.updateCustomer(customer);
        return ResponseEntity.ok("Client actualitzat correctament.");
    }

    // Eliminar client
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
        String result = service.deleteCustomer(id);
        if (result.contains("No s'ha trobat")) {
            return ResponseEntity.status(404).body(result);
        }
        return ResponseEntity.ok(result);
    }

    // Pujar imatge de perfil
    @PostMapping("/users/{user_id}/image")
    public ResponseEntity<String> uploadImage(@PathVariable Long user_id,
                                              @RequestParam MultipartFile imageFile) {
        String imagePath = service.uploadImage(user_id, imageFile);
        if (imagePath == null) {
            return ResponseEntity.status(404).body("Usuari no trobat o error en pujar la imatge.");
        }
        return ResponseEntity.ok("Imatge pujada: " + imagePath);
    }

    // Càrrega massiva d'usuaris via CSV
    @PostMapping("/users/upload-csv")
    public ResponseEntity<String> uploadCsv(@RequestParam MultipartFile csvFile) {
        try {
            int count = service.uploadCsvAndSaveUsers(csvFile);
            return ResponseEntity.ok("S'han afegit " + count + " registres a la base de dades.");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error en processar el fitxer CSV.");
        }
    }
}
