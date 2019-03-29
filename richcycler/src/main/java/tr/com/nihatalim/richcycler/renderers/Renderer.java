package tr.com.nihatalim.richcycler.renderers;

import android.view.View;

public interface Renderer<T1> {
    public View render();
    public T1 result();
    public void clear();
}
