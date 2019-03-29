package tr.com.nihatalim.richcycler.filters;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import tr.com.nihatalim.richcycler.renderers.Renderer;

public abstract class Filter<T1> implements Renderer<T1> {
    public String name, display, renderer;

    public FilterType type;

    // Item of declared in assets xml file
    public List<Item> items;

    public Context context;

    private Filter(){
        this.items = new ArrayList<>();
    }

    public Filter(Context context, String name, String display, String renderer, FilterType type){
        this();
        this.context = context;
        this.name = name;
        this.display = display;
        this.renderer = renderer;
        this.type = type;
    }

    public Item getItemWithDisplay(String display){
        for (Item item:items) {
            if(item.display.equals(display)) return item;
        }
        return null;
    }
}
