package runnermod.stances;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class SuperArtifactPower extends AbstractPower {
    public static final String POWER_ID = "Artifact";

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("Artifact");
    private static final String baseDescription = "Each time you play a card gain 1 strength and 1 dexterity";

    public static final String NAME = powerStrings.NAME;

    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public SuperArtifactPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        //this is the only way to get it to act as artifact (if artifact on each debuff rather than anything in the artifact itself
        //however causes normal artifact to stack and both get removed at end of stance
        this.ID = "Artifact";
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        loadRegion("artifact");
        this.type = AbstractPower.PowerType.BUFF;
    }

    public void onSpecificTrigger() {

    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
        }
    }
}
