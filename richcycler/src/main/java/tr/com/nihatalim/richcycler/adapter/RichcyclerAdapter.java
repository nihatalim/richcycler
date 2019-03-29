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

import java.util.Date;
import java.util.List;

/**
 * This class is an implementation of generic recyclerview adapter.
 * @param <THolder> This param is spesify holder type.
 * @param <TModel> This param is spesify model type.
 *
 * @Author Nihat ALİM
 */
public class RichcyclerAdapter<THolder extends RecyclerView.ViewHolder, TModel>
        extends RecyclerView.Adapter<THolder>{

    /**
     * This property contains all items from adapter
     */
    public List<TModel> items = null;

    /**
     * This property is holder of context
     */
    private Context context = null;

    /**
     * This property holds holder's layout resource id.
     */
    private int layout_element;

    /**
     * This property is an interface that uses for layout stuffs.
     */
    private OnAdapter<THolder> OnAdapter = null;

    /**
     * This property is an interface that uses for pagination stuffs.
     */
    private OnPaginate OnPaginate = null;

    /**
     * This property is layout manager for using layout
     */
    public RecyclerView.LayoutManager LayoutManager = null;

    /**
     * This property is holds last paginated time.
     */
    public Date LastPaginationTime = new Date();

    /**
     * This property holds page number.
     */
    public int pageNumber = 1;

    /**
     * This property holds pagination size.
     */
    public int paginationSize = 10;

    /**
     * This property holds pagination time limit.
     */
    public long paginationTimeLimit = 1000;

    /**
     * This property is a flag for loading items.
     */
    private boolean isLoading = false;

    /**
     * This property is using for sliding horizontally one by one.
     */
    private boolean isSnap = false;

    /**
     * Creates new instance RichcyclerAdapter
     */
    public RichcyclerAdapter(){}

    /**
     * Creates new instance RichcyclerAdapter
     * @param objectList
     * @param context
     */
    public RichcyclerAdapter(List<TModel> objectList, Context context){
        this();
        this.setObjectList(objectList);
        this.setContext(context);
    }

    /**
     * Creates new instance RichcyclerAdapter
     * @param objectList This parameter is an object list as start value.
     * @param context This parameter is context.
     * @param layout_element This parameter holds holder's layout resource id.
     */
    public RichcyclerAdapter(List<TModel> objectList, Context context, int layout_element){
        this(objectList,context);
        this.setLayout_element(layout_element);
    }

    /**
     * This method is overrided from RecyclerView.Adapter and it uses for creating holder of THolder type.
     * @param parent This parameter is coming from parent.
     * @param viewType This parameter is coming from parent.
     * @return Returns new holder.
     */
    @Override
    public THolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(this.layout_element,parent,false);
        return this.OnAdapter.onCreate(parent,viewType,view);
    }

    /**
     * This method is overrided from RecyclerView.Adapter and it uses for binding elements.
     * @param holder This parameter is holder of THolder type.
     * @param position This parameter is position of binding items order.
     */
    @Override
    public void onBindViewHolder(THolder holder, int position) {
        //this.OnBindInterface.OnBind(holder,position);
        this.OnAdapter.OnBind(holder, position);
    }

    /**
     * @return Returns items count.
     */
    @Override
    public int getItemCount() {
        if(this.items==null) return 0;
        return this.items.size();
    }

    /**
     * This method spesify snap.
     * @param snp
     */
    public void snap(boolean snp){
        this.isSnap = snp;
    }

    /**
     * This method is build recyclerview.
     * @param recyclerView
     */
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

    /**
     * This method adds a object inside items.
     * @param obj An object.
     * @param notify Notify for update ui.
     */
    public void add(TModel obj, boolean notify){
        if(this.items!=null){
            this.items.add(obj);
            if(notify) this.notifyDataSetChanged();
        }
    }

    /**
     * This method adds list of object inside items.
     * @param obj List of object.
     * @param notify Notify for update ui.
     */
    public void addAll(List<TModel> obj, boolean notify){
        if(this.items!=null){
            this.items.addAll(obj);
            if(notify) this.notifyDataSetChanged();
        }
    }

    /**
     * This method removes an object from items.
     * @param obj An object.
     * @param notify Notify for update ui.
     */
    public void remove(TModel obj, boolean notify){
        this.items.remove(obj);
        if (notify) this.notifyDataSetChanged();
    }

    /**
     * This method clear the items.
     * @param notify Notify for update ui.
     */
    public void clear(boolean notify){
        this.items.clear();
        if (notify) this.notifyDataSetChanged();
    }

    /**
     * This method is perform pagination stuffs.
     * @param pageNumber
     * @param bundle
     */
    public void paginate(int pageNumber, Bundle bundle){
        // TODO Require new generation of pagination.
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

    /**
     * @return Returns items.
     */
    public List<TModel> getObjectList() {
        return items;
    }

    /**
     * This method is set the items.
     * @param items
     */
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

    /**
     * @return Returns default created layout manager.
     */
    public LinearLayoutManager getDefaultLayoutManager() {
        LinearLayoutManager defaultLayoutManager = new LinearLayoutManager(this.context);
        defaultLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        defaultLayoutManager.scrollToPosition(0);
        return defaultLayoutManager;
    }
}
