package runnermod.stances;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;

public class RunnerStanceAura extends StanceAuraEffect {

    //Stand in for the background haze of the stance
    //extended to customise the colour
    public RunnerStanceAura(String stanceId) {
        super(stanceId);
    }

    public void setColour (Color c)
    {
        this.color = c;
    }

}
