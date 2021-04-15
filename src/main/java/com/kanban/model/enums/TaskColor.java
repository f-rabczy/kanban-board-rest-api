package com.kanban.model.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.kanban.model.enums.deserializer.TaskColorDeserializer;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonDeserialize(using = TaskColorDeserializer.class)
public enum TaskColor {
    White("White","white"),
    Blue("Blue","lightblue"),
    Green("Green","lightgreen"),
    Pink("Pink","lightpink"),
    Yellow("Yellow","lightyellow"),
    Red("Red","lightcoral"),
    Purple("Purple","plum");

    private final String name;
    private final String cssName;

    TaskColor(String name, String cssName) {
        this.name = name;
        this.cssName = cssName;
    }

    @Override
    public String toString() {
        return "Color{" +
                "name='" + name + '\'' +
                '}';
    }


    public String getName() {
        return name;
    }

    public String getCssName() {
        return cssName;
    }
}
