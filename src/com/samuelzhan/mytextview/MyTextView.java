package com.samuelzhan.mytextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class MyTextView extends View {
	
	private String text;
	private int textSize;
	private Paint paint;

	public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		
		//顾名思义，获取风格和属性，得到一个包含各种属性的数组array,包括你自定义的attr属性
		//R.styleable.MyTextView就是一个指向你刚在attrs.xml中自定义的属性数组的id
		TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.MyTextView);
		
		//获取文本内容
		text=array.getString(R.styleable.MyTextView_text);
		
		//获取文本字体大小，第二个参数是默认值，就是没有使用你定义属性时的提供值, sp2px()是sp转px函数。
		textSize=array.getDimensionPixelSize(R.styleable.MyTextView_textSize, sp2px(18));
		
		//这玩意初始化完成后务必回收
		array.recycle();
		
		//画笔初始化，用于后面的绘图；
		paint=new Paint();
		
		//至此，完成变量的初始化
		paint.setTextSize(textSize);
	}

	public MyTextView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		// TODO Auto-generated constructor stub
		
		//若使用xml加载view,必须要重写上面或这个构造体
	}

	public MyTextView(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
		
		//统一到第一个构造体进行初始化
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		
		//widthMeasureSpec参数可以被MeasureSpec类的静态方法解析出宽度计算的模式和值
		//模式有AT_MOST, EXACTLY, UNSPECIFIED
		int width=measureViewWidth(widthMeasureSpec);
		
		//计算高度，和宽度处理差不多
		int height=measureViewHeight(heightMeasureSpec);
		
		setMeasuredDimension(width, height);
	}
	
	//处理view的宽度
	private int measureViewWidth(int widthSpec){
		int result=0;
		
		int mode=MeasureSpec.getMode(widthSpec);
		int width=MeasureSpec.getSize(widthSpec);
		
		//对应wrap_content, viewgroup只提供一个最大值，子view尺寸不能超过这个值
		//这种情况下，可以根据内容大小设置view的大小，如令view的width=text的宽度
		if(mode==MeasureSpec.AT_MOST){
			int textWidth=measureTextWidth();
			result=Math.min(textWidth, width);
		}
		//对应match_parent或指定的值，viewgourp提供的值为parent的宽度或指定的宽度
		if(mode==MeasureSpec.EXACTLY){
			result=width;
		}
				
		return result;
	}
	
	//处理view的高度
	private int measureViewHeight(int heightSpec){
		int result=0;
		
		int mode=MeasureSpec.getMode(heightSpec);
		int height=MeasureSpec.getSize(heightSpec);
		
		if(mode==MeasureSpec.AT_MOST){
			int textHeight=measureTextHeight();
			result=Math.min(textHeight, height);
		}
		if(mode==MeasureSpec.EXACTLY){
			result=height;
		}
	
		return result;
	}
	
	//测量text的宽度
	private int measureTextWidth(){
		int textWidth=(int) paint.measureText(text);		
		return textWidth;
	}
	
	//测量text的高度
	private int measureTextHeight(){
		FontMetrics fm=paint.getFontMetrics();
		int textHeight=(int) (fm.bottom-fm.top);
		return textHeight;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		
		//把view的背景绘成黄色
		canvas.drawColor(Color.YELLOW);
		
		//测量绘制字体的高度
		FontMetrics fm=paint.getFontMetrics();
		int textHeight=(int) (fm.bottom-fm.top);
		
		//参数1.要绘制的文本， 2.文本左边位于view的x坐标， 3.文本baseline位于view的y坐标, 4.画笔
		//因为baseline到文本底部的距离无法获取，只能取文本高度的3/10
		canvas.drawText(text, 0, textHeight-textHeight*0.3f, paint);
	}
	
	
	//sp转px单位
	private int sp2px(int sp){
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
	}

}
