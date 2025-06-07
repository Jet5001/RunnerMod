package runnermod.util;

import com.badlogic.gdx.Gdx;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class LocalizedRunnerTutorialStrings {
    private static final String LOCALIZATIONPATH = "localization";
    private static Map<String,RunnerTutorialStrings> runnerTutorialStrings = new HashMap<String,RunnerTutorialStrings>();
    private static Gson gson = new Gson();
    private static Type runnerTutorialStringsType = (new TypeToken<Map<String,RunnerTutorialStrings>>(){}).getType();

    public static void loadRunnerTutorialStrings(String jsonString)
    {
        loadJsonStrings(jsonString);
    }

    public static void loadRunnerTutorialStringsFile(String filepath) {
        loadJsonStrings(Gdx.files.internal(filepath).readString(String.valueOf(StandardCharsets.UTF_8)));
    }

    private static void loadJsonStrings(String jsonString){
        Map map = new HashMap((Map)gson.fromJson(jsonString, runnerTutorialStringsType));
        runnerTutorialStrings.putAll(map);
    }

    public static RunnerTutorialStrings getRunnerTutorialStrings(String tutorialName) {
        if (runnerTutorialStrings.containsKey(tutorialName)) {
            return  runnerTutorialStrings.get(tutorialName);
        } else {
            return RunnerTutorialStrings.getMockRunnerTutorialString();
        }
    }
}
