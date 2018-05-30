package com.pluralsight.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;

public class RideDeserializer extends JsonDeserializer<Ride> {

  @Override
  public Class<?> handledType() {
    return Ride.class;
  }

  @Override
  public Ride deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException {
    JsonNode node = jsonParser.getCodec().readTree(jsonParser);
    int id = (Integer) node.get("id").numberValue();
    String name = node.get("name").asText();
    int duration = (Integer) node.get("duration").numberValue();

    return Ride.builder().withName(name).withDuration(duration).withId(id).build();
  }
}
