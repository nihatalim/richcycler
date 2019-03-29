package tr.com.nihatalim.richcycler.filters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CheckBoxFilter extends Filter<List<String>> {
    private List<CheckBox> checkBoxes = new ArrayList<>();

    public CheckBoxFilter(Context context, String name, String display, String renderer, FilterType type) {
        super(context, name, display, renderer, type);
    }

    @Override
    public View render() {
        LinearLayout linearLayout = new LinearLayout(this.context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(layoutParams);

        TextView textView = new TextView(context);
        textView.setLayoutParams(layoutParams);
        textView.setTextSize(18);
        textView.setText(this.display);
        linearLayout.addView(textView);

        if(checkBoxes.size() > 0 && !checkBoxes.contains(null)){
            for (CheckBox cb:checkBoxes) {
                ((ViewGroup) cb.getParent()).removeView(cb);
                linearLayout.addView(cb);
            }
        }
        else{
            for (Item item:items) {
                CheckBox cb = new CheckBox(context);
                cb.setText(item.display);
                this.checkBoxes.add(cb);
                linearLayout.addView(cb);
            }
        }

        return linearLayout;
    }

    @Override
    public List<String> result() {
        List<String> result = new ArrayList<>();

        for (CheckBox cb:this.checkBoxes) {
            if(cb.isChecked()){
                Item item = this.getItemWithDisplay(cb.getText().toString());
                if(item != null){
                    result.add(item.value);
                }
            }
        }
        return result;
    }

    @Override
    public void clear() {
        this.checkBoxes.clear();
    }
}