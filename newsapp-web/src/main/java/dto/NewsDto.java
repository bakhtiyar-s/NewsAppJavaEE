package dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class NewsDto implements Serializable {
    private UUID uuid = UUID.randomUUID();
    private Integer id;
    private String title;
    private String brief;
    private LocalDate newsDate;
    private String content;
}
