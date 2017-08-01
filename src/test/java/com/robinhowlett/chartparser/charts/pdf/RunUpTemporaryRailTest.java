package com.robinhowlett.chartparser.charts.pdf;

import com.robinhowlett.chartparser.TestChartResources;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class RunUpTemporaryRailTest {

    private TestChartResources sampleCharts = new TestChartResources();

    @Test
    public void parse_WithSampleRunningLines_Returns30FeetRunUp() throws Exception {
        RunUpTemporaryRail expected = new RunUpTemporaryRail(30, null);

        RunUpTemporaryRail runUpTemporaryRail = RunUpTemporaryRail.parse(sampleCharts.getRunningLineLines(0));
        assertThat(runUpTemporaryRail, equalTo(expected));
    }
}
