package tr.com.nihatalim.richcycler.adapter;

import android.os.Bundle;

public interface OnPaginate<TModel> {
    void paginate(int nextPageNumber, int paginationSize, TModel firstItem, TModel lastItem, Bundle bundle);
}
