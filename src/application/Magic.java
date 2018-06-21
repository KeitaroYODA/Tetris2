package application;

import javafx.scene.canvas.GraphicsContext;

class Magic {

	// 表示する魔法効果を指定
	public static final int MAGIC_ACTION_NONE = 0; // 表示なし
	public static final int MAGIC_ACTION_IO_SELECT = 1; // 魔法（イオ）発動準備中エフェクト表示
	public static final int MAGIC_ACTION_IO = 2; // 魔法（イオ）発動エフェクト表示
	public static final int MAGIC_ACTION_MERA_SELECT = 3; // 魔法（メラ）発動準備中エフェクト表示
	public static final int MAGIC_ACTION_MERA = 4; // 魔法（メラ）発動エフェクト表示

	// 魔法（メラ）の効果範囲
	private static final int COL = 3; // 列数
	private static final int ROW = 3; // 行数
	private int[][] cursorArray = new int[ROW][COL];

	private TetrisImage magicImage = new TetrisImage();

	// 魔法発動位置選択カーソル座標
	private int cursorX;
	private int cursorY;
	private double cursorW = Conf.PANEL_W * COL;
	private double cursorH = Conf.PANEL_H * ROW;
	private int action;

	// 表示させるアクションを設定
	public void setAction(int action) {
		if (this.action != action) {
			this.magicImage.init();
		}
		this.action = action;
	}

	public Magic() {
		init();

		for (int i = 0; i < ROW; i++) {
			for (int l = 0; l < COL; l++) {
				this.cursorArray[i][l] = 1;
			}
		}
	}

	public void init() {
		cursorX = Conf.MAGIC_CIRCLE_X;
		cursorY = Conf.MAGIC_CIRCLE_Y;
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
	public void show(GraphicsContext canvas) {

		double x = this.cursorX * Conf.PANEL_W + Conf.FIELD_X;
		double y = this.cursorY * Conf.PANEL_H + Conf.FIELD_Y;
		double w = this.cursorW;
		double h = this.cursorH;

		switch(this.action) {
		case MAGIC_ACTION_IO_SELECT:
			canvas.drawImage(this.magicImage.magicFlameEffectAnime(), Conf.PANEL_W, 0, Conf.PANEL_W * Field.COL(), Conf.PANEL_H * Field.ROW());
			break;
		case MAGIC_ACTION_IO:
			canvas.drawImage(this.magicImage.magicIoAnime(), Conf.PANEL_W, 0, Conf.PANEL_W * Field.COL(), Conf.PANEL_H * Field.ROW());
			break;
		case MAGIC_ACTION_MERA_SELECT:
			canvas.drawImage(this.magicImage.magicCircle(),x, y, w, h);
			break;
		case MAGIC_ACTION_MERA:
			canvas.drawImage(this.magicImage.bombAnime(),x, y, w, h);
			break;
		default:
			// 表示しない
			break;
		}
	}

	/*
	public int[][] getCursorArray() {
		return this.cursorArray;
	}*/


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