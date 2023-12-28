package runnermod.relics;

import com.evacipated.cardcrawl.mod.stslib.patches.BlockModifierPatches;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import runnermod.character.RunnerCharacter;

import static runnermod.RunnerMod.makeID;

public class ICEPick extends BaseRelic{
    private static final String NAME = "ICEPick";
    public static final String ID = makeID(NAME);
    public static final RelicTier RARITY = RelicTier.STARTER;
    private static final LandingSound SOUND = LandingSound.CLINK;
    private static final int BLOCK_BYPASS = 2;
    public ICEPick()
    {
        super(ID,NAME, RunnerCharacter.Enums.CARD_COLOR,RARITY,SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + BLOCK_BYPASS + DESCRIPTIONS[1];
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        if (targetCard.type == AbstractCard.CardType.ATTACK)
        {
            if (useCardAction.target == null)
            {
                super.onUseCard(targetCard,useCardAction);
                return;
            }
            AbstractCreature target = useCardAction.target;
            if (target.currentBlock >=BLOCK_BYPASS)
            {
                target.currentBlock = target.currentBlock - BLOCK_BYPASS;
            }
            else
            {
                target.currentBlock = 0;
            }
        }
        super.onUseCard(targetCard, useCardAction);
    }
}
