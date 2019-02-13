# MiniTicketView
Small UI library for a custom frame layout

### Specs
[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=21)
[![Download](https://api.bintray.com/packages/mclondi/android/MiniTicketView/images/download.svg)](https://bintray.com/mclondi/android/MiniTicketView/_latestVersion)
[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](https://github.com/vipulasri/Timeline-View/blob/master/LICENSE)

![img](https://github.com/MClondi/MiniTicketView/blob/master/art/img.png)

## Quick Setup

### 1. Include library

**Using Gradle**

``` gradle
compile 'com.mclondi:miniticketview:1.0.0'
```

### 2. Use

``` java
<com.mclondi.miniticketview.MiniTicketView
        android:layout_width="wrap_content"
		android:layout_height="wrap_content"
        android:id="@+id/ticketView"
		app:ticketBackgroundDrawable="@drawable/bg_gradient_content"
		app:ticketPerforationBackgroundDrawable="@drawable/bg_gradient_perforation"
		app:ticketPerforationPositionPercent="20"
		app:ticketScallopRadius="4dp"
		app:ticketShowDivider="true"
		app:ticketDividerPadding="1dp"
		app:ticketDividerColor="@android:color/background_dark"
		app:ticketDividerType="dash"
		app:ticketDividerDashLength="4dp"
		app:ticketCornerRadius="9dp"		
		>
		
</com.mclondi.miniticketview.MiniTicketView>
```
