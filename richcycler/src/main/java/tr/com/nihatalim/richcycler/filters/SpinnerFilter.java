package tr.com.nihatalim.richcycler.filters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import tr.com.nihatalim.richcycler.R;

public class SpinnerFilter extends Filter<Item> {

    private Spinner spinner;
    private SpinnerCustomAdapter adapter;
    private Item selectedItem;

    public SpinnerFilter(Context context, String name, String display, String renderer, FilterType type) {
        super(context, name, display, renderer, type);
    }

    @Override
    public View render() {
        Item[] spinnerItems = items.toArray(new Item[items.size()]);

        adapter = new SpinnerCustomAdapter(context,android.R.layout.simple_spinner_item, spinnerItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        View view = LayoutInflater.from(context).inflate(R.layout.spinner_view, null);

        TextView tvSpRich = view.findViewById(R.id.tvSpRich);
        tvSpRich.setText(this.display);

        this.spinner = view.findViewById(R.id.spRich);
        this.spinner.setAdapter(adapter);

        if(this.selectedItem != null){
            this.spinner.setSelection(adapter.getPosition(this.selectedItem));
        }

        return view;
    }

    @Override
    public Item result() {
        return selectedItem;
    }

    @Override
    public void clear() {
        this.selectedItem = null;
        this.adapter.clear();
    }

    @Override
    public void save() {
        selectedItem = this.adapter.getItem(this.spinner.getSelectedItemPosition());
    }

    public class SpinnerCustomAdapter extends ArrayAdapter<Item>{
        private Item[] _items;

        public SpinnerCustomAdapter(Context context, int resource, Item[] objects) {
            super(context, resource, objects);
            this._items = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView label = (TextView) super.getView(position, convertView, parent);
            label.setText(_items[position].display);
            return label;
        }

        @Override
        public Item getItem(int position) {
            return _items[position];
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            TextView label = (TextView) super.getDropDownView(position, convertView, parent);
            label.setText(_items[position].display);
            return label;
        }
    }
}
