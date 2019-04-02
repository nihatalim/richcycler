package tr.com.nihatalim.richcycler.filters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

public class TextBoxFilter extends Filter<String> {
    /**
     * This property is hold an edittext.
     */
    public EditText textBox = null;

    private String entry;

    /**
     * This method is a constructor for creating new instance of CheckBoxFilter
     * @param context This parameter is application context.
     * @param name This parameter is name of filter.
     * @param display This parameter is display of filter.
     * @param renderer This parameter is renderer class of filter.
     * @param type This parameter is type of filter.
     */
    public TextBoxFilter(Context context, String name, String display, String renderer, FilterType type) {
        super(context, name, display, renderer, type);
    }

    /**
     * This method create a view.
     * @return Return rendered view.
     */
    @Override
    public View render() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        textBox = new EditText(this.context);
        textBox.setHint(this.display);
        textBox.setLayoutParams(layoutParams);
        textBox.setText(entry);
        return textBox;
    }

    /**
     * This method is returns result of checkbox results.
     * @return Returns result of checkbox results.
     */
    @Override
    public String result() {
        return this.entry;
    }

    /**
     * This method is clear all checkboxes.
     */
    @Override
    public void clear() {
        this.textBox.setText("");
        this.entry = "";
    }

    @Override
    public void save() {
        this.entry = this.textBox.getText().toString();
    }
}
