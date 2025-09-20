package depauw.mnguyen.delink.health_check;

import depauw.mnguyen.delink.health_check.Models.HealthCheckData;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/health")
public class HealthCheckController {

    private final HealthCheckRepository healthCheckRepository;
    private final StringRedisTemplate redisTemplate;

    public HealthCheckController(HealthCheckRepository healthCheckRepository, StringRedisTemplate redisTemplate) {
        this.healthCheckRepository = healthCheckRepository;
        this.redisTemplate = redisTemplate;
    }

    @GetMapping
    public String health() {
        return "OK";
    }

    @PostMapping("/seed")
    public ResponseEntity<String> postSeed() {
        try {
            // 1. Seeding the PostgreSQL Database
            HealthCheckData dbData = new HealthCheckData(null, "test-user-minh", "jobless", 100);
            healthCheckRepository.save(dbData);

            // 2. Seeding the Redis Cache
            redisTemplate.opsForValue().set("minh", "jobless");

            // 3. Return a success response
            String responseMessage = "SUCCESS: DB seeded with user 'test-user-minh'. Redis key 'minh' set to 'jobless'.";
            return ResponseEntity.ok(responseMessage);

        } catch (Exception e) {
            // 4. Return an error response if anything fails
            String errorMessage = "ERROR: Failed to seed data. Check connections. Error: " + e.getMessage();
            return ResponseEntity.internalServerError().body(errorMessage);
        }
    }

    @GetMapping("/seed")
    public ResponseEntity<String> getSeed() {
        try {
            List<HealthCheckData> dbData = healthCheckRepository.findAll();
            String dbResult = "All records in 'health_check_data': " + dbData.toString();

            String redisValue = redisTemplate.opsForValue().get("minh");
            String redisResult = "Value for key 'minh': " + (redisValue != null ? redisValue : "NOT FOUND");

            String finalResponse = "--- Redis Data ---\n" + redisResult + "\n\n--- PostgreSQL Data ---\n" + dbResult;
            return ResponseEntity.ok(finalResponse);
        } catch (Exception e) {
            // 4. Return an error response if anything fails
            String errorMessage = "ERROR: Failed to retrieve data. Check connections. Error: " + e.getMessage();
            return ResponseEntity.internalServerError().body(errorMessage);
        }
    }
}
