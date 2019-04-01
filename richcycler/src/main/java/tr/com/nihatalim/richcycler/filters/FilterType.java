package tr.com.nihatalim.richcycler.filters;

public enum FilterType {
    CHECKBOX("checkbox"),
    TEXTBOX("textbox"),
    TEXTBOX_NUMBER("number"),
    SPINNER("spinner"),
    RADIO("radio"),
    SEEKBAR("seekbar"),
    CUSTOM("custom");

    private String type;

    private FilterType(String type){
        this.type = type;
    }

    public static FilterType getType(String type){
        for (FilterType f:FilterType.values()) {
            if(f.type.equals(type)) return f;
        }
        return null;
    }
}