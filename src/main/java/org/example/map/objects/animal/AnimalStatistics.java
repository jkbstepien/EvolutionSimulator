package org.example.map.objects.animal;

import org.example.utils.MapDirection;

import java.util.List;

public record AnimalStatistics(
        List<Integer> genome,
        MapDirection direction,
        int energy,
        int plantsEaten,
        int children,
        int age
) {
}
