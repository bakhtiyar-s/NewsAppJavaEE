package dto.mapper;


import dto.NewsDto;
import entity.News;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface NewsMapper extends DtoMapper<News, NewsDto> {
}
