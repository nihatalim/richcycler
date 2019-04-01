package tr.com.nihatalim.richcycler.parsers;

import android.content.Context;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import tr.com.nihatalim.richcycler.filters.Filter;
import tr.com.nihatalim.richcycler.filters.FilterType;
import tr.com.nihatalim.richcycler.filters.Item;

/**
 * This class is contains parsing methods of xml file.
 * 
 * @Author Nihat ALİM
 */
public class XmlParser {

    /**
     * This method is perform parsing xml file and create list of filter.
     * @param xmlFileName This parameter is file name of which contains filters.
     * @param context This parameter is a context instance of your application.
     * @return Returns list of filter after parse xml file.
     */
    public static List<Filter> parse(String xmlFileName, Context context){
        List<Filter> filters = null;
        Filter filter = null;
        Item item = null;

        XmlPullParserFactory pullParserFactory;

        try {
            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();

            InputStream in_s = context.getAssets().open(xmlFileName + ".xml");

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
                            Class renderClass = Class.forName(parser.getAttributeValue(null, "renderer"));
                            Class<?> param[] = new Class[5];
                            param[0] = Context.class;
                            param[1] = String.class;
                            param[2] = String.class;
                            param[3] = String.class;
                            param[4] = FilterType.class;

                            Object [] initargs = new Object[5];
                            initargs[0] = context;
                            initargs[1] = parser.getAttributeValue(null, "name");
                            initargs[2] = parser.getAttributeValue(null, "display");
                            initargs[3] = parser.getAttributeValue(null, "renderer");
                            initargs[4] = FilterType.getType(parser.getAttributeValue(null, "type"));

                            final Object o = renderClass.getConstructor(param).newInstance(initargs);
                            filter = ((Filter) o);

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
                            filters.add(filter);
                        }
                        break;
                }
                eventType = parser.next();
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
