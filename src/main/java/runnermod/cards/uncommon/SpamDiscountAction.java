package runnermod.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class SpamDiscountAction extends AbstractGameAction {

    private int discount;
    SpamDiscountAction(int discount)
    {
        this.discount = discount;
        this.actionType = ActionType.DRAW;
    }


    //the effect that executes on the stack when able to
    @Override
    public void update() {
        AbstractCard c = AbstractDungeon.player.hand.getTopCard();
        if (c.cost >= discount)
        {
            c.costForTurn = c.cost - discount;
        }
        else
        {
            c.costForTurn = 0;
        }
        //mark as completed so it doesn't repeat every frame and can be removed from the buffer
        this.isDone = true;
    }
}
