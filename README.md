# Chart Parser
Parses horse racing result charts into JSON/CSV/Java...

[![Build Status](https://travis-ci.org/robinhowlett/chart-parser.svg?branch=master)](https://travis-ci.org/robinhowlett/chart-parser)

## TL;DR

When given an Equibase result chart PDF file e.g. 

![sample-chart-img](https://i.imgur.com/jQtP1Dw.png)

`chart-parser` can turn it into machine-readable formats, like JSON, e.g.

![sample-json](https://i.imgur.com/hqtJpqb.png)

or CSV, e.g.

![sample-csv](https://i.imgur.com/ZHIIaJd.png)

or even to be used as code in an SDK:

![sample-code](https://i.imgur.com/yAWpxgG.png)

## Highlights

* The entire PDF is parsed; everything you see in the chart can be used, including:
    * the race conditions and restrictions
    * lengths ahead/behind at each point of call
    * fractional times
    * wagering payoffs, pools, and carryovers
    * footnotes etc.

* Full race card PDFs containing multiple races (including those spread over multiple pages) can be parsed.

* An SDK comes out-of-the-box that supports full serialization to and from a JSON API.

* Textual descriptions of race distances are converted to feet e.g. "Six Furlongs" becomes 3,960.

* Values for lengths ahead/behind are converted to decimal formats.

* The software adds additional features, including:
    * attempting to lookup the last-raced track details and linking to it
    * calculating estimated individual fractional and splits at each fraction for each starter in a race.
    * outlining each medication and equipment used
    * providing a normalized "X-to-1" odds determination for all wagering payoffs
    * displaying the day- of-the-week and -of-the-year that a race took place
 
* Thoroughbred, Quarter Horse, Arabian and Mixed breed races are all supported.

* The software handles edge-case scenarios such as dead-heats, walkovers, non-betting races, disqualifications (including adjusting final winning positions), cancellations, claiming price information etc.

## How it works

PDFs are parsed using the [Apache PDFBox](https://pdfbox.apache.org/) library.

For a given PDF file, each character present is written as pipe-delimited String that notes its x-y coordinates, height, width, scale, font-size, and unicode value within a page of the PDF.

This is done using [`ChartStripper`](https://github.com/robinhowlett/chart-parser/blob/master/src/main/java/com/robinhowlett/chartparser/charts/text/ChartStripper.java), a customized [`PDFTextStripper`](https://pdfbox.apache.org/docs/2.0.3/javadocs/org/apache/pdfbox/text/PDFTextStripper.html) instance.

For each pipe-delimited String representing a character within the PDF, it is converted to a custom POJO, [`ChartCharacter`](https://github.com/robinhowlett/chart-parser/blob/master/src/main/java/com/robinhowlett/chartparser/charts/pdf/ChartCharacter.java), using [the CSV Jackson data format](https://github.com/FasterXML/jackson-dataformats-text/tree/master/csv).

The list of `ChartCharacter`s is then further grouped by the line of text it is present on within the PDF. 

Each line of text within the PDF is then tested against a series of regex matchers to identify which parts of the race domain model it represents. When matched, the information is parsed and used to create an instance of [`RaceResult`](https://github.com/robinhowlett/chart-parser/blob/master/src/main/java/com/robinhowlett/chartparser/charts/pdf/RaceResult.java), following [the Builder pattern](https://www.javaworld.com/article/2074938/core-java/too-many-parameters-in-java-methods-part-3-builder-pattern.html).

See [`ChartParser#parse()`](https://github.com/robinhowlett/chart-parser/blob/master/src/main/java/com/robinhowlett/chartparser/ChartParser.java#L295) for more.

## How to use

**Chart Parser** is available in the [Maven Central repository](http://search.maven.org/#artifactdetails|com.robinhowlett|chart-parser|1.2.0.RELEASE|jar):

```xml
<dependency>
    <groupId>com.robinhowlett</groupId>
    <artifactId>chart-parser</artifactId>
    <version>1.2.0.RELEASE</version>
</dependency>
```

Parsing a PDF file is simple and can be done in one-line e.g.:

```java
List<RaceResult> raceResults = ChartParser.create().parse(Paths.get("ARP_2016-07-24_race-charts.pdf").toFile());

// print the winning margins
raceResults.stream()
        .flatMap(raceResult -> raceResult.getStarters().stream())
        .filter(Starter::isWinner)
        .forEach(starter -> System.out.println(
                String.format("%-20s: %10s",
                        starter.getHorse().getName(),
                        starter.getFinishPointOfCall().getRelativePosition().getLengthsAhead().getText())
        ));

// console output
Back Stop           :      1 1/2
Cowboy Cliff        :      9 1/2
Perkin Desire       :      1 3/4
Fast as Thunder     :        1/2
Takin the Blame     :      7 1/4
Acme Rocket         :      1 1/4
Magical Twist       :      3 3/4
Lady Jila           :       Neck
Prater Sixty Four   :      3 1/4
```

[Handycapper](https://github.com/robinhowlett/handycapper) is provided as a sample application to parse and convert PDF charts:

![UI](https://raw.githubusercontent.com/robinhowlett/handycapper/master/docs/img/ui_0-main.png)

<!--
## Documentation

* [JSON API Design](http://www.robinhowlett.com/chart-parser/json-design.html)
* [JavaDoc](http://www.robinhowlett.com/chart-parser/apidocs/index.html)
* [Maven Site](http://www.robinhowlett.com/chart-parser/index.html)
-->

## Compiling

***IMPORTANT:*** This project relies on enabling [the Java 8 method parameter reflection feature (`-parameters`)](https://docs.oracle.com/javase/tutorial/reflect/member/methodparameterreflection.html) in your JVM settings e.g. 

![intellij-settings](https://i.imgur.com/8S89Byp.png)

`chart-parser` is a [Maven-based](https://maven.apache.org/) Java open-source project. Running `mvn clean install` will compile the code, run [all tests](https://github.com/robinhowlett/chart-parser/tree/master/src/test/java/com/robinhowlett/chartparser), and install the built artificat to the local repository.

## Notes

This software is open-source and released under the [MIT License](https://github.com/robinhowlett/chart-parser/blob/master/LICENSE). 

This project contains [a single sample Equibase PDF chart](https://github.com/robinhowlett/chart-parser/blob/master/src/test/resources/ARP_2016-07-24_race-charts.pdf) included for testing, educational and demonstration purposes only.

It is recommended users of this software be aware of the conditions on the PDF charts that may apply.
