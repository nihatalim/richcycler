package tr.com.nihatalim.richcycler.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tr.com.nihatalim.richcycler.R;
import tr.com.nihatalim.richcycler.adapter.RichcyclerAdapter;
import tr.com.nihatalim.richcycler.filters.Filter;
import tr.com.nihatalim.richcycler.parsers.XmlParser;

public class Richcycler<THolder extends RecyclerView.ViewHolder, TModel> extends RecyclerView {
    public RichcyclerAdapter<THolder, TModel> adapter;

    public Map<String, List<Filter>> filters = new HashMap<>();

    private int holderResourceId = -1;
    private String filterFileName = null;

    public Richcycler(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray params = context.getTheme().obtainStyledAttributes(attrs, R.styleable.Richcycler,0,0);

        this.filterFileName = params.getString(R.styleable.Richcycler_filter);

        this.holderResourceId = params.getResourceId(R.styleable.Richcycler_holder, -1);
        if(this.holderResourceId != -1){
            this.adapter = new RichcyclerAdapter<>(new ArrayList<TModel>(), context, this.holderResourceId);
        }
        else {
            this.adapter = new RichcyclerAdapter<>(new ArrayList<TModel>(), context);
        }
    }

    public void loadFilters(String id) throws NullPointerException{
        if(this.filterFileName == null) throw new NullPointerException("Filter name musn't be null.");
        this.loadFilters(id, this.filterFileName);
    }

    public void loadFilters(String id, String xmlFile){
        if(this.filters.containsKey("id")){
            this.removeFilter(id);
        }
        this.addFilter(id, XmlParser.parse(xmlFile, getContext()));
    }

    public List<View> getViews(String id){
        List<View> views = new ArrayList<>();
        List<Filter> fs = this.getFilters(id);

        for (Filter fi : fs) {
            views.add(fi.render());
        }

        return views;
    }

    public List<Filter> getFilters(String id){
        return this.filters.get(id);
    }

    public void addFilter(String id, List<Filter> fs){
        this.filters.put(id, fs);
    }

    public void removeFilter(String id){
        this.filters.remove(id);
    }

    public void removeAllFilters(){
        this.filters.clear();
    }

    public void reloadFilters(String id){
        this.removeFilter(id);
        this.loadFilters(id);
    }

    public void reloadFilters(String id, String xmlFile){
        this.removeFilter(id);
        this.loadFilters(id, xmlFile);
    }

    public void build(){
        this.adapter.build(this);
    }

    public Object getFilterResult(String id, String name){
        for (Filter f:this.getFilters(id)) {
            if(f.name.equals(name)) return f.result();
        }
        return null;
    }
}