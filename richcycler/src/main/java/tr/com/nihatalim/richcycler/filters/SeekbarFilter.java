package tr.com.nihatalim.richcycler.filters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import tr.com.nihatalim.richcycler.R;

public class SeekbarFilter extends Filter<Integer> {

    private int savedValue;
    private SeekBar seekBar;

    private int minValue, maxValue, step;

    public SeekbarFilter(Context context, String name, String display, String renderer, FilterType type) {
        super(context, name, display, renderer, type);
    }

    @Override
    public View render() {

        for (Item item : items) {
            switch (item.value) {
                case "min":
                    minValue = Integer.valueOf(item.display);
                    break;
                case "max":
                    maxValue = Integer.valueOf(item.display);
                    break;
                case "step":
                    step = Integer.valueOf(item.display);
                    break;
            }
        }

        View view = LayoutInflater.from(context).inflate(R.layout.seekbar_view, null);

        TextView tvSbRich = view.findViewById(R.id.tvCbRich);
        tvSbRich.setText(this.display);

        TextView tvMinCbRich = view.findViewById(R.id.tvMinCbRich);
        tvMinCbRich.setText(String.valueOf(this.minValue));

        TextView tvMaxCbRich = view.findViewById(R.id.tvMaxCbRich);
        tvMaxCbRich.setText(String.valueOf(this.maxValue));

        final TextView tvCurCbRich = view.findViewById(R.id.tvCurCbRich);

        this.seekBar = view.findViewById(R.id.sbSbRich);
        this.seekBar.setIndeterminate(false);

        this.seekBar.setMax((this.maxValue - this.minValue) / this.step);

        if(this.savedValue > 0 ) {
            this.seekBar.setProgress((savedValue-minValue)/step);
            tvCurCbRich.setText(String.valueOf(savedValue));
        }

        this.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int value = minValue + (progress * step);
                tvCurCbRich.setText(String.valueOf(value));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        return view;
    }

    @Override
    public Integer result() {
        return savedValue;
    }

    @Override
    public void clear() {
        this.savedValue = 0;
    }

    @Override
    public void save() {
        this.savedValue = minValue + (this.seekBar.getProgress() * step);
    }
}
