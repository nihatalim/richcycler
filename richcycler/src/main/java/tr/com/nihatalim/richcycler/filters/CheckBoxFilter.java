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
    /**
     * This list of checkboxes stored inside.
     */
    private List<CheckBox> checkBoxes = new ArrayList<>();

    /**
     * This method is a constructor for creating new instance of CheckBoxFilter
     * @param context This parameter is application context.
     * @param name This parameter is name of filter.
     * @param display This parameter is display of filter.
     * @param renderer This parameter is renderer class of filter.
     * @param type This parameter is type of filter.
     */
    public CheckBoxFilter(Context context, String name, String display, String renderer, FilterType type) {
        super(context, name, display, renderer, type);
    }

    /**
     * This method create a view.
     * @return Return rendered view.
     */
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

    /**
     * This method is returns result of checkbox results.
     * @return Returns result of checkbox results.
     */
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

    /**
     * This method is clear all checkboxes.
     */
    @Override
    public void clear() {
        this.checkBoxes.clear();
    }
}