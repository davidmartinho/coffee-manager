package pt.ist.bennu.coffee.manager.rest;

import java.util.Iterator;

import javax.ws.rs.Consumes;
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

import pt.ist.bennu.bennu.core.rest.mapper.RestException;
import pt.ist.bennu.coffee.manager.domain.CoffeeItem;
import pt.ist.bennu.coffee.manager.domain.CoffeeOrder;
import pt.ist.bennu.coffee.manager.domain.CoffeeOrderEntry;
import pt.ist.bennu.coffee.manager.error.CoffeeManagerError;
import pt.ist.bennu.core.domain.User;
import pt.ist.bennu.service.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Path("/coffee-manager/orders")
public class OrderResource extends CoffeeManagerAbstractResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrders() {
        User author = verifyAndGetRequestAuthor();
        return Response.ok(view(author.getCoffeeOrderSet(), "orders")).build();
    }

    @GET
    @Path("/{oid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrder(@PathParam("oid") String oid) {
        return Response.ok(view(readDomainObject(oid))).build();
    }

    @POST
    @Service
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addOrder(String jsonData) {
        User author = verifyAndGetRequestAuthor();
        CoffeeOrder coffeeOrder = addOrder(author, jsonData);
        return Response.ok(view(coffeeOrder)).build();
    }
    
    @Service
    public CoffeeOrder addOrder(User author, String jsonData) {
        CoffeeOrder coffeeOrder = create(jsonData, CoffeeOrder.class);
        coffeeOrder.setUser(author);
        return coffeeOrder;
    }

    @PUT
    @Path("/{oid}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response putOrder(@PathParam("oid") String oid, String jsonData) {
        User author = verifyAndGetRequestAuthor();
        CoffeeOrder coffeeOrder = update(jsonData, (CoffeeOrder) readDomainObject(oid));
        if (coffeeOrder.getUser().equals(author)) {
            return Response.ok(view(coffeeOrder)).build();
        } else {
            throw new RestException(CoffeeManagerError.CANNOT_MODIFY_OTHERS_ORDERS);
        }
    }

    @DELETE
    @Path("/{oid}")
    @Service
    public Response deleteOrder(@PathParam("oid") String externalId) {
        CoffeeOrder coffeeOrder = readDomainObject(externalId);
        coffeeOrder.delete();
        return Response.ok().build();
    }

    @Service
    private CoffeeOrder editOrder(User author, String externalId, String jsonData) {
        final CoffeeOrder order = readDomainObject(externalId);
        for (CoffeeOrderEntry entry : order.getEntrySet()) {
            entry.delete();
        }
        final JsonObject jsonObject = new JsonParser().parse(jsonData).getAsJsonObject();
        final JsonArray entries = jsonObject.get("entries").getAsJsonArray();
        Iterator<JsonElement> iterator = entries.iterator();
        while (iterator.hasNext()) {
            JsonObject entry = iterator.next().getAsJsonObject();
            String itemExternalId = entry.get("id").getAsString();
            CoffeeItem item = CoffeeItem.fromExternalId(itemExternalId);
            int quantity = entry.get("quantity").getAsInt();
            order.addEntry(item, quantity);
        }
        return order;
    }
}
