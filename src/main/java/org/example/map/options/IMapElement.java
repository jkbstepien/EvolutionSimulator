package org.example.map.options;

import org.example.utils.Vector2d;

public interface IMapElement {
    Vector2d getPosition();
    String toString();
    String getSourceAddress();
}
