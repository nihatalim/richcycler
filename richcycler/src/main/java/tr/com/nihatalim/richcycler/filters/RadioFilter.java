package tr.com.nihatalim.richcycler.filters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import tr.com.nihatalim.richcycler.R;

public class RadioFilter extends Filter<Item> {

    private RadioGroup radioGroup;
    private Item checkedItem;

    public RadioFilter(Context context, String name, String display, String renderer, FilterType type) {
        super(context, name, display, renderer, type);
    }

    @Override
    public View render() {
        View view = LayoutInflater.from(context).inflate(R.layout.radio_view, null);

        TextView tvRdRich = view.findViewById(R.id.tvRdRich);
        tvRdRich.setText(this.display);

        this.radioGroup = view.findViewById(R.id.rgRich);

        for (Item item: items) {
            RadioButton rd = (RadioButton) LayoutInflater.from(context).inflate(R.layout.radio_button_view, null);
            rd.setText(item.display);
            this.radioGroup.addView(rd);

            if(checkedItem == item){
                this.radioGroup.check(rd.getId());
            }
        }

        return view;
    }

    @Override
    public Item result() {
        return checkedItem;
    }

    @Override
    public void clear() {
        checkedItem = null;
        this.radioGroup.clearCheck();
    }

    @Override
    public void save() {
        for(int i = 0; i < this.radioGroup.getChildCount(); i++){
            RadioButton radioButton = (RadioButton) this.radioGroup.getChildAt(i);
            if(radioButton.isChecked()){
                checkedItem = getItemWithDisplay(radioButton.getText().toString());
            }
        }
    }
}
