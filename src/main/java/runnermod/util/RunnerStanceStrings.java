package runnermod.util;

import runnermod.stances.RunnerStance;

public class RunnerStanceStrings {
    public String NAME;
    public String DESCRIPTION;

    public RunnerStanceStrings()
    {

    }

    public static RunnerStanceStrings getMockRunnerStanceString()
    {
        RunnerStanceStrings strings = new RunnerStanceStrings();
        strings.NAME = "MISSING NAME";
        strings.DESCRIPTION = "MISSING DESCTRIPTION";
        return strings;
    }


}
