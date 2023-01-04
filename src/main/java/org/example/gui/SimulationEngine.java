package org.example.gui;


import org.example.map.WorldMap;
import org.example.map.objects.animal.Animal;
import org.example.map.objects.animal.genes.Genes;

import java.util.ArrayList;
import java.util.List;

public class SimulationEngine implements Runnable {
    private final WorldMap map;

    private int day = 0;

    private final List<Animal> animals = new ArrayList<>();

    private int numberOfAllAnimals;

    private int numberOfAllPlants;

    private int freeFields;

    private List<Genes> mostPopularGenotypes;

    private double averageEnergy;

    private int averageDeadsLifespan;

    private final Object lock = new Object();

    public SimulationEngine(WorldMap map){
        this.map = map;
    }

    @Override
    public void run() {
        // rewrite and create methods for map
//        while(true){
//            animals.forEach(Animal::removeIfDied);
//            animals.stream().filter(Animal::isAlive).forEach(animal -> {
//                animal.changeOrientation();
//                animal.move();
//            });
//            map.eatPlants();
//            map.breeding();
//            map.growPlants();
//            day++;
//        }

    }



//    protected IWorldMap map;
//    protected boolean magicON;
//    protected int magicTricksLeft = 0;
//
//    protected int dayCounter = -1;
//
//    ArrayList<IEngineRefreshObserver> engineRefreshObservers = new ArrayList<>();
//
//    public SimulationEngine(IWorldMap map, boolean magicON) {
//        this.map = map;
//        this.magicON = magicON;
//        if (magicON) {
//            this.magicTricksLeft = 3;
//        }
//
//    }
//
//    public void run() {
//        while (this.map.countAnimals() > 0) {
//
//            dayCounter++;
//
//            if (this.magicTricksLeft > 0 && this.map.countAnimals() == 5) {
//                magic();
//                this.magicTricksLeft--;
//            }
//
//            this.map.moveAllAnimals();
//            this.map.eatPlants();
//            this.map.coupling();
//            this.map.growPlants(1, 1);
//
//            for (IEngineRefreshObserver observer : engineRefreshObservers) {
//                observer.refreshNeeded();
//            }
//            try {
//                Thread.sleep(300);
//            } catch (InterruptedException e) {
//                return;
//            }
//
//        }
//    }
//
//    private void magic() {
//        var allAnimals = this.map.getAllAnimals();
//
//        var freeSpace = this.map.getFreeSpace();
//
//        Random random = new Random();
//        for (var animal : allAnimals) {
//            if (freeSpace.size() > 0) {
//                var i = random.nextInt(freeSpace.size());
//                this.map.place(new Animal(this.map, this.map.getEnergyAtStart(), freeSpace.get(i), animal.genotype));
//                freeSpace.remove(i);
//            }
//        }
//    }
//
//    public void addEngineObserver(IEngineRefreshObserver observer) {
//        engineRefreshObservers.add(observer);
//    }
//
//    public int getDayCounter() {
//        return dayCounter;
//    }
}
