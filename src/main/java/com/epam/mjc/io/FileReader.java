package com.epam.mjc.io;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;


public class FileReader {

    private static final String AGE = "Age";
    private static final String NAME = "Name";
    private static final String EMAIL = "Email";
    private static final String PHONE = "Phone";

    public Profile getDataFromFile(File file) {
        String profileData = readProfileDataFromFile(file);

        String formattedProfileData = profileData.replaceAll("\\n", ",")
                .replaceAll("\\s", "");

        Map<String, String> parsedProfileData = parseProfileDataFromStringToMap(formattedProfileData);

        return mapDataToProfile(parsedProfileData);
    }

    private String readProfileDataFromFile(File file) {
        try (java.io.FileReader fileReader = new java.io.FileReader(file.getPath())) {
            return readContent(fileReader);
        } catch (IOException e) {
            // can be added logging
        }
        return null;
    }

    private String readContent(Reader reader) throws IOException {
        StringBuilder content = new StringBuilder();
        int nextChar;
        while ((nextChar = reader.read()) != -1) {
            content.append((char) nextChar);
        }

        return String.valueOf(content);
    }

    private Map<String, String> parseProfileDataFromStringToMap(String stringAsMap) {
        Map<String, String> map = Arrays.stream(stringAsMap.split(","))
                .map(entry -> entry.split(":"))
                .collect(Collectors.toMap(entry -> entry[0], entry -> entry[1]));

        return map;
    }

    private Profile mapDataToProfile(Map<String, String> parsedData) {
        Profile profile = new Profile();
        profile.setAge(Integer.parseInt(parsedData.get(AGE)));
        profile.setName(parsedData.get(NAME));
        profile.setEmail(parsedData.get(EMAIL));
        profile.setPhone(Long.parseLong(parsedData.get(PHONE)));

        return profile;
    }
}


