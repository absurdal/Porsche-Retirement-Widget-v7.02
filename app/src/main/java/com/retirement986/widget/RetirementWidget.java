package com.retirement986.widget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.RemoteViews;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public class RetirementWidget extends AppWidgetProvider {
 public static final LocalDate RETIREMENT=LocalDate.of(2028,11,7);
 private static final String REFRESH="com.retirement986.widget.REFRESH";
 @Override public void onUpdate(Context c,AppWidgetManager m,int[] ids){for(int id:ids)update(c,m,id);schedule(c);}
 @Override public void onAppWidgetOptionsChanged(Context c,AppWidgetManager m,int id,Bundle opts){update(c,m,id);}
 @Override public void onEnabled(Context c){schedule(c);}
 @Override public void onReceive(Context c,Intent intent){
  super.onReceive(c,intent);
  String action=intent.getAction();
  if(REFRESH.equals(action)||Intent.ACTION_DATE_CHANGED.equals(action)||Intent.ACTION_TIME_CHANGED.equals(action)||Intent.ACTION_TIMEZONE_CHANGED.equals(action)||Intent.ACTION_BOOT_COMPLETED.equals(action)){
   AppWidgetManager m=AppWidgetManager.getInstance(c);
   for(int id:m.getAppWidgetIds(new ComponentName(c,RetirementWidget.class)))update(c,m,id);
   schedule(c);
  }
 }
 private static void schedule(Context c){
  AlarmManager am=(AlarmManager)c.getSystemService(Context.ALARM_SERVICE);
  if(am==null)return;
  Intent i=new Intent(c,RetirementWidget.class).setAction(REFRESH);
  PendingIntent pi=PendingIntent.getBroadcast(c,986,i,PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE);
  long next=LocalDate.now().plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()+60000;
  am.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,next,pi);
 }
 public static void update(Context c,AppWidgetManager m,int id){
  Bundle opts=m.getAppWidgetOptions(id);
  int w=Math.max(250,opts.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH,300));
  int h=Math.max(130,opts.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT,150));
  // Bounded bitmap avoids exceeding the Android RemoteViews binder size limit.
  int pxW=Math.min(1000,Math.max(500,w*2));
  int pxH=Math.min(600,Math.max(250,h*2));
  long days=ChronoUnit.DAYS.between(LocalDate.now(),RETIREMENT);
  Bitmap bmp=GaugeRenderer.draw(c,pxW,pxH,days);
  RemoteViews views=new RemoteViews(c.getPackageName(),R.layout.widget_layout);
  views.setImageViewBitmap(R.id.gauges,bmp);
  PendingIntent pi=PendingIntent.getActivity(c,0,new Intent(c,MainActivity.class),PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE);
  views.setOnClickPendingIntent(R.id.gauges,pi);
  m.updateAppWidget(id,views);

 }
}
