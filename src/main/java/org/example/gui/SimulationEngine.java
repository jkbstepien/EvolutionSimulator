package org.example.gui;


import java.util.ArrayList;
import java.util.Random;

public class SimulationEngine implements Runnable {
    @Override
    public void run() {

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
