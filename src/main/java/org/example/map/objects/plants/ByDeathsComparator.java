package org.example.map.objects.plants;

import org.example.utils.Vector2d;

import java.util.Comparator;
import java.util.Map;

public class ByDeathsComparator implements Comparator<Vector2d> {
    private final Map<Vector2d, Integer> deaths;

    public ByDeathsComparator(Map<Vector2d, Integer> deaths){
        this.deaths = deaths;
    }

    @Override
    public int compare(Vector2d a, Vector2d b){
        long result = deaths.get(a) - deaths.get(b);
        if(result > 0) {
            return 1;
        }
        else if(result < 0){
            return -1;
        }
        return 0;
    }
}
