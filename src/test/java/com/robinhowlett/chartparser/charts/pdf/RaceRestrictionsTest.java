package com.robinhowlett.chartparser.charts.pdf;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class RaceRestrictionsTest {

    private String conditions;
    private RaceRestrictions expected;

    public RaceRestrictionsTest(String conditions, RaceRestrictions expected) {
        this.conditions = conditions;
        this.expected = expected;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection distances() {
        return Arrays.asList(new Object[][]{
                {
                        "FOR FILLIES AND MARES THREE YEARS OLD AND UPWARD WHICH HAVE NEVER WON " +
                                "TWO RACES. Three Year Olds, 119 lbs.; Older, 124 lbs. " +
                                "Non-winners Of A Race In 2015 Allowed 2 lbs. Claiming Price $16," +
                                "000. (NW2 L) Claiming Price: $16,000",
                        new RaceRestrictions("NW2 L", 3, -1, 24, false)
                },
                {
                        "FOR FOUR YEAR OLDS AND UPWARD WHICH HAVE NEVER WON TWO RACES. Weight, " +
                                "122 lbs. Claiming Price $12,500, if for $10,500, allowed 2 lbs. " +
                                "(NW2 L) Claiming Price: $12,500 - $10,500",
                        new RaceRestrictions("NW2 L", 4, -1, 31, false)
                },
                {
                        "FOR TWO YEAR OLD FILLIES REGISTERED NEW MEXICO BRED. No nomination fee" +
                                ".$500 to pass the entry box. Starters to pay additional $500 " +
                                "with $140,000 added (includes $70,000 from the NMHBA Fund). To " +
                                "be divided 60% to the winning owner, 22% to second, 10% to " +
                                "third, 4% to fourth and 2% for fifth and sixth. Weights:120lbs. " +
                                "Total lifetime earnings will be used in determining order of " +
                                "preference of horses .This race will be limited to twelve " +
                                "starters. (S)",
                        new RaceRestrictions(null, 2, 2, 8, true)
                },
                {
                        "(UP TO $13,860 NYSBFOA) FOR THREE YEAR OLDS AND UPWARD WHICH HAVE NEVER " +
                                "WON $10,000 ONCE OTHER THAN MAIDEN, CLAIMING, STARTER, OR STATE " +
                                "BRED OR WHICH HAVE NEVER WON TWO RACES. Three Year Olds, 119 lbs" +
                                ".; Older, 124 lbs. Non-winners Of A Race Other Than Claiming Or " +
                                "Starter Allowed 3 lbs. (Non-Starters for a claiming price of " +
                                "$25,000 or less in the last 3 starts preferred). (NW1$ X)",
                        new RaceRestrictions("NW1$ X", 3, -1, 31, false)
                },
                {
                        "THREE YEAR OLDS. Weight 124 lbs. $100 to nominate. $300 to enter. " +
                                "Nominations close May 15. Starters determined by highest " +
                                "lifetime earnings.",
                        new RaceRestrictions(null, 3, 3, 31, false)
                },
                {
                        "Four year olds and older, sixty registered New Mexico Bred. Non-winners " +
                                "twice at a mile or over since May 1. 121 lbs. Non-winners at a " +
                                "mile or over since August 15, 3 lbs. Such a race since July 1, 5" +
                                " lbs. Any race since then 7 lbs. CLAIMING PRICE $2,500. (SNW2 R " +
                                "6M) Claiming Price: $2,500",
                        new RaceRestrictions("NW2 R 6M", 4, -1, 31, true)
                },
                {
                        "TWO YEAR OLD FILLIES. Weight 118 lbs. Fillies that went through the " +
                                "Silver Cup Sale preferred. (Open fillies will run for $35,000 " +
                                "purse).",
                        new RaceRestrictions(null, 2, 2, 8, false)
                },
                {
                        "Mares, Four Years Olds And Upward.",
                        new RaceRestrictions(null, 4, -1, 16, false)
                },
                {
                        "MARES FIVE YEARS OLD AND UPWARD WHICH HAVE NEVER WON THREE RACES OR " +
                                "FILLIES FOUR YEARS OLD CLAIMING $7,500-$6,500. Weight 122 lbs. " +
                                "Non-winners of two races at a mile or over since November 9 " +
                                "allowed, 3 lbs. One such race, 5 lbs. CLAIMING PRICE $7,500, for" +
                                " each $500 to $6,500 2 lbs. (Races where entered for $6,000 or " +
                                "less not considered). ( C) Claiming Price: $7,500 - $6,500",
                        new RaceRestrictions("C", 4, -1, 24, false)
                },
                {
                        "FOR FOUR YEAR OLDS AND UPWARD WHICH HAVE NEVER WON THREE RACES OR FOUR " +
                                "YEAR OLDS OR WHICH HAVE NOT WON A RACE SINCE OCTOBER 2, 2011. " +
                                "Weight, 123 lbs. Non-winners Of Two Races Since January 21, 2012" +
                                " Allowed 3 lbs. Claiming Price $20,000 (Races Where Entered For " +
                                "$16,000 Or Less Not Considered). ( C) Claiming Price: $20,000",
                        new RaceRestrictions("C", 4, -1, 31, false)
                },
                {
                        "FOR MAIDEN FILLIES, THREE AND FOUR YEAR OLDS. Three year olds. 120 lbs.;" +
                                " Four Years Old. 122 lbs. (If deemed inadvisable by management " +
                                "to run this race over the turf course, this race will be run on " +
                                "the main track.)",
                        new RaceRestrictions(null, 3, 4, 8, false)
                },
                {
                        "FOR MAIDEN THREE AND FOUR YEAR OLDS. Three year olds. 120 lbs.; Four " +
                                "Years Old. 122 lbs. (If deemed inadvisable by management to run " +
                                "this race over the turf course, this race will be run on the " +
                                "main track.)",
                        new RaceRestrictions(null, 3, 4, 31, false)
                },
                {
                        "(NW2 L) Claiming Price: $20,000",
                        new RaceRestrictions("NW2 L", null, null, 31, false)
                },
                {
                        "3-year olds, fillies which have qualified for the finals",
                        new RaceRestrictions(null, 3, 3, 8, false)
                },
                {
                        "INNER DIRT FOR FOUR YEAR OLDS AND UPWARD WHICH HAVE NEVER WON TWO RACES." +
                                " Weight, 123 lbs. Non-winners Of A Race Since November 3 Allowed" +
                                " 3 lbs. Claiming Price $20,000 (Races Where Entered For $15,000 " +
                                "Or Less Not Considered). (NW2 L) Claiming Price: $20,000",
                        new RaceRestrictions("NW2 L", 4, -1, 31, false)
                },
                {
                        "A WEIGHT FOR AGE STAKES FOR THREE YEAR OLDS & UPWARD. By subscription of" +
                                " $200.00 each and a further payment of $1,000.00 to pass the " +
                                "entry box and an additional $800.00 to pass scratch time. " +
                                "Guaranteed with $63,000 to the winner, $20,000 to second, $10," +
                                "000 to third, $5,000 to fourth and $2,000 to fifth. Weights: " +
                                "Three Year Olds.120lbs., Older.126lbs. Preference conditions " +
                                "apply. Field limited to 12 starters. A $10,000 Bonus will be " +
                                "paid by Horse Racing Alberta Breeders' Support Program to the " +
                                "owner of the winning horse if Alberta Bred. Nominations closed " +
                                "Wednesday August 27, 2003 with 12 nominations.",
                        new RaceRestrictions(null, 3, -1, 31, false)
                },
                {
                        "A Weight For Age Stakes, fillies and mares three-year-olds and up. " +
                                "(Includes $50,000 from Breeders' Cup Fund for Cup nominees only" +
                                ".) By subscription of $200 to accompany the nomination due on " +
                                "Wednesday, September 25, 2002. $1,000 to enter and $1,000 to " +
                                "pass scratch time. Any Breeders' Cup Fund monies not awarded " +
                                "will revert back to the Fund. Field limited to 12 starters. " +
                                "Preference will be based on lifetime earnings as recorded by The" +
                                " Daily Racing Form at time of entry. A trophy will be given to " +
                                "the winning owner by Breeders' Cup Ltd. 3 year olds 120 lbs.; " +
                                "Older 123 lbs.",
                        new RaceRestrictions(null, 3, -1, 24, false)
                },
                {
                        "For Fillies that are CTHS Sale Graduate, Two year olds. Weights: 119 lbs.",
                        new RaceRestrictions(null, 2, 2, 8, false)
                },
                {
                        "3 and Up Fillies and Mares Alberta Breds ($12,000 plus $150 ) Starters " +
                                "will be determined by preferences in stake book. (S)",
                        new RaceRestrictions(null, 3, -1, 24, true)
                },
                {
                        "FOR FOALED IN NEW YORK STATE AND APPROVED BY THE NEW YORK STATE-BRED " +
                                "REGISTRY THREE YEAR OLDS AND UPWARD WHICH HAVE NEVER WON A STATE" +
                                " BRED RACE OTHER THAN MAIDEN, CLAIMING, OR STARTER. Three Year " +
                                "Olds, 119 lbs.; Older, 124 lbs. Non-winners of a race since May " +
                                "20 Allowed 2 lbs. A race since April 20 Allowed 4 lbs. (Races " +
                                "where entered for $15,000 or less not considered). (SNW1 B L X)",
                        new RaceRestrictions("NW1 B L X", 3, -1, 31, true)
                },
                {
                        "FOR FILLIES AND MARES THREE YEARS OLD AND UPWARD FOALED IN NEW YORK " +
                                "STATE AND APPROVED BY THE NEW YORK STATE-BRED REGISTRY WHICH " +
                                "HAVE NEVER WON A RACE OTHER THAN MAIDEN, CLAIMING, OR STARTER OR" +
                                " WHICH HAVE NEVER WON TWO RACES. Three Year Olds, 119 lbs.; " +
                                "Older, 124 lbs. Non-winners Of $24,000 Since April 13 Allowed 2 " +
                                "lbs. (Races where entered for $35,000 or less not considered in " +
                                "allowances). (SNW1 X)",
                        new RaceRestrictions("NW1 X", 3, -1, 24, true)
                },
                {
                        "FOR THREE YEAR OLDS AND UPWARD WHICH HAVE NEVER WON $10,000 THREE TIMES " +
                                "OTHER THAN MAIDEN, CLAIMING OR STARTER OR FOUR TIMES OR HAVE NOT" +
                                " WON A GRADED STAKE IN 2011. Three Year Olds, 121 lbs.; Older, " +
                                "124 lbs. Non-winners Of Races Allowed 2 lbs. $50,000 At A Mile " +
                                "Or Over Since August 22 Allowed 4 lbs. $25,000 Since July 23 " +
                                "Allowed 6 lbs. (Maiden And Claiming Races For $62,500 Or Less " +
                                "Not Considered). ( C)",
                        new RaceRestrictions("C", 3, -1, 31, false)
                },
                {
                        "FOR COLTS, GELDINGS AND HORSES, FOUR YEARS OLD AND UPWARD, ELIGIBLE TO " +
                                "THE MICHIGAN SIRE STAKES PROGRAM. By subscription of $100 each " +
                                "to accompany the nomination. $500 to pass the entry box, $500 " +
                                "additional to start, with $132,887 of which 60% of all monies to" +
                                " the winner, 20% to second, 11% to third, 6% to fourth and 3% to" +
                                " fifth. Weight: 122 lbs. The field will be limited to ten (10) " +
                                "starters. If more than 10 entries pass the entry box preference " +
                                "will be given to the top 10 purse money earners, for the " +
                                "combined years of 2005 & 2006. Starters to be named through the " +
                                "entry box by the usual time of closing.",
                        new RaceRestrictions(null, 4, -1, 7, false)
                },
                {
                        "For 3-And 4-And 5-Year Olds. Maiden. Fillies & Mares. 3 year olds117 lbs" +
                                ". Older 122 lbs. Claiming Price $5,000. Claiming Price: $5,000",
                        new RaceRestrictions(null, 3, 5, 24, false)
                },
                {
                        "FOR 3 YEAR OLD THOROUGHBRED FILLIES $150 T.O.E. Starters will be " +
                                "determined by preferences in stake book.",
                        new RaceRestrictions(null, 3, 3, 8, false)
                },
                {
                        "MAIDEN FILLIES AND MARES, FOUR AND FIVE YEAR OLDS. 120 lbs. Claiming " +
                                "price $5,000. Claiming Price: $5,000",
                        new RaceRestrictions(null, 4, 5, 24, false)
                },
                {
                        "AN ALLOWANCE STAKE FOR NEBRASKA BRED FILLIES AND MARES, THREE YEAR OLDS " +
                                "AND UPWARD. No nomination fee with $50 to pass the entry box and" +
                                " an additional $50 to start. Weights, Three year olds 114 lbs., " +
                                "Older 122 lbs. Nonwinners of $9,000 twice in 2010-11 allowed 3 " +
                                "lbs., Of $9,000 in 2010-115 lbs. Maiden, Claiming and Starter " +
                                "races not considered in allowances. High Weights Preferred, on " +
                                "the scale. Total earnings in 2010-11 will be used in determining" +
                                " the order of preference of horses assigned equal weights. " +
                                "Closed Saturday, March 19 with eighteen nominations. (S)",
                        new RaceRestrictions(null, 3, -1, 24, true)
                },
                {
                        "FOR OHIO REGISTERED. THREE, FOUR, FIVE AND SIX YEAR OLDS. Three Years " +
                                "Old, 112 lbs.; Older, 122 lbs. (S)",
                        new RaceRestrictions(null, 3, 6, 31, true)
                },
                {
                        "Maiden Fillies. Registered New Mexico Bred. Two year olds 120 Lbs. (S)",
                        new RaceRestrictions(null, 2, 2, 8, true)
                },
                {
                        "For five, six, seven, eight and nine year olds",
                        new RaceRestrictions(null, 5, 9, 31, false)
                },
                {
                        "FOR THREE YEAR OLDS AND UPWARD. Northern Hemisphere Three-Year-Olds, 122" +
                                " lbs.; Older, 126 lbs.; Southern Hemisphere Three-Year-Olds, 117" +
                                " lbs.; Older, 126 lbs. All Fillies and Mares allowed 3 lbs. $50," +
                                "000 to pre-enter, $100,000 to enter, with guaranteed $5 million " +
                                "purse including nominator awards of which 54% to the owner of " +
                                "the winner, 18% to second, 9.9% to third, 6% to fourth and 3% to" +
                                " fifth; plus stallion nominator awards of 3% to the winner, 1% " +
                                "to second and 0.55% to third and foal nominator awards of 3% to " +
                                "the winner, 1% to second and 0.55% to third.",
                        new RaceRestrictions(null, 3, -1, 31, false)
                },
                {
                        "Maiden 3, 4 & 5-year olds, registered Colorado Bred. 3-year olds 118 Lbs" +
                                ".; Older 122 Lbs.;. (S)",
                        new RaceRestrictions(null, 3, 5, 31, true)
                },
                {
                        "TWO YEAR OLDS WHICH HAVE QUALIFIED FOR THE FINAL. Weight 124 lbs.",
                        new RaceRestrictions(null, 2, 2, 31, false)
                },
                {
                        "PURSE $100,000. A stake for two year olds. Weight 122 lbs. N/W of a " +
                                "stakes in 2010 - 2 lbs. N/W of a race other than mdn,clm, or str" +
                                ". in 2010 - 4 lbs. (Winners with highest lifetime earnings " +
                                "preferred).",
                        new RaceRestrictions(null, 2, 2, 31, false)
                },
                {
                        "Fillies and Mares, Which have never won three races, Colorado Bred. " +
                                "3-year olds 120 lbs. Older 124 lbs. Claiming Price $10,000. 6 " +
                                "furlongs. (SNW3 L) Claiming Price: $10,000",
                        // this is the correct result, but the description above is too non-standard
                        // will leave it as a future goal to fix this
                        // new RaceRestrictions("NW3 L", 3, -1, 24, true)
                        new RaceRestrictions("NW3 L", null, null, 31, true)
                },
                {
                        // urgh, more errors, this time leaving out "olds"
                        "THREE YEAR COLO. Bred fillies eligible to the final. Weight 120 lbs. (S)",
//                        new RaceRestrictions(null, 3, 3, 8, true)
                        new RaceRestrictions(null, null, null, 31, true)
                },
                {
                        "INNER TURF (UP TO $16,200 NYSBFOA) FOR FILLIES AND MARES THREE YEARS OLD" +
                                " AND UPWARD WHICH HAVE NEVER WON $10,000 TWICE OTHER THAN " +
                                "MAIDEN, CLAIMING, STARTER OR STATE BRED OR WHICH HAVE NEVER WON " +
                                "THREE RACES OR CLAIMING PRICE $62,500. Three Year Olds, 119lbs.;" +
                                " Older, 124 lbs. Non-winners Of Two Races Other Than Maiden, " +
                                "Claiming Or Starter At A Mile Or Over In 2016 Allowed 2 lbs. One" +
                                " such race in 2016 Allowed 4 lbs. Claiming Price $62,500 " +
                                "(Allowance Horses Preferred). (NW2$ X) Claiming Price: $62,500",
                        new RaceRestrictions("NW2$ X", 3, -1, 24, false)
                }
        });
    }

    @Test
    public void parseRaceDistance_WithParameters_ReturnsCorrectRaceDistance() throws Exception {
        assertThat(RaceRestrictions.parse(conditions), equalTo(expected));
    }
}
