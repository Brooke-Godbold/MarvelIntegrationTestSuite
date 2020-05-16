package utils;

import java.util.ArrayList;
import java.util.List;

public class AssertUtils {

    private static List<String> expectedFields = new ArrayList<String>() {{
        add("id");
        add("name");
        add("description");
        add("modified");
        add("resourceURI");
        add("thumbnail");
        add("comics");
        add("stories");
        add("events");
        add("urls");
    }};

    public static List<String> getExpectedCharacterFields() {
        return expectedFields;
    }
}
