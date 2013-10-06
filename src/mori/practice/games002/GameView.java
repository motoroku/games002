package mori.practice.games002;

import android.content.Context;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView {

	private GameHolderCallBack gameHolderCallBack;

	public GameView(Context context) {
		super(context);
		SurfaceHolder holder = getHolder();
		gameHolderCallBack = new GameHolderCallBack(context);
		holder.addCallback(gameHolderCallBack);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		float xPoint = event.getX();

		gameHolderCallBack.getTouchEvent(xPoint);
		return super.onTouchEvent(event);
	}

}
