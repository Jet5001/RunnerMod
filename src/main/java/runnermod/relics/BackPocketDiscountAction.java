package runnermod.relics;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BackPocketDiscountAction extends AbstractGameAction {

    public BackPocketDiscountAction()
    {
        this.actionType = ActionType.CARD_MANIPULATION;
    }


    //the effect that executes on the stack when able to
    @Override
    public void update() {
        AbstractCard c = AbstractDungeon.player.hand.getTopCard();
        if (c.cost >= 1)
        {
            c.costForTurn = c.cost - 1;
        }
        else
        {
            c.costForTurn = 0;
        }
        //mark as completed so it doesn't repeat every frame and can be removed from the buffer
        this.isDone = true;
    }
}
