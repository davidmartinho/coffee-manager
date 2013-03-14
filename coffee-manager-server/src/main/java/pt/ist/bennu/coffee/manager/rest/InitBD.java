package pt.ist.bennu.coffee.manager.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/coffee-manager/initDB")
public class InitBD extends CoffeeManagerAbstractResource {

    @GET
    public Response doIt() {
        if (getCoffeeManager().initDB()) {
            return Response.ok("DB init sucessfully.").build();
        } else {
            return Response.ok("DB already initialized.").build();
        }
    }

}
