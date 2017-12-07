package com.robinhowlett.chartparser.charts.pdf;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses the race conditions text to determine, if possible, the age, gender, and breeding location
 * restrictions imposed for the race
 */
public class RaceRestrictions {

    private static final Pattern PARENTHESES_TEXT = Pattern.compile("(\\s*\\([^)]+\\)\\s*)");
    private static final Pattern RESTRICTIONS_CODE =
            Pattern.compile("\\(\\s*([c])\\s*\\)|\\(\\s*([s])\\s*\\)|\\((s?nw[^)]+)\\)");

    /*
    2 year olds
    3 years old & older
    3 & up
    4yo
    3 & 4 year olds
    3 4 & 5 year olds
    4 & 5 & 6 year olds
    2yrs
    3yos
    4yrs & older
    4 5 6 & 7 year olds
     */
    private static final String AGES = "((\\d?\\d)(\\s&?\\s?(\\d?\\d))?(\\s&?\\s?(\\d?\\d))?" +
            "(\\s&?\\s?" +
            "(\\d?\\d))?(yrs?|yos?| years? olds?)?)( & (upwards?|up|olders?))?";

    /*
    colts geldings & horses
    colts geldings & fillies
    colts geldings & mares
    colts & geldings
    fillies & mares
    fillies
    colts
    colts & fillies
     */
    private static final String SEXES =
            "(colts|fillies|mares)( (geldings))?( & (geldings|mares|horses|fillies))?";

    private static final Pattern AGES_PATTERN = Pattern.compile(AGES);
    private static final Pattern SEXES_PATTERN = Pattern.compile(SEXES);

    private static final Pattern SEXES_THEN_AGES = Pattern.compile(
            "^(.*?for.+?)?.*?" + SEXES + "[^\\d]*(" + AGES + ")?.*");
    private static final Pattern AGES_THEN_SEXES = Pattern.compile(
            "^(.*?for.+?)?" + AGES + ".+?(?=" + SEXES + "|$).*");

    private final String code;
    private final Integer minAge;
    private final Integer maxAge;
    /*
     a bitwise-style value to store the gender restrictions that apply

     1 = colts
     2 = geldings
     4 = horses
     8 = fillies
     16 = mares
     31 = all
      */
    private final int sex;
    private final boolean femaleOnly;
    private final boolean stateBred;

    RaceRestrictions(RaceRestrictionCodes raceRestrictionCodes, Integer minAge, Integer maxAge,
            int sex) {
        this((raceRestrictionCodes != null ? raceRestrictionCodes.getCode() : null),
                minAge, maxAge, sex,
                (raceRestrictionCodes != null && raceRestrictionCodes.isStateBred()));
    }

    RaceRestrictions(String code, Integer minAge, Integer maxAge, int sex, boolean stateBred) {
        this.code = code;
        this.minAge = minAge;
        // if no specified max, then assume same as the minimum
        this.maxAge = (maxAge != null ? maxAge : minAge);
        this.sex = sex;
        this.femaleOnly = (sex % 8 == 0); // 8 = fillies, 16 = mares, 24 = fillies & mares
        this.stateBred = stateBred;
    }

    public static RaceRestrictions parse(String raceConditionsText) {
        String text = cleanUpText(raceConditionsText);

        // 9. extract parenthesis and pattern match for (C) or (S) or (SNW...) or (NW1...)
        Matcher parenMatcher = PARENTHESES_TEXT.matcher(text);
        List<String> textInParentheses = new ArrayList<>();
        while (parenMatcher.find()) {
            textInParentheses.add(parenMatcher.group());
        }

        // 10. identify the short race restriction code and whether the race was for state-bred
        // horses
        RaceRestrictionCodes raceRestrictionCodes = parseRestrictionsCode(textInParentheses);

        // 11. remove the parentheses-contained texts from the main race conditions text
        for (String match : textInParentheses) {
            text = text.replaceAll(Pattern.quote(match), " ").trim();
        }

        // 12. attempt to identify multiple conditions e.g. 3yo fillies or 4yo mares
        String[] conditions = text.split("\\bor\\b");

        RaceRestrictions conditionRestrictions, raceRestrictions = null;
        for (String condition : conditions) {
            if (condition == null || condition.isEmpty()) {
                continue;
            }

            // 13. check whether the age restriction comes first or the sex restriction
            AgeSexPattern ageSexPattern = determineAgeSexPatternToUse(condition);

            if (ageSexPattern == null) {
                continue;
            }

            // 14. extract the age- and sex restrictions for this section of conditions
            conditionRestrictions = createRaceRestrictionsFromConditions(condition,
                    raceRestrictionCodes, ageSexPattern);

            if (conditionRestrictions != null) {
                // 15. if condition restrictions were previously identified, merge them together
                if (raceRestrictions != null) {
                    conditionRestrictions = mergeConditionRestrictions(raceRestrictionCodes,
                            conditionRestrictions, raceRestrictions);

                }
                raceRestrictions = conditionRestrictions;
            }
        }

        if (raceRestrictions != null) {
            return raceRestrictions;
        }

        return new RaceRestrictions(raceRestrictionCodes, null, null, 31);
    }

