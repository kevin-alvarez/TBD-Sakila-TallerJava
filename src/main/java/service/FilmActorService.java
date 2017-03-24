package service;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
//import javax.ws.rs.PUT;
import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import facade.FilmActorFacade;
import model.FilmActor;

@Path("/film_actor")

public class FilmActorService {
	@EJB 
	FilmActorFacade film_actorFacadeEJB;
	
	Logger logger = Logger.getLogger(FilmActorService.class.getName());
	
	@GET
	@Produces({"application/xml", "application/json"})
	public List<FilmActor> findAll(){
		return film_actorFacadeEJB.findAll();
	}
	
	
	@POST
    @Consumes({"application/xml", "application/json"})
    public void create(FilmActor entity) {
		film_actorFacadeEJB.create(entity);
    }

}
