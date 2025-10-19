package com.ra2.jdbctemplate_h2.controller;

import com.ra2.jdbctemplate_h2.model.Customer;
import com.ra2.jdbctemplate_h2.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jdbctemplate")
public class CustomerController {

    @Autowired
    private CustomerRepository repository;

    // Inicializar BD y datos de ejemplo
    // URL ENDPOINT: http://localhost:8080/jdbctemplate/init-db
    @PostMapping("/init-db")
    public ResponseEntity<String> initDb() {
        repository.createTable();
        repository.insertSampleData();
        return ResponseEntity.ok("Tabla creada y datos insertados");
    }

    // Listar todos los clientes
    // END POINT URL: http://localhost:8080/jdbctemplate/findAllCustomers
    @GetMapping("/findAllCustomers")
    public List<Customer> findAllCustomers() {
        return repository.findAll();
    }

    // Buscar cliente por id
    // END POINT URL: http://localhost:8080/jdbctemplate/customer/1
    @GetMapping("/customer/{id}")
    public Customer findById(@PathVariable Long id) {
        return repository.findById(id);
    }

    // Crear cliente
    // END POINT URL: http://localhost:8080/jdbctemplate/customer
    @PostMapping("/customer")
    public ResponseEntity<String> createCustomer(@RequestBody Customer customer) {
        repository.createCustomer(customer);
        return ResponseEntity.status(201).body("Cliente creado");
    }

    // Actualizar cliente
    // URL END POINT: http://localhost:8080/jdbctemplate/customer
    @PutMapping("/customer")
    public ResponseEntity<String> updateCustomer(@RequestBody Customer customer) {
        repository.updateCustomer(customer);
        return ResponseEntity.ok("Cliente actualizado");
    }

    // Eliminar cliente
    // URL END POINT: http://localhost:8080/jdbctemplate/customer/"id of desired customer here"
    @DeleteMapping("/customer/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
        repository.deleteCustomer(id);
        return ResponseEntity.ok("Cliente eliminado");
    }
}