    private static RaceRestrictions mergeConditionRestrictions(
            RaceRestrictionCodes raceRestrictionCodes, RaceRestrictions conditionRestrictions,
            RaceRestrictions raceRestrictions) {
        Integer ageMin;
        if (raceRestrictions.getMinAge() == null) {
            ageMin = conditionRestrictions.getMinAge();
        } else if (conditionRestrictions.getMinAge() == null) {
            ageMin = raceRestrictions.getMinAge();
        } else {
            ageMin = Math.min(raceRestrictions.getMinAge(), conditionRestrictions.getMinAge());
        }

        Integer ageMax = null;
        if (raceRestrictions.getMaxAge() == null) {
            if (conditionRestrictions.getMaxAge() != null) {
                ageMax = (conditionRestrictions.getMaxAge() < 0) ? -1 : null;
            }
        } else if (conditionRestrictions.getMaxAge() == null) {
            if (raceRestrictions.getMaxAge() != null) {
                ageMax = (raceRestrictions.getMaxAge() < 0) ? -1 : null;
            }
        } else {
            if (conditionRestrictions.getMaxAge() != null && raceRestrictions.getMaxAge() != null) {
                if (raceRestrictions.getMaxAge() < 0 || conditionRestrictions.getMaxAge() < 0) {
                    ageMax = -1;
                } else {
                    Math.max(raceRestrictions.getMaxAge(), conditionRestrictions.getMaxAge());
                }
            }
        }

        int sex = (raceRestrictions.getSex() == conditionRestrictions.getSex()) ?
                raceRestrictions.getSex() :
                (raceRestrictions.getSex() + conditionRestrictions.getSex());

        return new RaceRestrictions(raceRestrictionCodes, ageMin, ageMax, sex);
    }

    private static AgeSexPattern determineAgeSexPatternToUse(String text) {
        int agePosition = Integer.MAX_VALUE;
        int sexPosition = Integer.MAX_VALUE;

        Matcher matcher = AGES_PATTERN.matcher(text);
        if (matcher.find()) {
            agePosition = matcher.start();
        }

        matcher = SEXES_PATTERN.matcher(text);
        if (matcher.find()) {
            sexPosition = matcher.start();
        }

        if (agePosition == sexPosition) {
            return null;
        } else {
            return (agePosition < sexPosition ?
                    new AgeSexPattern(11, 3, AGES_THEN_SEXES) :
                    new AgeSexPattern(0, 9, SEXES_THEN_AGES));
        }
    }

    private static RaceRestrictions createRaceRestrictionsFromConditions(String text,
            RaceRestrictionCodes raceRestrictionCodes, AgeSexPattern ageSexPattern) {
        Matcher matcher = ageSexPattern.getPattern().matcher(text);
        if (matcher.matches()) {
            // sexes
            int sex = getBitwiseSexValue(matcher, ageSexPattern.getSexOffset());

            // ages
            String firstAgeGroup = matcher.group(ageSexPattern.getAgeOffset());
            String secondAgeGroup = matcher.group(ageSexPattern.getAgeOffset() + 2);
            String thirdAgeGroup = matcher.group(ageSexPattern.getAgeOffset() + 4);
            String fourthAgeGroup = matcher.group(ageSexPattern.getAgeOffset() + 6);
            String yearsOldGroup = matcher.group(ageSexPattern.getAgeOffset() + 7);
            String andOlderGroup = matcher.group(ageSexPattern.getAgeOffset() + 8);

            if (yearsOldGroup != null || andOlderGroup != null) {
                if (firstAgeGroup != null) {
                    // min age identified, can proceed
                    int ageMin = Integer.parseInt(firstAgeGroup);

                    Integer ageMax = null;
                    if (andOlderGroup != null) {                    // "and upward|older|up"
                        ageMax = -1;
                    } else {
                        if (fourthAgeGroup != null) {
                            ageMax = Integer.parseInt(fourthAgeGroup);
                        } else if (thirdAgeGroup != null) {
                            ageMax = Integer.parseInt(thirdAgeGroup);
                        } else if (secondAgeGroup != null) {
                            ageMax = Integer.parseInt(secondAgeGroup);
                        }
                    }

                    return new RaceRestrictions(raceRestrictionCodes, ageMin, ageMax, sex);
                }
            }
        }

        return null;
    }

