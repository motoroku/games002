package mori.practice.games002;

import android.graphics.Color;

public class Stage {

	static int SPEED = 10;
	int height;
	Block block1;
	Block block2;

	public Stage(int height) {
		this.height = height - Block.SIZE * 2;
		block1 = new Block(100, height / 2, Color.YELLOW);
		block2 = new Block(300, 200, Color.CYAN);
	}
}
