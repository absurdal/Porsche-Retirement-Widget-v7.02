package com.retirement986.widget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.graphics.Color;
import android.view.Gravity;

public class MainActivity extends Activity {
 @Override public void onCreate(Bundle state) {
  super.onCreate(state);
  LinearLayout layout = new LinearLayout(this);
  layout.setOrientation(LinearLayout.VERTICAL);
  layout.setGravity(Gravity.CENTER);
  layout.setPadding(48,48,48,48);
  layout.setBackgroundColor(Color.rgb(20,23,27));
  TextView title = new TextView(this);
  title.setText("RETIREMENT 986"); title.setTextSize(27); title.setTextColor(Color.WHITE); title.setGravity(Gravity.CENTER);
  TextView info = new TextView(this);
  info.setText("Emerytura: 07.11.2028\n\nAby dodać widget: przytrzymaj pusty obszar ekranu głównego → Widgety → Retirement 986.\n\nZegar aktualizuje się automatycznie.");
  info.setTextSize(17); info.setTextColor(Color.LTGRAY); info.setGravity(Gravity.CENTER);
  layout.addView(title); layout.addView(info); setContentView(layout);
  AppWidgetManager mgr = AppWidgetManager.getInstance(this);
  for(int id: mgr.getAppWidgetIds(new ComponentName(this, RetirementWidget.class))) RetirementWidget.update(this,mgr,id);
 }
}
