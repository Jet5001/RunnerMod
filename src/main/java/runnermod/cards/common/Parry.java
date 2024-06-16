package runnermod.cards.common;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runnermod.cards.BaseCard;
import runnermod.character.RunnerCharacter;
import runnermod.powers.Hacked;
import runnermod.util.CardStats;

public class Parry extends BaseCard {
    public static final String ID = makeID(Parry.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.NONE,
            1
    );

    private static int BLOCK = 6;
    private static int BLOCK_UPG = 3;
    public Parry()
    {
        super(ID,info);
        setBlock(BLOCK,BLOCK_UPG);
        tags.add(RunnerCharacter.Enums.NEON);
    }


    //called when the card is played and performs the actions for the card
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
            addToBot(new GainBlockAction(p,block));
    }
}
