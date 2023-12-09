package com.witcher.ttrpgapi.pojo;

import com.witcher.ttrpgapi.utils.TableReader;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.lang.reflect.Field;
import java.util.*;


@Getter
@Setter
@AllArgsConstructor

public class Character {

    @Id
    private Integer id;

    @NotNull
    private String name;
    @NotNull
    private String profession;
    @NotNull
    private String race;
    @NotNull
    private String gender;

    private Integer age;

    @NotNull
    private Integer intelligence;
    @NotNull
    private Integer ref;
    @NotNull
    private Integer dex;
    @NotNull
    private Integer body;
    @NotNull
    private Integer spd;
    @NotNull
    private Integer emp;
    @NotNull
    private Integer cra;
    @NotNull
    private Integer will;
    @NotNull
    private Integer luck;
    private Integer stun;
    private Integer run;
    private Integer leap;
    private Integer hp;
    private Integer sta;
    private Integer enc;
    private Integer rec;

    private Integer maxHp;
    private Integer meleeBonus;



    private Integer punch = 0;
    private Integer kick = 0;
    private Integer awareness = 0;
    private Integer business = 0;
    private Integer deduction = 0;
    private Integer education = 0;
    private Integer commonSpeech = 0;
    private Integer elderSpeech = 0;
    private Integer dwarvenSpeech = 0;
    private Integer monsterLore = 0;
    private Integer socialEtiquette = 0;
    private Integer streetwise = 0;
    private Integer tactics = 0;
    private Integer teaching = 0;
    private Integer wildernessSurvival = 0;
    private Integer brawling = 0;
    private Integer dodge = 0;
    private Integer melee = 0;
    private Integer riding = 0;
    private Integer sailing = 0;
    private Integer smallBlades = 0;
    private Integer staff = 0;
    private Integer swordsmanship = 0;
    private Integer archery = 0;
    private Integer athletic = 0;
    private Integer crossbow = 0;
    private Integer sleightOfHand = 0;
    private Integer stealth = 0;
    private Integer physique = 0;
    private Integer endurance = 0;
    private Integer charisma = 0;
    private Integer deceit = 0;
    private Integer fineArt = 0;
    private Integer gambling = 0;
    private Integer style = 0;
    private Integer humanPerception = 0;
    private Integer leadership = 0;
    private Integer persuasion = 0;
    private Integer performance = 0;
    private Integer seduction = 0;
    private Integer alchemy = 0;
    private Integer crafting = 0;
    private Integer disguise = 0;
    private Integer firstAid = 0;
    private Integer forgery = 0;
    private Integer pickLock = 0;
    private Integer trapCrafting = 0;
    private Integer courage = 0;
    private Integer hexWeaving = 0;
    private Integer intimidation = 0;
    private Integer spellCasting = 0;
    private Integer resistMagic = 0;
    private Integer resistCoercion = 0;
    private Integer ritualCrafting = 0;

    public Character(){

    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public void setCalculatedStats(){
        TableReader reader = new TableReader();
        int keyCalc = (int) Math.floor ((this.body + this.will) / 2);
        this.setHp(reader.physicalTableRead(keyCalc, "hp"));
        this.setSta(reader.physicalTableRead(keyCalc, "sta"));
        this.setRec(reader.physicalTableRead(keyCalc, "rec"));
        this.setStun(reader.physicalTableRead(keyCalc, "stun"));
        //MELEE BONUIS
        this.setMeleeBonus(reader.meleeBonusRead(this.body));
        //RUN (SPD X 3)
        this.setRun(this.spd*3);
        //LEAP
        this.setLeap((int) Math.floor (this.run / 5));
        //ENC
        this.setEnc(this.body * 10);
    }

    public int rollAbillity(String attrKey){
        try {
            Field field = getClass().getDeclaredField(attrKey);
            field.setAccessible(true);
            Random random = new Random();
            int rolledNumber = random.nextInt(10) + 1;
            System.out.println("INENTÖLLL");
            System.out.println(this.getAthletic());
            int total = (int) field.get(this) + rolledNumber + skillModifyerCalculator(attrKey);
            return total;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return 0;
        }

    }

    private int skillModifyerCalculator(String attrKey){
        List<String> intelligence = List.of("awareness",
                "business",
                "deduction",
                "education",
                "commonSpeech",
                "elderSpeech",
                "dwarvenSpeech",
                "monsterLore",
                "socialEtiquette",
                "streetwise",
                "tactics",
                "teaching",
                "wildernessSurvival");

        List<String> ref = List.of("brawling",
                "dodge",
                "melee",
                "riding",
                "sailing",
                "smallBlades",
                "staff",
                "swordsmanship");

        List<String> dex = List.of(    "archery",
                "athletic",
                "crossbow",
                "sleightOfHand",
                "stealth");
        List<String> body = List.of(      "physique",
                "endurance");
        List<String> emp = List.of(  "charisma",
                "deceit",
                "fineArt",
                "gambling",
                "style",
                "humanPerception",
                "leadership",
                "persuasion",
                "performance",
                "seduction");
        List<String> craft = List.of(    "alchemy",
                "crafting",
                "disguise",
                "firstAid",
                "forgery",
                "pickLock",
                "trapCrafting");
        List<String> will = List.of(  "courage",
                "hexWeaving",
                "intimidation",
                "spellCasting",
                "resistMagic",
                "resistCoercion",
                "ritualCrafting");

        if(intelligence.contains(attrKey)){
            return this.intelligence;
        }
        if(ref.contains(attrKey)){
            return this.ref;
        }
        if(dex.contains(attrKey)){
            return this.dex;
        }
        if(body.contains(attrKey)){
            return this.body;
        }
        if(emp.contains(attrKey)){
            return this.emp;
        }
        if(craft.contains(attrKey)){
            return this.cra;
        }
        if(will.contains(attrKey)){
            return this.will;
        }
        return 0;

    }


}
