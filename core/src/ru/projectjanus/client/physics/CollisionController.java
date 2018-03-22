package ru.projectjanus.client.physics;

import com.badlogic.gdx.math.Vector2;

import java.util.List;

import ru.projectjanus.client.player.Player;
import ru.projectjanus.client.substances.Substance;
import ru.projectjanus.client.visual.VisualData;
import ru.projectjanus.client.world.World;

/**
 * Created by raultaylor.
 */
public class CollisionController {
    private long deltaTimer;
    private Vector2 tempPToS;
    private Vector2 tempPToW;
    private VisualData visualData;

    public CollisionController(VisualData visualData) {
        tempPToS = new Vector2();
        tempPToW = new Vector2();
        this.visualData = visualData;
    }

    public void CollisionPlayerToSubstance(List<Player> players, List<Substance> substances) {

        deltaTimer = System.currentTimeMillis();

        visualData.cleanAllOld();
        for (Player player : players) {
            for (Substance substance : substances) {
                tempPToS.set(player.getPos());
                if (!substance.isDestroyed() && tempPToS.sub(substance.getPos()).len() < player.getSize() + substance.getSize()) {
                    substance.setDestroyed(true);
                    visualData.addOldData(substance);
                    player.addSubstance(substance.getWeight(), substance.getVolume());
                }
            }
        }

        //System.out.println("Collision CollisionPTOS Time: "+(System.currentTimeMillis()-deltaTimer));
    }

    public void CollisionPlayerToWorld(List<Player> players, World world) {

        for (Player player : players) {
            tempPToW.set(player.getPos());
            if (tempPToW.len() > world.getSize() - player.getSize()) {
                player.getPos().set(tempPToW.nor().scl(world.getSize() - player.getSize()));
            }
        }
    }
}