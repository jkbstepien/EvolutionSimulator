package org.example.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class GetFromFile {
    private final ObjectMapper mapper = new ObjectMapper();
    private Preferences preferences;

    public Preferences getPreferencesFromFile(String filename) throws IOException {
        preferences = mapper.readValue(new File(filename), Preferences.class);
        return preferences;
    }


}
