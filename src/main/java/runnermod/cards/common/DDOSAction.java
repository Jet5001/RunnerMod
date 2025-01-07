package runnermod.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

public class DDOSAction extends AbstractGameAction {

    private DamageInfo info;
    private int VulnerableAmount;
    DDOSAction(AbstractCreature source, DamageInfo info, int VulnerableAmount)
    {
        this.info = info;
        setValues(target,info);
        this.actionType = ActionType.DAMAGE;
        this.VulnerableAmount = VulnerableAmount;
    }


    //the effect that executes on the stack when able to
    @Override
    public void update() {
        boolean healthLost = false;
        for (AbstractMonster m:AbstractDungeon.getMonsters().monsters ) {
            int prevHealth = m.currentHealth;
            m.damage(info);
            if (m.currentHealth < prevHealth)
            {
                healthLost = true;
                addToTop(new ApplyPowerAction(m,AbstractDungeon.player, new VulnerablePower(m,VulnerableAmount,false)));
            }
            }
        if (healthLost)
        {
             //do nothing no but might add later/replace vulnerable on each enemy
        }
        //mark as completed so it doesn't repeat every frame and can be removed from the buffer
        this.isDone = true;
    }
}
