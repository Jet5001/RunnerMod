package runnermod.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Random;

public class UpgradeRandomCardsInHandAction extends AbstractGameAction {

    Random rng;
    int amount;
    UpgradeRandomCardsInHandAction(int amount)
    {
        super();
        rng = new Random();
        this.amount = amount;
    }


    @Override
    public void update() {
        for (int i = 0; i < amount; i++) {
            int handSize = AbstractDungeon.player.hand.group.size();
            int cardIndex = rng.nextInt(handSize)-1;
            AbstractCard c = AbstractDungeon.player.hand.group.get(cardIndex);
            c.upgrade();
        }

        this.isDone = true;
    }
}
