package runnermod.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import runnermod.cards.BaseCard;
import runnermod.character.RunnerCharacter;
import runnermod.util.CardStats;

public class EMP extends BaseCard {
    public static final String ID = makeID(EMP.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.NONE,
            0
    );


    public EMP()
    {
        super(ID,info);
        this.exhaust = true;
    }

    //called when the card is upgraded to enact implemented change
    @Override
    public void upgrade()
    {
        if (!this.upgraded)
        {
            this.retain = true;
            super.upgrade();
        }
    }

    //called when the card is played and performs the actions for the card
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster mo: AbstractDungeon.getCurrRoom().monsters.monsters) {
            //puts remove all block action on the stack
            addToBot(new RemoveAllBlockAction(mo,p));
        }
    }
}
