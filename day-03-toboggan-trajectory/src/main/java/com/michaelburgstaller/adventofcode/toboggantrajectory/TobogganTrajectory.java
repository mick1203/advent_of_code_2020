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

    private static void driveThreeRightAndOneDown(List<String[]> forest) {
        var coordinates = calculatePath(forest.size(), forest.get(0).length, 3, 1);
        var trees = coordinates.stream().map(coordinate -> forest.get(coordinate.y)[coordinate.x]).filter(cell -> cell.equalsIgnoreCase("#")).count();
        System.out.println("On your way down, you encountered a total of '" + trees + "' trees.");
    }

    public static void main(String[] args) {
        var forest = getLineStream().map(line -> line.split("")).toList();

        driveThreeRightAndOneDown(forest);
    }

}
