package tr.com.nihatalim.richcycler.renderers;

import android.view.View;

/**
 * @param <T1> T1 is spesify result object.
 *
 * @Author Nihat ALİM
 */
public interface Renderer<T1> {
    public View render();
    public T1 result();
    public void clear();
}
