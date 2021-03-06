package application;

import javafx.scene.canvas.GraphicsContext;

/**
 * 魔法効果クラス
 *
 */
public final class Magic {

	// 表示する魔法効果を指定
	public static final int MAGIC_ACTION_NONE = 0; // 表示なし
	public static final int MAGIC_ACTION_IO_SELECT = 1; // 魔法（イオ）発動準備中エフェクト表示
	public static final int MAGIC_ACTION_IO = 2; // 魔法（イオ）発動エフェクト表示
	public static final int MAGIC_ACTION_FRAME = 3; // 魔法（イオ）発動後の全パネル落下中のエフェクト表示
	public static final int MAGIC_ACTION_MERA_SELECT = 4; // 魔法（メラ）発動準備中エフェクト表示
	public static final int MAGIC_ACTION_MERA = 5; // 魔法（メラ）発動エフェクト表示
	public static final int MAGIC_ACTION_FRAME_MERA = 6; // 魔法（メラ）発動後の全パネル落下中のエフェクト表示

	private static Magic magic;

	// 魔法（メラ）の効果範囲
	private static final int COL = Conf.MAGIC_COL; // 列数
	private static final int ROW = Conf.MAGIC_ROW; // 行数
	private int[][] cursorArray = new int[ROW][COL];

	// 魔法エフェクト表示用
	private TetrisImage magicImage = new TetrisImage();

	// 魔法発動位置選択カーソル座標
	private int cursorX = Conf.MAGIC_CIRCLE_X;
	private int cursorY = Conf.MAGIC_CIRCLE_Y;
	private double cursorW = Conf.PANEL_W * COL;
	private double cursorH = Conf.PANEL_H * ROW;
	private int action = MAGIC_ACTION_NONE;

	/**
	 * 自身のオブジェクトを返す
	 * @return Magic
	 */
	public static Magic getInstance() {
		if (magic == null) {
			magic = new Magic();
		}
		return magic;
	}

	/**
	 * コンストラクタ
	 */
	private Magic() {
		initCursor();
		for (int i = 0; i < ROW; i++) {
			for (int l = 0; l < COL; l++) {
				this.cursorArray[i][l] = 1;
			}
		}
	}

	/**
	 * 表示させるアクションを設定
	 * @param action MAGIC_ACTION_*
	 */
	public void setAction(int action) {
		if (this.action != action) {
			this.magicImage.init();
		}
		this.action = action;
	}

	/*
	public int getAction() {
		return this.action;
	}*/

	/**
	 * 魔法陣のカーソル位置を初期化
	 */
	public void initCursor() {
		cursorX = Conf.MAGIC_CIRCLE_X;
		cursorY = Conf.MAGIC_CIRCLE_Y;
	}

	/**
	 * 魔法陣操作(上に移動)
	 */
	public void moveUp() {
		if (this.cursorY > 0) {
			this.cursorY = this.cursorY - 1;
		}
	}

	/**
	 * 魔法陣操作(下に移動)
	 */
	public void moveDown() {
		if ((this.cursorY + ROW) < Field.ROW()) {
			this.cursorY = this.cursorY + 1;
		}
	}

	/**
	 * 魔法陣操作(左に移動)
	 */
	public void moveLeft() {
		if (this.cursorX > 0) {
			this.cursorX = this.cursorX - 1;
		}
	}

	/**
	 * 魔法陣操作(右に移動)
	 */
	public void moveRight() {
		if ((this.cursorX + COL) < Field.COL()) {
			this.cursorX = this.cursorX + 1;
		}
	}

	/**
	 * 指定された座標が魔法陣カーソル内であるかチェック
	 * @param x チェックする横位置
	 * @param y チェックする縦位置
	 * @return true:魔法陣カーソル内、false:魔法陣カーソル外
	 */
	public boolean inCursor(int x, int y) {
		boolean result = false;
		for (int i = 0; i < ROW; i++) {
			for (int l = 0; l < COL; l++) {

				if ((this.cursorY + i > y) && (this.cursorX + l) == x) {
					result = true;
				}
			}
		}
		return result;
	}

	/**
	 * アニメの再生が終わったら真を返す
	 * @return true:アニメ再生終了、false:アニメ再生中
	 */
	public boolean animeIsEnd() {
		return magicImage.isEnd();
	}

	/**
	 *  魔法効果を描画
	 * @param canvas GraphicsContext2D
	 */
	public void show(GraphicsContext canvas) {
		// メラ魔方陣表示用
		double x = this.cursorX * Conf.PANEL_W + Conf.FIELD_X;
		double y = this.cursorY * Conf.PANEL_H + Conf.FIELD_Y;
		double w = this.cursorW;
		double h = this.cursorH;

		switch(this.action) {
		case MAGIC_ACTION_IO_SELECT:
			canvas.drawImage(this.magicImage.magicFlameEffectAnime(), Conf.FIELD_X, Conf.FIELD_Y, Conf.PANEL_W * Field.COL(), Conf.PANEL_H * Field.ROW());
			break;
		case MAGIC_ACTION_IO:
			canvas.drawImage(this.magicImage.magicIoAnime(), Conf.FIELD_X, Conf.FIELD_Y, Conf.PANEL_W * Field.COL(), Conf.PANEL_H * Field.ROW());
			break;
		case MAGIC_ACTION_FRAME:
			canvas.drawImage(this.magicImage.frameAnime(), Conf.FIELD_X, (Conf.PANEL_H * Field.ROW()) - ((Conf.PANEL_H * Field.ROW()) / 4) + Conf.FIELD_Y, Conf.PANEL_W * Field.COL(), (Conf.PANEL_H * Field.ROW()) / 4);
			break;
		case MAGIC_ACTION_FRAME_MERA:
			canvas.drawImage(this.magicImage.frameAnime(), ((Conf.PANEL_W * this.cursorX) + Conf.FIELD_X), (Conf.PANEL_H * Field.ROW()) - ((Conf.PANEL_H * Field.ROW()) / 4) + Conf.FIELD_Y, Conf.PANEL_W * Magic.COL(), (Conf.PANEL_H * Field.ROW()) / 4);
			break;
		case MAGIC_ACTION_MERA_SELECT:
			canvas.drawImage(this.magicImage.magicCircle(),x, y, w, h);
			break;
		case MAGIC_ACTION_MERA:
			canvas.drawImage(this.magicImage.bombAnime(),x, y, w, h);
			break;
		case MAGIC_ACTION_NONE:
			// 表示しない
			break;
		}
	}

	/**
	 * 魔法陣の横位置を返す
	 * @return 魔法陣の横位置
	 */
	public int cursorX() {
		return this.cursorX;
	}

	/**
	 * 魔法陣の縦位置を返す
	 * @return 魔法陣の縦位置
	 */
	public int cursorY() {
		return this.cursorY;
	}

	/**
	 * 魔法陣の行数を返す
	 * @return 魔法陣の行数
	 */
	public static int ROW() {
		return ROW;
	}

	/**
	 * 魔法陣の列数を返す
	 * @return 魔法陣の列数
	 */
	public static int COL() {
		return COL;
	}
}