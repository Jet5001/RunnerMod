package runnermod.util;

import com.megacrit.cardcrawl.localization.LocalizedStrings;

public class RunnerTutorialStrings {
    public String[] Lines;
    public RunnerTutorialStrings()
    {

    }

    public static RunnerTutorialStrings getMockRunnerTutorialString()
    {
        RunnerTutorialStrings strings = new RunnerTutorialStrings();
        strings.Lines = LocalizedStrings.createMockStringArray(4);
        return strings;
    }


}
