package com.michaelburgstaller.adventofcode.passwordphilosophy;

import com.michaelburgstaller.adventofcode.Exercise;

import java.util.Arrays;
import java.util.List;

public class PasswordPhilosophy extends Exercise {

    private static class PasswordPolicy {
        public Integer minimum;
        public Integer maximum;
        public String character;

        public PasswordPolicy(Integer minimum, Integer maximum, String character) {
            this.minimum = minimum;
            this.maximum = maximum;
            this.character = character;
        }

        public static PasswordPolicy parse(String rawValue) {
            var policyTokens = rawValue.split(" ");
            var rangeTokens = policyTokens[0].split("-");
            var minimum = Integer.parseInt(rangeTokens[0]);
            var maximum = Integer.parseInt(rangeTokens[1]);
            var character = policyTokens[1];
            return new PasswordPolicy(minimum, maximum, character);
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

    private static boolean passwordEntryIsValid(PasswordEntry passwordEntry) {
        var policy = passwordEntry.policy;
        var password = passwordEntry.password.split("");
        var occurrences = Arrays.stream(password).filter(character -> character.compareTo(policy.character) == 0).count();
        return occurrences >= policy.minimum && occurrences <= policy.maximum;
    }

    private static void findValidPasswords(List<PasswordEntry> passwordEntries) {
        var validPasswordEntries = passwordEntries.stream().filter(PasswordPhilosophy::passwordEntryIsValid);
        System.out.println("There were '" + validPasswordEntries.count() + "' valid password from a total of '" + passwordEntries.size() + "'");
    }

    public static void main(String[] args) {
        var passwordEntries = getLineStream().map(PasswordEntry::parse).toList();

        findValidPasswords(passwordEntries);

    }

}
