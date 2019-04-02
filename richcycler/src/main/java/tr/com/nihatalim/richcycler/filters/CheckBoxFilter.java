package tr.com.nihatalim.richcycler.filters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tr.com.nihatalim.richcycler.R;

public class CheckBoxFilter extends Filter<List<Item>> {
    /**
     * This list of checked items stored inside.
     */
    private List<Item> checkedItems = new ArrayList<>();

    /**
     * Container of checkboxes
     */
    private LinearLayout llCbRich;

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
        View view = LayoutInflater.from(context).inflate(R.layout.checkbox_view, null);

        TextView tvCbRich = view.findViewById(R.id.tvCbRich);
        tvCbRich.setText(this.display);

        llCbRich = view.findViewById(R.id.llCbRich);

        for (Item item : items) {
            CheckBox checkBox = (CheckBox) LayoutInflater.from(context).inflate(R.layout.checkbox_cb_view, null);
            checkBox.setText(item.display);
            if(this.checkedItems.contains(item)) checkBox.setChecked(true);
            this.llCbRich.addView(checkBox);
        }

        return view;
    }

    /**
     * This method is returns result of checkbox results.
     * @return Returns result of checkbox results.
     */
    @Override
    public List<Item> result() {
        return this.checkedItems;
    }

    /**
     * This method is clear all checkboxes.
     */
    @Override
    public void clear() {
        this.checkedItems.clear();
    }


    /**
     * This method called for save current status of filter object and saving state.
     */
    @Override
    public void save() {
        for(int i = 0; i < this.llCbRich.getChildCount(); i++){
            CheckBox checkBox = (CheckBox) this.llCbRich.getChildAt(i);
            if(checkBox.isChecked()){
                this.checkedItems.add(getItemWithDisplay(checkBox.getText().toString()));
            }
        }
    }
}