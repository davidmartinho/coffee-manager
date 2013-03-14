package pt.ist.bennu.coffee.manager.adapter;

import pt.ist.bennu.coffee.manager.domain.CoffeeOrderEntry;
import pt.ist.bennu.json.JsonBuilder;
import pt.ist.bennu.json.JsonViewer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class CoffeeOrderEntryViewer implements JsonViewer<CoffeeOrderEntry> {

    @Override
    public JsonElement view(CoffeeOrderEntry coffeeOrderEntry, JsonBuilder ctx) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", coffeeOrderEntry.getExternalId());
        jsonObject.addProperty("quantity", coffeeOrderEntry.getQuantity());
        jsonObject.add("item", ctx.view(coffeeOrderEntry.getItem()));
        jsonObject.addProperty("total", coffeeOrderEntry.getTotal().setScale(2));
        return jsonObject;
    }
}
