package com.kanban.model.enums.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.kanban.model.enums.TaskColor;

import java.io.IOException;

public class TaskColorDeserializer extends StdDeserializer<TaskColor> {

    @Override
    public TaskColor deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String name = node.get("name").asText();
        String cssName = node.get("cssName").asText();
        for (TaskColor color : TaskColor.values()) {
            if (color.getName().equals(name) && color.getCssName().equals(cssName)) {
                return color;
            }
        }
        return null;
    }

    public TaskColorDeserializer() {
        super(TaskColor.class);
    }
}
