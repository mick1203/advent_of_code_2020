package com.michaelburgstaller.adventofcode.binaryboarding;

import com.michaelburgstaller.adventofcode.common.Exercise;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BinaryBoarding extends Exercise {

    private static class BoardingPass {
        public Integer seatId;
        public Integer row;
        public Integer column;

        public BoardingPass(Integer row, Integer column) {
            this.row = row;
            this.column = column;
            this.seatId = row * 8 + column;
        }

        public static BoardingPass parse(String rawValue) {
            var binaryRepresentation = rawValue
                    .replaceAll("(F|L)", "0")
                    .replaceAll("(B|R)", "1");

            var rowPart = binaryRepresentation.substring(0, rawValue.length() - 3);
            var columnPart = binaryRepresentation.substring(rawValue.length() - 3);

            var row = Integer.parseInt(rowPart, 2);
            var seat = Integer.parseInt(columnPart, 2);

            return new BoardingPass(row, seat);
        }
    }

    private static void findBoardingPassWithHighestSeatId(List<BoardingPass> boardingPasses) {
        boardingPasses.sort(Comparator.comparing(boardingPass -> boardingPass.seatId));
        var boardingPassWithHighestSeatId = boardingPasses.get(0);

        System.out.println("The highest SeatId on a boarding pass is '" + boardingPassWithHighestSeatId.seatId + "'");
    }

    private static void findOwnBoardingPass(List<BoardingPass> boardingPasses) {
        boardingPasses.sort(Comparator.comparing(boardingPass -> boardingPass.seatId));

        var ownBoardingPass = new BoardingPass(0, 0);
        for (var i = 0; i < boardingPasses.size() - 1; i++) {
            var left = boardingPasses.get(i);
            var right = boardingPasses.get(i + 1);
            if (Math.abs(left.seatId - right.seatId) == 2) {
                ownBoardingPass = new BoardingPass(left.row, left.column + 1);
            }
        }

        System.out.println("My own boarding pass has seat id '" + ownBoardingPass.seatId + "'");
    }

    public static void main(String[] args) {
        var boardingPasses = new ArrayList<>(getLineStream().map(BoardingPass::parse).toList());

        findBoardingPassWithHighestSeatId(boardingPasses);
        findOwnBoardingPass(boardingPasses);
    }

}
