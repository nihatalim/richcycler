# Richcycler
Generic RecyclerView implementation with filters (xml and json supported), swiper and pagination. For more details, [READ WIKI](https://github.com/nihatalim/richcycler/wiki/0.Home).

# Step 1: Installation

## a) Add it in your root build.gradle at the end of repositories

```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

## b) Add the dependency

```
dependencies {
    implementation 'com.github.nihatalim:richcycler:v1.0.1'
}
```

# Step 2: Usage

## a) Define on layout file like this:

```
<tr.com.nihatalim.richcycler.views.Richcycler
        android:id="@+id/richcycler"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:holder="@layout/holder_user"
        app:filter="filters_example"
        />
```

app:holder is your recyclerview holder layout.

app:filter is your defined default filter file in assets folder. You have not to define.

## b) Define Richcycler instance on the class level in class file of defined layout:

```
private Richcycler<UserHolder, User> richcycler;
```

UserHolder is a holder type.

User is a model type.


## c) Inflate from layout file:
```
this.richcycler = findViewById(R.id.richcycler);
```

## d) Set OnAdapter interface to generic adapter inside of richcycler:
```
this.richcycler.adapter.setOnAdapter(new OnAdapter<UserHolder>() {
    @Override
    public UserHolder onCreate(ViewGroup parent, int viewType, View view) {
        return new UserHolder(view);
    }

    @Override
    public void OnBind(UserHolder userHolder, int position) {
        userHolder.tvUserHolder.setText(richcycler.adapter.getObjectList().get(position).name);
    }

    @Override
    public RecyclerView.LayoutManager setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.VERTICAL);
        return layoutManager;
    }
});
```

## e) Set item list if you want:
```
this.richcycler.adapter.setObjectList(fetchUsers());
```

## f) Build richcycler:
```
this.richcycler.build();
```

## g) Add snap functionality 

```
// You may use snap functionality for swipe only one item. To do this you need to set orientation of LayoutManager as HORIZONTAL.
adapter.snap(true);
```

# Step 3: Filters

## a) Filter xml file structure:
```
<?xml version="1.0" encoding="utf-8"?>
<filters>
    <filter name="job" display="Job" type="checkbox"
        renderer="tr.com.nihatalim.richcycler.filters.CheckBoxFilter">
        <item value="engineer" display="Engineer"/>
        <item value="worker" display="Worker"/>
    </filter>

    <filter name="search" display="Search" type="textbox"
        renderer="tr.com.nihatalim.richcycler.filters.TextBoxFilter">
    </filter>

    <filter name="age" display="Age" type="number"
        renderer="tr.com.nihatalim.richcycler.filters.TextBoxNumberFilter">
    </filter>

    <filter name="spinner_sex" display="Sex" type="number"
        renderer="tr.com.nihatalim.richcycler.filters.SpinnerFilter">
        <item value="male" display="Male" />
        <item value="female" display="Female" />
        <item value="ball" display="Ball" />
    </filter>

    <filter name="radio_sex" display="Sex" type="radio"
        renderer="tr.com.nihatalim.richcycler.filters.RadioFilter">
        <item value="male" display="Male" />
        <item value="female" display="Female" />
        <item value="ball" display="Ball" />
    </filter>

    <filter name="seekbar_age" display="Age" type="seekbar"
        renderer="tr.com.nihatalim.richcycler.filters.SeekbarFilter">
        <item value="min" display="0" />
        <item value="max" display="100" />
        <item value="step" display="1" />
    </filter>
</filters>
```

## b) Load filters from xml file:
```
this.richcycler.loadFilters("MY_FILTERS_ID", "my_filter_file");
```

## c) Filter json file structure:
```
{
	"filters": [
			{
				"items": [
					{
						"value": "engineer",
						"display": "Engineer"
					},
					{
						"value": "lawyer",
						"display": "Lawyer"
					},
					{
						"value": "teacher",
						"display": "Teacher"
					},
					{
						"value": "student",
						"display": "Student"
					},
					{
						"value": "worker",
						"display": "Worker"
					}
				],
				"name": "job",
				"display": "Job",
				"type": "checkbox",
				"renderer": "tr.com.nihatalim.richcycler.filters.CheckBoxFilter"
			},
			{
				"name": "search",
				"display": "Search",
				"type": "textbox",
				"renderer": "tr.com.nihatalim.richcycler.filters.TextBoxFilter"
			},
			{
				"name": "age",
				"display": "Age",
				"type": "number",
				"renderer": "tr.com.nihatalim.richcycler.filters.TextBoxNumberFilter"
			},
			{
				"items": [
					{
						"value": "male",
						"display": "Male"
					},
					{
						"value": "female",
						"display": "Female"
					},
					{
						"value": "ball",
						"display": "Ball"
					}
				],
				"name": "spinner sex",
				"display": "Sex",
				"type": "number",
				"renderer": "tr.com.nihatalim.richcycler.filters.SpinnerFilter"
			},
			{
				"items": [
					{
						"value": "male",
						"display": "Male"
					},
					{
						"value": "female",
						"display": "Female"
					},
					{
						"value": "ball",
						"display": "Ball"
					}
				],
				"name": "radio sex",
				"display": "Sex",
				"type": "radio",
				"renderer": "tr.com.nihatalim.richcycler.filters.RadioFilter"
			},
			{
				"items": [
					{
						"value": "min",
						"display": "0"
					},
					{
						"value": "max",
						"display": "100"
					},
					{
						"value": "step",
						"display": "1"
					}
				],
				"name": "seekbar age",
				"display": "Age",
				"type": "seekbar",
				"renderer": "tr.com.nihatalim.richcycler.filters.SeekbarFilter"
			}
	]
}
```

## d) Load filters from json file:
```
this.richcycler.loadFilters("MY_FILTERS_ID", "my_filter_file");
```

## e) Load filters from json:
```
this.richcycler.loadFiltersFromJson("MY_FILTERS_ID", jsonAsString);
```

Filters are stored with an identity inside map collection. At the first argument you pass an identity. After that you always use this identity because this framework is support working multiple filter file.

my_filter_file is an xml file stored in assets folder. (my_filter_file.xml) If you already work only a filter file, you don't have to define this parameter.


## c) Than magic:

![Alt Text](https://github.com/nihatalim/richcycler/raw/master/richfilter.png)
