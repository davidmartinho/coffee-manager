package pt.ist.bennu.coffee.manager.rest;

import java.math.BigDecimal;
import java.util.Set;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import pt.ist.bennu.coffee.manager.domain.CoffeeItem;
import pt.ist.bennu.service.Service;

@Path("/coffee-manager/items")
public class ItemResource extends CoffeeManagerAbstractResource {

    @GET
    @Path("/{oid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItem(@PathParam("oid") String oid) {
        return Response.ok(view(readDomainObject(oid))).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllItems() {
        Set<CoffeeItem> coffeeItemSet = getCoffeeManager().getCoffeeItemSet();
        return Response.ok(view(coffeeItemSet, "items")).build();
    }

    @PUT
    @Path("/{oid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response setItem(@PathParam("oid") String oid, @FormParam("data") String jsonData) {
        final CoffeeItem item = update(jsonData, (CoffeeItem) readDomainObject(oid));
        return Response.ok(view(item)).build();
    }

    @Service
    public void updateItem(CoffeeItem item, final String name, final BigDecimal unitPrice, int numUnits, final String imageUrl) {
        item.update(name, unitPrice, numUnits, imageUrl);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response addItem(@FormParam("model") String jsonData) {
        CoffeeItem newItem = create(jsonData, CoffeeItem.class);
        return Response.ok(view(newItem)).build();
    }

    @DELETE
    @Service
    @Path("/{oid}")
    public Response deleteItem(@PathParam("oid") String oid) {
        CoffeeItem item = readDomainObject(oid);
        item.delete();
        return Response.ok().build();
    }
}
