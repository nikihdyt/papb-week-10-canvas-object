package com.example.android.papb_week_10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private Canvas mCanvas;
    private Paint mPaint = new Paint();
    private Paint mPaintText = new Paint(Paint.UNDERLINE_TEXT_FLAG);
    private Bitmap mBitmap;
    private ImageView mImageView;
    private Rect mRect = new Rect();
    private Rect mBounds = new Rect();

    private static final int OFFSET = 500;
    private int mOffset = OFFSET;
    private static final int MULTIPLIER = 100;

    private int mColorBackground;
    private int mColorRectangle;
    private int mColorAccent;
    private int mColorCat;
    private int mColorGrass;
    private int mColorSky;
    private int mColorSun;

    private int catX;
    private int catY;
    private int catWidth;
    private int catHeight;

    private int tapCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mColorBackground = ResourcesCompat.getColor(getResources(),
                R.color.colorBackground, null);
        mColorRectangle = ResourcesCompat.getColor(getResources(),
                R.color.colorRectangle, null);
        mColorAccent = ResourcesCompat.getColor(getResources(),
                R.color.colorAccent, null);
        mColorCat = ResourcesCompat.getColor(getResources(), R.color.colorCat, null);
        mColorGrass = ResourcesCompat.getColor(getResources(), R.color.colorGrass, null);
        mColorSky = ResourcesCompat.getColor(getResources(), R.color.colorSky, null);
        mColorSun = ResourcesCompat.getColor(getResources(), R.color.colorSun, null);

        mPaint.setColor(mColorBackground);
        mPaintText.setColor(ResourcesCompat.getColor(getResources(),
                R.color.black, null));
        mPaintText.setTextSize(70);

        mImageView = findViewById(R.id.myimageview);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawSomething(view);
            }
        });
    }

    private void drawSomething(View view) {
        int vWidth = view.getWidth();
        int vHeight = view.getHeight();
        int halfWidth = vWidth / 2;
        int halfHeight = vHeight / 2;

        if (mOffset == OFFSET) {
            mBitmap = Bitmap.createBitmap(vWidth, vHeight, Bitmap.Config.ARGB_8888);
            mImageView.setImageBitmap(mBitmap);
            mCanvas = new Canvas(mBitmap);
            mCanvas.drawColor(mColorBackground);

//            mPaintText.setTextSize(50);
//            mCanvas.drawText(getString(R.string.keep_tapping), 100, 100, mPaintText);

            tapCount += 1;
            mOffset += OFFSET;
        }
        else {
            if (mOffset < halfWidth && mOffset < halfHeight) {
                mPaint.setColor(mColorRectangle - MULTIPLIER * mOffset);

                mRect.set(mOffset, mOffset, vWidth - mOffset, vHeight - mOffset);
                mCanvas.drawRect(mRect, mPaint);
                mOffset += OFFSET;
            }
            else {
                if (tapCount == 1) {
                    // Draw grass
                    mPaint.setColor(mColorGrass);
                    mRect.set(0, halfHeight, vWidth, vHeight);
                    mCanvas.drawRect(mRect, mPaint);

                    tapCount += 1;
                    mOffset += OFFSET;
                }
                else if (tapCount == 2) {
                    // Draw sun
                    mPaint.setColor(mColorSun);
                    mCanvas.drawCircle(halfWidth , halfHeight / 2, halfHeight / 4, mPaint);

                    tapCount += 1;
                    mOffset += OFFSET;
                }
                else if (tapCount == 3) {
                    // Draw cat
                    catX = halfWidth - 200;
                    catY = halfHeight - 200;
                    catWidth = 400;
                    catHeight = 300;

                    // Draw cat's head
                    int headRadius = catWidth / 3;
                    mPaint.setColor(mColorCat);
                    mCanvas.drawCircle(catX + catWidth / 2, catY - headRadius / 2, headRadius, mPaint);

                    tapCount += 1;
                    mOffset += OFFSET;
                }
                else if (tapCount == 4) {
                    // Draw cat's body
                    mPaint.setColor(mColorCat);
                    mCanvas.drawOval(catX, catY, catX + catWidth, catY + catHeight, mPaint);

                    tapCount += 1;
                    mOffset += OFFSET;
                }
                else if (tapCount == 5) {
                    // Draw cat's ears
                    int earHeight = catHeight / 3;
                    int earWidth = catWidth / 3;
                    int earOffsetX = catWidth / 6;
                    int earOffsetY = catHeight / 6;
                    int earTopY = catY - catHeight / 2 - earHeight;

                    Path leftEarPath = new Path();
                    leftEarPath.moveTo(catX + earOffsetX + 50, earTopY + earOffsetY + 40);
                    leftEarPath.quadTo(catX + earOffsetX - earWidth / 2, earTopY - earHeight - earOffsetY, catX + earOffsetX - earWidth + 30, earTopY + earOffsetY + 200);

                    Path rightEarPath = new Path();
                    rightEarPath.moveTo(catX + catWidth - earOffsetX - 50, earTopY + earOffsetY + 40);
                    rightEarPath.quadTo(catX + catWidth - earOffsetX + earWidth / 2, earTopY - earHeight - earOffsetY, catX + catWidth - earOffsetX + earWidth - 30, earTopY + earOffsetY + 200);

                    // Rotate the left ear path
                    Matrix matrix = new Matrix();
                    matrix.postRotate(-30, catX + earOffsetX, earTopY);
                    leftEarPath.transform(matrix);

                    // Rotate the right ear path
                    matrix.reset();
                    matrix.postRotate(30, catX + catWidth - earOffsetX, earTopY);
                    rightEarPath.transform(matrix);

                    // Draw the ears
                    mPaint.setColor(Color.parseColor("#FFA500"));
                    mCanvas.drawPath(leftEarPath, mPaint);
                    mCanvas.drawPath(rightEarPath, mPaint);

                    tapCount += 1;
                    mOffset += OFFSET;
                }
                else if (tapCount == 6) {
                    // Draw cat's whiskers
                    int whiskerLength = catWidth / 4;
                    int whiskerOffsetY = catHeight / 8;
                    mPaint.setStrokeWidth(5);
                    mPaint.setColor(Color.BLACK);
                    mCanvas.drawLine(catX + catWidth / 3, catY - catHeight / 6, catX + catWidth / 3 - whiskerLength, catY - catHeight / 6 - whiskerOffsetY, mPaint);
                    mCanvas.drawLine(catX + catWidth / 3, catY - catHeight / 6, catX + catWidth / 3 - whiskerLength, catY - catHeight / 6 + whiskerOffsetY, mPaint);
                    mCanvas.drawLine(catX + catWidth * 2 / 3, catY - catHeight / 6, catX + catWidth * 2 / 3 + whiskerLength, catY - catHeight / 6 - whiskerOffsetY, mPaint);
                    mCanvas.drawLine(catX + catWidth * 2 / 3, catY - catHeight / 6, catX + catWidth * 2 / 3 + whiskerLength, catY - catHeight / 6 + whiskerOffsetY, mPaint);

                    tapCount += 1;
                    mOffset += OFFSET;
                }
                else if (tapCount == 7) {
                    // Draw cat's eyes
                    int eyeRadius = catWidth / 10;
                    mPaint.setColor(Color.WHITE);
                    mCanvas.drawCircle(catX + catWidth / 3, catY - catHeight / 6, eyeRadius, mPaint);
                    mCanvas.drawCircle(catX + catWidth * 2 / 3, catY - catHeight / 6, eyeRadius, mPaint);
                    mPaint.setColor(Color.BLACK);
                    mCanvas.drawCircle(catX + catWidth / 3, catY - catHeight / 6, eyeRadius / 2, mPaint);
                    mCanvas.drawCircle(catX + catWidth * 2 / 3, catY - catHeight / 6, eyeRadius /2, mPaint);

                    tapCount += 1;
                    mOffset += OFFSET;
                }
                else if (tapCount == 8) {
                    // Draw cat's nose
                    int noseRadius = catWidth / 12;
                    mPaint.setColor(Color.parseColor("#F48484"));
                    mCanvas.drawCircle(catX + catWidth / 2, catY - catHeight / 5, noseRadius, mPaint);

                    tapCount += 1;
                    mOffset += OFFSET;
                }
                else if (tapCount == 9) {
                    // Draw cat's mouth
                    int mouthOffsetY = catHeight / 12;
                    int mouthWidth = catWidth / 4;
                    int mouthHeight = catHeight / 12;
                    mRect.set(catX + catWidth / 2 - mouthWidth / 2, catY - catHeight / 5 + mouthOffsetY,
                            catX + catWidth / 2 + mouthWidth / 2, catY - catHeight / 5 + mouthOffsetY + mouthHeight);
                    mCanvas.drawRect(mRect, mPaint);

                    tapCount += 1;
                    mOffset += OFFSET;
                }
            }
        }
        view.invalidate();
    }

}