package com.android.girish.vlog.chatheads.chatheads;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class Genre extends ExpandableGroup<Artist> {

  public Genre(String title, List<Artist> items) {
    super(title, items);
  }
}

