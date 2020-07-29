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
      if (i % 7 == 0) {
        priority = VLogModel.WARN;
        tag = "##7##" + i;
        logMessage = "We are adding this warning log for " + i + "th index "
                + "......................"
                + "......................"
                + "......................"
                + "......................"
                + "......................"
                + "......................"
                + "......................"
                + "......................";
      } else if (i % 5 == 0) {
        priority = VLogModel.DEBUG;
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
        priority = VLogModel.INFO;
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

}

