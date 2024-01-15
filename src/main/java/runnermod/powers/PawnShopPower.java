package runnermod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import runnermod.cards.tempcards.*;

import java.util.Random;

import static runnermod.RunnerMod.makeID;

public class PawnShopPower extends BasePower implements CloneablePowerInterface {


    public static final String POWER_ID = makeID("PawnShopPower");
    public static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURNBASED = false;
    private Random rng;
    public PawnShopPower(AbstractCreature owner, int amount)
    {
        super(POWER_ID,TYPE,TURNBASED, owner, amount);
        rng = new Random();
    }


    @Override
    public void atStartOfTurnPostDraw() {

        super.atStartOfTurnPostDraw();
        int equip = -1;
        for (int i = 0; i < amount; i++) {
            equip = rng.nextInt(5);
            switch (equip)
            {
                case 0:
                    addToBot(new MakeTempCardInHandAction(new BrokenBlades()));
                case 1:
                    addToBot(new MakeTempCardInHandAction(new BrokenShields()));
                case 2:
                    addToBot(new MakeTempCardInHandAction(new BrokenFirewall()));
                case 3:
                    addToBot(new MakeTempCardInHandAction(new BrokenBlaster()));
                case 4:
                    addToBot(new MakeTempCardInHandAction(new BrokenOverclocker()));
            }

        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new PawnShopPower(owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount;
    }
}
