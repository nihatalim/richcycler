package tr.com.nihatalim.richcycler.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RichcyclerAdapter<THolder extends RecyclerView.ViewHolder, TModel>
        extends RecyclerView.Adapter<THolder>{

    public List<TModel> items = null;
    public List<TModel> searchedItems = null;

    private Context context = null;

    private int layout_element;

    private OnAdapter<THolder> OnAdapter = null;

    private OnPaginate OnPaginate = null;

    public RecyclerView.LayoutManager LayoutManager = null;

    public Date LastPaginationTime = new Date();

    public int pageNumber = 1;
    public int paginationSize = 10;
    public long paginationTimeLimit = 1000;

    private boolean isLoading = false;

    private boolean isSnap = false;

    public RichcyclerAdapter(){}

    public RichcyclerAdapter(List<TModel> objectList, Context context){
        this();
        this.setObjectList(objectList);
        this.setContext(context);
    }

    public RichcyclerAdapter(List<TModel> objectList, Context context, int layout_element){
        this(objectList,context);
        this.setLayout_element(layout_element);
    }

    @Override
    public THolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(this.layout_element,parent,false);
        //return this.OnCreateInterface.onCreate(parent,viewType,view);
        return this.OnAdapter.onCreate(parent,viewType,view);
    }

    @Override
    public void onBindViewHolder(THolder holder, int position) {
        //this.OnBindInterface.OnBind(holder,position);
        this.OnAdapter.OnBind(holder, position);
    }

    @Override
    public int getItemCount() {
        if(this.items==null) return 0;
        return this.items.size();
    }

    public void snap(boolean snp){
        this.isSnap = snp;
    }

    public void build(RecyclerView recyclerView){
        // Added for fragments if this LM is not binded already
        this.LayoutManager = OnAdapter.setLayoutManager(getDefaultLayoutManager());
        if(this.LayoutManager == null) this.LayoutManager = getDefaultLayoutManager();

        recyclerView.setLayoutManager(this.LayoutManager);

        recyclerView.setAdapter(this);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        if(this.isSnap){
            SnapHelper snapHelper = new PagerSnapHelper();
            snapHelper.attachToRecyclerView(recyclerView);
        }
    }

    public void add(TModel obj, boolean notify){
        if(this.items!=null){
            this.items.add(obj);
            if(notify) this.notifyDataSetChanged();
        }
    }

    public void addAll(List<TModel> obj, boolean notify){
        if(this.items!=null){
            this.items.addAll(obj);
            if(notify) this.notifyDataSetChanged();
        }
    }

    public void remove(TModel obj, boolean notify){
        this.items.remove(obj);
        if (notify) this.notifyDataSetChanged();
    }

    public void clear(boolean notify){
        this.items.clear();
        if (notify) this.notifyDataSetChanged();
    }

    public void paginate(int pageNumber, Bundle bundle){
        TModel firstItem = null;
        TModel lastItem = null;

        Date nextTime = new Date(this.LastPaginationTime.getTime() + this.paginationTimeLimit);
        Date currentTime = new Date();

        if(nextTime.before(currentTime)){
            if(this.items.size()>0){
                firstItem = this.items.get(0);
                lastItem = this.items.get(this.items.size()-1);
            }
            this.OnPaginate.<TModel>paginate(pageNumber, this.paginationSize, firstItem, lastItem, bundle);
            LastPaginationTime = currentTime;
        }
    }

    // GETTERS AND SETTERS
    public List<TModel> getObjectList() {
        return items;
    }

    public void setObjectList(List<TModel> items) {
        this.items = items;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getLayout_element() {
        return layout_element;
    }

    public void setLayout_element(int layout_element) {
        this.layout_element = layout_element;
    }

    public OnAdapter<THolder> getOnAdapter() {
        return OnAdapter;
    }

    public void setOnAdapter(OnAdapter<THolder> onAdapter) {
        OnAdapter = onAdapter;
    }

    public OnPaginate getOnPaginate() {
        return OnPaginate;
    }

    public void setOnPaginate(OnPaginate onPaginate) {
        OnPaginate = onPaginate;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return LayoutManager;
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        LayoutManager = layoutManager;
    }

    public LinearLayoutManager getDefaultLayoutManager() {
        LinearLayoutManager defaultLayoutManager = new LinearLayoutManager(this.context);
        defaultLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        defaultLayoutManager.scrollToPosition(0);
        return defaultLayoutManager;
    }
}
