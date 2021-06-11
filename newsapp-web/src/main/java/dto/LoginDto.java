package dto;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class LoginDto implements Serializable {

    private UUID uuid = UUID.randomUUID();
    private String email;
    private String password;
}
