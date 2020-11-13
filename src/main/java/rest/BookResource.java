package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.BookDTO;
import utils.EMF_Creator;
import facades.BookFacade;
import java.io.IOException;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

//Todo Remove or change relevant parts before ACTUAL use
@Path("book")
public class BookResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
       
    private static final BookFacade FACADE =  BookFacade.getBookFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;
            
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }
    
    @Path("count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getBookCount() {
        long count = FACADE.getBookCount();
        //System.out.println("--------------->"+count);
        return "{\"count\":"+count+"}";  //Done manually so no need for a DTO
    }
    
    @Path("add")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("user")
    public String addBookToUser(String json) {
        BookDTO book = GSON.fromJson(json, BookDTO.class);
        FACADE.addBookToPerson(book);
        return "{\"msg\":\"Added " + book.getTitle() + " to user " + book.getUsername() + "\"";
    }
    
    //I know this should be a GET, but I'm too tired to work around how to get the user information atm
    @Path("booksbyuser")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("user")
    public Response getBooksOnUser(String user) {
        //I really should make a separate dto for user, but I'm too lazy to do that for this small project
        BookDTO dtouser = GSON.fromJson(user, BookDTO.class);
        return Response.ok().entity(FACADE.getAllBooksOnPerson(dtouser.getUsername())).build();
    }
    
    @Path("joke")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response fetchFromAPI() throws IOException {
        return Response.ok().entity(GSON.toJson(FACADE.fetchJoke())).build();
    }
}
