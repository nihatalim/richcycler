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

/**
 * This class is generic implementation of recyclerview. It contains adapter, filtering etc. operations for performing quickly stuffs.
 *
 * @param <THolder> This param is a holder type of recyclerview.
 * @param <TModel> This param is a model type of recyclerview.
 *
 * @Author Nihat ALİM
 */
public class Richcycler<THolder extends RecyclerView.ViewHolder, TModel> extends RecyclerView {
    /**
     * This is a generic adapter of recyclerview.
     */
    public RichcyclerAdapter<THolder, TModel> adapter;

    /**
     * This map holds parsed and created filters. It supports working multiple filter file.
     */
    public Map<String, List<Filter>> filters = new HashMap<>();

    /**
     * This is holder's layout's resource id.
     */
    private int holderResourceId = -1;

    /**
     * This is xml filter file name.
     */
    private String filterFileName = null;

    /**
     * This is base constructor for creating richcyclerview.
     *
     * @param context This param is a context object of application.
     * @param attrs This param is attributes that contains layout's custom xml commands.
     */
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

    /**
     * This method loads that filters inside of filters variable.
     * @param id This parameter is an identity value of spesifing how holding with identity value inside map.
     * @throws NullPointerException That throw an exception when xml filter file doesn't exist.
     */
    public void loadFilters(String id) throws NullPointerException{
        if(this.filterFileName == null) throw new NullPointerException("Filter name musn't be null.");
        this.loadFilters(id, this.filterFileName);
    }

    /**
     * This method loads that filters inside of filters variable.
     * @param id This parameter is an identity value of spesifing how holding with identity value inside map.
     * @param xmlFile This parameter is file name of xml filter
     */
    public void loadFilters(String id, String xmlFile){
        if(this.filters.containsKey("id")){
            this.removeFilter(id);
        }
        this.addFilter(id, XmlParser.parse(xmlFile, getContext()));
    }

    /**
     * This method returns render each of filter loaded before as a list.
     * @param id This parameter is an identity value and it uses for get from loaded filters.
     * @return Returns list of view that rendered from loaded filters.
     */
    public List<View> getViews(String id){
        List<View> views = new ArrayList<>();
        List<Filter> fs = this.getFilters(id);

        for (Filter fi : fs) {
            views.add(fi.render());
        }

        return views;
    }
    /**
     * This method returns list of filter that loaded before.
     * @param id This parameter is require for getting loaded filters which id.
     * @return
     */
    public List<Filter> getFilters(String id){
        return this.filters.get(id);
    }

    /**
     * This method perform adding filter with spesified id.
     * @param id This parameter is require for getting loaded filters which id.
     * @param fs This parameter is a list of filter that pass for adding filters.
     */
    public void addFilter(String id, List<Filter> fs){
        this.filters.put(id, fs);
    }

    /**
     * This method is removes a filter with id.
     * @param id This parameter is require for getting loaded filters which id.
     */
    public void removeFilter(String id){
        this.filters.remove(id);
    }

    /**
     * This method is remove all filters which before loaded.
     */
    public void removeAllFilters(){
        this.filters.clear();
    }

    /**
     * This method is reload a filter
     * @param id This parameter is require for getting loaded filters which id.
     */
    public void reloadFilters(String id){
        this.removeFilter(id);
        this.loadFilters(id);
    }

    /**
     * This method is reload a filter
     * @param id This parameter is require for getting loaded filters which id.
     * @param xmlFile This parameter is file name of xml filter
     */
    public void reloadFilters(String id, String xmlFile){
        this.removeFilter(id);
        this.loadFilters(id, xmlFile);
    }

    /**
     * This method is require for build recyclerview.
     */
    public void build(){
        this.adapter.build(this);
    }

    /**
     * This method is
     * @param id This parameter is require for getting loaded filters which id.
     * @param name This parameter is name of the filter.
     * @return Returns an object of resulted from filter.
     */
    public Object getFilterResult(String id, String name){
        for (Filter f:this.getFilters(id)) {
            if(f.name.equals(name)) return f.result();
        }
        return null;
    }
}