package game;

import thoseBoringClassesThatExistJustToStoreThings.Skill;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

public class Inventory {
    private HashSet<Skill> ownedSkills;
    private ArrayList<Skill> deck;
    private LinkedList<Skill> currentDeck;
    private LinkedList<Skill> hand;

    public Inventory(GameData data){
        ownedSkills = new HashSet<>();
        deck = new ArrayList<>();
        ownedSkills.add(data.getSkill("s_p_test"));
        for (int i = 0; i < 10; i++) {
            deck.add(data.getSkill("s_p_test"));
        }
    }

    public void unlockSkill(Skill s){
        ownedSkills.add(s);
    }

    public void addDeck(Skill s){
        if(deck.size() >= 30){
            if (ownedSkills.contains(s)){
                deck.add(s);
                System.out.println("Added successfully");
            }else System.out.println("You do not own this skill");
        }else System.out.println("Your deck is already at the maximum size");
    }

    public void removeDeck(Skill s){
        if (deck.remove(s)){
            System.out.println("Removed successfully");
        }else System.out.println("None of this skill are in your deck");
    }

    public void makeCurrentDeck(){
        Random random;
        random  = new Random();
        currentDeck = new LinkedList<>();
        ArrayList<Skill> tempList = new ArrayList<>(deck);
        for (int i = 0; i < deck.size(); i++) {
            currentDeck.add(tempList.remove(random.nextInt(tempList.size())).clone(null));
        }
    }

    public void makeHand(int size){
        hand = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            hand.add(currentDeck.pollFirst());
        }
    }

    public void returnHand(){
        currentDeck.addAll(hand);
        hand = null;
    }

    public String printHand(){
        return hand.toString();
    }

    public boolean removeHand(Skill skill){
        return hand.remove(skill);
    }

    public void addHand(Skill skill){
        hand.add(skill);
    }

    @Override
    public String toString() {
        return "Owned skills:" + System.lineSeparator()
                + ownedSkills + System.lineSeparator()
                + "Deck:" + System.lineSeparator() + deck;
    }
}
