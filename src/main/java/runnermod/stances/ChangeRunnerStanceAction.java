package runnermod.stances;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.stances.NeutralStance;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import runnermod.character.RunnerCharacter;
import runnermod.powers.DurablePower;
import runnermod.powers.ScrapArmourPower;

import java.util.Collections;
import java.util.Dictionary;
import java.util.Hashtable;

public class ChangeRunnerStanceAction extends AbstractGameAction {

    private static Dictionary<String,String> comboLookup = new Hashtable<>();

    AbstractStance finalStance;
    AbstractStance previousStance;
    boolean changeStance = true;
    int[] durabilities = new int[3];
    String newStanceID;
    String stanceID;
    int additionalDurability;
    //Only use for single stance
    public ChangeRunnerStanceAction(String stanceID, int durability)
    {
        this.stanceID = stanceID;
        newStanceID = stanceID;
        additionalDurability = 0;
        for (AbstractPower p :AbstractDungeon.player.powers) {
            if(p instanceof DurablePower)
            {
                additionalDurability += p.amount;
            }
        }
        this.durabilities[0] = durability + additionalDurability;
        for (AbstractPower p :AbstractDungeon.player.powers) {
            if (p.getClass() == ScrapArmourPower.class)
            {
                ((ScrapArmourPower) p).onDurabilityGain(durability);
            }
        }
        //Combo table to reference previous stance and new stance to see what you get
        comboLookup.put("BladesWall","Accel");
        comboLookup.put("WallBlades","Accel");
        comboLookup.put("BladesArtifact","Hack");
        comboLookup.put("ArtifactBlades","Hack");
        comboLookup.put("WallArtifact","Metal");
        comboLookup.put("ArtifactWall","Metal");
        comboLookup.put("WallOverclock","Tinker");
        comboLookup.put("OverclockWall","Tinker");
        comboLookup.put("ArtifactOverclock","Cards");
        comboLookup.put("OverclockArtifact","Cards");
        comboLookup.put("BladesOverclock", "Berserker");
        comboLookup.put("OverclockBlades", "Berserker");
        previousStance = AbstractDungeon.player.stance;



    }

