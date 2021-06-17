package controller;

import dto.NewsDto;
import dto.mapper.NewsMapper;
import entity.News;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jms.JmsService;
import org.slf4j.Logger;
import service.NewsService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("news")
@Api(value = "News Controller")
@RolesAllowed({"ADMIN", "USER"})
public class NewsController {

    @Inject
    private NewsService newsService;

    @Inject
    private NewsMapper newsMapper;

    @Inject
    private Logger logger;

    @GET
    @Produces(value = MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve all news", notes = "Return some json to the client")
    public Response listNews() {
        try {
            List<News> news = newsService.findAll();
            List<NewsDto> newsDtos = news.stream().map(news1 -> newsMapper.toDto(news1)).collect(Collectors.toList());
            return Response.status(Response.Status.OK).entity(newsDtos).build();
        } catch (NotFoundException e){
            logger.error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(value = MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve news by id", notes = "Return some json to the client")
    public Response newsById(@PathParam("id") int id) {
        try {
            News news = newsService.findById(id);
            NewsDto newsDto = newsMapper.toDto(news);
            return Response.status(Response.Status.OK).entity(newsDto).build();
        } catch (NotFoundException e){
            logger.error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @POST
    @Produces(value = MediaType.APPLICATION_JSON)
    @Consumes(value = MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Add news to database", notes = "Accepts some json from the client")
    public Response addNews(NewsDto newsDto) {
        if (newsDto == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Request body is empty").build();
        }
        try {
            News savedNews = newsService.save(newsMapper.fromDto(newsDto));
            return Response.status(Response.Status.OK).entity(newsMapper.toDto(savedNews)).build();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Produces(value = MediaType.APPLICATION_JSON)
    @Consumes(value = MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Update news by id", notes = "Accepts some json from the client")
    public Response updateNews(@PathParam("id") int id, NewsDto newsToUpdateDto) {
        if (newsToUpdateDto == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Request body is empty").build();
        }
        try {
            newsService.update(newsMapper.fromDto(newsToUpdateDto), id);
            return Response.status(Response.Status.OK).build();
        } catch (NotFoundException e) {
            logger.error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(value = MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Delete news by id")
    @RolesAllowed("ADMIN")
    public Response deleteNews(@PathParam("id") int id) {
        try {
            newsService.deleteById(id);
            return Response.status(Response.Status.OK).build();
        } catch (NotFoundException e) {
            logger.error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }

    }
}
