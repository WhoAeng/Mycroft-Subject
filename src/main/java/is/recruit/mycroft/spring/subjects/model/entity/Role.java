package is.recruit.mycroft.spring.subjects.model.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String roleName;

//    @OneToMany(mappedBy = "role")
//    private List<UserRole> userRoles = new ArrayList<>();


    @Builder
    private Role(String roleName) {
        this.roleName = roleName;
    }
//    @Builder
//    private Role(String roleName, String userame) {
//        this.roleName = roleName;
//    }
}