    AbstractStance makeStance(String stanceID)
    {
        switch (stanceID)
        {
            case "Wall":
                if (AbstractDungeon.player instanceof RunnerCharacter)
                {
                    AbstractDungeon.player.img = RunnerStance.GetStanceImg("Wall");
                }
                return RunnerStance.MakeStance("Wall", new String[]{"Wall"},new int[]{durabilities[0]});
            case "Blades":
                if (AbstractDungeon.player instanceof RunnerCharacter)
                {
                    AbstractDungeon.player.img = RunnerStance.GetStanceImg("Blades");
                }
                return RunnerStance.MakeStance("Blades", new String[]{"Blades"},new int[]{durabilities[0]});
            case "Artifact":
                if (AbstractDungeon.player instanceof RunnerCharacter)
                {
                    AbstractDungeon.player.img = RunnerStance.GetStanceImg("Artifact");
                }
                return RunnerStance.MakeStance("Artifact", new String[]{"Artifact"},new int[]{durabilities[0]});
            case "Overclock":
                if (AbstractDungeon.player instanceof RunnerCharacter)
                {
                    AbstractDungeon.player.img = RunnerStance.GetStanceImg("Overclock");
                }
                return RunnerStance.MakeStance("Overclock", new String[]{"Overclock"},new int[]{durabilities[0]});
            case "Accel":
                //shuffle durabilities into correct spots for this combo
                if (newStanceID == "Wall")
                {

                    durabilities[1] = durabilities[0];
                    durabilities[0]=0;
                }
                // get max durability for each part
                for (String id: Collections.list(((RunnerStance) previousStance).durabilityDictionary.keys())) {
                    if (id == "Blades")
                    {
                        durabilities[0] = Math.max(durabilities[0], ((RunnerStance)previousStance).durabilityDictionary.get(id));
                    }
                    if (id == "Wall")
                    {
                        durabilities[1] = Math.max(durabilities[1], ((RunnerStance)previousStance).durabilityDictionary.get(id));
                    }
                }
                //make new stance and return
                if (AbstractDungeon.player instanceof RunnerCharacter)
                {
                    AbstractDungeon.player.img = ((RunnerCharacter) AbstractDungeon.player).accelStanceImg;
                }
                return new AccelStance(new String[]{"Blades", "Wall"},new int[]{durabilities[0], durabilities[1]});
            case "Hack":
                if (newStanceID == "Blades")
                {
                    durabilities[1] = durabilities[0];
                    durabilities[0]=0;
                }
                for (String id: Collections.list(((RunnerStance) previousStance).durabilityDictionary.keys())) {
                    if (id == "Artifact")
                    {
                        durabilities[0] = Math.max(durabilities[0], ((RunnerStance)previousStance).durabilityDictionary.get(id));
                    }
                    if (id == "Blades")
                    {
                        durabilities[1] = Math.max(durabilities[1], ((RunnerStance)previousStance).durabilityDictionary.get(id));
                    }
                }
                if (AbstractDungeon.player instanceof RunnerCharacter)
                {
                    AbstractDungeon.player.img = ((RunnerCharacter) AbstractDungeon.player).hackStanceImg;
                }
                return new HackStance(new String[]{"Artifact", "Blades"},new int[]{durabilities[0], durabilities[1]});
            case "Metal":
                if (newStanceID == "Wall")
                {
                    durabilities[1] = durabilities[0];
                    durabilities[0]=0;
                }
                for (String id: Collections.list(((RunnerStance) previousStance).durabilityDictionary.keys())) {
                    if (id == "Artifact")
                    {
                        durabilities[0] = Math.max(durabilities[0], ((RunnerStance)previousStance).durabilityDictionary.get(id));
                    }
                    if (id == "Wall")
                    {
                        durabilities[1] = Math.max(durabilities[1], ((RunnerStance)previousStance).durabilityDictionary.get(id));
                    }
                }
                if (AbstractDungeon.player instanceof RunnerCharacter)
                {
                    AbstractDungeon.player.img = ((RunnerCharacter) AbstractDungeon.player).metalStanceImg;
                }
                return new MetalStance(new String[]{"Artifact", "Wall"},new int[]{durabilities[0], durabilities[1]});
            case "Tinker":
                if (newStanceID == "Wall")
                {
                    durabilities[1] = durabilities[0];
                    durabilities[0]=0;
                }
                for (String id: Collections.list(((RunnerStance) previousStance).durabilityDictionary.keys())) {
                    if (id == "Overclock")
                    {
                        durabilities[0] = Math.max(durabilities[0], ((RunnerStance)previousStance).durabilityDictionary.get(id));
                    }
                    if (id == "Wall")
                    {
                        durabilities[1] = Math.max(durabilities[1], ((RunnerStance)previousStance).durabilityDictionary.get(id));
                    }
                }
                if (AbstractDungeon.player instanceof RunnerCharacter)
                {
                    AbstractDungeon.player.img = ((RunnerCharacter) AbstractDungeon.player).tinkerStanceImg;
                }
                return new TinkerStance(new String[]{"Overclock", "Wall"},new int[]{durabilities[0], durabilities[1]});
            case "Cards":
                if (newStanceID == "Overclock")
                {
                    durabilities[1] = durabilities[0];
                    durabilities[0]=0;
                }
                for (String id: Collections.list(((RunnerStance) previousStance).durabilityDictionary.keys())) {
                    if (id == "Artifact")
                    {
                        durabilities[0] = Math.max(durabilities[0], ((RunnerStance)previousStance).durabilityDictionary.get(id));
                    }
                    if (id == "Overclock")
                    {
                        durabilities[1] = Math.max(durabilities[1], ((RunnerStance)previousStance).durabilityDictionary.get(id));
                    }
                }
                if (AbstractDungeon.player instanceof RunnerCharacter)
                {
                    AbstractDungeon.player.img = ((RunnerCharacter) AbstractDungeon.player).blasterStanceImg;
                }
                return new CardsStance(new String[]{"Artifact", "Overclock"},new int[]{durabilities[0], durabilities[1]});
            case "Berserker":
                if (newStanceID == "Overclock")
                {
                    durabilities[1] = durabilities[0];
                    durabilities[0]=0;
                }
                for (String id: Collections.list(((RunnerStance) previousStance).durabilityDictionary.keys())) {
                    if (id == "Blades")
                    {
                        durabilities[0] = Math.max(durabilities[0], ((RunnerStance)previousStance).durabilityDictionary.get(id));
                    }
                    if (id == "Overclock")
                    {
                        durabilities[1] = Math.max(durabilities[1], ((RunnerStance)previousStance).durabilityDictionary.get(id));
                    }
                }
                if (AbstractDungeon.player instanceof RunnerCharacter)
                {
                    AbstractDungeon.player.img = ((RunnerCharacter) AbstractDungeon.player).berserkerStanceImg;
                }
                return new BerserkerStance(new String[]{"Blades", "Overclock"},new int[]{durabilities[0], durabilities[1]});
            default:
                if (AbstractDungeon.player instanceof RunnerCharacter)
                {
                    AbstractDungeon.player.img = ((RunnerCharacter) AbstractDungeon.player).baseImg;
                }
                return new NeutralStance();
        }

    }


