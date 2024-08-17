package runnermod.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Random;

public class UpgradeRandomCardsInHandAction extends AbstractGameAction {

    Random rng;
    int amount;
    AbstractCard source;
    UpgradeRandomCardsInHandAction(int amount, AbstractCard source)
    {
        super();
        rng = new Random();
        this.amount = amount;
        this.source = source;
    }


    @Override
    public void update() {
        int cardsSelected = 0;
        int handSize = AbstractDungeon.player.hand.group.size();
        if (handSize<amount)
        {
            amount = handSize;
        }
        while (cardsSelected < amount)
        {
            int cardIndex = rng.nextInt(handSize);
            AbstractCard c = AbstractDungeon.player.hand.group.get(cardIndex);
            if (c != source && c.canUpgrade())
            {
                c.upgrade();
                cardsSelected++;
            }

        }

        this.isDone = true;
    }
}
