package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

class Field {

	// 表示位置
	private static final double dispX = Panel.panelW();
	private static final double dispY = 0;

	// 画面に表示するパネルの範囲
	private static final int COL = 16; // 列数
	private static final int ROW = 18; // 行数

	// 画面に表示されるパネルの表示位置を保持
	private Panel[][] panelArray = new Panel[ROW][COL];

	private static Field field;
	private Mino mino;
	private TetrisImage magicImage = new TetrisImage();

	// 魔法発動カーソル
	private Cursor cursor = new Cursor();

	public static Field getInstance() {
		if (field == null) {
			field = new Field();
		}
		return field;
	}

	private Field() {
		this.init();
	}

	public void init() {
		for (int i = 0; i < ROW; i++) {
			for (int l = 0; l < COL; l++) {
				this.panelArray[i][l] = null;
			}
		}
	}

	// 落下中のミノをフィールドのセット
	public void setMino(Mino mino) {
		this.mino = mino;
	}

	// カーソルオブジェクトを返す
	public Cursor getCursor() {
		return this.cursor;
	}

	// ミノを落下させる
	public void moveDown() {
		int minoY = this.mino.getY();
		minoY++;

		int[][] minoPanelArray = this.mino.getPanelArray();
		for (int i = 0; i < Mino.ROW(); i++) {
			for (int l = 0; l < Mino.COL(); l++) {
				int row = i +  minoY;
				int col = l + this.mino.getX();

				// 画面の範囲外
				if (minoPanelArray[i][l] == 1) {
					if (row >= Field.ROW || col < 0 || col >= Field.COL) {
						return;
					}
					// パネルに衝突した
					if (this.panelArray[row][col] != null) {
						return;
					}
				}
			}
		}
		this.mino.setY(minoY);
	}

	// ミノを右に移動させる
	protected void moveRight() {
		int minoX = this.mino.getX();
		minoX++;

		int[][] minoPanelArray = this.mino.getPanelArray();
		for (int i = 0; i < Mino.ROW(); i++) {
			for (int l = 0; l < Mino.COL(); l++) {
				int row = i +  this.mino.getY();
				int col = l + minoX;

				// 画面の範囲外
				if (minoPanelArray[i][l] == 1) {
					if (row >= Field.ROW || col < 0 || col >= Field.COL) {
						return;
					}
					// パネルに衝突した
					if (this.panelArray[row][col] != null) {
						return;
					}
				}
			}
		}

		this.mino.setX(minoX);
	}

	// ミノを左に移動させる
	protected void moveLeft() {
		int minoX = this.mino.getX();
		minoX--;

		int[][] minoPanelArray = this.mino.getPanelArray();
		for (int i = 0; i < Mino.ROW(); i++) {
			for (int l = 0; l < Mino.COL(); l++) {
				int row = i +  this.mino.getY();
				int col = l + minoX;

				// 画面の範囲外
				if (minoPanelArray[i][l] == 1) {
					if (row >= Field.ROW || col < 0 || col >= Field.COL) {
						return;
					}
					// パネルに衝突した
					if (this.panelArray[row][col] != null) {
						return;
					}
				}
			}
		}
		this.mino.setX(minoX);
	}

	// ミノを回転
	protected void turnLeft() {

		int direction = this.mino.getDirection();

		direction--;
		if (direction < 1) {
			direction = 4;
		}
		this.mino.setDirection(direction);
		this.mino.turn();

		int[][] minoPanelArray = this.mino.getPanelArray();
		for (int i = 0; i < Mino.ROW(); i++) {
			for (int l = 0; l < Mino.COL(); l++) {
				int row = i +  this.mino.getY();
				int col = l + this.mino.getX();

				// 画面の範囲外
				if (minoPanelArray[i][l] == 1) {
					if (row >= Field.ROW || col < 0 || col >= Field.COL) {
						direction++;
						if (direction > 4) {
							direction = 1;
						}
						this.mino.setDirection(direction);
						this.mino.turn();
						return;
					}
					// パネルに衝突した
					if (this.panelArray[row][col] != null) {
						direction++;
						if (direction > 4) {
							direction = 1;
						}
						this.mino.setDirection(direction);
						this.mino.turn();
						return;
					}
				}
			}
		}
	}

	// ミノの衝突判定
	public boolean colision() {
		int[][] minoPanelArray = this.mino.getPanelArray();

		for (int i = 0; i < Mino.ROW(); i++) {
			for (int l = 0; l < Mino.COL(); l++) {
				int row = i +  this.mino.getY() + 1;
				int col = l + this.mino.getX();

				// 画面の範囲外
				if (minoPanelArray[i][l] == 1) {
					// 床に接地
					if (row == Field.ROW) {
						return true;
					}
					// パネルに接地
					if (this.panelArray[row][col] != null) {
						return true;
					}
				}
			}
		}

		return false;
	}

	// 積みあがったパネルが揃っていれば揃った行数を返す
	public int checkRemoveRow() {

		int checkNum = 0;
		boolean check = false;

		for (int row = 0; row < ROW; row++) {
			check = true;
			for (int col = 0; col < COL; col++) {
				if (this.panelArray[row][col] == null) {
					check = false;
				}
			}

			// 行が揃っている
			if (check) {
				checkNum++;
			}
		}

		return checkNum;
	}

