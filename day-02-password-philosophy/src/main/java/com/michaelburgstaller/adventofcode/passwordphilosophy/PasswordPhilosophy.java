package com.michaelburgstaller.adventofcode.passwordphilosophy;

import com.michaelburgstaller.adventofcode.Exercise;

import java.util.Arrays;
import java.util.List;

public class PasswordPhilosophy extends Exercise {

    private static class PasswordPolicy {
        public Integer lhs;
        public Integer rhs;
        public String character;

        public PasswordPolicy(Integer lhs, Integer rhs, String character) {
            this.lhs = lhs;
            this.rhs = rhs;
            this.character = character;
        }

        public static PasswordPolicy parse(String rawValue) {
            var policyTokens = rawValue.split(" ");
            var rangeTokens = policyTokens[0].split("-");
            var lhs = Integer.parseInt(rangeTokens[0]);
            var rhs = Integer.parseInt(rangeTokens[1]);
            var character = policyTokens[1];
            return new PasswordPolicy(lhs, rhs, character);
        }
    }

    private static class PasswordEntry {
        public PasswordPolicy policy;
        public String password;

        public PasswordEntry(PasswordPolicy policy, String password) {
            this.policy = policy;
            this.password = password;
        }

        public static PasswordEntry parse(String rawValue) {
            var entryTokens = rawValue.split(":");
            var policy = PasswordPolicy.parse(entryTokens[0]);
            var password = entryTokens[1].strip();
            return new PasswordEntry(policy, password);
        }
    }

    private static boolean passwordCharacterOccurrencesAreValid(PasswordEntry passwordEntry) {
        var policy = passwordEntry.policy;
        var password = passwordEntry.password.split("");
        var occurrences = Arrays.stream(password).filter(character -> character.compareTo(policy.character) == 0).count();
        return occurrences >= policy.lhs && occurrences <= policy.rhs;
    }

    private static boolean passwordCharacterOccurAtExactlyOneValidLocation(PasswordEntry passwordEntry) {
        var policy = passwordEntry.policy;
        var password = passwordEntry.password.split("");
        return password[policy.lhs - 1].equalsIgnoreCase(policy.character) ^ password[policy.rhs - 1].equalsIgnoreCase(policy.character);
    }

    private static void findPasswordEntriesWithValidAmountOfCharacters(List<PasswordEntry> passwordEntries) {
        var validPasswordEntries = passwordEntries.stream().filter(PasswordPhilosophy::passwordCharacterOccurrencesAreValid);
        System.out.println("There were '" + validPasswordEntries.count() + "' valid password from a total of '" + passwordEntries.size() + "'");
    }

    private static void findPasswordEntriesWithCorrectCharacterPositions(List<PasswordEntry> passwordEntries) {
        var validPasswordEntries = passwordEntries.stream().filter(PasswordPhilosophy::passwordCharacterOccurAtExactlyOneValidLocation);
        System.out.println("There were '" + validPasswordEntries.count() + "' valid password from a total of '" + passwordEntries.size() + "'");
    }

    public static void main(String[] args) {
        var passwordEntries = getLineStream().map(PasswordEntry::parse).toList();

        findPasswordEntriesWithValidAmountOfCharacters(passwordEntries);
        findPasswordEntriesWithCorrectCharacterPositions(passwordEntries);
    }

}
