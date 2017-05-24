package sibi.bird_attempt;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        DrawingView dv = new DrawingView(this);
        setContentView(dv);
    }
    protected class DrawingView extends View implements Runnable{
        Handler handler = new Handler(){
            @Override
            public void handleMessage(android.os.Message msg) {
                invalidate();
            }
        };
        Bitmap map[]= new Bitmap[4];
        double xv,yv;
        float grav= (float) +.5;
        double xpos,ypos;
        double theta;
        int iterator1=0,iterator=0;
        DrawingView(Context c)
        {
            super(c);
            Bitmap temp;
            int arr[]= new int[]{R.drawable.frame_1,R.drawable.frame_2,R.drawable.frame_3,R.drawable.frame_4};
            xpos=100;
            ypos=100;
            xv=3;
            yv=-3;
            for(int i=0;i<4;i++)
            {
                temp= BitmapFactory.decodeResource(getResources(),arr[i]);
                map[i]=Bitmap.createScaledBitmap(temp,100,100,true);
            }
            new Thread(this).start();
        }
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            theta = Math.atan2(xv,yv);
            yv += grav;
            ypos += yv;
            Matrix matrix = new Matrix();
            matrix.postRotate((float)Math.toDegrees(theta),(float)xpos+50,(float)ypos+50);
            matrix.postTranslate((float)xpos,(float)ypos);
            canvas.drawBitmap(map[iterator],matrix,new Paint());
            iterator = (iterator+1)%4;
        }

        @Override
        public void run() {
            while(true) {
                try {
                    Thread.sleep(1000 / 30);

                } catch (InterruptedException e) {
                }
                handler.sendEmptyMessage(0);
            }
            }

    }
}
