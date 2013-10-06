package mori.practice.games002;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.view.Display;
import android.view.SurfaceHolder;

@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
public class GameHolderCallBack implements SurfaceHolder.Callback, Runnable {

	private SurfaceHolder holder = null;
	private Thread thread = null;
	private boolean isAttached = true;
	private SoundPool soundPool;
	private int soundId1;
	Context context;

	Baloon baloon;
	Stage stage;
	Display display;

	private long t1 = 0, t2 = 0; // スリープ用変数

	public GameHolderCallBack(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		MainActivity activity = (MainActivity) context;
		display = activity.getWindowManager().getDefaultDisplay();
		int y = 0;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			Point point = new Point();
			display.getSize(point);
			y = point.y;
		} else {
			y = display.getHeight();
		}

		baloon = new Baloon(200, 300);
		stage = new Stage(y);
		soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		soundId1 = soundPool.load(context, R.raw.se_maoudamashii_system02, 1);

		this.holder = holder;
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		while (isAttached) {
			t1 = System.currentTimeMillis();
			// 描画処理を開始
			drawGameObject();

			if (baloon.xPosition - Baloon.SIZE < stage.block1.xPosition + Block.SIZE
					&& baloon.xPosition + Baloon.SIZE > stage.block1.xPosition - Block.SIZE) {
				if (baloon.yPosition - Baloon.SIZE < stage.block1.yPosition + Block.SIZE
						&& baloon.yPosition + Baloon.SIZE > stage.block1.yPosition - Block.SIZE) {
					soundPool.play(soundId1, 1.0f, 1.0f, 0, 0, 1.0f);
				}
			}

			if (baloon.xPosition - Baloon.SIZE < stage.block2.xPosition + Block.SIZE
					&& baloon.xPosition + Baloon.SIZE > stage.block2.xPosition - Block.SIZE) {
				if (baloon.yPosition - Baloon.SIZE < stage.block2.yPosition + Block.SIZE
						&& baloon.yPosition + Baloon.SIZE > stage.block2.yPosition - Block.SIZE) {
					soundPool.play(soundId1, 1.0f, 1.0f, 0, 0, 1.0f);
				}
			}

			// スリープ
			t2 = System.currentTimeMillis();
			if (t2 - t1 < 16) { // 1000 / 60 = 16.6666
				try {
					Thread.sleep(16 - (t2 - t1));
				} catch (InterruptedException e) {
				}
			}

		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		soundPool.release();
		isAttached = false;
		thread = null;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// TODO Auto-generated method stub

	}

	public void getTouchEvent(float xPoint) {
		// TODO Auto-generated method stub
		if (baloon.yPosition > 0) {
			baloon.move(xPoint);
		}
	}

	private void drawGameObject() {

		if (baloon.yPosition != stage.height) {
			baloon.yPosition += Stage.SPEED;
		}

		Canvas canvas = holder.lockCanvas();
		canvas.drawColor(0, PorterDuff.Mode.CLEAR);
		Paint baloonPaint = new Paint();
		Paint block1 = new Paint();
		Paint block2 = new Paint();
		baloonPaint.setColor(baloon.color);
		block1.setColor(stage.block1.blockColor);
		block2.setColor(stage.block2.blockColor);
		canvas.drawCircle(baloon.xPosition, baloon.yPosition, Baloon.SIZE, baloonPaint);
		canvas.drawCircle(stage.block1.xPosition, stage.block1.yPosition, Block.SIZE, block1);
		canvas.drawCircle(stage.block2.xPosition, stage.block2.yPosition, Block.SIZE, block2);
		// 描画処理を終了
		holder.unlockCanvasAndPost(canvas);
	}
}
