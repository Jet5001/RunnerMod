package runnermod.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runnermod.cards.BaseCard;
import runnermod.character.RunnerCharacter;
import runnermod.util.CardStats;

public class Stockpile extends BaseCard {
    public static final String ID = makeID(Stockpile.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.NONE,
            1
    );

//    //Card Stats
//    private static final int DAMAGE = 10;
//    private static final int UPG_DAMAGE =5;
//
    private static final int MAGIC = 10;
    private static final int UPG_MAGIC =0;

    public Stockpile()
    {
        super(ID,info);
        this.exhaust = true;
        this.isEthereal = true;
        //using magic number for the gold because why not. Might come in handy later
        this.setMagic(MAGIC);
    }

    //called when the card is upgraded
    @Override
    public void upgrade()
    {
        if (!this.upgraded)
        {
            upgradeName();
            //remove ehtereal on upgrade
            this.isEthereal = false;
        }
    }

    //called when the card is played
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //add gain gold action to the stack
        addToBot(new GainGoldAction(magicNumber));
    }
}
