package runnermod.cards.common;

import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.DamageHooks;
import com.evacipated.cardcrawl.mod.stslib.patches.powerInterfaces.OnDrawPileShufflePowerPatch;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.BetterOnApplyPowerPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnDrawPileShufflePower;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runnermod.cards.BaseCard;
import runnermod.character.RunnerCharacter;
import runnermod.stances.ChangeRunnerStanceAction;
import runnermod.util.CardStats;

public class Escape extends BaseCard {
    public static final String ID = makeID(Escape.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.NONE,
            5
    );

//    //Card Stats
    private static final int MAGIC = 15;
    private static final int UPG_MAGIC =0;

    private static final int BLOCK = 15;
    private static final int BLOCK_UPG = 5;
    public Escape()
    {
        super(ID,info);
        this.setBlock(BLOCK,BLOCK_UPG);
    }

    @Override
    public void atTurnStart() {
        super.atTurnStart();
        this.resetAttributes();
        this.cost = this.baseCost;
        applyPowers();
    }

    @Override
    public void applyPowers()
    {
        int cardsPlayedThisTurn = (int) AbstractDungeon.actionManager.cardsPlayedThisTurn.stream().count();
        if(cardsPlayedThisTurn >=5)
        {
            costForTurn = 0;
        }
        else
        {
            costForTurn = this.cost - cardsPlayedThisTurn;
        }
    }


//    @Override
//    public void onPlayCard(AbstractCard c, AbstractMonster m) {
//        super.onPlayCard(c, m);
//        if (costForTurn > 0)
//        {
//            this.costForTurn -=1;
//        }
//    }

    //called when the card is played
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p,this.block));
    }
}
