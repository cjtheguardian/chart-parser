package com.robinhowlett.chartparser.charts.pdf;

import com.robinhowlett.chartparser.TestChartResources;
import com.robinhowlett.chartparser.charts.pdf.RaceConditions.ClaimingPriceRange;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

import static com.robinhowlett.chartparser.charts.pdf.RaceRestrictions.ALL_SEXES;
import static com.robinhowlett.chartparser.charts.pdf.RaceTypeNameBlackTypeBreed.RACE_TYPE_CODES;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class RaceConditionsTest {

    private TestChartResources sampleCharts = new TestChartResources();

    @Test
    public void parse_WithSampleChart_ReturnsValidRaceDescription() throws Exception {
        RaceConditions expected = new RaceConditions("FOR " +
                "MAIDENS, FILLIES AND MARES THREE YEARS OLD AND UPWARD. Three Year Olds, 120 lbs" +
                ".; Older, 124 lbs.", null);

        RaceConditions raceConditions =
                RaceConditions.parse(sampleCharts.getSampleChartLines(0));
        assertThat(raceConditions, equalTo(expected));
    }

    @Test
    public void parse_WithClaimingPriceRanger_ParsesCorrectly() throws Exception {
        ClaimingPriceRange expected = new ClaimingPriceRange(22500, 25000);

        String raceConditions = "Maidens three years old and upward. Weights: 3-year-olds, " +
                "119 lbs; Older, 122 lbs. Claiming Price $25,000; if for $ 22,500 allowed 2 lbs. " +
                "Claiming Price: $25,000 - $22,500";

        ClaimingPriceRange claimingPriceRange = ClaimingPriceRange.parse(raceConditions);

        assertThat(claimingPriceRange, equalTo(expected));
    }

    @Test
    public void isClaimingRace_WithAllRaceTypes_IdentifiesClaimingRaceCountCorrectly() throws
            Exception {
        Assert.assertThat(
                RACE_TYPE_CODES.keySet().stream()
                        .filter(RaceConditions::isClaimingRace)
                        .collect(Collectors.toList()),
                equalTo(Arrays.asList(
                        "SPEED INDEX OPTIONAL CLAIMING",
                        "ALLOWANCE OPTIONAL CLAIMING",
                        "OPTIONAL CLAIMING HANDICAP",
                        "STARTER OPTIONAL CLAIMING",
                        "MAIDEN OPTIONAL CLAIMING",
                        "OPTIONAL CLAIMING STAKES",
                        "WAIVER MAIDEN CLAIMING",
                        "CLAIMING HANDICAP",
                        "OPTIONAL CLAIMING",
                        "CLAIMING STAKES",
                        "MAIDEN CLAIMING",
                        "WAIVER CLAIMING",
                        "CLAIMING"
                )));
    }

    @Test
    public void buildAgeSexesSummary_WithDefinedAgeRangeAndSexesValue_BuildsSimpleSummary()
            throws Exception {
        Assert.assertThat(
                RaceConditions.buildAgeSexesSummary(
                        new RaceRestrictions(null, 3, 4, 24)),
                equalTo("3-4 (F&M)"));
    }

    @Test
    public void buildAgeSexesSummary_WithUndefinedAgeRangeAndAllSexes_BuildsSimpleSummary()
            throws Exception {
        Assert.assertThat(
                RaceConditions.buildAgeSexesSummary(
                        new RaceRestrictions("NW2 L", 3, -1, 31, true)),
                equalTo("3+"));
    }

    @Test
    public void buildStateBredSummary_WithStateBredRaces_AddsStateBredPrefix() throws Exception {
        Assert.assertThat(
                RaceConditions.buildStateBredSummary(
                        "3+ (F&M)", new RaceRestrictions("NW2 L", 3, -1, 24, true)),
                equalTo("3+ (F&M) [S]"));
    }

    @Test
    public void buildStateBredSummary_WithNormalRace_DoesNotAddStateBredPrefix() throws Exception {
        Assert.assertThat(
                RaceConditions.buildStateBredSummary(
                        "3", new RaceRestrictions(null, 3, 3, ALL_SEXES)),
                equalTo("3"));
    }

    @Test
    public void buildCodeSummary_WithGradedStakes_BuildsSummaryWithGradeSummary()
            throws Exception {
        Assert.assertThat(
                RaceConditions.buildCodeSummary(
                        "", new RaceTypeNameBlackTypeBreed("STAKES", "Breeders' Cup Classic", 1,
                                "Grade 1", Breed.THOROUGHBRED)),
                equalTo("G1"));
    }

    @Test
    public void buildCodeSummary_WithNonGradedStakes_BuildsSummaryWithStakesSummary()
            throws Exception {
        Assert.assertThat(
                RaceConditions.buildCodeSummary(
                        "", new RaceTypeNameBlackTypeBreed("STAKES", "Aspen S.", null,
                                "Black Type", Breed.THOROUGHBRED)),
                equalTo("STK"));
    }

    @Test
    public void buildPurseSummary_WithPurseValueOfOneThousand_BuildsSummaryWithOneDecimalPoint()
            throws Exception {
        Assert.assertThat(
                RaceConditions.buildPurseSummary("ALW", new Purse(1000, null, null, null, null)),
                equalTo("ALW 1.0K"));
    }

    @Test
    public void buildPurseSummary_WithPurseValueOfTwentyThousand_BuildsBasicSummary()
            throws Exception {
        Assert.assertThat(
                RaceConditions.buildPurseSummary("ALW", new Purse(20000, null, null, null, null)),
                equalTo("ALW 20K"));
    }

    @Test
    public void buildClaimingPriceSummary_WithHighClaimingPriceRange_BuildsSimpleSummary() throws
            Exception {
        Assert.assertThat(
                RaceConditions.buildClaimingPriceSummary("CLM",
                        new ClaimingPriceRange(90000, 100000)),
                equalTo("CLM 100-90K"));
    }

    @Test
    public void buildClaimingPriceSummary_WithLowClaimingPriceRange_BuildsSimpleSummary() throws
            Exception {
        Assert.assertThat(
                RaceConditions.buildClaimingPriceSummary("CLM",
                        new ClaimingPriceRange(2000, 2500)),
                equalTo("CLM 2.5-2.0K")); // max goes first
    }

    @Test
    public void buildClaimingPriceSummary_WithSingleClaimingPrice_BuildsSimpleSummary() throws
            Exception {
        Assert.assertThat(
                RaceConditions.buildClaimingPriceSummary("CLM",
                        new ClaimingPriceRange(10000, 10000)),
                equalTo("CLM 10K")); // max goes first
    }

    @Test
    public void buildRestrictionsCode_WithDefinedCode_AddsCodeAsSuffix() throws Exception {
        Assert.assertThat(
                RaceConditions.buildRestrictionsCode("ALW 10K",
                        new RaceRestrictions("NW2 L", 3, -1, 31, true)),
                equalTo("ALW 10K (NW2 L)"));
    }

    @Test
    public void buildRestrictionsCode_WithoutCode_DoesNotAddCodeAsSuffix() throws Exception {
        Assert.assertThat(
                RaceConditions.buildRestrictionsCode("CLM 5.5K",
                        new RaceRestrictions(null, 3, -1, 24)),
                equalTo("CLM 5.5K"));
    }

    @Test
    public void buildSummary() throws Exception {
        Assert.assertThat(
                RaceConditions.buildSummary(
                        new RaceRestrictions("NW2 L", 3, -1, 3, true),
                        new RaceTypeNameBlackTypeBreed("CLAIMING", Breed.THOROUGHBRED),
                        new ClaimingPriceRange(8500, 10000),
                        new Purse(20000, "$20,000", null, null, null)
                ),
                equalTo("3+ (C&G) [S] CLM 10-8.5K (NW2 L)"));
    }
}
