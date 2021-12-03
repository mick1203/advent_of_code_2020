package com.michaelburgstaller.adventofcode.passportprocessing;

import com.michaelburgstaller.adventofcode.Exercise;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PassportProcessing extends Exercise {

    private static Stream<String> bufferPassports(Stream<String> passportDataStream) {
        var passportData = passportDataStream.toList();
        var passports = new ArrayList<String>();
        var passport = new StringBuilder();

        for (var line : passportData) {
            if (line.isBlank()) {
                passports.add(passport.toString());
                passport.setLength(0);
                continue;
            }
            passport.append(" " + line);
        }
        passports.add(passport.toString());

        return passports.stream().map(p -> p.strip());
    }

    private enum PassportFieldType {
        BIRTH_YEAR, ISSUE_YEAR, EXPIRATION_YEAR, HEIGHT, HAIR_COLOR, EYE_COLOR, PASSPORT_ID, COUNTRY_ID;

        public static PassportFieldType parse(String rawValue) {
            return switch (rawValue) {
                case "byr" -> BIRTH_YEAR;
                case "iyr" -> ISSUE_YEAR;
                case "eyr" -> EXPIRATION_YEAR;
                case "hgt" -> HEIGHT;
                case "hcl" -> HAIR_COLOR;
                case "ecl" -> EYE_COLOR;
                case "pid" -> PASSPORT_ID;
                case "cid" -> COUNTRY_ID;
                default -> throw new IllegalArgumentException("'" + rawValue + "' is not a valid passport field type!");
            };
        }
    }

    private static class PassportField {
        public PassportFieldType fieldType;
        public String value;

        public PassportField(PassportFieldType fieldType, String value) {
            this.fieldType = fieldType;
            this.value = value;
        }

        private static PassportField parse(String rawValue) {
            var passportFieldTokens = rawValue.split(":");
            var fieldType = PassportFieldType.parse(passportFieldTokens[0]);
            var value = passportFieldTokens[1];
            return new PassportField(fieldType, value);
        }
    }

    private static class Passport {
        public List<PassportField> fields;

        public Passport(List<PassportField> fields) {
            this.fields = fields;
        }

        public static Passport parse(String rawValue) {
            var passportTokens = rawValue.split(" ");
            var fields = Arrays.stream(passportTokens).map(PassportField::parse).filter(Objects::nonNull).toList();
            return new Passport(fields);
        }
    }

    private static boolean isYearBetween(String rawValue, Integer minimum, Integer maximum) {
        return (Pattern.compile("^[0-9]{4}$").matcher(rawValue).find()
                && Integer.parseInt(rawValue) >= minimum && Integer.parseInt(rawValue) <= maximum);
    }

    private static boolean isHeightValid(String rawValue) {
        var unit = rawValue.substring(rawValue.length() - 2);
        var height = rawValue.substring(0, rawValue.length() - 2);

        return (unit.equalsIgnoreCase("cm") && Integer.parseInt(height) >= 150
                && Integer.parseInt(height) <= 193) || (unit.equalsIgnoreCase("in") && Integer.parseInt(height) >= 59
                && Integer.parseInt(height) <= 76);
    }

    private static boolean passportFieldIsValid(PassportField passportField) {
        return switch (passportField.fieldType) {
            case BIRTH_YEAR -> isYearBetween(passportField.value, 1920, 2002);
            case ISSUE_YEAR -> isYearBetween(passportField.value, 2010, 2020);
            case EXPIRATION_YEAR -> isYearBetween(passportField.value, 2020, 2030);
            case HEIGHT -> isHeightValid(passportField.value);
            case HAIR_COLOR -> Pattern.compile("^#[0-9,a-f]{6}$").matcher(passportField.value).find();
            case EYE_COLOR -> Pattern.compile("^(amb|blu|brn|gry|grn|hzl|oth)$").matcher(passportField.value).find();
            case PASSPORT_ID -> Pattern.compile("^[0-9]{9}$").matcher(passportField.value).find();
            default -> true;
        };
    }

    private static boolean passportHasValidFieldValues(Passport passport) {
        for (var passportField : passport.fields) {
            if (!passportFieldIsValid(passportField)) {
                return false;
            }
        }
        return true;
    }

    private static boolean passportHasRequiredFields(Passport passport, Set<PassportFieldType> requiredFieldTypes) {
        var passportFieldTypes = passport.fields.stream().map(field -> field.fieldType).collect(Collectors.toSet());
        var missingFieldTypes = new HashSet<>(requiredFieldTypes);
        missingFieldTypes.removeAll(passportFieldTypes);
        return missingFieldTypes.isEmpty();
    }

    private static void checkForPassportWithAllRequiredFields(List<Passport> passports) {
        var allPassportFields = new HashSet<>(List.of(PassportFieldType.values()));
        var requiredPassportFields = new HashSet<>(allPassportFields);
        requiredPassportFields.remove(PassportFieldType.COUNTRY_ID);
        var validPassports = passports.stream()
                .filter(passport -> passportHasRequiredFields(passport, requiredPassportFields))
                .collect(Collectors.toList());

        System.out.println("There were a total of '" + validPassports.size() + "' passports with all required fields in the input data!");
    }

    private static void checkForPassportWithAllRequiredFieldsAndValidFieldValues(List<Passport> passports) {
        var allPassportFields = new HashSet<>(List.of(PassportFieldType.values()));
        var requiredPassportFields = new HashSet<>(allPassportFields);
        requiredPassportFields.remove(PassportFieldType.COUNTRY_ID);
        var validPassports = passports.stream()
                .filter(passport -> passportHasRequiredFields(passport, requiredPassportFields))
                .filter(passport -> passportHasValidFieldValues(passport))
                .collect(Collectors.toList());

        System.out.println("There were a total of '" + validPassports.size() + "' passports with all fields and valid field values in the input data!");
    }

    public static void main(String[] args) {
        var passports = bufferPassports(getLineStream()).map(Passport::parse).toList();

        checkForPassportWithAllRequiredFields(passports);
        checkForPassportWithAllRequiredFieldsAndValidFieldValues(passports);
    }

}
