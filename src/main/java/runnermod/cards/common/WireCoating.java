package runnermod.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import runnermod.RunnerMod;
import runnermod.cards.BaseCard;
import runnermod.character.RunnerCharacter;
import runnermod.util.CardStats;

public class WireCoating extends BaseCard {
    public static final String ID = makeID(WireCoating.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.NONE,
            2
    );

//    //Card Stats
    private static final int MAGIC = 3;
    private static final int UPG_MAGIC =1;

    private static final int BLOCK = 12;
    private static final int BLOCK_UPG = 3;
    public WireCoating()
    {
        super(ID,info);
        this.setBlock(BLOCK,BLOCK_UPG);
        this.setMagic(MAGIC,UPG_MAGIC);

    }



    //called when the card is played
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p,this.block));
        addToBot(new ApplyPowerAction(p,p,new VigorPower(p,magicNumber)));
    }
}
