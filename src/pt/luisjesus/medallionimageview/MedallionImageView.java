package pt.luisjesus.medallionimageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

public class MedallionImageView extends View {
	private int borderWidth = 3;
	private int viewWidth;
	private int viewHeight;
	private Bitmap image;
	private Paint paint;
	private Paint paintBorder;
	private int borderColor;
	private Drawable d;
	
	public MedallionImageView(Context context) {
		super(context);
		setup();
	}

	public MedallionImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		 TypedArray a = context.getTheme().obtainStyledAttributes(
			        attrs,
			        R.styleable.MedallionImageView,
			        0, 0);

			   try {
				   borderColor = a.getInteger(R.styleable.MedallionImageView_borderColor, borderColor);
			       borderWidth = a.getInteger(R.styleable.MedallionImageView_borderWidth, 0);
			       d = a.getDrawable(R.styleable.MedallionImageView_image);
			       

			   } finally {
			       a.recycle();
			   }
		
		setup();
	}

	public MedallionImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		 TypedArray a = context.getTheme().obtainStyledAttributes(
			        attrs,
			        R.styleable.MedallionImageView,
			        0, 0);

			   try {
				   borderColor = a.getInteger(R.styleable.MedallionImageView_borderColor, borderColor);
			       borderWidth = a.getInteger(R.styleable.MedallionImageView_borderWidth, 0);
			       d = a.getDrawable(R.styleable.MedallionImageView_image);

			   } finally {
			       a.recycle();
			   }
			   
		setup();
	}

	private void setup()
	{
		//load the bitmap
		loadBitmap();
		// init paint
		paint = new Paint();
		paint.setAntiAlias(true);

		paintBorder = new Paint();
		setBorderColor(Color.BLUE);
		paintBorder.setAntiAlias(true);     
	}

	public void setBorderWidth(int borderWidth)
	{
		this.borderWidth = borderWidth;
		this.invalidate();
	}

	public void setBorderColor(int borderColor)
	{       
		if(paintBorder != null)
			paintBorder.setColor(borderColor);

		this.invalidate();
	}

	private void loadBitmap()
	{
		image = ((BitmapDrawable)d).getBitmap();
	}

	@Override
	public void onDraw(Canvas canvas)
	{

		// init shader
		if(image !=null)
		{

			BitmapShader shader = new BitmapShader (image,  TileMode.CLAMP, TileMode.CLAMP);

			Paint paint = new Paint();
			paint.setShader(shader);
			paint.setAntiAlias(true);

			Paint paintBorder = new Paint();
			paintBorder.setColor(borderColor);
			paintBorder.setAntiAlias(true);	

			if(viewWidth >= viewHeight){
				canvas.drawCircle(viewWidth / 2 , viewHeight / 2 , viewHeight / 2, paintBorder);
				canvas.drawCircle(viewWidth / 2 , viewHeight / 2 , viewHeight / 2 - borderWidth, paint);
			} else {
				canvas.drawCircle(viewWidth / 2 , viewHeight / 2 , viewWidth / 2, paintBorder);
				canvas.drawCircle(viewWidth / 2 , viewHeight / 2 , viewWidth / 2 - borderWidth, paint);
			}

		}    

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		//super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		int width = measureWidth(widthMeasureSpec);
		int height = measureHeight(heightMeasureSpec, widthMeasureSpec);        

		viewWidth = width - (borderWidth *2);
		viewHeight = height - (borderWidth*2);

		if(viewWidth <= 0){
			viewWidth = image.getWidth();
		}

		if(viewHeight <= 0){
			viewHeight = image.getHeight();
		} 

		configureBounds();

		setMeasuredDimension(viewWidth, viewHeight);
	}

	private int measureWidth(int measureSpec)
	{
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		if (specMode == MeasureSpec.EXACTLY) {
			// We were told how big to be
			result = specSize;
		} else {
			// Measure the text
			result = viewWidth;
		}

		return result;
	}

	private int measureHeight(int measureSpecHeight, int measureSpecWidth) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpecHeight);
		int specSize = MeasureSpec.getSize(measureSpecHeight);

		if (specMode == MeasureSpec.EXACTLY) {
			// We were told how big to be
			result = specSize;
		} else {
			// Measure the text (beware: ascent is a negative number)
			result = viewHeight;           
		}
		return result;
	}

	private void configureBounds() {
		if (image == null) {
			return;
		}

		int dwidth = image.getWidth();
		int dheight = image.getHeight();

		if (dwidth > viewWidth){
			dwidth = viewWidth;
		}
		if (dheight > viewHeight){
			dheight = viewHeight;
		}

		image = Bitmap.createScaledBitmap(image,(int) dwidth,(int) dheight, false);
	}

	public void showInDebug(String s){
		TextView wDebugView = (TextView)this.getRootView().findViewWithTag("debug");
		wDebugView.setVisibility(View.VISIBLE);
		wDebugView.setText(s);
	}

}
