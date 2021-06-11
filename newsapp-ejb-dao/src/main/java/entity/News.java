package entity;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "news", schema = "news_portal")
public class News implements MyEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String brief;
    @Column(name = "news_date")
    @NotNull
    private LocalDate newsDate;
    private String content;
}
