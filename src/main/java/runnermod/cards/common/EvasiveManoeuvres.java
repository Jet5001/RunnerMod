package runnermod.cards.common;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runnermod.cards.BaseCard;
import runnermod.character.RunnerCharacter;
import runnermod.powers.EvasiveManoeuvresPower;
import runnermod.util.CardStats;

public class EvasiveManoeuvres extends BaseCard {
    public static final String ID = makeID(EvasiveManoeuvres.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.NONE,
            1
    );

    private static final int BLOCK = 4;
    private static final int BLOCK_UPG = 3;
    private static final int MAG = 1;
    private static final int MAG_UPG = 0;
    public EvasiveManoeuvres()
    {
        super(ID,info);
        setMagic(MAG,MAG_UPG);
        setBlock(BLOCK,BLOCK_UPG);
    }


    //called when the card is played and performs the actions for the card
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, this.block));
        addToBot(new ApplyPowerAction(p,p, new EvasiveManoeuvresPower(p,magicNumber)));
    }
}
