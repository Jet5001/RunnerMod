package runnermod.relics;

import com.evacipated.cardcrawl.mod.stslib.relics.OnApplyPowerRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.blue.Defend_Blue;
import com.megacrit.cardcrawl.cards.red.Strike_Red;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import runnermod.character.RunnerCharacter;
import runnermod.powers.VitalityPower;

import static runnermod.RunnerMod.makeID;

public class BalanceProtocols extends BaseRelic implements OnApplyPowerRelic {
    private static final String NAME = "BalanceProtocols";
    public static final String ID = makeID(NAME);
    public static final RelicTier RARITY = RelicTier.UNCOMMON;
    private static final LandingSound SOUND = LandingSound.CLINK;
    boolean usedThisTurn =false;
    public BalanceProtocols()
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
    public boolean onApplyPower(AbstractPower abstractPower, AbstractCreature abstractCreature, AbstractCreature abstractCreature1) {
        if (abstractPower instanceof VigorPower)
        {
            usedThisTurn = true;
            addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new VitalityPower(AbstractDungeon.player,abstractPower.amount)));
        }
        return true;
    }



    @Override
    public int onApplyPowerStacks(AbstractPower power, AbstractCreature target, AbstractCreature source, int stackAmount) {
        if(power instanceof VigorPower)
        {
            usedThisTurn = true;
            //addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new VitalityPower(AbstractDungeon.player,stackAmount)));
        }
        return OnApplyPowerRelic.super.onApplyPowerStacks(power, target, source, stackAmount);
    }
}
