package practice.games002;

import android.graphics.Color;

public class Block {

	static int SIZE = 50;
	int blockColor = Color.YELLOW;
	float xPosition;
	float yPosition;

	public Block(float x, float y, int color) {
		xPosition = x;
		yPosition = y;
		blockColor = color;
	}
}
