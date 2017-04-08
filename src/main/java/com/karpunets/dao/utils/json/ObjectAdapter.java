package com.karpunets.dao.utils.json;

import com.google.gson.*;
import com.karpunets.dao.utils.ObjectCache;
import com.karpunets.pojo.CompanyObject;

import java.lang.reflect.Type;

/**
 * @author Karpunets
 * @since 13.03.2017
 */
public class ObjectAdapter<T extends CompanyObject> implements JsonSerializer<T>, JsonDeserializer<T> {

    @Override
    public JsonElement serialize(T companyObject, Type type, JsonSerializationContext context) {
        JsonElement elem = context.serialize(companyObject);
        return elem;
    }

    @Override
    public T deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        T companyObject = context.deserialize(jsonElement, JsonObject.class);
        if (ObjectCache.isExist(companyObject.getId())) {
            return (T) ObjectCache.get(companyObject.getId());
        } else {
            ObjectCache.add(companyObject.getId(), companyObject);
            return companyObject;
        }
    }
}