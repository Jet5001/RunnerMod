package runnermod.cards.uncommon;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.AbstractStance;
import runnermod.cards.BaseCard;
import runnermod.cards.tempcards.Decoy;
import runnermod.character.RunnerCharacter;
import runnermod.stances.AKIRAStance;
import runnermod.stances.ChangeRunnerStanceAction;
import runnermod.stances.RunnerStance;
import runnermod.util.CardStats;

import java.util.Collections;
import java.util.Dictionary;
import java.util.Hashtable;

public class DoubleTime extends BaseCard {
    public static final String ID = makeID(DoubleTime.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            1
    );

//    //Card Stats
    private static final int MAGIC = 2;
    private static final int UPG_MAGIC =1;
    private static final int DUR = 3;

    public DoubleTime()
    {
        super(ID,info);
        this.setMagic(MAGIC, UPG_MAGIC);
        this.misc = DUR;
        this.tags.add(RunnerCharacter.Enums.NEON);
        this.cardsToPreview = new Decoy();
    }


    //called when the card is played
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToTop(new ChangeRunnerStanceAction("Overclock",misc));
        addToBot(new MakeTempCardInHandAction(new Decoy(),magicNumber));
    }

    @Override
    public void triggerOnGlowCheck() {
        super.triggerOnGlowCheck();
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        rawDescription = "";
        this.rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).DESCRIPTION;
        String newStance = RunnerStance.determineNewStance("Overclock");
        this.rawDescription += RunnerStance.getStanceChangeDescription(newStance);
        if(RunnerStance.getStanceChangeDescription(newStance) != "")
        {
            this.glowColor = Color.RED;
        }
        initializeDescription();

    }

}
