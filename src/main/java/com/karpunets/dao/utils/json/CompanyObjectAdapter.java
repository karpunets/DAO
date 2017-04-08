package com.karpunets.dao.utils.json;

import com.google.gson.*;
import com.karpunets.pojo.CompanyObject;

import java.lang.reflect.Type;

/**
 * @author Karpunets
 * @since 15.02.2017
 */
public class CompanyObjectAdapter implements JsonSerializer<CompanyObject>, JsonDeserializer<CompanyObject> {

    private static final String CLASSNAME = "CLASSNAME";
    private static final String INSTANCE  = "INSTANCE";

    @Override
    public JsonElement serialize(CompanyObject companyObject, Type type, JsonSerializationContext context) {
        JsonObject retValue = new JsonObject();

        String className = companyObject.getClass().getName();
        retValue.addProperty(CLASSNAME, className);

        JsonElement elem = context.serialize(companyObject);
        retValue.add(INSTANCE, elem);
        return retValue;
    }

    @Override
    public CompanyObject deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject =  jsonElement.getAsJsonObject();

        JsonPrimitive prim = (JsonPrimitive) jsonObject.get(CLASSNAME);
        String className = prim.getAsString();

        Class<?> objectClass;
        try {
            objectClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new JsonParseException(e.getMessage());
        }
        return context.deserialize(jsonObject.get(INSTANCE), objectClass);
    }
}
