package runnermod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.evacipated.cardcrawl.mod.stslib.actions.common.AllEnemyApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static runnermod.RunnerMod.makeID;

public class LaughingManPower extends BasePower implements CloneablePowerInterface {


    public static final String POWER_ID = makeID("LaughingManPower");
    public static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURNBASED = false;

    public LaughingManPower(AbstractCreature owner, int scrapAmount)
    {
        super(POWER_ID,TYPE,TURNBASED, owner, scrapAmount);

    }

    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
    }


    @Override
    public AbstractPower makeCopy() {
        return new LaughingManPower(owner, amount);
    }

//    @Override
//    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
//        super.onApplyPower(power, target, source);
//        if (power.type == PowerType.DEBUFF && source == AbstractDungeon.player)
//        {
//            if (power.getClass() == Hacked.class)
//            {
//                if (!((Hacked) power).fromLaughingMan)
//                {
//                    addToBot(new ApplyPowerAction(target,source, new Hacked(target,amount,true)));
//                }
//            }
//            else
//            {
//                addToBot(new ApplyPowerAction(target,source, new Hacked(target,amount,true)));
//            }
//        }
//
//    }


    public void atStartOfTurnPostDraw() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            flash();
            for (AbstractMonster m : (AbstractDungeon.getMonsters()).monsters) {
                if (!m.isDead && !m.isDying)
                    addToBot(new ApplyPowerAction((AbstractCreature)m, this.owner, new Hacked(m, this.amount,true), this.amount));
            }
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

}
