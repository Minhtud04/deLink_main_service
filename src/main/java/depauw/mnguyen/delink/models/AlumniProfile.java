package depauw.mnguyen.delink.models;

import depauw.mnguyen.delink.models.enums.AlumniAvailability;
import depauw.mnguyen.delink.models.enums.RequestType;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "alumni_profiles")
@Data



public class AlumniProfile {

    @Id
    private UUID userId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    private int gradYear;
    private String major;
    private String aboutMe;
    private String currentCompany;
    private String jobTitle;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AlumniAvailability availabilityStatus = AlumniAvailability.AVAILABLE;

    @ElementCollection(targetClass = RequestType.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "alumni_willingness", joinColumns = @JoinColumn(name = "alumni_user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "willingness_type", nullable = false)
    private Set<RequestType> willingnessToHelp;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "alumni_expertise_fields",
            joinColumns = @JoinColumn(name = "alumni_user_id"),
            inverseJoinColumns = @JoinColumn(name = "career_field_id")
    )
    private Set<CareerField> expertiseFields;
}