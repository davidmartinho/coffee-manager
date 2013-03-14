package pt.ist.bennu.coffee.manager.adapter;

import java.math.BigDecimal;

import pt.ist.bennu.coffee.manager.domain.CoffeeItem;
import pt.ist.bennu.json.JsonBuilder;
import pt.ist.bennu.json.JsonCreator;
import pt.ist.bennu.json.JsonViewer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class CoffeeItemAdapter implements JsonCreator<CoffeeItem>, JsonViewer<CoffeeItem> {

    @Override
    public JsonElement view(CoffeeItem coffeeItem, JsonBuilder context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", coffeeItem.getExternalId());
        jsonObject.addProperty("name", coffeeItem.getName());
        jsonObject.addProperty("imageUrl", coffeeItem.getImageUrl());
        jsonObject.addProperty("unitPrice", coffeeItem.getUnitPrice());
        jsonObject.addProperty("numUnits", coffeeItem.getNumUnits());
        return jsonObject;
    }

    @Override
    public CoffeeItem create(JsonElement jsonElement, JsonBuilder jsonRegistry) {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        final String name = jsonObject.get("name").getAsString();
        final String photo = jsonObject.get("imageUrl").getAsString();
        final BigDecimal price = jsonObject.get("unitPrice").getAsBigDecimal();
        final int units = jsonObject.get("units").getAsInt();
        CoffeeItem item = new CoffeeItem(name, price, units);
        item.setImageUrl(photo);
        return item;
    }
}
