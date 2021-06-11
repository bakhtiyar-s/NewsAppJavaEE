package dto.mapper;

import java.io.Serializable;

public interface DtoMapper<T, S extends Serializable> {
    T fromDto(S var);
    S toDto(T var);
}
