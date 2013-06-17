package pt.luisjesus.medallionimageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * Medallion Image View is a Custom View built for Android that supports displaying a rounded image with a border. 
 * GitHub Project at {@link https://github.com/ljesus/medallion-image-view}
 * @author Lu’s Jesus
 */

public class MedallionImageView extends View {
	private int viewWidth;
	private int viewHeight;
	private Bitmap image;

	private BitmapShader shader;
	private Paint paint;
	private Paint paintBorder;
	
	//Attributes
	private Drawable d;
	private int borderWidth = 3;
	private int borderColor = android.R.color.darker_gray;

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
			       borderWidth = a.getInteger(R.styleable.MedallionImageView_borderWidth, borderWidth);
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
			       borderWidth = a.getInteger(R.styleable.MedallionImageView_borderWidth, borderWidth);
			       d = a.getDrawable(R.styleable.MedallionImageView_image);

			   } finally {
			       a.recycle();
			   }
			   
		setup();
	}

	private void setup()
	{
		loadBitmap(); 
	}

	public void setBorderWidth(int borderWidth)
	{
		this.borderWidth = borderWidth;
		preloadPaints();
		this.invalidate();
	}

	public void setBorderColor(int borderColor)
	{       
		this.borderColor = borderColor;
		preloadPaints();
		this.invalidate();
	}

	private void loadBitmap()
	{
		image = ((BitmapDrawable)d).getBitmap();
	}

	@Override
	public void onDraw(Canvas canvas)
	{
		if(image !=null)
		{
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
			result = specSize;
		} else {
			result = viewWidth;
		}

		return result;
	}

	private int measureHeight(int measureSpecHeight, int measureSpecWidth) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpecHeight);
		int specSize = MeasureSpec.getSize(measureSpecHeight);

		if (specMode == MeasureSpec.EXACTLY) {
			result = specSize;
		} else {
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
		
		preloadPaints();
	}

	private void preloadPaints() {
		shader = new BitmapShader (image,  TileMode.CLAMP, TileMode.CLAMP);

		paint = new Paint();
		paint.setShader(shader);
		paint.setAntiAlias(true);

		paintBorder = new Paint();
		paintBorder.setColor(borderColor);
		paintBorder.setAntiAlias(true);			
	}

	public void showInDebug(String s){
		TextView wDebugView = (TextView)this.getRootView().findViewWithTag("debug");
		wDebugView.setVisibility(View.VISIBLE);
		wDebugView.setText(s);
	}

}
