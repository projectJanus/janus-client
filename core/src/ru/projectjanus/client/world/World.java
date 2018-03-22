package ru.projectjanus.client.world;

import java.util.ArrayList;
import java.util.List;

import ru.projectjanus.client.math.Circle;
import ru.projectjanus.client.Linkable;
import ru.projectjanus.client.physics.CollisionController;
import ru.projectjanus.client.player.Player;
import ru.projectjanus.client.substances.Substance;
import ru.projectjanus.client.substances.SubstanceEmitter;
import ru.projectjanus.client.visual.VisualData;

/**
 * Created by raultaylor.
 */
public class World extends Circle implements Linkable {
    private long weight;
    private VisualData visualData;
    private List<Substance> allSubstance;
    private SubstanceEmitter substanceEmitter;
    private ArrayList<Player> players;
    private CollisionController collisionController;
    private static final float DENSITY = 0.02f;
    private final String myType = "world";

    public World(VisualData visualData) {
        size = 59.16f;
        players = new ArrayList<Player>();
        substanceEmitter = new SubstanceEmitter(this);
        collisionController = new CollisionController(visualData);
        this.visualData = visualData;
        updateWorld();
    }

    public void updateWorld() {

        collisionController.CollisionPlayerToWorld(players, this);
        collisionController.CollisionPlayerToSubstance(players, allSubstance);
        substanceEmitter.addSubstance();
        allSubstance = substanceEmitter.getActiveSubstance();
        updateWeight();
        extendBound();

        // System.out.println("CurrentWeight World: "+weight+" Bounds: "+size+" FreeSubstance: " + allSubstance.size());
    }

    private void updateWeight() {
        long tempWeight = 0;
        for (Substance substance : allSubstance) {
            tempWeight += substance.getWeight();
        }
        for (Player player : players) {
            tempWeight += player.getWeight();
        }
        weight = tempWeight;
    }

    private void extendBound() {
        double volume = weight / DENSITY;
        //size = (float)(Math.exp(Math.log(volume)/2.5f));
        size = (float) (Math.exp(Math.log(volume) / 1.8f));
    }

    @Override
    public String getNameType() {
        return myType;
    }

    public void addPlayer(Player player) {
        players.add(player);
        updateWorld();
    }

    public void getNewData() {
        visualData.cleanAll();
        visualData.add(this);
        visualData.addAll(players);
        visualData.addAll(allSubstance);
        //System.out.println("VD: "+visualData.getData().size());
    }

    public float getBound() {
        return this.size;
    }

    public float getWeight() {
        //System.out.println("World weight: " + weight);
        return weight;
    }
}
