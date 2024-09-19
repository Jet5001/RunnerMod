package runnermod.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import runnermod.character.RunnerCharacter;

import static runnermod.RunnerMod.makeID;

public class MirrorShard extends BaseRelic{
    private static final String NAME = "MirrorShard";
    public static final String ID = makeID(NAME);
    public static final RelicTier RARITY = RelicTier.RARE;
    private static final LandingSound SOUND = LandingSound.CLINK;
    boolean usedThisTurn =false;
    public MirrorShard()
    {
        super(ID,NAME, RunnerCharacter.Enums.CARD_COLOR,RARITY,SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }


    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if (c.cost == 0)
        {
            flash();
            addToBot(new GainBlockAction(AbstractDungeon.player,1));
        }
        super.onPlayCard(c, m);
    }
}
