package entity;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;


@Entity
@Data
@Table(name = "news", schema = "news_portal")
@XmlRootElement(name = "news")
@XmlAccessorType(XmlAccessType.FIELD)
public class News implements MyEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String brief;
    @Column(name = "news_date")
    @NotNull
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date newsDate;
    private String content;
}
