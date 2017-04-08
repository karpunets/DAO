package com.karpunets.dao.utils.json;

import com.google.gson.*;
import com.karpunets.dao.utils.ObjectCache;
import com.karpunets.pojo.CompanyObject;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Karpunets
 * @since 16.02.2017
 */
public class SetAdapter implements JsonSerializer<Set<CompanyObject>>, JsonDeserializer<Set<CompanyObject>> {

    @Override
    public JsonElement serialize(Set<CompanyObject> set, Type type, JsonSerializationContext context) {
        JsonArray jsonArray = new JsonArray();
        for (CompanyObject companyObject : set) {
            jsonArray.add(context.serialize(companyObject, CompanyObject.class));
        }
        return jsonArray;
    }

    @Override
    public Set<CompanyObject> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {

        Set<CompanyObject> set = new HashSet<>();

        JsonArray jsonElements = jsonElement.getAsJsonArray();
        for (JsonElement element : jsonElements) {
            CompanyObject companyObject = context.deserialize(element, CompanyObject.class);
            if (ObjectCache.isExist(companyObject.getId())) {
                set.add(ObjectCache.get(companyObject.getId()));
            } else {
                ObjectCache.add(companyObject.getId(), companyObject);
                set.add(companyObject);
            }
        }

        return set;
    }


}
