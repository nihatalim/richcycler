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
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

import tr.com.nihatalim.richcycler.adapter.OnAdapter;
import tr.com.nihatalim.richcycler.filters.Filter;
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

                for (View v:richcycler.getViews(FILTER_ID)) {
                    linearLayout.addView(v);
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
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

        this.richcycler.loadFilters(FILTER_ID);
    }

    private Context getContext(){
        return this;
    }

    private void handleResults(){
        String priceFilter = "price";
        String topicFilter = "topic";
        String jobFilter = "job";
        String searchFilter = "search";

        List<String> priceFilterResult = (List<String>) this.richcycler.getFilterResult(FILTER_ID, priceFilter);
        List<String> topicFilterResult = ((List<String>) this.richcycler.getFilterResult(FILTER_ID, topicFilter));
        List<String> jobFilterResult = ((List<String>) this.richcycler.getFilterResult(FILTER_ID, jobFilter));
        String searchFilterResult = (String) this.richcycler.getFilterResult(FILTER_ID, searchFilter);

        Log.d("PriceFilterResult", priceFilterResult.toString());
        Log.d("TopicFilterResult", topicFilterResult.toString());
        Log.d("JobFilterResult", jobFilterResult.toString());
        Log.d("SearchFilterResult", searchFilterResult);
    }


    private void reloadFilters(){
        this.richcycler.reloadFilters(FILTER_ID);
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
}
