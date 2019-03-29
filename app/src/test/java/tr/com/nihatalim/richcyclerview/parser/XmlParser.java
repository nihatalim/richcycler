package tr.com.nihatalim.richcyclerview.parser;

import android.content.Context;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import tr.com.nihatalim.richcycler.filters.BaseFilter;
import tr.com.nihatalim.richcycler.filters.Filter;
import tr.com.nihatalim.richcycler.filters.FilterType;
import tr.com.nihatalim.richcycler.filters.Item;

public class XmlParser {

    public static List<Filter> parse(String xmlFileName, Context context){
        List<Filter> filters = null;
        Filter filter = null;
        Item item = null;

        XmlPullParserFactory pullParserFactory;

        try {
            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();

            InputStream in_s = context.getAssets().open(xmlFileName);

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in_s, null);

            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT){
                String name;

                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:
                        filters = new ArrayList<>();
                        break;

                    case XmlPullParser.START_TAG:
                        name = parser.getName();
                        if (name.equals("filter")){
                            filter = new Filter();
                            filter.name = parser.getAttributeValue(null, "name");
                            filter.display = parser.getAttributeValue(null, "display");
                            filter.type = FilterType.valueOf(parser.getAttributeValue(null, "type"));
                            filter.renderer = parser.getAttributeValue(null, "renderer");

                        } else if (filter != null){
                            if (name.equals("item")){
                                item = new Item();
                                item.value = parser.getAttributeValue(null, "value");
                                item.display = parser.getAttributeValue(null, "display");
                                filter.items.add(item);
                            }
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        name = parser.getName();
                        if (name.equalsIgnoreCase("filter") && filter != null){
                            Class renderClass = Class.forName(filter.renderer);
                            Class<?> param[] = new Class[1];
                            param[0] = String.class;

                            String [] initargs = new String[1];
                            initargs[0] = filter.display;

                            final Object o = renderClass.getConstructor(param).newInstance(initargs);
                            filter.filter = (BaseFilter) o;
                            filters.add(filter);
                        }
                        break;
                }

            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return filters;
    }
}
