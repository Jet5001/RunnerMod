package runnermod.stances;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.stance.DivinityStanceChangeParticle;
import com.megacrit.cardcrawl.vfx.stance.StanceChangeParticleGenerator;
import com.megacrit.cardcrawl.vfx.stance.WrathStanceChangeParticle;

public class RunnerStanceChangeParticleGenerator extends StanceChangeParticleGenerator {
    private String stanceId;
    private float x;
    private float y;

    public RunnerStanceChangeParticleGenerator(float x, float y, String stanceId) {
        super(x, y, stanceId);
        this.stanceId = stanceId;
        this.x = x;
        this.y =y;
    }

    public void update() {
        if (!this.stanceId.equals("Calm")) {
            int i;
            if (this.stanceId.equals("Divinity")) {
                for(i = 0; i < 20; ++i) {
                    AbstractDungeon.effectsQueue.add(new DivinityStanceChangeParticle(Color.PINK, this.x, this.y));
                }
            } else if (this.stanceId.equals("Wrath")) {
                for(i = 0; i < 10; ++i) {
                    AbstractDungeon.effectsQueue.add(new WrathStanceChangeParticle(this.x));
                }
            } else if (this.stanceId.equals("Neutral")) {
                for(i = 0; i < 20; ++i) {
                    AbstractDungeon.effectsQueue.add(new DivinityStanceChangeParticle(Color.WHITE, this.x, this.y));
                }
            } else if (this.stanceId.equals("Blades")) {
                for(i = 0; i < 20; ++i) {
                    AbstractDungeon.effectsQueue.add(new DivinityStanceChangeParticle(Color.BLUE, this.x, this.y));
                }
            } else {
                for(i = 0; i < 20; ++i) {
                    AbstractDungeon.effectsQueue.add(new DivinityStanceChangeParticle(Color.WHITE, this.x, this.y));
                }
            }
        }

        this.isDone = true;
    }
}
