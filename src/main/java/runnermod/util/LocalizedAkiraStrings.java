package runnermod.util;

import com.badlogic.gdx.Gdx;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class LocalizedAkiraStrings {
    private static final String LOCALIZATIONPATH = "localization";
    private static Map<String,AkiraStrings> akiraStrings = new HashMap<String,AkiraStrings>();
    private static Gson gson = new Gson();
    private static Type AkiraStringsType = (new TypeToken<Map<String,AkiraStrings>>(){}).getType();

    public static void loadAkiraStrings(String jsonString)
    {
        loadJsonStrings(jsonString);
    }

    public static void loadAkiraStringsFile(String filepath) {
        loadJsonStrings(Gdx.files.internal(filepath).readString(String.valueOf(StandardCharsets.UTF_8)));
    }

    private static void loadJsonStrings(String jsonString){
        Map map = new HashMap((Map)gson.fromJson(jsonString, AkiraStringsType));
        akiraStrings.putAll(map);
    }

    public static AkiraStrings getAkiraStrings(String tutorialName) {
        if (akiraStrings.containsKey(tutorialName)) {
            return  akiraStrings.get(tutorialName);
        } else {
            return AkiraStrings.getMockAkiraStrings();
        }
    }
}
