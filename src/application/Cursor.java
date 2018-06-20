package application;

import javafx.scene.canvas.GraphicsContext;

// 魔法発動時に表示されるカーソル
class Cursor {

	// 画面に表示するパネルの範囲
	private static final int COL = Field.COL(); // 列数
	private static final int ROW = Field.ROW(); // 行数

	// カーソル表示位置を保持
	private int[][] cursorArray = new int[ROW][COL];


	// 魔法発動位置選択カーソル座標
	private int cursorX;
	private int cursorY;
	private double cursorW = Panel.panelW() * 2;
	private double cursorH = Panel.panelH() * 2;

	public Cursor() {
		init();

		for (int i = 0; i < ROW; i++) {
			for (int l = 0; l < COL; l++) {
				this.cursorArray[i][l] = 0;
			}
		}
	}

	public void init() {
		cursorX = 10;
		cursorY = 10;
	}

	// カーソルを上に移動
	public void moveUp() {
		if (this.cursorY > 0) {
			this.cursorY = this.cursorY - 1;
		}
	}

	// カーソルを下に移動
	public void moveDown() {
		if (this.cursorY < (ROW - 1)) {
			this.cursorY = this.cursorY + 1;
		}
	}

	// カーソルを左に移動
	public void moveLeft() {
		if (this.cursorX > 0) {
			this.cursorX = this.cursorX - 1;
		}
	}

	// カーソルを右に移動
	public void moveRight() {
		if (this.cursorX < (COL - 1)) {
			this.cursorX = this.cursorX + 1;
		}
	}

	// カーソルを表示
	public void show(GraphicsContext canvas) {
		double x = this.cursorX * Panel.panelW() + Field.dispX();
		double y = this.cursorY * Panel.panelH() + Field.dispY();
		double w = this.cursorW;
		double h = this.cursorH;
		canvas.drawImage(TetrisImage.magicCircle,x, y, w, h);
	}

	public int cursorX() {
		return this.cursorX;
	}

	public int cursorY() {
		return this.cursorY;
	}
}