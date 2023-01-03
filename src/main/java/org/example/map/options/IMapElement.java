package org.example.map.options;

import org.example.utils.Vector2d;

public interface IMapElement {
    public Vector2d getPosition();
    public String toString();
    String getSourceAddress();
}
