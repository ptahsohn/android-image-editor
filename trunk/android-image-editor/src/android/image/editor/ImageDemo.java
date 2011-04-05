package android.image.editor;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class ImageDemo extends Activity {
	
	Panel panel;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    panel = new Panel(this);
	    setContentView(panel);

	}


	class Panel extends View{

	    private Bitmap  mBitmap;
	    private Canvas  mCanvas;
	    private Path    mPath;
	    private Paint   mPaint;
	    Bitmap bitmap;
	    Canvas pcanvas ;
	    Bitmap bm;
	     int x = 0;
	     int y =0;
	     int r =0;
	    public Panel(Context context) {
	        super(context);

	        //Log.v("Panel", ">>>>>>");

	        setFocusable(true);
	        setBackgroundColor(Color.GREEN);

	        // setting paint 
	             mPaint = new Paint();
	            mPaint.setAlpha(0);
	            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
	            mPaint.setAntiAlias(true);

	            // getting image from resources
	            Resources r = this.getContext().getResources();

	            //Bitmap bm = BitmapFactory.decodeResource(getResources(),  R.drawable.mickey);
	            Bitmap bm = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/dress.jpg");
	            this.bm = bm;
	            // converting image bitmap into mutable bitmap

	              bitmap =  Bitmap.createBitmap(295, 260, Config.ARGB_8888);
	               pcanvas = new Canvas();
	              pcanvas.setBitmap(bitmap);                   // drawXY will result on that Bitmap
	              pcanvas.drawBitmap(bm, 0, 0, null);


	    }

	    @Override
	    protected void onDraw(Canvas canvas) {


	    // draw a circle that is  erasing bitmap            
	        pcanvas.drawCircle(x, y, r, mPaint);

	        canvas.drawBitmap(bitmap, 0, 0,null);

	        super.onDraw(canvas);

	    }



	    @Override
	    public boolean onTouchEvent(MotionEvent event) {

	         // set paramete to draw circle on touch event
	         x = (int) event.getX();
	            y = (int) event.getY();

	            r =20;
	            // Atlast invalidate canvas
	            invalidate();
	            return true;
	    }
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("save");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getTitle().equals("save")) {
			FileOutputStream out = null;
			try {
				out = new FileOutputStream(Environment.getExternalStorageDirectory() + "/dress1.png");
		        Bitmap picture = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/dress.jpg").
		        	copy(Bitmap.Config.ARGB_8888, true);
		        Canvas c = new Canvas(picture);
		        
		        Bitmap logo = panel.bitmap;
		        c.drawBitmap(logo, 0, 0, null);
		        setContentView(R.layout.main);
		        ((ImageView)findViewById(R.id.dressImageView)).setImageBitmap(picture);
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			panel.bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}