    /*
    1 = colts
    2 = geldings
    4 = horses
    8 = fillies
    16 = mares
    31 = open
     */
    private static int getBitwiseSexValue(Matcher matcher, int seed) {
        int sex = 0;
        // sexes
        String firstSexGroup = matcher.group(2 + seed);
        if (firstSexGroup != null) {
            if (firstSexGroup.equals("colts")) {
                sex += 1;
            } else if (firstSexGroup.equals("fillies")) {
                sex += 8;
            } else if (firstSexGroup.equals("mares")) {
                sex += 16;
            }

            String secondSexGroup = matcher.group(4 + seed);
            if (secondSexGroup != null && secondSexGroup.equals("geldings")) {
                sex += 2;
            }

            String thirdSexGroup = matcher.group(6 + seed);
            if (thirdSexGroup != null) {
                if (thirdSexGroup.equals("geldings")) {
                    sex += 2;
                } else if (thirdSexGroup.equals("horses")) {
                    sex += 4;
                } else if (thirdSexGroup.equals("fillies")) {
                    sex += 8;
                } else if (thirdSexGroup.equals("mares")) {
                    sex += 16;
                }
            }
        } else {
            sex += 31;
        }
        return sex;
    }

    private static RaceRestrictionCodes parseRestrictionsCode(List<String> matches) {
        for (String match : matches) {
            Matcher codeMatcher = RESTRICTIONS_CODE.matcher(match);
            if (codeMatcher.find()) {
                if (codeMatcher.group(1) != null) {
                    // complex, compound, combination (?)
                    return new RaceRestrictionCodes("C", false);
                } else if (codeMatcher.group(2) != null) {
                    // state-bred
                    return new RaceRestrictionCodes("S", true);
                } else if (codeMatcher.group(3) != null) {
                    boolean stateBred = false;
                    String group = codeMatcher.group(3);
                    // identify state bred restriction
                    if (group.contains("s")) {
                        stateBred = true;
                    }
                    String code = group.toUpperCase(Locale.US);
                    return new RaceRestrictionCodes(code, stateBred);
                }
            }
        }
        return null;
    }

    static String cleanUpText(String raceConditionsText) {
        return raceConditionsText
                // 1. convert to lowercase
                .toLowerCase()
                // 2. replace edge-case typos
                .replaceAll("\\by-year-olds\\b", "year olds")
                .replaceAll("\\bfillies/mares\\b", "fillies and mares")
                .replaceAll("\\bthre\\b", "three")
                .replaceAll("\\bthreeyear\\b", "three year")
                .replaceAll("\\bttwo\\b", "two")
                .replaceAll("\\bmaresthree\\b", "mares three")
                // 3. replace punctuation with space character
                .replaceAll("[.,:;\\[\\]\\-\"'%+\\\\/*!]", " ")
                // 4. ensure spaces around & (ampersand character) and parentheses
                .replaceAll("&", " & ")
                .replaceAll("\\(\\s*", " (")
                .replaceAll("\\s*\\)", ") ")
                // 5. remove text contained in angle bracket
                .replaceAll("<.+>\\s?", "")
                // 6. fix common typos
                .replaceAll("\\b(fof|f0r|fo|foe|fofor|foor|ffor)\\b", "for")
                .replaceAll("\\b(colt)\\b", "colts")
                .replaceAll("\\b(gelding)\\b", "geldings")
                .replaceAll("\\b(filly|filiies|filles|filllies|filies|fillie|fililies|filliies" +
                        "|fllies|filli\\ses|fillie\\ss)\\b", "fillies")
                .replaceAll("\\b(mare|maress|mareds|marees|amres)\\b", "mares")
                .replaceAll("\\b(yaer|yera|yr|yar|yer|yers)\\b", "years")
                .replaceAll("\\b(and|adn|ands|und|amd|ans|a\\snd|an\\sd)\\b", "&")
                .replaceAll("\\b(oldsa|olda|ols|0lds|onld)\\b", "olds")
                .replaceAll("\\b(up|upaward|uwpard|uward|upwrd|upqward|upwa|upwar)\\b", "upwards")
                // 7. replace numeric words with their digit equivalent
                .replaceAll("\\b(one)\\b", "1")
                .replaceAll("\\b(two)\\b", "2")
                .replaceAll("\\b(three)\\b", "3")
                .replaceAll("\\b(four)\\b", "4")
                .replaceAll("\\b(five)\\b", "5")
                .replaceAll("\\b(six)\\b", "6")
                .replaceAll("\\b(seven)\\b", "7")
                .replaceAll("\\b(eight)\\b", "8")
                .replaceAll("\\b(nine)\\b", "9")
                .replaceAll("\\b(ten)\\b", "10")
                .replaceAll("\\b(eleven)\\b", "11")
                .replaceAll("\\b(twelve)\\b", "12")
                // 8. replace consecutive spaces or tabs with a single space
                .replaceAll("\\s{2,}|\\t{1,}", " ");
    }

