package runnermod.potions;
import basemod.BaseMod;
import basemod.interfaces.OnPlayerTurnStartSubscriber;
import basemod.interfaces.OnStartBattleSubscriber;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import runnermod.potions.BasePotion;
import runnermod.character.RunnerCharacter;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.helpers.CardHelper;

public class LiquidGold extends BasePotion implements OnStartBattleSubscriber {
    public static final String ID = makeID(LiquidGold.class.getSimpleName());

    private static final Color LIQUID_COLOR = CardHelper.getColor(255, 190, 20);
    private static final Color HYBRID_COLOR = CardHelper.getColor(255, 190, 20);
    private static final Color SPOTS_COLOR = null;

    public LiquidGold()
    {
        super(ID,10,PotionRarity.UNCOMMON,PotionSize.SPHERE,LIQUID_COLOR,HYBRID_COLOR,SPOTS_COLOR);
        playerClass = RunnerCharacter.Enums.RUNNER;
        BaseMod.subscribe(this);
    }

    @Override
    public String getDescription() {
        return potionStrings.DESCRIPTIONS[0] + this.potency + potionStrings.DESCRIPTIONS[1];
    }



    @Override
    public void use(AbstractCreature abstractCreature) {
        addToBot(new GainGoldAction(this.potency));
    }


    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        this.potency += 10;
        this.description = getDescription();
    }
}
