package com.robinhowlett.chartparser.charts.pdf;

import com.robinhowlett.chartparser.TestChartResources;
import com.robinhowlett.chartparser.exceptions.ChartParserException;

import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;

import static com.robinhowlett.chartparser.charts.pdf.DistanceSurfaceTrackRecord.parse;
import static com.robinhowlett.chartparser.charts.pdf.DistanceSurfaceTrackRecord
        .parseDistanceSurface;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class DistanceSurfaceTrackRecordTest {

    private TestChartResources sampleCharts = new TestChartResources();

    @Test
    public void parseDistanceSurface_WithDistanceSurfaceTrackRecord_ParsesCorrectly()
            throws Exception {
        DistanceSurfaceTrackRecord.TrackRecord expectedTrackRecord =
                new DistanceSurfaceTrackRecord.TrackRecord(new Horse("No It Ain't"), "1:08.190",
                        68190L,
                        LocalDate.of(2011, 8, 12));
        DistanceSurfaceTrackRecord expected = new DistanceSurfaceTrackRecord("Six Furlongs",
                "Dirt", null, expectedTrackRecord);

        DistanceSurfaceTrackRecord distanceSurface = parseDistanceSurface("Six Furlongs" +
                " On The Dirt|Track Record: (No It Ain't - 1:08.19 - August 12, 2011)");

        assertThat(distanceSurface, equalTo(expected));
    }

    @Test
    public void parseDistanceSurface_WithOffTheTurf_ParsesCorrectly() throws Exception {
        DistanceSurfaceTrackRecord.TrackRecord expectedTrackRecord =
                new DistanceSurfaceTrackRecord.TrackRecord(new Horse("No It Ain't"), "1:08.190",
                        68190L,
                        LocalDate.of(2011, 8, 12));
        DistanceSurfaceTrackRecord expected = new DistanceSurfaceTrackRecord("Six Furlongs",
                "Dirt", "Turf", expectedTrackRecord);

        DistanceSurfaceTrackRecord distanceSurface = parseDistanceSurface("Six Furlongs" +
                " On The Dirt - Originally Scheduled For the Turf|Track Record: (No It Ain't - " +
                "1:08.19 - August 12, 2011)");

        assertThat(distanceSurface, equalTo(expected));
    }

    @Test
    public void parseDistanceSurface_WithFalsePositive_DoesNotParse() throws Exception {
        DistanceSurfaceTrackRecord distanceSurface =
                parseDistanceSurface("Or Restricted Over A Mile On The Turf In 1998-99. Weight " +
                        "122 lbs. Non-winners of $35,000 over a mile since June 1 allowed, 3 lbs.");

        assertThat(distanceSurface, equalTo(null));
    }

    @Test
    public void parse_WithLinesFromChart_ParsesCorrectly()
            throws ChartParserException, IOException, URISyntaxException {
        DistanceSurfaceTrackRecord.TrackRecord expectedTrackRecord =
                new DistanceSurfaceTrackRecord.TrackRecord(new Horse("No It Ain't"), "1:08.190",
                        68190L, LocalDate.of(2011, 8, 12));
        DistanceSurfaceTrackRecord expected = new DistanceSurfaceTrackRecord("Six Furlongs",
                "Dirt", null, expectedTrackRecord);

        DistanceSurfaceTrackRecord distanceSurface =
                parse(sampleCharts.getSampleChartLines(0));

        assertThat(distanceSurface, equalTo(expected));
    }
}
