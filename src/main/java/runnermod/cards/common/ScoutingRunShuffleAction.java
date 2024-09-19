package runnermod.cards.common;

import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.PutOnBottomOfDeckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ScoutingRunShuffleAction extends AbstractGameAction {

    private int amount;
    ScoutingRunShuffleAction(int amount)
    {
        this.actionType = ActionType.DRAW;
        this.amount = amount;
    }


    //the effect that executes on the stack when able to
    @Override
    public void update() {
        if (AbstractDungeon.player.hand.size() <= 0)
        {
            this.isDone = true;
            return;
        }
        addToTop(new PutOnBottomOfDeckAction(AbstractDungeon.player,AbstractDungeon.player,Math.min(AbstractDungeon.player.hand.size(),amount),false));
        //mark as completed so it doesn't repeat every frame and can be removed from the buffer
        this.isDone = true;
    }
}
