package runnermod.relics;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import runnermod.character.RunnerCharacter;

import static runnermod.RunnerMod.makeID;

public class ICEBreaker extends BaseRelic{
    private static final String NAME = "ICEBreaker";
    public static final String ID = makeID(NAME);
    public static final RelicTier RARITY = RelicTier.BOSS;
    private static final LandingSound SOUND = LandingSound.CLINK;
    boolean blockBrokenThisTurn;
    public ICEBreaker()
    {
        super(ID,NAME, RunnerCharacter.Enums.CARD_COLOR,RARITY,SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic("RunnerMod:ICEPick");
    }

    @Override
    public void atTurnStart() {
        blockBrokenThisTurn = false;
    }

    @Override
    public void onBlockBroken(AbstractCreature m) {
        if(!blockBrokenThisTurn && m != AbstractDungeon.player)
        {
            AbstractDungeon.player.gainEnergy(1);
            blockBrokenThisTurn = true;
        }
        super.onBlockBroken(m);
    }
}
