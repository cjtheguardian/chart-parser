package com.robinhowlett.chartparser.charts.pdf;

import com.robinhowlett.chartparser.charts.pdf.DistanceSurfaceTrackRecord.RaceDistance;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static com.robinhowlett.chartparser.charts.pdf.DistanceSurfaceTrackRecord.parseRaceDistance;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class RaceDistanceTest {

    private String distanceWebDescription;
    private RaceDistance expected;

    public RaceDistanceTest(String distanceWebDescription, RaceDistance expected) {
        this.distanceWebDescription = distanceWebDescription;
        this.expected = expected;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection distances() {
        return Arrays.asList(new Object[][]{
                {"About One And Nine Sixteenth Miles",
                        new RaceDistance("About One And Nine Sixteenth Miles", "Abt 1 9/16m", false, 8250)},
                {"One Mile",
                        new RaceDistance("One Mile", "1m", true, 5280)},
                {"About Two Miles",
                        new RaceDistance("About Two Miles", "Abt 2m", false, 10560)},
                {"Two And One Eighth Miles",
                        new RaceDistance("Two And One Eighth Miles", "2 1/8m", true, 11220)},
                {"About One And One Half Miles",
                        new RaceDistance("About One And One Half Miles", "Abt 1 1/2m", false, 7920)},
                {"One Furlong",
                        new RaceDistance("One Furlong", "1f", true, 660)},
                {"About Seven Furlongs",
                        new RaceDistance("About Seven Furlongs", "Abt 7f", false, 4620)},
                {"Five And One Half Furlongs",
                        new RaceDistance("Five And One Half Furlongs", "5 1/2f", true, 3630)},
                {"About Five And One Fourth Furlongs",
                        new RaceDistance("About Five And One Fourth Furlongs", "Abt 5 1/4f", false, 3465)},
                {"Two Hundred Yards",
                        new RaceDistance("Two Hundred Yards", "200y", true, 600)},
                {"One Thousand Yards",
                        new RaceDistance("One Thousand Yards", "1000y", true, 3000)},
                {"About Four Hundred Yards",
                        new RaceDistance("About Four Hundred Yards", "Abt 400y", false, 1200)},
                {"Six Hundred And Sixty Yards",
                        new RaceDistance("Six Hundred And Sixty Yards", "660y", true, 1980)},
                {"One Thousand One Hundred Yards",
                        new RaceDistance("One Thousand One Hundred Yards", "1100y", true, 3300)},
                {"About Two Hundred And Twenty Yards",
                        new RaceDistance("About Two Hundred And Twenty Yards", "Abt 220y", false, 660)},
                {"One Thousand Three Hundred And Twenty Yards",
                        new RaceDistance("One Thousand Three Hundred And Twenty Yards", "1320y", true,
                                3960)},
                {"Two Miles And Forty Yards",
                        new RaceDistance("Two Miles And Forty Yards", "2m 40y", true, 10680)},
                {"About One Mile And Seventy Yards",
                        new RaceDistance("About One Mile And Seventy Yards", "Abt 1m 70y", false, 5490)},
                {"Four Furlongs And Seventy Yards",
                        new RaceDistance("Four Furlongs And Seventy Yards", "4f 70y", true, 2850)},
                {"Five Hundred And Seventy",
                        new RaceDistance("Five Hundred And Seventy", "570y", true, 1710)},
                {"Three Hundred And Seventy Five Yards",
                        new RaceDistance("Three Hundred And Seventy Five Yards", "375y", true, 1125)},
                {"Three Hundred And Fifteen Yards",
                        new RaceDistance("Three Hundred And Fifteen Yards", "315y", true, 945)}
        });
    }

    @Test
    public void parseRaceDistance_WithParameters_ReturnsCorrectRaceDistance() throws Exception {
        assertThat(parseRaceDistance(distanceWebDescription), equalTo(expected));
    }
}
