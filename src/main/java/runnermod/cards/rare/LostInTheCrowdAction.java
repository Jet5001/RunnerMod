package runnermod.cards.rare;

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
        int cardsToMake = 10 - AbstractDungeon.player.hand.size();
        //mark as completed so it doesn't repeat every frame and can be removed from the buffer
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
