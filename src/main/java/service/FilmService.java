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
import facade.FilmActorFacade;
import facade.FilmFacade;
import model.Actor;
import model.Film;
import model.FilmActor;

@Path("/films")

public class FilmService {
	@EJB
	ActorFacade actorFacadeEJB;
	@EJB 
	FilmFacade filmFacadeEJB;
	@EJB
	FilmActorFacade filmActorFacadeEJB;
	
	Logger logger = Logger.getLogger(FilmService.class.getName());
	
	@GET
	@Produces({"application/xml", "application/json"})
	public List<Film> findAll(){
		return filmFacadeEJB.findAll();
	}
	
	@GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Film find(@PathParam("id") Integer id) {
        return filmFacadeEJB.find(id);
    }
	
	@POST
    @Consumes({"application/xml", "application/json"})
    public void create(Film entity) {
        filmFacadeEJB.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Film entity) {
    	entity.setFilmId(id.intValue());
        filmFacadeEJB.edit(entity);
    }
    
    //Consultas
    @GET
    @Path("{id}/actors")
    @Produces({"application/xml", "application/json"})
    public List<Actor> getActorsFromFilm(@PathParam("id") Integer id) {
    	List<FilmActor> film_actorsList = filmActorFacadeEJB.findAll();
    	List<Actor> filmSelectedActors = new ArrayList<Actor>();
    	
    	for(int i=0;i<film_actorsList.size();i++){
    		if (id == film_actorsList.get(i).getFilmId()){
    			filmSelectedActors.add(actorFacadeEJB.find(film_actorsList.get(i).getActorId()));
    		}
    	}
    	
    	return filmSelectedActors;
    }
    
    @POST
    @Path("{idFilm}/films/{idActor}")
    @Consumes({"application/xml", "application/json"})
    public void setActorToFilm(@PathParam("idFilm") Integer idFilm, @PathParam("idActor") Integer idActor){
    	Actor actor = actorFacadeEJB.find(idActor);
    	if(actor != null){
    		FilmActor fa = new FilmActor();
    		fa.setActorId(idActor);
    		fa.setFilmId(idFilm);
    		filmActorFacadeEJB.create(fa);
    	}
    }
}
