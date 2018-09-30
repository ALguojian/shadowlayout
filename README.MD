## 绘制阴影，可以指定绘制的方向，默认全部

### 使用如下：

[![](https://jitpack.io/v/ALguojian/shadowlayout.svg)](https://jitpack.io/#ALguojian/shadowlayout)

Step 1. Add the JitPack repository to your build file

```
allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }
```

Step 2. Add the dependency
```
dependencies {
             implementation 'com.github.ALguojian:shadowlayout:Tag'
        }
```

### 继承自framlayout，在xml中使用

```
   <com.alguojian.widget.ShadowLayout
         android:layout_width="wrap_content"
         android:layout_height="wrap_content"
         app:shadowColor="#fcff0000"
         app:shadowDirection="top|bottom|right"
         app:shadowLength="8dp"
         app:shadowRadius="8dp"
         app:shadowTranslationX="8dp"
         app:shadowTranslationY="8dp">

         <TextView
             android:layout_width="wrap_content"
             android:layout_height="wrap_content"
             android:background="#fff"
             android:padding="40dp"
             android:text="Hello World!" />

     </com.alguojian.widget.ShadowLayout>
```

### 属性如下

|属性| 功能|
|---|---|
|shadowColor|阴影颜色，必须带有透明度|
|shadowDirection | 指定绘制方向，多选|
|shadowLength | 阴影的长度，即扩散范围|
|shadowRadius | 阴影角度|
|shadowTranslationX | 阴影在x方向的位移，默认0|
|shadowTranslationY| 阴影在Y方向的位移，默认0|





