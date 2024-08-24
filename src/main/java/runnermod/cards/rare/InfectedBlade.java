package runnermod.cards.rare;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import runnermod.cards.BaseCard;
import runnermod.cards.tempcards.Bankroll;
import runnermod.character.RunnerCharacter;
import runnermod.powers.Hacked;
import runnermod.stances.AKIRAStance;
import runnermod.stances.ChangeRunnerStanceAction;
import runnermod.stances.RunnerStance;
import runnermod.util.CardStats;

import java.util.Collections;
import java.util.Dictionary;
import java.util.Hashtable;

public class InfectedBlade extends BaseCard {
    public static final String ID = makeID(InfectedBlade.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ENEMY,
            -1

    );

    private static final int DMG = 3;
    private static final int DMG_UPG  = 1;
    private static final int MAG = 2;
    private static final int MAG_UPG = 1;

    public InfectedBlade() {
        super(ID, info);
        setDamage(DMG,DMG_UPG);
        setMagic(MAG,MAG_UPG);
        tags.add(RunnerCharacter.Enums.NEON);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < energyOnUse; i++) {
            addToBot(new DamageAction(m,new DamageInfo(p,damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            addToBot(new ApplyPowerAction(m,p,new Hacked(m,this.magicNumber)));
        }
        addToBot(new ChangeRunnerStanceAction("Blades",3));
        if (!this.freeToPlayOnce) {
            p.energy.use(EnergyPanel.totalCount);
        }
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