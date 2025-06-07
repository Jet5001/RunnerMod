package runnermod.monsters;

import basemod.BaseMod;
import basemod.interfaces.OnCardUseSubscriber;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import runnermod.RunnerMod;
import runnermod.cards.tempcards.Virus;
import runnermod.stances.ChangeRunnerStanceAction;
import runnermod.stances.GlitchedStance;
import runnermod.util.AkiraStrings;
import runnermod.util.LocalizedAkiraStrings;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

import static runnermod.RunnerMod.*;

public class AKIRABoss extends AbstractMonster implements OnCardUseSubscriber {

    public static final String ID = RunnerMod.makeID("AKIRABoss");
    private static final AkiraStrings monsterStrings = LocalizedAkiraStrings.getAkiraStrings(makeID("AkiraBoss"));
    public static final String[] DIALOG = monsterStrings.DIALOG;

    private static int maxHP = 398;

    private static String imgUrl = imagePath("monsters/Akira_Boss.png");

    private static final byte ForceStance = 0;
    private static final byte BufferDefence = 1;
    private static final byte DebuffAttacks = 2;
    private static final byte ChunkAttack = 3;
    private static final byte Heal = 4;

    private int MultiAttackDamage = 6;
    private int MultiAttackDamageWeaker =2;
    private int ChunkDamage = 28;


    private boolean usedHeal = false;
    private boolean firstTurn = false;
    private boolean attackedRecently =false;
    private int turnCount = 1;
    private int bufferCount = 3;

    int mirrorsSpawned = 0;


    private static float hb_x  = -10.0F;
    private static float hb_y  = -30.0F;
    private static float offsetX = 0f;
    private static float offsetY = 0f;
    private static int hb_w  = 316;
    private static int hb_h  = 450;

    public AKIRABoss() {
        super("Akira", ID, maxHP, hb_x, hb_y, hb_w, hb_h, imgUrl,offsetX,offsetY);
        this.damage.add(new DamageInfo(this, this.MultiAttackDamage, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this, this.ChunkDamage, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this, this.MultiAttackDamageWeaker, DamageInfo.DamageType.NORMAL));
        this.type = AbstractMonster.EnemyType.BOSS;
        BaseMod.subscribe(this);
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
                if(AbstractDungeon.ascensionLevel >= 9) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, 1)));
                }
                break;
            case 2:
                for (i = 0; i < 3; i++) {
                    if(usedHeal)
                    {
                        AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(2), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                    }
                    else
                    {
                        AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                    }

                }
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, 1, true), 1));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new VulnerablePower(AbstractDungeon.player, 1, true), 1));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, 1, true), 1));
                if(AbstractDungeon.ascensionLevel >= 19)
                {
                    AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Virus(),3,true,true ));
                }
                break;
            case 3:
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.SMASH));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this,this, new StrengthPower(this,2)));
                if(usedHeal)
                {
                    SpawnDarkMirror();
                }
                break;
            case 4:
                AbstractDungeon.actionManager.addToBottom(new RemoveDebuffsAction(this));
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this, this, "Shackled"));
                AbstractDungeon.actionManager.addToBottom(new HealAction(this, this, this.maxHealth / 4));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this,this, new IntangiblePlayerPower(this,2)));
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
                SpawnDarkMirror();
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
        turnCount++;
    }

    private void SpawnDarkMirror() {
        List<DarkMirror> mirrors = AbstractDungeon.getMonsters().monsters.stream().map(m -> m instanceof DarkMirror ? (DarkMirror)m:null).filter(m -> m!=null && !m.isDeadOrEscaped()).collect(Collectors.toList());
        if(mirrors.size() <=4)
        {
            AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(new DarkMirror(), true));
        }
    }


    public void damage(DamageInfo info) {
        super.damage(info);
        if (!this.isDying && this.currentHealth <= this.maxHealth / 2.0F && this.nextMove != 3 && !usedHeal) {
            setMove(Heal, AbstractMonster.Intent.UNKNOWN);
            createIntent();
            AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new SetMoveAction(this, Heal, AbstractMonster.Intent.UNKNOWN));
            usedHeal = true;
        }
    }

    @Override
    protected void getMove(int num) {
        if(currentHealth < maxHealth/3 && !usedHeal)
        {
            usedHeal = true;
            setMove(Heal, Intent.UNKNOWN);
            return;
        }
        if(firstTurn)
        {
            setMove(ForceStance, Intent.STRONG_DEBUFF);
            firstTurn = false;
        }
        if(turnCount % 5 == 1)
        {
            setMove(ForceStance, Intent.STRONG_DEBUFF);
            return;
        }
        if(num < 33 && !lastMove(DebuffAttacks))
        {
            setMove(DebuffAttacks,Intent.ATTACK_DEBUFF,this.damage.get(0).base,3,true);
            attackedRecently = true;
            return;
        }
        if(num < 75 && !lastMove(ChunkAttack))
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

    @Override
    public void die() {
        super.die();
        BaseMod.unsubscribe(this);
    }

    @Override
    public void receiveCardUsed(AbstractCard abstractCard) {
        if(AbstractDungeon.player.stance instanceof GlitchedStance)
        {
            int cardsPlayedThisTurn = AbstractDungeon.actionManager.cardsPlayedThisTurn.size();
            if(cardsPlayedThisTurn <=2)
            {
                AbstractDungeon.actionManager.addToBottom(new ShoutAction(this, DIALOG[cardsPlayedThisTurn]+"...", 1.7F, 1.7F));
            }
            if(cardsPlayedThisTurn ==3)
            {
                AbstractDungeon.actionManager.addToBottom(new ShoutAction(this, DIALOG[cardsPlayedThisTurn], 1.7F, 1.7F));
            }
        }
    }
}
