package runnermod.cards.Unused;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runnermod.cards.BaseCard;
import runnermod.character.RunnerCharacter;
import runnermod.stances.ChangeRunnerStanceAction;
import runnermod.util.CardStats;

public class Ward extends BaseCard {
    public static final String ID = makeID(Ward.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            1

    );

    private static final int BLOCK = 5;
    private static final int BLOCK_UPG = 3;
    private static final int MAGIC = 5;
    private static final int MAGIC_UPG = 0;
    public Ward()
    {
        super(ID,info);
        setMagic(MAGIC,MAGIC_UPG);
        this.setBlock(BLOCK,BLOCK_UPG);
    }



    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ChangeRunnerStanceAction("Wall",magicNumber));
        addToBot(new GainBlockAction(p,p,this.block));
    }
}
