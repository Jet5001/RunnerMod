package runnermod.util;

import com.badlogic.gdx.Gdx;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class LocalizedRunnerStanceStrings {
    private static final String LOCALIZATIONPATH = "localization";
    private static Map<String,RunnerStanceStrings> runnerStaceStrings = new HashMap<String,RunnerStanceStrings>();
    private static Gson gson = new Gson();
    private static Type runnerStanceStringType = (new TypeToken<Map<String,RunnerStanceStrings>>(){}).getType();

    public static void loadRunnerStanceStrings(String jsonString)
    {
        loadJsonStrings(jsonString);
    }

    public static void loadRunnerStanceStringsFile(String filepath) {
        loadJsonStrings(Gdx.files.internal(filepath).readString(String.valueOf(StandardCharsets.UTF_8)));
    }

    private static void loadJsonStrings(String jsonString){
        Map map = new HashMap((Map)gson.fromJson(jsonString, runnerStanceStringType));
        runnerStaceStrings.putAll(map);
    }

    public static RunnerStanceStrings getRunnerStanceStrings(String cutsceneName) {
        if (runnerStaceStrings.containsKey(cutsceneName)) {
            return  runnerStaceStrings.get(cutsceneName);
        } else {
            return RunnerStanceStrings.getMockRunnerStanceString();
        }
    }
}
