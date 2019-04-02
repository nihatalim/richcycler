package tr.com.nihatalim.richcycler.filters;

import android.content.Context;
import android.text.InputType;
import android.view.View;

public class TextBoxNumberFilter extends TextBoxFilter {
    /**
     * This method is a constructor for creating new instance of CheckBoxFilter
     *
     * @param context  This parameter is application context.
     * @param name     This parameter is name of filter.
     * @param display  This parameter is display of filter.
     * @param renderer This parameter is renderer class of filter.
     * @param type     This parameter is type of filter.
     */
    public TextBoxNumberFilter(Context context, String name, String display, String renderer, FilterType type) {
        super(context, name, display, renderer, type);
    }

    @Override
    public View render() {
        View view = super.render();
        this.textBox.setInputType(InputType.TYPE_CLASS_NUMBER);
        return view;
    }

    @Override
    public String result() {
        return super.result();
    }

    @Override
    public void clear() {
        super.clear();
    }

    @Override
    public void save() {
        super.save();
    }
}
