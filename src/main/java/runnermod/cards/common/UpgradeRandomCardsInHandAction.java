package runnermod.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Map;
import java.util.Random;

public class UpgradeRandomCardsInHandAction extends AbstractGameAction {

    Random rng;
    int amount;
    AbstractCard source;
    public UpgradeRandomCardsInHandAction(int amount)
    {
        super();
        rng = new Random();
        this.amount = amount;
    }


    @Override
    public void update() {
        int cardsSelected = 0;
        int handSize = AbstractDungeon.player.hand.group.size();
        int numCanUpgrade = 0;
        for (AbstractCard c:AbstractDungeon.player.hand.group)
        {
            if(c.canUpgrade())
            {
                numCanUpgrade ++;
            }
        }
        amount = Math.min(amount,handSize);
        amount = Math.min(amount,numCanUpgrade);
        while (cardsSelected < amount)
        {
            int cardIndex = rng.nextInt(handSize);
            AbstractCard c = AbstractDungeon.player.hand.group.get(cardIndex);
            if (c.canUpgrade())
            {
                c.upgrade();
                cardsSelected++;
            }

        }

        this.isDone = true;
    }
}
