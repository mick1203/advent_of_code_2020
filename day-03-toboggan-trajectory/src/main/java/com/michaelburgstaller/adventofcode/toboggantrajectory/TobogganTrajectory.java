package com.michaelburgstaller.adventofcode.toboggantrajectory;

import com.michaelburgstaller.adventofcode.Exercise;

import java.util.ArrayList;
import java.util.List;

public class TobogganTrajectory extends Exercise {

    private static class Coordinate {
        public Integer x;
        public Integer y;

        public Coordinate(Integer x, Integer y) {
            this.x = x;
            this.y = y;
        }
    }

    private static List<Coordinate> calculatePath(Integer height, Integer width, Integer horizontal, Integer vertical) {
        var coordinates = new ArrayList<Coordinate>();

        var currentX = 0;
        var currentY = 0;
        coordinates.add(new Coordinate(currentX, currentY));

        while (currentY < (height - 1)) {
            currentX = (currentX + horizontal) % width;
            currentY += vertical;
            coordinates.add(new Coordinate(currentX, currentY));
        }

        return coordinates;
    }

    private static Long calculateTreeCollisions(List<String[]> forest, Integer horizontal, Integer vertical) {
        var coordinates = calculatePath(forest.size(), forest.get(0).length, horizontal, vertical);
        return coordinates.stream().map(coordinate -> forest.get(coordinate.y)[coordinate.x]).filter(cell -> cell.equalsIgnoreCase("#")).count();
    }

    private static void driveThreeRightAndOneDown(List<String[]> forest) {
        var trees = calculateTreeCollisions(forest, 3, 1);
        System.out.println("On your way down, you encountered a total of '" + trees + "' trees.");
    }

    private static void driveAllGivenConfigurations(List<String[]> forest) {
        var r1d1 = calculateTreeCollisions(forest, 1, 1);
        var r3d1 = calculateTreeCollisions(forest, 3, 1);
        var r5d1 = calculateTreeCollisions(forest, 5, 1);
        var r7d1 = calculateTreeCollisions(forest, 7, 1);
        var r1d2 = calculateTreeCollisions(forest, 1, 2);
        var collisions = r1d1 * r3d1 * r5d1 * r7d1 * r1d2;

        System.out.println("WIth all configurations, you would have had '" + collisions + "' collisions with a tree");
    }

    public static void main(String[] args) {
        var forest = getLineStream().map(line -> line.split("")).toList();

        driveThreeRightAndOneDown(forest);
        driveAllGivenConfigurations(forest);
    }

}
