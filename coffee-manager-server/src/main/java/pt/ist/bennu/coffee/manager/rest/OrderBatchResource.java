package pt.ist.bennu.coffee.manager.rest;

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

import pt.ist.bennu.coffee.manager.domain.CoffeeBatch;
import pt.ist.bennu.coffee.manager.domain.CoffeeManager;
import pt.ist.bennu.coffee.manager.domain.CoffeeOrder;
import pt.ist.bennu.service.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Path("/coffee-manager/batches")
public class OrderBatchResource extends CoffeeManagerAbstractResource {

    @GET
    @Path("/{oid}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getOrderBatch(@PathParam("oid") String oid) {
        return view(readDomainObject(oid));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBatches() {
        Set<CoffeeBatch> coffeeBatchSet = CoffeeManager.getInstance().getBatchSet();
        return Response.ok(view(coffeeBatchSet, "batches")).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response newBatch(@FormParam("model") String jsonData) {
        final CoffeeBatch batch = create(jsonData, CoffeeBatch.class);
        return Response.created(getURIFor(batch, "batches")).build();
    }

    @PUT
    @Path("/{oid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response editBatch(@PathParam("oid") String oid, @FormParam("data") String data) {
        final CoffeeBatch batch = readDomainObject(oid);
        final CoffeeBatch coffeeBatch = update(data, batch);
        return Response.ok(view(coffeeBatch)).build();
    }

    @Service
    private CoffeeBatch editBatch(CoffeeBatch coffeeBatch, String data) {
        final JsonParser parser = new JsonParser();
        final JsonObject jsonObject = parser.parse(data).getAsJsonObject();
        final JsonArray orders = jsonObject.get("orders").getAsJsonArray();
        for (final JsonElement order : orders) {
            CoffeeOrder coffeeOrder = readDomainObject(order.getAsString());
            coffeeBatch.addCoffeeOrder(coffeeOrder);
        }
        return coffeeBatch;
    }

    @DELETE
    @Path("/{oid}")
    @Service
    public Response delete(@PathParam("oid") String externalId) {
        CoffeeBatch batch = readDomainObject(externalId);
        batch.delete();
        return Response.ok().build();
    }

    @DELETE
    @Path("/{oid}/{orderOid}")
    @Service
    public Response delete(@PathParam("oid") String externalId, @PathParam("orderOid") String orderOid) {
        CoffeeBatch batch = readDomainObject(externalId);
        CoffeeOrder coffeeOrder = readDomainObject(orderOid);
        batch.removeCoffeeOrder(coffeeOrder);
        return Response.ok().build();
    }

}
