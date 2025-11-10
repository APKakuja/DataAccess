package com.ra2.jdbctemplate_h2.Services;
import com.ra2.jdbctemplate_h2.model.Customer;
import com.ra2.jdbctemplate_h2.repo.CustomerRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.nio.file.*;
import java.io.File.*;
import java.util.List;
import java.util.Optional;

public class CustomerService {

    private CustomerRepository repository;

    public String createCustomer(Customer customer) {
        if (customer.getName() == null && customer.getName().length() > 100 && customer.getName().length() < 3 ) {
            return "El nom ha de tenir com a mínim 3 lletres i maxim 100.";
        }
        if (customer.getId() == null) {
            return "Usuari necesita un ID";
        }
        if (customer.getAge() < 18) {
            return "Usuari ha de ser major d'edat";
        }
        if (customer.getEmail() == null)
            return "Usuari ha de tenir un email";

        repository.createCustomer(customer);
        return "Client creat correctament.";
    }
    public Customer findCustomerById(Long id) {
        try {
            return repository.findById(id);
        } catch (Exception e) {
            return null;
        }
    }

    public String deleteCustomer(Long id) {
        Customer customer = findCustomerById(id);
        if (customer != null) {
            repository.deleteCustomer(id);
            return "Client eliminat correctament.";
        } else {
            return "No s'ha trobat cap client amb ID: " + id;
        }
    }

    public int uploadCsvAndSaveUsers(MultipartFile csvFile) throws IOException {
        List<Customer> customers = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(csvFile.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] fields = line.split(",");
            if (fields.length != 5) continue;

            Customer customer = new Customer();
            customer.setName(fields[0]);
            customer.setEmail(fields[1]);
            customer.setAge(Integer.parseInt(fields[2]));
            customer.setCicle(fields[3]);
            customer.setYear_val(Integer.parseInt(fields[4]));

            customers.add(customer);
        }

        for (Customer c : customers) {
            repository.createCustomer(c);
        }

        Path processedDir = Paths.get("src/main/resources/csv_processed");
        Files.createDirectories(processedDir);

        String filename = "processed_" + System.currentTimeMillis() + "_" + csvFile.getOriginalFilename();
        Path destination = processedDir.resolve(filename);
        Files.copy(csvFile.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

        return customers.size();
    }

    public String uploadImage(Long userId, MultipartFile imageFile) {
        Customer customer = findCustomerById(userId);
        if (customer == null) {
            return null;
        }

        try {
            String folderPath = "src/main/resources/images";
            Files.createDirectories(Paths.get(folderPath));

            String filename = "user_" + userId + "_" + imageFile.getOriginalFilename();
            Path filePath = Paths.get(folderPath, filename);
            Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            String imagePath = "/images/" + filename;
            repository.updateImagePath(userId, imagePath);

            return imagePath;
        } catch (Exception e) {
            return null;
        }
    }
}
