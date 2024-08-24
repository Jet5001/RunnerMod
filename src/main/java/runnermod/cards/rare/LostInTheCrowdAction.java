package runnermod.cards.rare;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import runnermod.cards.tempcards.Decoy;

public class LostInTheCrowdAction extends AbstractGameAction {

    boolean upgraded;
    LostInTheCrowdAction(boolean upgraded)
    {
        this.upgraded = upgraded;
        this.actionType = ActionType.CARD_MANIPULATION;
    }


    //the effect that executes on the stack when able to
    @Override
    public void update() {
        int cardsToMake =  - AbstractDungeon.player.hand.size();
        if (upgraded)
        {
            AbstractCard newCard = new Decoy();
            newCard.upgrade();
            addToTop(new MakeTempCardInHandAction(newCard, cardsToMake));
        }
        else
        {
            addToTop(new MakeTempCardInHandAction(new Decoy(), cardsToMake));
        }
        this.isDone = true;
    }
}