	// パネル１枚を削除する。魔法実行時に呼び出される
	public void deletePanel() {
		int row, col;
		for (int i = 0; i < Cursor.ROW(); i++) {
			for (int l = 0; l < Cursor.COL(); l++) {
				row = this.cursor.cursorY() + i;
				col = this.cursor.cursorX() + l;
				this.panelArray[row][col] = null;
			}
		}
	}

	// 床またはパネルに衝突するまでパネルを落下させる
	// １マスずつ落下させる
	public void movePanel() {
		int x = this.cursor.cursorX();
		int y = this.cursor.cursorY();

		for (int row = (ROW - 1); row > 0; row--) {
			for (int col = 0; col < COL; col++) {

				if (col != x || row > y) {
					continue;
				}

				if (this.panelArray[row][col] != null) {
					// 自身より下のパネルの有無をチェック
					int index = 0;
					for (int i = row + 1; i < ROW; i++) {
						if (this.panelArray[i][col] == null) {
							index++;
						}
					}
					if (index > 0) {
						this.panelArray[index + row][col] = this.panelArray[row][col];
						this.panelArray[row][col] = null;
					}
				}
			}
		}
	}

	// 行が揃ったパネルを削除する
	public void deleteRow() {

		for (int row = (ROW - 1); row > 0; row--) {
			boolean check = true;
			for (int col = 0; col < COL; col++) {
				if (this.panelArray[row][col] == null) {
					check = false;
				}
			}

			if (check) {
				for (int col = 0; col < COL; col++) {
					this.panelArray[row][col] = null;
				}
			}
		}
	}

	// 削除された行の上にあるブロックを落とす
	public void moveRow() {

		int index = ROW - 1;
		Panel[][] editArray = new Panel[ROW][COL];

		// 下から揃っているかチェック
		for (int row = (ROW - 1); row > 0; row--) {
			boolean check = false;
			for (int col = 0; col < COL; col++) {
				if (this.panelArray[row][col] != null) {
					check = true;
				}
			}

			// 空洞の行を埋める
			if (check) {
				for (int col = 0; col < COL; col++) {
					editArray[index][col] = this.panelArray[row][col];
				}
				index--;
			}
		}
		this.panelArray = editArray;
	}

	// ミノの情報のパネル情報をフィールドに渡す
	public void setMino2Field() {
		int[][] minoPanelArray = this.mino.getPanelArray();
		for (int i = 0; i < Mino.ROW(); i++) {
			for (int l = 0; l < Mino.COL(); l++) {

				// 移動した分を加算
				int row = i + this.mino.getY();
				int col = l + this.mino.getX();

				if (row < Field.ROW && col >= 0 && col < Field.COL) {
					if (minoPanelArray[i][l] == 1) {
						this.panelArray[row][col] = mino.getPanel();
					}
				}
			}
		}
	}

	// フィールドを表示
	public void show(GraphicsContext canvas, int gameStatus) {

		int[][] cursorArray = this.cursor.getCursorArray();

		// 背景及び積み上げられたパネルを表示
		for (int i = 0; i < ROW; i++) {
			for (int l = 0; l < COL; l++) {

				double x = l * Panel.panelW() + Field.dispX;
				double y = i * Panel.panelH() + Field.dispY;
				double w = Panel.panelW();
				double h = Panel.panelH();

				if (this.panelArray[i][l] != null) {
					// パネルを表示
					if (gameStatus == 6) {
						canvas.setGlobalAlpha(0.5);
						canvas.drawImage(panelArray[i][l].getImage(),x, y, w, h);
						canvas.setGlobalAlpha(1.0);
					} else {
						canvas.drawImage(panelArray[i][l].getImage(),x, y, w, h);
					}

				} else {
					// 背景色で塗りつぶし
					canvas.setFill(Color.BLACK);
					canvas.fillRect(x, y, w, h);
				}
			}
		}

		/*
		// 魔法対象パネル以外は半透明表示
		for (int k = 0; k < Cursor.ROW(); k++) {
			for (int m = 0; m < Cursor.COL(); m++) {
				int row = this.cursor.cursorY() + k;
				int col = this.cursor.cursorX() + m;

				double x = m * Panel.panelW() + Field.dispX;
				double y = k * Panel.panelH() + Field.dispY;
				double w = Panel.panelW();
				double h = Panel.panelH();

				if (this.panelArray[row][col] != null) {
					canvas.setGlobalAlpha(0.5);
					canvas.drawImage(panelArray[row][col].getImage(),x, y, w, h);
					canvas.setGlobalAlpha(1.0);
				}
			}
		}
		*/

		// 落下中のミノを表示
		this.mino.show(canvas);

		// 魔法発動カーソルを表示
		//this.cursor.show(canvas);
	}

	public static int ROW() {
		return ROW;
	}

	public static int COL() {
		return COL;
	}

	public static double dispX() {
		return dispX;
	}

	public static double dispY() {
		return dispY;
	}
}