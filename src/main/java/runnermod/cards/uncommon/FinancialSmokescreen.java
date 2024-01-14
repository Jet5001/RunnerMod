package runnermod.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.watcher.SkipEnemiesTurnAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.SmokeBombEffect;
import runnermod.cards.BaseCard;
import runnermod.character.RunnerCharacter;
import runnermod.util.CardStats;

public class FinancialSmokescreen extends BaseCard {
    public static final String ID = makeID(FinancialSmokescreen.class.getSimpleName());
    private static final CardStats info = new CardStats(
            RunnerCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF_AND_ENEMY,
            1
    );

    //Card Stats
    private static final int MAGIC = 75;
    private static final int UPG_MAGIC = -25;

    public FinancialSmokescreen()
    {
        super(ID,info);
        setMagic(MAGIC,UPG_MAGIC);
    }


    //when the card is played
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (AbstractDungeon.player.gold < magicNumber)
        {
            return;
        }
        p.gold -= magicNumber;
        //Escape from combat
        (AbstractDungeon.getCurrRoom()).smoked = true;
        addToBot(new VFXAction(new SmokeBombEffect((p.hb.cX), p.hb.cY)));
        p.hideHealthBar();
        p.isEscaping = true;
        p.flipHorizontal = !AbstractDungeon.player.flipHorizontal;
        AbstractDungeon.overlayMenu.endTurnButton.disable();
        p.escapeTimer = 2.5F;
    }
}
