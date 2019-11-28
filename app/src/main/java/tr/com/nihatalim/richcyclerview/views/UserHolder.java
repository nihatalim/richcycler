package tr.com.nihatalim.richcyclerview.views;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import tr.com.nihatalim.richcyclerview.R;

public class UserHolder extends RecyclerView.ViewHolder{
    public TextView tvUserHolder;

    public UserHolder(@NonNull View itemView) {
        super(itemView);
        this.tvUserHolder = itemView.findViewById(R.id.tvUserHolder);
    }
}
