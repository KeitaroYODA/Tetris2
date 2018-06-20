package application;

import javafx.scene.canvas.GraphicsContext;

// 魔法発動時に表示されるカーソル
class Cursor {

	private static final int COL = 3; // 列数
	private static final int ROW = 3; // 行数

	private int crtGameStatus = 0;

	// カーソル表示位置を保持
	private int[][] cursorArray = new int[ROW][COL];

	// 魔法陣アニメ
	private TetrisImage magicImage = new TetrisImage();

	// 魔法発動位置選択カーソル座標
	private int cursorX;
	private int cursorY;
	private double cursorW = Panel.panelW() * COL;
	private double cursorH = Panel.panelH() * ROW;

	public Cursor() {
		init();

		for (int i = 0; i < ROW; i++) {
			for (int l = 0; l < COL; l++) {
				this.cursorArray[i][l] = 1;
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
		if ((this.cursorY + ROW) < Field.ROW()) {
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
		if ((this.cursorX + COL) < Field.COL()) {
			this.cursorX = this.cursorX + 1;
		}
	}

	public boolean animeIsEnd() {
		return magicImage.isEnd();
	}

	// カーソルを表示
	public void show(GraphicsContext canvas, int gameStatus) {

		// 画面によって表示を変える
		if (this.crtGameStatus != gameStatus) {
			this.crtGameStatus = gameStatus;
			this.magicImage.init();
		}

		double x = this.cursorX * Panel.panelW() + Field.dispX();
		double y = this.cursorY * Panel.panelH() + Field.dispY();
		double w = this.cursorW;
		double h = this.cursorH;

		switch(gameStatus) {
		case Tetris_Obj.GAME_MAGIC_SELECT:
			canvas.drawImage(this.magicImage.magicCircle(),x, y, w, h);
			break;
		case Tetris_Obj.GAME_MAGIC_EXEC:
			//canvas.drawImage(this.magicImage.bombAnime(),x, y, w, h);
			canvas.drawImage(this.magicImage.magicIoAnime(),Panel.panelW(), 0, Panel.panelW() * Field.COL(), Panel.panelH() * Field.ROW());
			break;
		}
	}

	public int[][] getCursorArray() {
		return this.cursorArray;
	}

	public int cursorX() {
		return this.cursorX;
	}

	public int cursorY() {
		return this.cursorY;
	}

	public static int ROW() {
		return ROW;
	}

	public static int COL() {
		return COL;
	}
}