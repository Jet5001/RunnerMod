package runnermod.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import runnermod.character.RunnerCharacter;
import runnermod.stances.RunnerStance;

import java.util.Random;

public class RepairAction extends AbstractGameAction {

    int amount;
    public RepairAction(int amount)
    {
        super();
        this.amount = amount;
    }


    @Override
    public void update() {
        if(!(AbstractDungeon.player.stance instanceof RunnerStance))
        {
            this.isDone = true;
            return;
        }
        RunnerStance stance = (RunnerStance) AbstractDungeon.player.stance;
        stance.reduceDurability(-amount);
        this.isDone = true;

    }
}
