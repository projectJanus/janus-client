package ru.projectjanus.client.visual;

import java.util.LinkedList;
import java.util.List;

import ru.projectjanus.client.Linkable;

/**
 * Created by raultaylor.
 */
public class VisualData {
    private LinkedList<Linkable> allVisualData;
    private LinkedList<Linkable> oldData;

    public VisualData() {
        allVisualData = new LinkedList<Linkable>();
        oldData = new LinkedList<Linkable>();
    }

    public void add(Linkable obj) {
        allVisualData.add(obj);
    }

    public <T extends Linkable> void addAll(List<T> list) {
        allVisualData.addAll(list);
    }

    public void addOldData(Linkable obj) {
        oldData.add(obj);
    }

    public void cleanAll() {
        allVisualData.clear();
    }

    public void cleanAllOld() {
        oldData.clear();
    }

    public List<Linkable> getData() {
        return allVisualData;
    }

    public List<Linkable> getOldData() {
        return oldData;
    }
}
