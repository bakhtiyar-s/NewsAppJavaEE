package controller;

import dto.NewsDto;
import dto.mapper.NewsMapper;
import entity.News;
import org.slf4j.Logger;
import service.NewsService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("news")
public class NewsController {

    @Inject
    private NewsService newsService;

    @Inject
    private NewsMapper newsMapper;

    @Inject
    private Logger logger;

    @GET
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response listNews() {
        List<News> news = newsService.findAll();
        if (!news.isEmpty()) {
            List<NewsDto> newsDtos = news.stream().map(news1 -> newsMapper.toDto(news1)).collect(Collectors.toList());
            return Response.status(Response.Status.OK).entity(newsDtos).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("No news were found").build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response newsById(@PathParam("id") int id) {
        News news = newsService.findById(id);
        if (news != null) {
            NewsDto newsDto = newsMapper.toDto(news);
            return Response.status(Response.Status.OK).entity(newsDto).build();
        } else {
            logger.error("News with id {} was not found", id);
            return Response.status(Response.Status.NOT_FOUND).entity("News with id " + id + " was not found").build();
        }
    }

    @POST
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response addNews(NewsDto newsDto) {
        if (newsDto == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        try {
            News savedNews = newsService.save(newsMapper.fromDto(newsDto));
            return Response.status(Response.Status.OK).entity(newsMapper.toDto(savedNews)).build();
        } catch (Exception e) {
            logger.error("Exception: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response updateNews(@PathParam("id") int id, NewsDto newsToUpdateDto) {
        if (newsToUpdateDto == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        News oldNews = newsService.findById(id);
        if (oldNews == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("News with id " + id + " was not found").build();
        }
        try {
            News newsToUpdate = newsMapper.fromDto(newsToUpdateDto);
            newsToUpdate.setId(oldNews.getId());
            newsService.update(newsToUpdate);
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            logger.error("Exception: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response deleteNews(@PathParam("id") int id) {
        try {
            newsService.deleteById(id);
            return Response.status(Response.Status.OK).build();
        } catch (NotFoundException e) {
            logger.error("Not Found Exception: ", e);
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }

    }
}
