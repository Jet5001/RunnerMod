package runnermod.potions;

import basemod.BaseMod;
import basemod.interfaces.OnStartBattleSubscriber;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import runnermod.cards.tempcards.Bolt;
import runnermod.cards.tempcards.Decoy;
import runnermod.character.RunnerCharacter;

public class BottledAmmo extends BasePotion {
    public static final String ID = makeID(BottledAmmo.class.getSimpleName());

    private static final Color LIQUID_COLOR = CardHelper.getColor(255, 190, 20);
    private static final Color HYBRID_COLOR = CardHelper.getColor(255, 190, 20);
    private static final Color SPOTS_COLOR = null;

    public BottledAmmo()
    {
        super(ID,2,PotionRarity.UNCOMMON,PotionSize.SPHERE,LIQUID_COLOR,HYBRID_COLOR,SPOTS_COLOR);
        playerClass = RunnerCharacter.Enums.RUNNER;
    }

    @Override
    public String getDescription() {
        return potionStrings.DESCRIPTIONS[0] + this.potency + potionStrings.DESCRIPTIONS[1];
    }


    @Override
    public void use(AbstractCreature abstractCreature) {
        addToBot(new MakeTempCardInHandAction(new Bolt(), this.potency));
    }


}
