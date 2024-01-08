package runnermod.cards.rare;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EnableEndTurnButtonAction;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
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
            0

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
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        if (AbstractDungeon.player.gold < magicNumber)
        {
            return;
        }
        abstractPlayer.gold -= magicNumber;
        //Starts a new Turn
        AbstractDungeon.player.cardsPlayedThisTurn = 0;
        GameActionManager GAM = AbstractDungeon.actionManager;
        GAM.orbsChanneledThisTurn.clear();
        AbstractDungeon.player.applyStartOfTurnRelics();
        AbstractDungeon.player.applyStartOfTurnPreDrawCards();
        AbstractDungeon.player.applyStartOfTurnCards();
        AbstractDungeon.player.applyStartOfTurnPowers();
        AbstractDungeon.player.applyStartOfTurnOrbs();
        GAM.turn++;
        (AbstractDungeon.getCurrRoom()).skipMonsterTurn = false;
        GAM.turnHasEnded = false;
        GAM.totalDiscardedThisTurn = 0;
        GAM.cardsPlayedThisTurn.clear();
        GAM.damageReceivedThisTurn = 0;
        if (!AbstractDungeon.player.hasPower("Barricade") && !AbstractDungeon.player.hasPower("Blur"))
            if (!AbstractDungeon.player.hasRelic("Calipers")) {
                AbstractDungeon.player.loseBlock();
            } else {
                AbstractDungeon.player.loseBlock(15);
            }
        if (!(AbstractDungeon.getCurrRoom()).isBattleOver) {
            GAM.addToBottom((AbstractGameAction)new DrawCardAction(null, AbstractDungeon.player.gameHandSize, true));
            AbstractDungeon.player.applyStartOfTurnPostDrawRelics();
            AbstractDungeon.player.applyStartOfTurnPostDrawPowers();
        }
    }
}

