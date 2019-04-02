package tr.com.nihatalim.richcycler.models;

import java.util.List;

import tr.com.nihatalim.richcycler.filters.FilterType;

public class Filter {
    public String name;
    public String display;
    public String renderer;
    public FilterType type;
    public List<Item> items;
}
