package runnermod.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import runnermod.character.RunnerCharacter;

import static runnermod.RunnerMod.makeID;

public class RoboLouse extends BaseRelic{
    private static final String NAME = "RoboLouse";
    public static final String ID = makeID(NAME);
    public static final RelicTier RARITY = RelicTier.COMMON;
    private static final LandingSound SOUND = LandingSound.CLINK;
    boolean usedThisTurn =false;
    public RoboLouse()
    {
        super(ID,NAME, RunnerCharacter.Enums.CARD_COLOR,RARITY,SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }


    @Override
    public void atBattleStart() {
        for (AbstractMonster m:AbstractDungeon.getMonsters().monsters ) {
            addToTop(new ApplyPowerAction(m, AbstractDungeon.player, new VulnerablePower(m, 3, false)));
        }
        super.atBattleStart();
    }
}
