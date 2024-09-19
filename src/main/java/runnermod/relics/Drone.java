package runnermod.relics;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.blue.Defend_Blue;
import com.megacrit.cardcrawl.cards.red.Strike_Red;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runnermod.cards.tempcards.Decoy;
import runnermod.character.RunnerCharacter;

import static runnermod.RunnerMod.makeID;

public class Drone extends BaseRelic{
    private static final String NAME = "Drone";
    public static final String ID = makeID(NAME);
    public static final RelicTier RARITY = RelicTier.RARE;
    private static final LandingSound SOUND = LandingSound.CLINK;
    public Drone()
    {
        super(ID,NAME, RunnerCharacter.Enums.CARD_COLOR,RARITY,SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atTurnStartPostDraw() {
        super.atTurnStartPostDraw();
        addToBot(new MakeTempCardInHandAction(new Decoy(), 2));
    }
}
