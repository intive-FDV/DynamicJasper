package ar.com.fdvs.dj.domain.entities.container;

import ar.com.fdvs.dj.domain.DJBaseElement;
import ar.com.fdvs.dj.domain.Style;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by mamana on 7/7/14.
 */
public class ElementContainer extends GraphicElement {

    List<GraphicElement> elements = new ArrayList<GraphicElement>();

    public int size() {
        return elements.size();
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }

    public boolean contains(Object o) {
        return elements.contains(o);
    }

    public Iterator<GraphicElement> iterator() {
        return elements.iterator();
    }

    public boolean add(GraphicElement element) {
        return elements.add(element);
    }

    public boolean remove(Object o) {
        return elements.remove(o);
    }

    public GraphicElement[] getElementsArray() {
        return (GraphicElement[]) elements.toArray(new GraphicElement[]{});
    }
}
