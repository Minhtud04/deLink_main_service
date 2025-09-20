package depauw.mnguyen.delink.health_check;

import depauw.mnguyen.delink.health_check.Models.HealthCheckData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthCheckRepository extends JpaRepository<HealthCheckData, Long> {

}