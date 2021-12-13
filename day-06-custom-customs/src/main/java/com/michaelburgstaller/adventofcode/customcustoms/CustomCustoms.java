package com.michaelburgstaller.adventofcode.customcustoms;

import com.michaelburgstaller.adventofcode.common.Exercise;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomCustoms extends Exercise {

    private static class Group {
        public Map<String, Integer> positivelyAnsweredQuestions;
        public Integer groupSize;

        public Group(Map<String, Integer> positivelyAnsweredQuestions, Integer groupSize) {
            this.positivelyAnsweredQuestions = positivelyAnsweredQuestions;
            this.groupSize = groupSize;
        }

        public static Group parse(List<String> rawValues) {
            var values = rawValues.stream()
                    .map(String::strip)
                    .flatMap(value -> Arrays.stream(value.split("")))
                    .sorted();

            var positivelyAnsweredQuestions = new HashMap<String, Integer>();
            values.forEach(value -> positivelyAnsweredQuestions.put(value, positivelyAnsweredQuestions.getOrDefault(value, 0) + 1));

            return new Group(positivelyAnsweredQuestions, rawValues.size());
        }
    }

    private static void countDistinctPositivelyAnsweredQuestions(List<Group> groups) {
        var distincPositivelyAnsweredQuestions = 0;

        for (var group : groups) {
            distincPositivelyAnsweredQuestions += group.positivelyAnsweredQuestions.keySet().size();
        }

        System.out.println("There are a total of '" + distincPositivelyAnsweredQuestions + "' distinct answers for all the groups!");
    }

    private static void countCommonPositivelyAnsweredQuestions(List<Group> groups) {
        var commonPositivelyAnsweredQuestions = 0;

        for (var group : groups) {
            for (var entry : group.positivelyAnsweredQuestions.entrySet()) {
                if (entry.getValue().compareTo(group.groupSize) == 0) {
                    commonPositivelyAnsweredQuestions++;
                }
            }
        }

        System.out.println("There are a total of '" + commonPositivelyAnsweredQuestions + "' common answers for all the groups!");
    }

    public static void main(String[] args) {
        var groups = getBufferedLineStream(getLineStream(), "").map(Group::parse).toList();

        countDistinctPositivelyAnsweredQuestions(groups);
        countCommonPositivelyAnsweredQuestions(groups);
    }
}