    @Override
    public void update() {
        previousStance = AbstractDungeon.player.stance;
        if(previousStance instanceof AKIRAStance && stanceID.equals("Glitched"))
        {
            AbstractDungeon.effectList.add(new SpeechBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, "Can't Corrupt me now, I'm already broken", true));
        }
        if (previousStance instanceof AKIRAStance || previousStance instanceof  GlitchedStance)
        {
            this.isDone = true;
            return;
        }
        if (stanceID == "AKIRA")
        {
            if (AbstractDungeon.player instanceof RunnerCharacter)
            {
                AbstractDungeon.player.img = ((RunnerCharacter) AbstractDungeon.player).baseImg;
            }
            finalStance =  new AKIRAStance(new String[]{"AKIRA"}, new int[]{0});
            AbstractDungeon.actionManager.addToTop(new ChangeStanceAction(finalStance));
            this.isDone = true;
            return;
        }
        if (stanceID == "Glitched")
        {
            finalStance =  new GlitchedStance(new String[]{"Glitched"}, new int[]{0}, AbstractDungeon.player.stance);
            AbstractDungeon.actionManager.addToTop(new ChangeStanceAction(finalStance));
            this.isDone = true;
            return;
        }
        if (!(previousStance instanceof RunnerStance))
        {
            finalStance = makeStance(stanceID);
        }
        else
        {
            String newID = "";
            String components = "";
            for (String id: Collections.list(((RunnerStance) previousStance).durabilityDictionary.keys())) {
                components += id;
            }
            if(stanceID == "Neutral")
            {
                for (String id: Collections.list(((RunnerStance) previousStance).durabilityDictionary.keys())) {
                    if (((RunnerStance) previousStance).durabilityDictionary.get(id) >0)
                    {
                        this.isDone = true;
                        changeStance = false;
                        return;
                    }
                }
            }
            System.out.println("Previous stance durabilities: " + ((RunnerStance) previousStance).durabilityDictionary.keys().toString());
            //if new stance already part of existing stance then flag as the same
            if (components.contains(stanceID))
            {
                newID = "same";
            }
            else
            {
                //get combo
                newID = RunnerStance.determineNewStance(stanceID);
                //extra check to be careful (defaults to new stance)
                if (newID == null)
                {
                    newID = stanceID;
                }
            }
            //if the same update durabilities
            System.out.println(newID);
            if (newID == "same")
            {
                changeStance = false;
                durabilities[0] = Math.max(durabilities[0], ((RunnerStance) previousStance).durabilityDictionary.get(stanceID));
                ((RunnerStance) previousStance).durabilityDictionary.put(stanceID,durabilities[0]);
                previousStance.updateDescription();
            }
            else
            {
                //if a combo make new stance
                finalStance = makeStance(newID);
                System.out.println("New Stance : " + finalStance.ID);
            }
        }
        if (changeStance)
        {
            AbstractDungeon.actionManager.addToTop(new ChangeStanceAction(finalStance));
        }
        this.isDone = true;
    }
}
