package pt.ist.bennu.coffee.manager.adapter;

import pt.ist.bennu.coffee.manager.domain.CoffeeBatch;
import pt.ist.bennu.coffee.manager.domain.CoffeeOrder;
import pt.ist.bennu.json.JsonBuilder;
import pt.ist.bennu.json.JsonCreator;
import pt.ist.bennu.json.JsonViewer;
import pt.ist.fenixframework.pstm.AbstractDomainObject;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class CoffeeBatchAdapter implements JsonViewer<CoffeeBatch>, JsonCreator<CoffeeBatch> {

    @Override
    public JsonElement view(CoffeeBatch coffeeBatch, JsonBuilder ctx) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", coffeeBatch.getExternalId());
        jsonObject.addProperty("shippingCharges", coffeeBatch.getShippingCharges());
        jsonObject.addProperty("numCapsules", coffeeBatch.getTotalCount());
        jsonObject.add("creationTimestamp", ctx.view(coffeeBatch.getCreationTimestamp()));
        jsonObject.add("sentTimestamp", ctx.view(coffeeBatch.getSentTimestamp()));
        jsonObject.add("receivedTimestamp", ctx.view(coffeeBatch.getReceivedTimestamp()));
        jsonObject.add("orders", ctx.view(coffeeBatch.getCoffeeOrder()));
        return jsonObject;
    }

    @Override
    public CoffeeBatch create(JsonElement jsonElement, JsonBuilder jsonRegistry) {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        CoffeeBatch batch = new CoffeeBatch();
        final JsonArray orders = jsonObject.get("orders").getAsJsonArray();
        for (JsonElement order : orders) {
            batch.addCoffeeOrder((CoffeeOrder) AbstractDomainObject.fromExternalId(order.getAsString()));
        }
        return batch;
    }

}
