package depauw.mnguyen.delink.models;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "student_profiles")
@Data
public class StudentProfile {

    @Id
    private UUID userId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId // This tells JPA that the ID is also the foreign key
    @JoinColumn(name = "user_id")
    private User user;

    private int gradYear;
    private String major;
    private String aboutMe;
    private String resumeS3Key;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "student_career_interests",
            joinColumns = @JoinColumn(name = "student_user_id"),
            inverseJoinColumns = @JoinColumn(name = "career_field_id")
    )
    private Set<CareerField> careerInterests;
}