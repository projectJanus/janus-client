package ru.projectjanus.client.pool;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by raultaylor.
 */

public abstract class Pool<T extends Exterminable> {
    // список активных объектов
    protected final List<T> activeObjects = new LinkedList<T>();

    // список свободных объектов
    protected final List<T> freeObjects = new ArrayList<T>();

    protected abstract T newObject();

    public T obtain() {
        T object;
        if (freeObjects.isEmpty()) {
            object = newObject();
        } else {
            object = freeObjects.remove(freeObjects.size() - 1);
        }
        activeObjects.add(object);
        debugLog();
        return object;
    }

    public void freeAllDestroyedActiveObjects() {
        for (int i = 0; i < activeObjects.size(); i++) {
            T object = activeObjects.get(i);
            if (object.isDestroyed()) {
                free(object);
                i--;
                object.setDestroyed(false);
            }
        }
    }

    public void freeAllActiveObjects() {
        freeObjects.addAll(activeObjects);
        activeObjects.clear();
    }

    public void free(T object) {
        if (!activeObjects.remove(object)) {
            throw new RuntimeException("Попытка удаления несуществующего объекта");
        }
        freeObjects.add(object);
    }

    public void dispose() {
        activeObjects.clear();
        freeObjects.clear();
    }

    protected  void debugLog() {

    }

    public List<T> getActiveObjects() {
        return activeObjects;
    }
}
