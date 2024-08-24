package runnermod.cards.common;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ThornsPower;
import com.megacrit.cardcrawl.stances.AbstractStance;
import runnermod.cards.BaseCard;
import runnermod.character.RunnerCharacter;
import runnermod.stances.AKIRAStance;
import runnermod.stances.ChangeRunnerStanceAction;
import runnermod.stances.RunnerStance;
import runnermod.util.CardStats;

import java.util.Collections;
import java.util.Dictionary;
import java.util.Hashtable;

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
    private static final int MAGIC = 3;
    private static final int UPG_MAGIC =0;

    private static final int BLOCK = 5;
    private static final int BLOCK_UPG = 3;
    public WallStanceSwitch()
    {
        super(ID,info);
        this.setMagic(MAGIC, UPG_MAGIC);
        this.tags.add(RunnerCharacter.Enums.NEON);
        this.setBlock(BLOCK,BLOCK_UPG);
    }


    //called when the card is played
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //add gain gold action to the stack
        addToTop(new ChangeRunnerStanceAction("Wall",magicNumber));
        addToBot(new GainBlockAction(p,this.block));
    }

    @Override
    public void triggerOnGlowCheck() {
        super.triggerOnGlowCheck();
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        rawDescription = "";
        this.rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).DESCRIPTION;
        String newStance = RunnerStance.determineNewStance("Wall");
        this.rawDescription += RunnerStance.getStanceChangeDescription(newStance);
        if(RunnerStance.getStanceChangeDescription(newStance) != "")
        {
            this.glowColor = Color.RED;
        }
        initializeDescription();
    }

}
