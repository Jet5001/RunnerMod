package runnermod.cards.common;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DescriptionLine;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
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

public class BladesStanceSwitch extends BaseCard {
    public static final String ID = makeID(BladesStanceSwitch.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            1
    );

//    //Card Stats
    private static final int MAGIC = 3;
    private static final int UPG_MAGIC =0;

    private static final int DMG = 6;
    private static final int DMG_UPG = 3;
    public BladesStanceSwitch()
    {
        super(ID,info);
        //using magic number for the gold because why not. Might come in handy later
        this.setMagic(MAGIC, UPG_MAGIC);
        setDamage(DMG,DMG_UPG);
        this.tags.add(RunnerCharacter.Enums.NEON);
    }



    //called when the card is played
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //add gain gold action to the stack
        addToTop(new ChangeRunnerStanceAction("Blades",magicNumber));
        addToBot(new DamageAction(m,new DamageInfo(p,damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
    }

    @Override
    public void triggerOnGlowCheck() {
        super.triggerOnGlowCheck();
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        rawDescription = "";
        this.rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).DESCRIPTION;
        String newStance = RunnerStance.determineNewStance("Blades");
        this.rawDescription += RunnerStance.getStanceChangeDescription(newStance);
        if(RunnerStance.getStanceChangeDescription(newStance) != "")
        {
            this.glowColor = Color.RED;
        }
        initializeDescription();

    }


}
