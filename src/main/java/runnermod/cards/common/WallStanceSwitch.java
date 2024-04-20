package runnermod.cards.common;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ThornsPower;
import runnermod.cards.BaseCard;
import runnermod.character.RunnerCharacter;
import runnermod.stances.ChangeRunnerStanceAction;
import runnermod.util.CardStats;

public class WallStanceSwitch extends BaseCard {
    public static final String ID = makeID(WallStanceSwitch.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.NONE,
            1
    );

//    //Card Stats
    private static final int MAGIC = 5;
    private static final int UPG_MAGIC =0;

    private static final int BLOCK = 5;
    private static final int BLOCK_UPG = 3;
    public WallStanceSwitch()
    {
        super(ID,info);
        //using magic number for the gold because why not. Might come in handy later
        this.setMagic(MAGIC, UPG_MAGIC);
        this.tags.add(RunnerCharacter.Enums.NEON);
        this.setBlock(BLOCK,BLOCK_UPG);
    }

    //called when the card is upgraded
    @Override
    public void upgrade()
    {
        if (!this.upgraded)
        {
            upgradeName();
            super.upgrade();
        }
    }

    //called when the card is played
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //add gain gold action to the stack
        addToTop(new ChangeRunnerStanceAction("Wall",magicNumber));
        addToBot(new GainBlockAction(p,block));
    }
}
