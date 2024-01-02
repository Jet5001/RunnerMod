package runnermod.cards.common;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import runnermod.cards.BaseCard;
import runnermod.character.RunnerCharacter;
import runnermod.util.CardStats;

public class Trojan extends BaseCard {
    public static final String ID = makeID(Trojan.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            1
    );

//    //Card Stats
    private static final int MAGIC = 3;
    private static final int UPG_MAGIC =2;

    public Trojan()
    {
        super(ID,info);
        //using magic number for the gold because why not. Might come in handy later
        this.setMagic(MAGIC, UPG_MAGIC);
    }

    //called when the card is played
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //add gain gold action to the stack
        addToBot(new ApplyPowerAction(m,p,new VulnerablePower(m,magicNumber,false)));
    }
}
