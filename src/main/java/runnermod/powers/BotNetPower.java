package runnermod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import runnermod.cards.RandomDebuffAction;

import static runnermod.RunnerMod.makeID;

public class BotNetPower extends BasePower implements CloneablePowerInterface {


    public static final String POWER_ID = makeID("BotNetPower");
    public static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURNBASED = false;
    public BotNetPower(AbstractCreature owner, int amount)
    {
        super(POWER_ID,TYPE,TURNBASED, owner, amount);
    }


    @Override
    public void atStartOfTurn() {
        for (int i = 0; i < this.amount; i++) {
            AbstractMonster m = AbstractDungeon.getRandomMonster();
            addToBot(new RandomDebuffAction(this.owner, m));
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new BotNetPower(owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
