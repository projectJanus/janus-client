package ru.projectjanus.client.substances;

import com.badlogic.gdx.math.Vector2;

import java.util.List;

import ru.projectjanus.client.math.Circle;
import ru.projectjanus.client.math.Rnd;
import ru.projectjanus.client.world.World;

/**
 * Created by raultaylor.
 */
public class SubstanceEmitter {
    public static final int HEAVY_TYPE = 3;
    public static final int LIGHT_TYPE = 1;
    public static final int MEDIUM_TYPE = 2;
    private long deltaTimer;
    private int countAdd;
    private int currentStage = 0;
    private World myWorld;
    private Vector2 tempPos = new Vector2();
    private SubstancePool substancePool;
    //Stages
    private static final int[] COUNT_FREE_SUBSTANCE = {150, 140, 120, 110, 100, 80};
    private static final int[] WEIGHT_SUBSTANCE = {1, 5, 10, 25, 50, 200};
    //HEAVY_TYPE DENSITY
    private static final float HEAVY_DENSITY = 1.5f;
    private static final String HEAVY_NAME_TYPE = "heavy_subst";
    //MEDIUM_TYPE DENSITY
    private static final float MEDIUM_DENSITY = 1.0f;
    private static final String MEDIUM_NAME_TYPE = "medium_subst";
    //LIGHT_TYPE DENSITY
    private static final float LIGHT_DENSITY = 0.5f;
    private static final String LIGHT_NAME_TYPE = "light_subst";

    public SubstanceEmitter(World myWorld) {
        this.myWorld = myWorld;
        substancePool = new SubstancePool();
    }

    public void addSubstance() {

        deltaTimer = System.currentTimeMillis();

        substancePool.freeAllDestroyedActiveObjects();

        selectStage();

        countAdd = 0;

        //System.out.println("Subs: " + substancePool.getActiveObjects().size());

        while (substancePool.getActiveObjects().size() < COUNT_FREE_SUBSTANCE[currentStage]) {

            countAdd++;

            switch (Rnd.nextInt(1, 4)) {
                case HEAVY_TYPE:
                    this.generateSubstance(SubstanceEmitter.HEAVY_TYPE);
                    break;
                case LIGHT_TYPE:
                    this.generateSubstance(SubstanceEmitter.LIGHT_TYPE);
                    break;
                case MEDIUM_TYPE:
                    this.generateSubstance(SubstanceEmitter.MEDIUM_TYPE);
            }
        }

        //System.out.println("Count add: "+ countAdd);
        //System.out.println("Substance addSub() Time: " + (System.currentTimeMillis()-deltaTimer));

    }

    private void selectStage() {
        if (myWorld.getWeight() > 150) {
            currentStage = 1;
        } else if (myWorld.getWeight() > 500) {
            currentStage = 2;
        } else if (myWorld.getWeight() > 2000) {
            currentStage = 3;
        } else if (myWorld.getWeight() > 5000) {
            currentStage = 4;
        } else if (myWorld.getWeight() > 12000) {
            currentStage = 5;
        } else {
            currentStage = 0;
        }
    }

    public void generateSubstance(int type) {
        ru.projectjanus.client.substances.Substance substance;
        switch (type) {
            case LIGHT_TYPE:
                substance = substancePool.obtain();
                substance.set(generateNewPosition(), WEIGHT_SUBSTANCE[currentStage], LIGHT_DENSITY, LIGHT_NAME_TYPE);
                break;
            case MEDIUM_TYPE:
                substance = substancePool.obtain();
                substance.set(generateNewPosition(), WEIGHT_SUBSTANCE[currentStage], MEDIUM_DENSITY, MEDIUM_NAME_TYPE);
                break;
            case HEAVY_TYPE:
                substance = substancePool.obtain();
                substance.set(generateNewPosition(), WEIGHT_SUBSTANCE[currentStage], HEAVY_DENSITY, HEAVY_NAME_TYPE);
        }

    }

    private Vector2 generateNewPosition() {
        tempPos.set(Circle.convertToPos(Rnd.nextFloat(0.15f * myWorld.getBound(), myWorld.getBound()), Rnd.nextFloat(0, 2 * 3.14f)));
        return tempPos;
    }

    public List<ru.projectjanus.client.substances.Substance> getActiveSubstance() {
        return substancePool.getActiveObjects();
    }
}
