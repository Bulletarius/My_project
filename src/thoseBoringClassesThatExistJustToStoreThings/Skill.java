package thoseBoringClassesThatExistJustToStoreThings;

import java.util.Objects;

public class Skill implements Cloneable{
    private String id;
    private String name;
    private String description;
    private int cost;
    private int basePower;
    private int coinPower;
    private int coinCount;
    private int chargeGained;
    private int chargeCost;
    private boolean chargeClash;
    private int sanityDamage;
    private int sanityCost;
    private boolean sanityClash;
    private Enemy owner;

    public Skill(String id, String name, String description,
                 int cost, int basePower, int coinPower, int coinCount,
                 int chargeGained, int chargeCost, boolean chargeClash,
                 int sanityDamage, int sanityCost, boolean sanityClash,
                 Enemy owner) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.basePower = basePower;
        this.coinPower = coinPower;
        this.coinCount = coinCount;
        this.chargeGained = chargeGained;
        this.chargeCost = chargeCost;
        this.chargeClash = chargeClash;
        this.sanityDamage = sanityDamage;
        this.sanityCost = sanityCost;
        this.sanityClash = sanityClash;
        this.owner = owner;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getCost() {
        return cost;
    }

    public int getBasePower() {
        return basePower;
    }

    public int getCoinPower() {
        return coinPower;
    }

    public int getCoinCount() {
        return coinCount;
    }

    public int getChargeGained() {
        return chargeGained;
    }

    public int getChargeCost() {
        return chargeCost;
    }

    public boolean isChargeClash() {
        return chargeClash;
    }

    public int getSanityDamage() {
        return sanityDamage;
    }

    public int getSanityCost() {
        return sanityCost;
    }

    public boolean isSanityClash() {
        return sanityClash;
    }

    public Enemy getOwner(){return owner;}

    public void changeCoinCount(int change){coinCount += change;}

    @Override
    public String toString() {
        String string = name + ", Cost:" + cost + ", " + basePower ;
        if (coinPower > 0){
            string = string.concat("|+" + coinPower + " ");
        }else{
            string = string.concat("|-" + coinPower + " ");
        }
        for (int i = 0; i < coinCount; i++) {
            string  = string.concat("◯");
        }
        string = string.concat(",]");
        return string;
    }


    public Skill clone(Enemy owner) {
        return new Skill(getId(),getName(),getDescription(),getCost(),getBasePower(),getCoinPower()
                ,getCoinCount(),getChargeGained(),getChargeCost(),isChargeClash(),getSanityDamage()
                ,getSanityCost(),isSanityClash(),owner);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Skill skill = (Skill) o;
        return Objects.equals(id, skill.id) && Objects.equals(name, skill.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
