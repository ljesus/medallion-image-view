Medallion Image View for Android
====================

Medallion Image View is a Custom View built for Android that supports displaying a rounded image with a border. It supports customizable attributes for border width and color and it scales by default within the predefined width and height.
It is distributed with a sample project to show its' usage.

<img src="http://i.imgur.com/aDSwgai.png"></img>


Usage
-------------------------

Simple copy MedallionImageView package and the attrs.xml into your project. Below is an example of usage:

    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:miv="http://schemas.android.com/apk/res/pt.luisjesus.medallionimageview"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

    <pt.luisjesus.medallionimageview.MedallionImageView
        android:id="@+id/medallionImageView"
        android:layout_width="128dip"
        android:layout_height="128dip"
        miv:image="@drawable/image1"
        miv:borderColor="@android:color/white"
        miv:borderWidth="8" />

    </LinearLayout>



Known Issues
-------------------------

* The border width does not take into account the dips of the device.
* When using big sizes sometimes the image is badly stretched.
