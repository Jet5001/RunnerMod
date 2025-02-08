package runnermod.util;

import com.megacrit.cardcrawl.localization.LocalizedStrings;
import runnermod.stances.RunnerStance;

public class RunnerStanceStrings {
    public String NAME;
    public String DESCRIPTION;
    public String[] EXTRA_DESCRIPTIONS;
    public RunnerStanceStrings()
    {

    }

    public static RunnerStanceStrings getMockRunnerStanceString()
    {
        RunnerStanceStrings strings = new RunnerStanceStrings();
        strings.NAME = "MISSING NAME";
        strings.DESCRIPTION = "MISSING DESCTRIPTION";
        strings.EXTRA_DESCRIPTIONS = LocalizedStrings.createMockStringArray(12);
        return strings;
    }


}