    public String getCode() {
        return code;
    }

    public Integer getMinAge() {
        return minAge;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public int getSex() {
        return sex;
    }

    public boolean isFemaleOnly() {
        return femaleOnly;
    }

    public boolean isStateBred() {
        return stateBred;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RaceRestrictions that = (RaceRestrictions) o;

        if (sex != that.sex) return false;
        if (femaleOnly != that.femaleOnly) return false;
        if (stateBred != that.stateBred) return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (minAge != null ? !minAge.equals(that.minAge) : that.minAge != null) return false;
        return maxAge != null ? maxAge.equals(that.maxAge) : that.maxAge == null;
    }

    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + (minAge != null ? minAge.hashCode() : 0);
        result = 31 * result + (maxAge != null ? maxAge.hashCode() : 0);
        result = 31 * result + sex;
        result = 31 * result + (femaleOnly ? 1 : 0);
        result = 31 * result + (stateBred ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RaceRestrictions{" +
                "code='" + code + '\'' +
                ", minAge=" + minAge +
                ", maxAge=" + maxAge +
                ", sex=" + sex +
                ", femaleOnly=" + femaleOnly +
                ", stateBred=" + stateBred +
                '}';
    }

    private static class RaceRestrictionCodes {
        private final String code;
        private final boolean stateBred;

        public RaceRestrictionCodes(String code, boolean stateBred) {
            this.code = code;
            this.stateBred = stateBred;
        }

        public String getCode() {
            return code;
        }

        public boolean isStateBred() {
            return stateBred;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            RaceRestrictionCodes that = (RaceRestrictionCodes) o;

            if (stateBred != that.stateBred) return false;
            return code != null ? code.equals(that.code) : that.code == null;
        }

        @Override
        public int hashCode() {
            int result = code != null ? code.hashCode() : 0;
            result = 31 * result + (stateBred ? 1 : 0);
            return result;
        }

        @Override
        public String toString() {
            return "RaceRestrictionCodes{" +
                    "code='" + code + '\'' +
                    ", stateBred=" + stateBred +
                    '}';
        }
    }

    private static class AgeSexPattern {
        private final int sexOffset;
        private final int ageOffset;
        private final Pattern pattern;

        public AgeSexPattern(int sexOffset, int ageOffset, Pattern pattern) {
            this.sexOffset = sexOffset;
            this.ageOffset = ageOffset;
            this.pattern = pattern;
        }

        public int getSexOffset() {
            return sexOffset;
        }

        public int getAgeOffset() {
            return ageOffset;
        }

        public Pattern getPattern() {
            return pattern;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            AgeSexPattern that = (AgeSexPattern) o;

            if (sexOffset != that.sexOffset) return false;
            if (ageOffset != that.ageOffset) return false;
            return pattern != null ? pattern.equals(that.pattern) : that.pattern == null;
        }

        @Override
        public int hashCode() {
            int result = sexOffset;
            result = 31 * result + ageOffset;
            result = 31 * result + (pattern != null ? pattern.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "AgeSexPattern{" +
                    "sexOffset=" + sexOffset +
                    ", ageOffset=" + ageOffset +
                    ", pattern=" + pattern +
                    '}';
        }
    }
}
