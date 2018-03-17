package ru.projectjanus.client;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import ru.projectjanus.client.visual.VisualData;
import ru.projectjanus.client.visual.VisualObject;
import ru.projectjanus.client.visual.VisualPool;


/**
 * Created by raultaylor.
 */

public class VisualController {

    private VisualPool visualPool;

    private VisualData visualData;
    private ArrayList<Linkable> newLinks;

    private static TextureAtlas worldAtlas;
    private static TextureAtlas solarisAtlas;

    private static HashMap<String, TextureAtlas> atlasMap;

    private long deltaTimer;

    static {
        worldAtlas = new TextureAtlas("worldAtlas.tpack");
        solarisAtlas = new TextureAtlas("solarisAtlas.tpack");
        atlasMap = new HashMap<String, TextureAtlas>();
        atlasMap.put("world", worldAtlas);
        atlasMap.put("player", solarisAtlas);
        atlasMap.put("heavy_subst", solarisAtlas);
        atlasMap.put("light_subst", solarisAtlas);
        atlasMap.put("medium_subst", solarisAtlas);
    }

    public VisualController(VisualData visualData) {
        visualPool = new VisualPool();
        this.visualData = visualData;
        newLinks = new ArrayList<Linkable>();
    }

    public List<VisualObject> getActiveObjects() {
        updateObjects();
        return visualPool.getActiveObjects();
    }

    public VisualObject findAndGetPlayer() {
        VisualObject playerVO = null;
        for (VisualObject obj : visualPool.getActiveObjects()) {
            if (obj.getLink().getNameType() == "player") {
                playerVO = obj;
                break;
            }
        }
        return playerVO;
    }

    public void updateObjects() {

        deltaTimer = System.currentTimeMillis();

        for (Linkable link : visualData.getOldData()) {
            for (VisualObject visualObject : visualPool.getActiveObjects()) {
                if (visualObject.getLink() == link) {
                    visualObject.setDestroyed(true);
                    break;
                }
            }
        }

        visualPool.freeAllDestroyedActiveObjects();
        //System.out.println("VO: " + visualPool.getActiveObjects().size());

        boolean isFinded;
        newLinks.clear();
        for (Linkable data : visualData.getData()) {
            isFinded = false;
            int i = 0;
            for (VisualObject visualObject : visualPool.getActiveObjects()) {
                if (visualObject.getLink() == data) {
                    isFinded = true;
                    break;
                }
                i++;
            }
            if (!isFinded || i >= visualPool.getActiveObjects().size()) {
                newLinks.add(data);
            }
        }
        for (VisualObject visualObject : visualPool.getActiveObjects()) {
            isFinded = false;
            int i = 0;
            for (Linkable data : visualData.getData()) {
                if (data == visualObject.getLink()) {
                    isFinded = true;
                    break;
                }
                i++;
            }
            if (!isFinded || i >= visualData.getData().size()) {
                visualObject.setDestroyed(true);
            }

        }
        visualPool.freeAllDestroyedActiveObjects();
        if (!newLinks.isEmpty()) {
            for (Linkable link : newLinks) {
                set(link);
            }
        }

        for (VisualObject obj : visualPool.getActiveObjects()) {
            obj.update();
        }
        //System.out.println("VC: " + visualPool.getActiveObjects().size() + " new: " + newLinks.size());

        //System.out.println("VC updateObj Time: "+(System.currentTimeMillis()-deltaTimer));

    }

    public void addOnCamera(Camera camera) {
        camera.cleanVisualObjects();
        for (VisualObject obj : getActiveObjects()) {
            camera.addVisualObject(obj);
        }
    }

    private void set(Linkable link) {
        VisualObject visualObject = visualPool.obtain();
        visualObject.set(link, atlasMap.get(link.getNameType()));
    }
}
