package tr.com.nihatalim.richcyclerview.parser;

import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.util.List;

import tr.com.nihatalim.richcycler.filters.Filter;
import tr.com.nihatalim.richcyclerview.activities.MainActivity;

@RunWith(RobolectricTestRunner.class)
public class XmlParserTest {

    @Test
    public void xml_parser_test(){
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        Context applicationContext = activity.getApplicationContext();

        List<Filter> filters = XmlParser.parse("filters_example", applicationContext);

    }
}
