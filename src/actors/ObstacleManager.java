package actors;

import game.MooseGame;

import java.awt.*;
import java.util.ArrayList;

public class ObstacleManager {

    private static ArrayList<Obstacle> mooseObstacles  = new ArrayList<>();
//    private static ArrayList<Obstacle> vehicleObstacles = new ArrayList<>();
//    private static ArrayList<Obstacle> staticObstacles = new ArrayList<>();

    public ObstacleManager(MooseGame canvas) {
        for (int i = 0; i < 1; i++) {
            mooseObstacles.add(new MooseObstacle(canvas));
//            vehicleObstacles.add(new VehicleObstacle(canvas));
//            staticObstacles.add(new StaticObstacle(canvas));
        }

        mooseObstacles.get(0).spawn();
    }

    public void update(){
        for (int i = 0; i < mooseObstacles.size(); i++) {
            mooseObstacles.get(i).update();
        }
    }

    public void paint(Graphics g){
        for (int i = 0; i < mooseObstacles.size(); i++) {
            mooseObstacles.get(i).paint(g);
        }
    }


}
