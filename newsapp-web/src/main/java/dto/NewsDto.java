package dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Data
public class NewsDto implements Serializable {
    private UUID uuid = UUID.randomUUID();
    private Integer id;
    private String title;
    private String brief;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date newsDate;
    private String content;
}
