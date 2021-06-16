package entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "role", schema = "news_portal")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "role_name", nullable = false)
    private String roleName;
}
