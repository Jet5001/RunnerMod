package runnermod.relics;

import basemod.interfaces.PrePlayerUpdateSubscriber;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import runnermod.character.RunnerCharacter;

import static runnermod.RunnerMod.makeID;

public class BackPocket extends BaseRelic implements PrePlayerUpdateSubscriber {
    private static final String NAME = "BackPocket";
    public static final String ID = makeID(NAME);
    public static final RelicTier RARITY = RelicTier.SHOP;
    private static final LandingSound SOUND = LandingSound.CLINK;
    boolean usedThisTurn =false;
    public BackPocket()
    {
        super(ID,NAME, RunnerCharacter.Enums.CARD_COLOR,RARITY,SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atTurnStart() {
        usedThisTurn = false;
        super.atTurnStart();
    }


    @Override
    public void receivePrePlayerUpdate() {
        if(!usedThisTurn && EnergyPanel.getCurrentEnergy() == 0)
        {
            flash();
            usedThisTurn = true;
            addToBot(new DrawCardAction(1));
        }
    }
}
