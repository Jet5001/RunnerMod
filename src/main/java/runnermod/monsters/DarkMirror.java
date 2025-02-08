package runnermod.monsters;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.ApplyStasisAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import runnermod.RunnerMod;
import runnermod.powers.DetonatePower;

import java.util.List;
import java.util.stream.Collectors;

import static runnermod.RunnerMod.imagePath;

public class DarkMirror extends AbstractMonster {

    public static final String ID = RunnerMod.makeID("DarkMirror");
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;

    private static int maxHP = 30;
    private static float hb_x  = -10.0F;
    private static float hb_y  = -30.0F;
    private static float offsetX = 0f;
    private static float offsetY = 0f;
    private static int hb_w  = 447;
    private static int hb_h  = 364;
    private static String imgUrl = imagePath("monsters/DarkMirrorSmol.png");

    private static final byte BufferDefence = 1;
    private static final byte StealCard = 0;

    public static final int  MAX_MIRRORS = 5;
    public int MirrorID = 1;
    private Boolean ignited = false;
    private static int GetMirrorID()
    {
        List<DarkMirror> mirrors = (List<DarkMirror>) AbstractDungeon.getMonsters().monsters.stream().map(m -> m instanceof DarkMirror ? (DarkMirror)m:null).filter(m -> m!=null && !m.isDeadOrEscaped()).collect(Collectors.toList());
        if(mirrors.isEmpty())
        {
            return 1;
        }
        List<Integer> mirrorIDs = mirrors.stream().map(m -> m.MirrorID).collect(Collectors.toList());
        for (int i = 1; i < MAX_MIRRORS; i++) {
            if(!mirrorIDs.contains(i))
            {
                return i;
            }
        }
        return MAX_MIRRORS;
    }

    private static float MirrorXOrigin = -250;
    private static float MirrorXOffset = -100;

    private static float getX(int MirrorID)
    {
        return  MirrorXOrigin + MirrorID * MirrorXOffset;
    }

    private static float MirrorY1 = 0;
    private static float MirrorY2 = 300;

    private static float getY(int MirrorID)
    {
        if(MirrorID %2 == 0)
        {
            return MirrorY1;
        }
        else
        {
            return MirrorY2;
        }
    }

    private boolean stasised = false;

    public DarkMirror() {
        super("DarkMirror", ID, maxHP, 0, 0, 200, 200, imgUrl,getX(GetMirrorID()),getY(GetMirrorID()));
        MirrorID = GetMirrorID();
        this.type = EnemyType.NORMAL;
    }

    @Override
    public void applyEndOfTurnTriggers() {
        super.applyEndOfTurnTriggers();
        if(!ignited)
        {
            addToBot(new ApplyPowerAction(this,this,new DetonatePower(this,5)));
            addToBot(new ApplyPowerAction(this,this,new MinionPower(this)));
            ignited = true;
        }

    }

    @Override
    public void takeTurn() {
        int i;
        AbstractPlayer p = AbstractDungeon.player;
        switch (this.nextMove)
        {
            case 0:
                AbstractDungeon.actionManager.addToBottom(new ApplyStasisAction(this));
                break;
            case 1:
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this,this,new BufferPower(this,1)));
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    @Override
    protected void getMove(int num) {
        if(!stasised)
        {
            setMove(StealCard,Intent.STRONG_DEBUFF);
            stasised = true;
        }
        else
        {
            setMove(BufferDefence,Intent.DEFEND);
        }
    }

    public void update() {
        super.update();
        if (this.MirrorID % 2 == 0) {
            this.animY = MathUtils.cosDeg((float)(System.currentTimeMillis() / 6L % 360L)) * 6.0F * Settings.scale;
        } else {
            this.animY = -MathUtils.cosDeg((float)(System.currentTimeMillis() / 6L % 360L)) * 6.0F * Settings.scale;
        }
    }

}
