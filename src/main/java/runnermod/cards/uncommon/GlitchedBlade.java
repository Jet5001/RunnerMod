package runnermod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runnermod.cards.BaseCard;
import runnermod.cards.tempcards.Virus;
import runnermod.character.RunnerCharacter;
import runnermod.util.CardStats;

public class GlitchedBlade extends BaseCard {
    public static final String ID = makeID(GlitchedBlade.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    //Card Stats
    private static final int DAMAGE = 7;
    private static final int UPG_DAMAGE = 3;

    public GlitchedBlade()
    {
        super(ID,info);
        setDamage(DAMAGE, UPG_DAMAGE);
        cardsToPreview = new Virus();
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        initializeDescription();
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        super.calculateCardDamage(m);
        initializeDescription();
    }

    //when the card is played
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int numViruses = 1;
        for (AbstractCard c: AbstractDungeon.player.exhaustPile.group)
        {
            if(c instanceof Virus)
            {
                numViruses +=1;
            }
        }
        for (AbstractCard c: AbstractDungeon.player.discardPile.group)
        {
            if(c instanceof Virus)
            {
                numViruses +=1;
            }
        }
        for (AbstractCard c: AbstractDungeon.player.drawPile.group)
        {
            if(c instanceof Virus)
            {
                numViruses +=1;
            }
        }
        for (AbstractCard c: AbstractDungeon.player.hand.group)
        {
            if(c instanceof Virus)
            {
                numViruses +=1;
            }
        }
        for (AbstractCard c: AbstractDungeon.player.limbo.group)
        {
            if(c instanceof Virus)
            {
                numViruses +=1;
            }
        }
        addToBot(new DamageAction(m, new DamageInfo(p,damage*numViruses, DamageInfo.DamageType.NORMAL)));
        addToBot(new MakeTempCardInDiscardAction(new Virus(),1));
    }
}
