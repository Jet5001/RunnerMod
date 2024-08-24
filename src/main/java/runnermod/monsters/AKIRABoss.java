package runnermod.monsters;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import runnermod.RunnerMod;
import runnermod.cards.tempcards.Virus;
import runnermod.stances.ChangeRunnerStanceAction;

import static runnermod.RunnerMod.characterPath;
import static runnermod.RunnerMod.imagePath;

public class AKIRABoss extends AbstractMonster {

    public static final String ID = RunnerMod.makeID("AKIRABoss");
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;

    private static int maxHP = 348;
    private static float hb_x  = -10.0F;
    private static float hb_y  = -30.0F;
    private static float offsetX = 0f;
    private static float offsetY = 0f;
    private static int hb_w  = 447;
    private static int hb_h  = 364;
    private static String imgUrl = imagePath("monsters/CharDefault.png");

    private static final byte ForceStance = 0;
    private static final byte BufferDefence = 1;
    private static final byte DebuffAttacks = 2;
    private static final byte AddViruses = 3;
    private static final byte ChunkAttack = 4;
    private static final byte Heal = 5;

    private int MultiAttackDamage = 6;
    private int ChunkDamage = 35;


    private boolean usedHeal = false;
    private boolean lastDebuffIsStance;
    private boolean firstTurn = false;
    private boolean attackedRecently =false;
    private int turnCount = 1;
    private int bufferCount = 3;

    public AKIRABoss() {
        super("Akira", ID, maxHP, hb_x, hb_y, hb_w, hb_h, imgUrl,offsetX,offsetY);
        this.damage.add(new DamageInfo(this, this.MultiAttackDamage, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this, this.ChunkDamage, DamageInfo.DamageType.NORMAL));
        this.type = AbstractMonster.EnemyType.BOSS;
    }

    public void usePreBattleAction() {
        CardCrawlGame.music.unsilenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();
        AbstractDungeon.getCurrRoom().playBgmInstantly("BOSS_BEYOND");
    }

    @Override
    public void takeTurn() {
        int i;
        AbstractPlayer p = AbstractDungeon.player;
        switch (this.nextMove)
        {
            case 0:
                AbstractDungeon.actionManager.addToBottom(new VFXAction(this,new ShockWaveEffect(this.hb.cX, this.hb.cY, Settings.BLUE_TEXT_COLOR, ShockWaveEffect.ShockWaveType.CHAOTIC), 0.75F));
                AbstractDungeon.actionManager.addToBottom(new ChangeRunnerStanceAction("Glitched",0));
                break;
            case 1:
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this,this,new BufferPower(this,bufferCount)));
                bufferCount++;
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this,this, new StrengthPower(this,1)));
                break;
            case 2:
                for (i = 0; i < 3; i++) {
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                }
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, 1, true), 1));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new VulnerablePower(AbstractDungeon.player, 1, true), 1));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, 1, true), 1));
                break;
            case 3:
                AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new ShockWaveEffect(this.hb.cX, this.hb.cY, Settings.BLUE_TEXT_COLOR, ShockWaveEffect.ShockWaveType.CHAOTIC), 0.75F));
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Virus(),3,true,true ));
                break;
            case 4:
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.SMASH));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this,this, new StrengthPower(this,3)));
                break;
            case 5:
                AbstractDungeon.actionManager.addToBottom(new RemoveDebuffsAction(this));
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this, this, "Shackled"));
                AbstractDungeon.actionManager.addToBottom(new HealAction(this, this, this.maxHealth / 4));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this,this, new IntangiblePlayerPower(this,1)));
                int strAmount = 0;
                for (int j = 0; j < this.powers.size(); j++) {
                    if(powers.get(j) instanceof StrengthPower)
                    {
                        strAmount = powers.get(j).amount;
                    }
                }
                if(strAmount > 0)
                {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this,this, new StrengthPower(this,strAmount)));
                }
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
        turnCount++;
    }

    @Override
    protected void getMove(int num) {
        if(currentHealth < maxHealth/3 && !usedHeal)
        {
            usedHeal = true;
            setMove(Heal, Intent.BUFF);
            return;
        }
        if(firstTurn)
        {
            setMove(ForceStance, Intent.DEBUFF);
            firstTurn = false;
        }
        if(turnCount % 5 == 1)
        {
            if(!lastDebuffIsStance)
            {
                setMove(ForceStance, Intent.DEBUFF);
            }
            else
            {
                setMove(AddViruses, Intent.DEBUFF);
            }
            return;
        }
        if(num < 33 && !lastMove(DebuffAttacks))
        {
            setMove(DebuffAttacks,Intent.ATTACK_DEBUFF,this.damage.get(0).base,3,true);
            attackedRecently = true;
            return;
        }
        if(num < 70 && !lastMove(ChunkAttack))
        {
            setMove(ChunkAttack,Intent.ATTACK_BUFF, this.damage.get(1).base);
            attackedRecently = true;
            return;
        }
        if(num < 100 && !lastTwoMoves(BufferDefence) && !lastMove(BufferDefence) && attackedRecently)
        {
            setMove(BufferDefence,Intent.DEFEND_BUFF);
            attackedRecently = false;
            return;
        }
        getMove(AbstractDungeon.aiRng.random(99));
    }

}
