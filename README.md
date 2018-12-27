# PinyinTextView
An Android custom Textview that can display chinese pinyin with tone above.


![Screenshot](sample.png)

1. Include the repository and add the dependency
```
repositories {
    maven {
        url  "https://dl.bintray.com/lostinireland/Pinyin-TextView"
    }
}

implementation 'com.blackjade.pinyintextview:pinyintextview:1.0.0'
```

2. Declare it in your XML:
```xml
<com.blackjade.pinyintextview.PinyinTextView
        android:id="@+id/pinyin"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        android:textSize="50dp"
        android:text="hao3"/>
```

3. You can access it in your code as noraml textview
```
pinyin.text = "Ni3"
```
