package runnermod.potions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import runnermod.character.RunnerCharacter;
import runnermod.powers.VitalityPower;

public class VigorPotion extends BasePotion {
    public static final String ID = makeID(VigorPotion.class.getSimpleName());

    private static final Color LIQUID_COLOR = CardHelper.getColor(255, 166, 77);
    private static final Color HYBRID_COLOR = CardHelper.getColor(255, 153, 187);
    private static final Color SPOTS_COLOR = null;

    public VigorPotion()
    {
        super(ID,5,PotionRarity.UNCOMMON,PotionSize.T,LIQUID_COLOR,HYBRID_COLOR,SPOTS_COLOR);
        playerClass = RunnerCharacter.Enums.RUNNER;
    }

    @Override
    public String getDescription() {
        return potionStrings.DESCRIPTIONS[0] + this.potency + potionStrings.DESCRIPTIONS[1];
    }


    @Override
    public void use(AbstractCreature abstractCreature) {
        addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player, new VigorPower(AbstractDungeon.player, potency)));
    }


}
