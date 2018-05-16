package com.robinhowlett.chartparser.charts.pdf;

import com.robinhowlett.chartparser.TestChartResources;

import org.junit.Test;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class FootnotesTest {

    private TestChartResources sampleCharts = new TestChartResources();

    @Test
    public void parseFootnotes_WithSampleChartText_ExtractsFootnotesCorrectly() throws Exception {
        String footnotes = Footnotes.parse(sampleCharts.getSampleChartLines(8));

        StringWriter sw = new StringWriter();
        PrintWriter printWriter = new PrintWriter(sw);
        printWriter.append("PRATER SIXTY FOUR opened a big lead in he stretch and held on for the");
        printWriter.append(" win. CANDY SWEETHEART closed well midstretch for place. ");
        printWriter.append("MIDNIGHTWITHDRAWAL was well back early and finished with interest. ");
        printWriter.append("PROWERS COUNTY off slow closed well mid track. AL BAZ (GB) was up ");
        printWriter.append("close arly then shuffled back on the inside and then tired late. ");
        printWriter.append("COOK INLET raced midpack and had no response in the ");
        printWriter.append("stretch. ANTARES DREAM up clsoe early faded after three-quarters. ");
        printWriter.append("TRAVELIN TREV was always well back and not a factor.");
        String expected = sw.toString();

        assertThat(footnotes, equalTo(expected));
    }
}
