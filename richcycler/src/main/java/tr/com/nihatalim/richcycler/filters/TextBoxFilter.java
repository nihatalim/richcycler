package tr.com.nihatalim.richcycler.filters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TextBoxFilter extends Filter<String> {
    private EditText textBox = null;

    public TextBoxFilter(Context context, String name, String display, String renderer, FilterType type) {
        super(context, name, display, renderer, type);
    }

    @Override
    public View render() {
        LinearLayout linearLayout = new LinearLayout(this.context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(layoutParams);

        if(textBox==null){
            textBox = new EditText(this.context);
            textBox.setHint(this.display);
            textBox.setLayoutParams(layoutParams);
        }else{
            ((ViewGroup) textBox.getParent()).removeView(textBox);
        }
        return textBox;
    }

    @Override
    public String result() {
        return textBox.getText().toString();
    }

    @Override
    public void clear() {
        this.textBox.setText("");
    }
}
