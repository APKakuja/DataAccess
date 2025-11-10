package com.ra2.jdbctemplate_h2.repo;

import com.ra2.jdbctemplate_h2.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CustomerRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void updateImagePath(Long userId, String imagePath) {

    }

    // RowMapper para convertir ResultSet a Customer
    private static class CustomerRowMapper implements RowMapper<Customer> {
        @Override
        public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Customer(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getInt("age"),
                    rs.getString("cicle"),
                    rs.getInt("year_val") // <-- coincide con la columna en la tabla
            );
        }
    }

    // Crear tabla
    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS customer (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(255)," +
                "email VARCHAR(255)," +
                "age INT," +
                "cicle VARCHAR(50)," +
                "year_val INT)"; // <-- usa year_val
        jdbcTemplate.execute(sql);
    }

    // Listar todos
    public List<Customer> findAll() {
        return jdbcTemplate.query("SELECT * FROM customer", new CustomerRowMapper());
    }

    // Buscar por ID
    public Customer findById(Long id) {
        String sql = "SELECT * FROM customer WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new CustomerRowMapper(), id);
    }

    // Crear/insertar cliente
    public void createCustomer(Customer c) {
        String sql = "INSERT INTO customer (name, email, age, cicle, year_val) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, c.getName(), c.getEmail(), c.getAge(), c.getCicle(), c.getYear_val());
    }

    // Actualizar cliente
    public void updateCustomer(Customer c) {
        String sql = "UPDATE customer SET name=?, email=?, age=?, cicle=?, year_val=? WHERE id=?";
        jdbcTemplate.update(sql, c.getName(), c.getEmail(), c.getAge(), c.getCicle(), c.getYear_val(), c.getId());
    }

    // Eliminar cliente
    public void deleteCustomer(Long id) {
        String sql = "DELETE FROM customer WHERE id=?";
        jdbcTemplate.update(sql, id);
    }
}
