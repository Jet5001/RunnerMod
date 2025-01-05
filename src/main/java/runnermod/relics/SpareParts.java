package runnermod.relics;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import runnermod.character.RunnerCharacter;
import runnermod.stances.ChangeRunnerStanceAction;

import java.util.Random;

import static runnermod.RunnerMod.makeID;

public class SpareParts extends BaseRelic{
    private static final String NAME = "SpareParts";
    public static final String ID = makeID(NAME);
    public static final RelicTier RARITY = RelicTier.STARTER;
    private static final LandingSound SOUND = LandingSound.CLINK;
    public SpareParts()
    {
        super(ID,NAME, RunnerCharacter.Enums.CARD_COLOR,RARITY,SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }


    @Override
    public void atBattleStart() {
        switch (AbstractDungeon.miscRng.random(3)) {
            case 0:
                addToBot(new ChangeRunnerStanceAction("Blades", 3));
                break;
            case 1:
                addToBot(new ChangeRunnerStanceAction("Wall",3));
                break;
            case 2:
                addToBot(new ChangeRunnerStanceAction("Artifact", 3));
                break;
            case 3:
                addToBot(new ChangeRunnerStanceAction("Overclock", 3));
                break;
        }
        super.atBattleStart();
    }
}
