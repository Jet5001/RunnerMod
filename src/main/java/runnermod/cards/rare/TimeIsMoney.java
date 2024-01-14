package runnermod.cards.rare;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EnableEndTurnButtonAction;
import com.megacrit.cardcrawl.actions.common.EndTurnAction;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.actions.watcher.SkipEnemiesTurnAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runnermod.cards.BaseCard;
import runnermod.character.RunnerCharacter;
import runnermod.util.CardStats;

public class TimeIsMoney extends BaseCard {
    public static final String ID = makeID(TimeIsMoney.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.NONE,
            1

    );

    private static final int MAG = 50;
    private static final int MAG_UPG = -20;


    public TimeIsMoney()
    {
        super(ID,info);
        this.exhaust = true;
        setMagic(MAG,MAG_UPG);
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster mo) {
        if (AbstractDungeon.player.gold < magicNumber)
        {
            return;
        }
        p.gold -= magicNumber;
        //Starts a new Turn
        addToBot(new SkipEnemiesTurnAction());
    }
}

