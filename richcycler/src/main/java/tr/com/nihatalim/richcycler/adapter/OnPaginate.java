package tr.com.nihatalim.richcycler.adapter;

import android.os.Bundle;

public interface OnPaginate<TListObject> {
    void paginate(int nextPageNumber, int paginationSize, TListObject firstItem, TListObject lastItem, Bundle bundle);
}
