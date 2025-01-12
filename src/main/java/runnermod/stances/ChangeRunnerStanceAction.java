package runnermod.stances;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PainfulStabsPower;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.stances.NeutralStance;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import javafx.util.Pair;
import runnermod.character.RunnerCharacter;
import runnermod.powers.DurablePower;
import runnermod.powers.ScrapArmourPower;

import java.util.Collections;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.LinkedHashMap;

public class ChangeRunnerStanceAction extends AbstractGameAction {


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
        previousStance = AbstractDungeon.player.stance;



    }

    AbstractStance makeBaseStance(String stanceID) {
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
        }
        return new NeutralStance();
    }


    AbstractStance makeComboStance(String newstanceID)
    {
        Hashtable<String, Pair<String,String>> componentLookup = new Hashtable<>();
        componentLookup.put("Accel",new Pair<>("Blades","Wall"));
        componentLookup.put("Hack",new Pair<>("Artifact","Blades"));
        componentLookup.put("Metal",new Pair<>("Artifact","Wall"));
        componentLookup.put("Tinker",new Pair<>("Overclock","Wall"));
        componentLookup.put("Cards",new Pair<>("Artifact","Overclock"));
        componentLookup.put("Berserker",new Pair<>("Blades","Overclock"));
        String Component1 = componentLookup.get(newstanceID).getKey();
        String Component2 = componentLookup.get(newstanceID).getValue();
        LinkedHashMap<String,Integer> newDurabilities = new LinkedHashMap<String,Integer>();
        LinkedHashMap<String,Integer> previousDurabilities = ((RunnerStance) previousStance).durabilityDictionary;
        for (String id: (previousDurabilities.keySet())) {
            if(id.equals(Component1) || id.equals(Component2))
            {
                newDurabilities.put(id,previousDurabilities.get(id));
            }
        }
        newDurabilities.put(stanceID,3);
        switch (newstanceID)
        {
            case "Accel":
                if (AbstractDungeon.player instanceof RunnerCharacter)
                {
                    AbstractDungeon.player.img = ((RunnerCharacter) AbstractDungeon.player).accelStanceImg;
                }
                return new AccelStance(newDurabilities);
            case "Hack":
                if (AbstractDungeon.player instanceof RunnerCharacter)
                {
                    AbstractDungeon.player.img = ((RunnerCharacter) AbstractDungeon.player).hackStanceImg;
                }
                return new HackStance(newDurabilities);
            case "Tinker":
                if (AbstractDungeon.player instanceof RunnerCharacter)
                {
                    AbstractDungeon.player.img = ((RunnerCharacter) AbstractDungeon.player).tinkerStanceImg;
                }
                return new TinkerStance(newDurabilities);
            case "Cards":
                if (AbstractDungeon.player instanceof RunnerCharacter)
                {
                    AbstractDungeon.player.img = ((RunnerCharacter) AbstractDungeon.player).blasterStanceImg;
                }
                return new BlasterStance(newDurabilities);
            case "Berserker":
                if (AbstractDungeon.player instanceof RunnerCharacter)
                {
                    AbstractDungeon.player.img = ((RunnerCharacter) AbstractDungeon.player).berserkerStanceImg;
                }
                return new BerserkerStance(newDurabilities);
            case "Metal":
                if (AbstractDungeon.player instanceof RunnerCharacter)
                {
                    AbstractDungeon.player.img = ((RunnerCharacter) AbstractDungeon.player).metalStanceImg;
                }
                return new MetalStance(newDurabilities);
        }
        AbstractDungeon.player.img = ((RunnerCharacter) AbstractDungeon.player).baseImg;
        return new NeutralStance();
        //return  RunnerStance.MakeStance(newstanceID,newDurabilities.keys(),newDurabilities.elements());
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
            finalStance =  new GlitchedStance(AbstractDungeon.player.stance);
            AbstractDungeon.actionManager.addToTop(new ChangeStanceAction(finalStance));
            this.isDone = true;
            return;
        }
        if (!(previousStance instanceof RunnerStance))
        {
            finalStance = makeBaseStance(stanceID);
        }
        else
        {
            String newID = "";
            String components = "";
            for (String id: (((RunnerStance) previousStance).durabilityDictionary.keySet().toArray(new String[0]))) {
                components += id;
                System.out.println("Previous stance durabilities: " + id + "Durability: " + ((RunnerStance) previousStance).durabilityDictionary.get(id));
            }
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
                finalStance = makeComboStance(newID);
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
