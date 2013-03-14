package pt.ist.bennu.coffee.manager.adapter;

import java.lang.reflect.Type;

import org.joda.time.DateTime;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class DateTimeSerializer implements JsonSerializer<DateTime> {

    @Override
    public JsonElement serialize(DateTime timestamp, Type type, JsonSerializationContext ctx) {
        JsonPrimitive jsonPrimite = new JsonPrimitive(timestamp.toString());
        return jsonPrimite;
    }
}
