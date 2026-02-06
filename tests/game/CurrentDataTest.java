package game;

import thoseBoringClassesThatExistJustToStoreThings.Enemy;
import thoseBoringClassesThatExistJustToStoreThings.Location;
import thoseBoringClassesThatExistJustToStoreThings.Skill;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CurrentDataTest {
    CurrentData cData;

    private static class FakeData extends GameData{
        private ArrayList<Enemy> enemies;
        private ArrayList<Skill> skills;
        private Location location;

        public FakeData(Enemy[] enemies, Skill[] skill, Location location){
            this.enemies = new ArrayList<>(List.of(enemies));
            this.skills = new ArrayList<>(List.of(skill));
            this.location = location;

        }
    }

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        cData = new CurrentData(new FakeData(new Enemy[]{new Enemy("test", "testGuy", 10,
                new ArrayList<>(List.of("test_skill")), "testDescription", 1, 0)},
                new Skill[]{new Skill("s","test_skill","test",1,2,3,
                        4,5,6,true,8,
                        9,false,null)},
                new Location()));

    }

    @org.junit.jupiter.api.Test
    void startCombat() {
    }

    @org.junit.jupiter.api.Test
    void startTurn() {
    }

    @org.junit.jupiter.api.Test
    void evaluate() {
    }
}