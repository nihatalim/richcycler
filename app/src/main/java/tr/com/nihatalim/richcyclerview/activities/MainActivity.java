package tr.com.nihatalim.richcyclerview.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import tr.com.nihatalim.richcycler.adapter.OnAdapter;
import tr.com.nihatalim.richcycler.filters.Item;
import tr.com.nihatalim.richcycler.views.Richcycler;
import tr.com.nihatalim.richcyclerview.R;
import tr.com.nihatalim.richcyclerview.models.User;
import tr.com.nihatalim.richcyclerview.views.UserHolder;

public class MainActivity extends AppCompatActivity {
    private static final String FILTER_ID = "MainActivity";

    private Richcycler<UserHolder, User> richcycler;

    private Button btnShowFilter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.btnShowFilter = findViewById(R.id.btnShowFilter);

        this.btnShowFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View inflatedView = LayoutInflater.from(getContext()).inflate(R.layout.filter_main, null);
                LinearLayout linearLayout = inflatedView.findViewById(R.id.llContent);

                List<View> viewss = richcycler.getViews(FILTER_ID);
                if(viewss != null) {
                    for (View v : viewss) {
                        linearLayout.addView(v);
                    }
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        richcycler.saveFiltersStates(FILTER_ID);
                        handleResults();
                    }
                });

                builder.setNegativeButton("Clear Filters", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        reloadFilters();
                    }
                });
                builder.setView(inflatedView);
                builder.show();
            }
        });

        this.richcycler = findViewById(R.id.richcycler);

        this.richcycler.adapter.setOnAdapter(new OnAdapter<UserHolder>() {
            @Override
            public UserHolder onCreate(ViewGroup parent, int viewType, View view) {
                return new UserHolder(view);
            }

            @Override
            public void OnBind(UserHolder userHolder, int position) {
                userHolder.tvUserHolder.setText(richcycler.adapter.getObjectList().get(position).name);
            }

            @Override
            public RecyclerView.LayoutManager setLayoutManager(RecyclerView.LayoutManager layoutManager) {
                ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.VERTICAL);
                return layoutManager;
            }
        });

        this.richcycler.adapter.setObjectList(this.fetchUsers());

        this.richcycler.build();

        String json = fetchJson();

        if(json != null){
            try {
                this.richcycler.loadFiltersFromJson(FILTER_ID, json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //this.richcycler.loadFilters(FILTER_ID);
    }

    private void handleResults(){
        // Identified name of filters
        String jobFilter = "job";
        String searchFilter = "search";
        String seekbarFilter = "seekbar_age";
        String radioFilter = "radio_sex";

        // Get results from filters result functions
        List<String> jobFilterResult = ((List<String>) this.richcycler.getFilterResult(FILTER_ID, jobFilter));
        String searchFilterResult = (String) this.richcycler.getFilterResult(FILTER_ID, searchFilter);
        Item radioFilterResult = (Item) this.richcycler.getFilterResult(FILTER_ID, radioFilter);
        Integer seekbarFilterResult = (Integer) this.richcycler.getFilterResult(FILTER_ID, seekbarFilter);

        // Logging
        if(jobFilterResult!=null){
            Log.d("JobFilterResult", jobFilterResult.toString());
        }
        if(searchFilterResult!=null){
            Log.d("SearchFilterResult", searchFilterResult);
        }
        if(radioFilterResult!=null){
            Log.d("RadioFilterResult", radioFilterResult.toString());
        }
        if(seekbarFilterResult!=null){
            Log.d("SeekbarResult", seekbarFilterResult.toString());
        }
    }

    private void reloadFilters(){
        //this.richcycler.reloadFilters(FILTER_ID);
        this.richcycler.clearFiltersStates(FILTER_ID);
    }

    private List<User> fetchUsers(){
        List<User> users = new ArrayList<>();
        users.add(new User("Nihat"));
        users.add(new User("Ali"));
        users.add(new User("Veli"));
        users.add(new User("Nalan"));
        users.add(new User("Haluk"));
        return users;
    }

    private String fetchJson(){
        String json = null;
        try {
            InputStream is = getAssets().open("filters_example.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return json;
    }

    private Context getContext(){
        return this;
    }

}
