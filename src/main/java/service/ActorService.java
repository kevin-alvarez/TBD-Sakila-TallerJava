package service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import facade.ActorFacade;
import facade.FilmFacade;
import facade.FilmActorFacade;
import model.Actor;
import model.Film;
import model.FilmActor;

@Path("/actors")
public class ActorService {
	
	@EJB 
	ActorFacade actorFacadeEJB;
	@EJB
	FilmFacade filmFacadeEJB;
	@EJB
	FilmActorFacade filmActorFacadeEJB;
	
	Logger logger = Logger.getLogger(ActorService.class.getName());
	
	@GET
	@Produces({"application/xml", "application/json"})
	public List<Actor> findAll(){
		return actorFacadeEJB.findAll();
	}
	
	@GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Actor find(@PathParam("id") Integer id) {
        return actorFacadeEJB.find(id);
    }
	
	@POST
    @Consumes({"application/xml", "application/json"})
    public void create(Actor entity) {
        actorFacadeEJB.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Actor entity) {
    	entity.setActorId(id.intValue());
        actorFacadeEJB.edit(entity);
    }
	
    //Consultas
    @GET
    @Path("{id}/films")
    @Produces({"application/xml", "application/json"})
    public List<Film> getFilmsFromActor(@PathParam("id") Integer id) {
    	List<FilmActor> film_actorsList = filmActorFacadeEJB.findAll();
    	List<Film> actorSelectedFilms = new ArrayList<Film>();
    	
    	for(int i=0;i<film_actorsList.size();i++){
    		if (id == film_actorsList.get(i).getActorId()){
    			actorSelectedFilms.add(filmFacadeEJB.find(film_actorsList.get(i).getFilmId()));
    		}
    	}
    	
    	return actorSelectedFilms;
    }
    
    @POST
    @Path("{idActor}/films/{idFilm}")
    @Consumes({"application/xml", "application/json"})
    public void setFilmToActor(@PathParam("idActor") Integer idActor, @PathParam("idFilm") Integer idFilm){
    	Film film = filmFacadeEJB.find(idFilm);
    	if(film != null){
    		FilmActor fa = new FilmActor();
    		fa.setActorId(idActor);
    		fa.setFilmId(idFilm);
    		filmActorFacadeEJB.create(fa);
    	}
    }
    
}
