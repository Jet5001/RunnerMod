package runnermod.util;

import com.megacrit.cardcrawl.localization.LocalizedStrings;

public class AkiraStrings {
    public String[] DIALOG;
    public AkiraStrings()
    {

    }

    public static AkiraStrings getMockAkiraStrings()
    {
        AkiraStrings strings = new AkiraStrings();
        strings.DIALOG = LocalizedStrings.createMockStringArray(4);
        return strings;
    }


}
