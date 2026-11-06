package com.retirement986.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.Typeface;
import java.time.LocalDate;

/** V7: user-retouched matching bitmap backgrounds; central needle and digital readouts. */
public final class GaugeRenderer {
 private static final int W=1774,H=887;
 private static final int AMBER=Color.rgb(255,154,28);
 private GaugeRenderer() {}
 private static Paint paint(int color){Paint p=new Paint(Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG);p.setColor(color);return p;}
 private static void led(Canvas c,String text,float x,float baseline,int size){
  Paint p=paint(AMBER);p.setTypeface(Typeface.create("monospace",Typeface.BOLD));
  p.setTextAlign(Paint.Align.CENTER);p.setTextSize(size);p.setShadowLayer(9,0,0,Color.rgb(190,66,0));c.drawText(text,x,baseline,p);
 }
 /** Needle pointing at angle in Android canvas coordinates: 0=right, 90=down. */
 private static void needle(Canvas c,float x,float y,float length,double degrees,float width){
  c.save();c.translate(x,y);c.rotate((float)degrees);
  Path shape=new Path();shape.moveTo(-21,-width*.60f);shape.lineTo(18,-width*.63f);
  shape.lineTo(length-17,-width*.18f);shape.lineTo(length,0);
  shape.lineTo(length-17,width*.18f);shape.lineTo(18,width*.63f);
  shape.lineTo(-21,width*.60f);shape.close();
  Paint shadow=paint(Color.argb(155,0,0,0));shadow.setShadowLayer(13,4,7,Color.argb(190,0,0,0));
  c.save();c.translate(2,5);c.drawPath(shape,shadow);c.restore();
  Paint red=paint(Color.RED);red.setShader(new LinearGradient(0,-width,0,width,
    new int[]{Color.rgb(255,164,129),Color.rgb(244,42,27),Color.rgb(144,8,9)},null,Shader.TileMode.CLAMP));
  c.drawPath(shape,red);
  Paint glint=paint(Color.argb(165,255,219,187));glint.setStrokeWidth(2.3f);
  c.drawLine(23,-width*.30f,length-21,-1.5f,glint);
  c.restore();
  Paint rim=paint(Color.rgb(7,8,10));rim.setShadowLayer(8,2,4,Color.argb(160,0,0,0));c.drawCircle(x,y,35,rim);
  Paint ring=paint(Color.rgb(62,64,67));ring.setStyle(Paint.Style.STROKE);ring.setStrokeWidth(3);c.drawCircle(x,y,31,ring);
  Paint hub=paint(Color.BLACK);hub.setShader(new RadialGradient(x-10,y-12,48,
    new int[]{Color.rgb(70,73,76),Color.rgb(25,27,30),Color.rgb(5,6,8)},null,Shader.TileMode.CLAMP));c.drawCircle(x,y,28,hub);
  c.drawCircle(x-9,y-10,7,paint(Color.argb(75,255,255,255)));
 }
 public static Bitmap draw(Context context,int width,int height,long days){
  int appeal=(Math.floorMod(LocalDate.now().toEpochDay(),2)==0)?60:40;
  Bitmap base=BitmapFactory.decodeResource(context.getResources(),appeal==60?R.drawable.cluster_60:R.drawable.cluster_40);
  Bitmap output=Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
  Canvas canvas=new Canvas(output);float scale=Math.min(width/(float)W,height/(float)H)*1.045f;
  canvas.save();canvas.translate((width-W*scale)/2f,(height-H*scale)/2f);canvas.scale(scale,scale);
  canvas.drawBitmap(base,0,0,paint(Color.WHITE));
  long remaining=Math.max(0,days);
  // Gauge runs counter-clockwise from 0 (bottom-left, 155 deg) to 7 (right, 360 deg).
  // Unlike V3, it travels through the upper dial instead of pointing downward.
  double angle=150.0+210.0*remaining/700.0;
  angle=Math.min(375.0,angle); // 700 exactly at 7; >700 just beyond the red 7 mark
  needle(canvas,859,416,274,angle,23);
  // Day-stable reading; changing the phone date lets us verify the dynamic needle.
  // Left needle is already part of the selected 40/60 bitmap. Do not draw a second one.
  led(canvas,Long.toString(remaining),875,639,96);
  led(canvas,"07.11.2028",875,682,43);
  led(canvas,appeal+"%",440,666,66);
  canvas.restore();base.recycle();return output;
 }
}
