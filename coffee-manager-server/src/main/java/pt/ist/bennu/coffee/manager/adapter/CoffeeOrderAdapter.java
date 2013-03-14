package pt.ist.bennu.coffee.manager.adapter;

import java.util.Iterator;

import pt.ist.bennu.coffee.manager.domain.CoffeeItem;
import pt.ist.bennu.coffee.manager.domain.CoffeeOrder;
import pt.ist.bennu.json.JsonAdapter;
import pt.ist.bennu.json.JsonBuilder;
import pt.ist.fenixframework.pstm.AbstractDomainObject;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class CoffeeOrderAdapter implements JsonAdapter<CoffeeOrder> {

    @Override
    public CoffeeOrder create(JsonElement jsonElement, JsonBuilder jsonRegistry) {
        final JsonArray entries = jsonElement.getAsJsonObject().get("entries").getAsJsonArray();
        final CoffeeOrder order = new CoffeeOrder();
        for (final JsonElement entry : entries) {
            final JsonObject entryObj = entry.getAsJsonObject();
            final String itemId = entryObj.get("id").getAsString();
            final Integer quantity = entryObj.get("quantity").getAsInt();
            order.addEntry((CoffeeItem) CoffeeItem.fromExternalId(itemId), quantity);
        }
        return order;
    }

    @Override
    public JsonElement view(CoffeeOrder coffeeOrder, JsonBuilder ctx) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", coffeeOrder.getExternalId());
        jsonObject.add("owner", ctx.view(coffeeOrder.getUser()));
        jsonObject.add("entries", ctx.view(coffeeOrder.getEntry()));
        jsonObject.addProperty("total", String.format("%.2f", coffeeOrder.getTotal()));
        jsonObject.addProperty("boxes", coffeeOrder.getNumBoxes());
        jsonObject.addProperty("count", coffeeOrder.getCount());
        jsonObject.addProperty("batched", coffeeOrder.isBatched());
        jsonObject.addProperty("sent", coffeeOrder.isSent());
        return jsonObject;

    }

    @Override
    public CoffeeOrder update(JsonElement jsonElement, CoffeeOrder obj, JsonBuilder jsonRegistry) {
        Iterator<JsonElement> iterator = jsonElement.getAsJsonArray().iterator();
        while(iterator.hasNext()) {
            JsonObject jsonObject = iterator.next().getAsJsonObject();
            String itemId = jsonObject.get("id").getAsString();
            int quantity = jsonObject.get("quantity").getAsInt();
            CoffeeItem item = AbstractDomainObject.fromExternalId(itemId);
            obj.addEntry(item, quantity);
        }
        return obj;
    }

}
