package depauw.mnguyen.delink.health_check.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// Lombok annotations to reduce boilerplate code (getters, setters, etc.)
// Make sure you have the Lombok dependency in your pom.xml
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "health_check_data") // Explicitly name the table in the database
@Data // Generates getters, setters, toString, etc.
@NoArgsConstructor // Generates a no-argument constructor, required by JPA
@AllArgsConstructor // Generates a constructor with all arguments
public class HealthCheckData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String job;

    private int age;
}