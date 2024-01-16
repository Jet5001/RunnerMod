package runnermod.cards.tempcards;

import com.evacipated.cardcrawl.mod.stslib.patches.BlockModifierPatches;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runnermod.cards.BaseCard;
import runnermod.cards.uncommon.GlitchedBlade;
import runnermod.util.CardStats;

public class Virus extends BaseCard {
    public static final String ID = makeID(Virus.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.STATUS,
            CardRarity.SPECIAL,
            CardTarget.NONE,
            1
    );

    public Virus()
    {
        super(ID,info);
        this.exhaust = true;
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        AbstractCreature p = AbstractDungeon.player;
        addToBot(new DiscardAction(p,p,1,true));
    }

    @Override
    public void upgrade() {
        super.upgrade();
        upgradeBaseCost(0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractCard c:AbstractDungeon.player.discardPile.group ) {
            if (c instanceof GlitchedBlade)
            {
                c.baseDamage +=6;
                c.applyPowers();
            }
        }
        for (AbstractCard c:AbstractDungeon.player.hand.group ) {
            if (c instanceof GlitchedBlade)
            {
                c.baseDamage +=6;
                c.applyPowers();
            }
        }
        for (AbstractCard c:AbstractDungeon.player.drawPile.group ) {
            if (c instanceof GlitchedBlade)
            {
                c.baseDamage +=6;
                c.applyPowers();
            }
        }
    }
}
