package runnermod.cards.common;

import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runnermod.cards.BaseCard;
import runnermod.character.RunnerCharacter;
import runnermod.stances.BruteStance;
import runnermod.stances.ChangeRunnerStanceAction;
import runnermod.util.CardStats;

public class BruteStanceSwitch extends BaseCard {
    public static final String ID = makeID(BruteStanceSwitch.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.NONE,
            1
    );

//    //Card Stats
    private static final int MAGIC = 10;
    private static final int UPG_MAGIC =0;

    public BruteStanceSwitch()
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
        addToBot(new ChangeRunnerStanceAction("Brute",5));
    }
}
