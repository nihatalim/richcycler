package tr.com.nihatalim.richcycler.parsers;

import android.content.Context;

import com.google.gson.Gson;
import org.json.JSONException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import tr.com.nihatalim.richcycler.filters.Filter;
import tr.com.nihatalim.richcycler.filters.FilterType;
import tr.com.nihatalim.richcycler.filters.Item;
import tr.com.nihatalim.richcycler.models.JsonFilterModel;

public class JsonParser {

    public static List<Filter> parse(String json, Context context) throws JSONException {
        List<Filter> filters = new ArrayList<>();
        Filter filter;
        Gson gson = null;
        Class<?> param[];
        Object [] initargs;

        param = new Class[5];
        param[0] = Context.class;
        param[1] = String.class;
        param[2] = String.class;
        param[3] = String.class;
        param[4] = FilterType.class;

        initargs = new Object[5];
        initargs[0] = context;

        gson = new Gson();

        JsonFilterModel jsonFilterModel = gson.fromJson(json, JsonFilterModel.class);

        for (tr.com.nihatalim.richcycler.models.Filter f : jsonFilterModel.filters) {
            try {
                initargs[1] = f.name;
                initargs[2] = f.display;
                initargs[3] = f.renderer;
                initargs[4] = f.type;

                Class renderClass = Class.forName(f.renderer);
                final Object o = renderClass.getConstructor(param).newInstance(initargs);

                filter = ((Filter) o);

                filter.items = new ArrayList();

                if(f.items!= null && f.items.size()>0) {
                    for (tr.com.nihatalim.richcycler.models.Item item : f.items) {
                        filter.items.add(new Item(item.value, item.display));
                    }
                }
                filters.add(filter);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return filters;
    }
}
