package com.android.girish.vlog.chatheads.chatheads;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GenreDataFactory {


  public static List<VLogModel> generateLogs() {
    ArrayList<VLogModel> logDataList = new ArrayList<>();
    int priority = VLogModel.UNKNOWN;
    String tag = "";
    String logMessage = "";
    for (int i = 0; i < 30; i++) {
      if (i % 5 == 0) {
        priority = VLogModel.INFO;
        tag = "##5##" + i;
        logMessage = "We are adding this info log for " + i + "th index "
                + "......................"
                + "......................"
                + "......................"
                + "......................"
                + "......................"
                + "......................"
                + "......................"
                + "......................";
      } else if (i % 3 == 0) {
        priority = VLogModel.ERROR;
        tag = "##3##" + i;
        logMessage = "We are adding this error log for " + i + "th index "
                + "......................"
                + "......................"
                + "......................"
                + "......................"
                + "......................"
                + "......................"
                + "......................"
                + "......................";
      } else if (i % 2 == 0) {
        priority = VLogModel.DEBUG;
        tag = "##2##" + i;
        logMessage = "We are adding this debug log for " + i + "th index "
                + "......................"
                + "......................"
                + "......................"
                + "......................"
                + "......................"
                + "......................"
                + "......................"
                + "......................";
      } else {
        priority = VLogModel.VERBOSE;
        tag = "#####" + i;
        logMessage = "We are adding this verbose log for " + i + "th index "
                + "......................"
                + "......................"
                + "......................"
                + "......................"
                + "......................"
                + "......................"
                + "......................"
                + "......................";
      }
      VLogModel vlog = new VLogModel(priority, tag, logMessage);
      logDataList.add(vlog);
    }
    return logDataList;
  }



  public static List<Genre> makeGenres() {
    return Arrays.asList(makeRockGenre(),
        makeJazzGenre(),
        makeClassicGenre(),
        makeSalsaGenre(),
        makeBluegrassGenre());
  }


  public static Genre makeRockGenre() {
    return new Genre("Rock", makeRockArtists());
  }

  public static List<Artist> makeRockArtists() {
    Artist queen = new Artist("Queen", true);
    Artist styx = new Artist("Styx", false);
    Artist reoSpeedwagon = new Artist("REO Speedwagon", false);
    Artist boston = new Artist("Boston", true);

    return Arrays.asList(queen, styx, reoSpeedwagon, boston);
  }

  public static Genre makeJazzGenre() {
    return new Genre("Jazz", makeJazzArtists());
  }

  public static List<Artist> makeJazzArtists() {
    Artist milesDavis = new Artist("Miles Davis", true);
    Artist ellaFitzgerald = new Artist("Ella Fitzgerald", true);
    Artist billieHoliday = new Artist("Billie Holiday", false);

    return Arrays.asList(milesDavis, ellaFitzgerald, billieHoliday);
  }

  public static Genre makeClassicGenre() {
    return new Genre("Classic", makeClassicArtists());
  }

  public static List<Artist> makeClassicArtists() {
    Artist beethoven = new Artist("Ludwig van Beethoven", false);
    Artist bach = new Artist("Johann Sebastian Bach", true);
    Artist brahms = new Artist("Johannes Brahms", false);
    Artist puccini = new Artist("Giacomo Puccini", false);

    return Arrays.asList(beethoven, bach, brahms, puccini);
  }

  public static Genre makeSalsaGenre() {
    return new Genre("Salsa", makeSalsaArtists());
  }

  public static List<Artist> makeSalsaArtists() {
    Artist hectorLavoe = new Artist("Hector Lavoe", true);
    Artist celiaCruz = new Artist("Celia Cruz", false);
    Artist willieColon = new Artist("Willie Colon", false);
    Artist marcAnthony = new Artist("Marc Anthony", false);

    return Arrays.asList(hectorLavoe, celiaCruz, willieColon, marcAnthony);
  }

  public static Genre makeBluegrassGenre() {
    return new Genre("Bluegrass", makeBluegrassArtists());
  }

  public static List<Artist> makeBluegrassArtists() {
    Artist billMonroe = new Artist("Bill Monroe", false);
    Artist earlScruggs = new Artist("Earl Scruggs", false);
    Artist osborneBrothers = new Artist("Osborne Brothers", true);
    Artist johnHartford = new Artist("John Hartford", false);

    return Arrays.asList(billMonroe, earlScruggs, osborneBrothers, johnHartford);
  }

}

