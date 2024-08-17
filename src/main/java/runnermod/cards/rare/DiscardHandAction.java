package runnermod.cards.rare;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import runnermod.cards.tempcards.Decoy;

public class DiscardHandAction extends AbstractGameAction {

    boolean upgraded;
    AbstractPlayer target;
    AbstractCreature source;
    DiscardHandAction(AbstractPlayer target, AbstractCreature source)
    {
        this.target = target;
        this.source = source;
        this.actionType = ActionType.DISCARD;
    }


    //the effect that executes on the stack when able to
    @Override
    public void update() {
        addToTop(new DiscardAction(target,source,target.hand.size(),true));
        this.isDone = true;
    }
}
