package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

class Field {

	// フィールドの表示タイプ
	public static final int FIELD_VIEW_NORMAL = 0;
	public static final int FIELD_VIEW_RENSA = 1;

	// 画面に表示するパネルの範囲
	private static final int COL = 16; // 列数
	private static final int ROW = 18; // 行数

	// 画面に表示されるパネルの表示位置を保持
	private Panel[][] panelArray = new Panel[ROW][COL];
	private int viewType = FIELD_VIEW_NORMAL;

	private static Field field;
	private Mino mino;
	private Magic magic = new Magic();

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

	public void setViewType(int viewType) {
		this.viewType = viewType;
	}

	// 落下中のミノをフィールドのセット
	public void setMino(Mino mino) {
		this.mino = mino;
	}

	// カーソルオブジェクトを返す
	public Magic getMagic() {
		return this.magic;
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
	public int checkDeleteRow() {

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

	// フィールド上のパネルをすべて落下させる。イオ発動時に呼び出される
	public boolean allDown() {
		Panel[][] editArray = new Panel[ROW][COL];
		boolean result = false;

		for (int i = 0; i < ROW; i++) {
			for (int l = 0; l < COL; l++) {
				editArray[i][l] = null;
			}
		}

		for (int row = (ROW - 1); row > 0; row--) {
			for (int col = 0; col < COL; col++) {

				if (this.panelArray[row][col] != null) {
					int index = row + 1;

					// 既に床に接地している場合
					if (index == ROW) {
						// 移動させない
						editArray[row][col] = this.panelArray[row][col];
					} else if (editArray[index][col] != null) {
						// ほかのパネルに衝突した場合も移動させない
						editArray[row][col] = this.panelArray[row][col];
					} else {
						editArray[index][col] = this.panelArray[row][col];
						result = true;
					}
				}

			}
		}
		this.panelArray = editArray;
		return result;
	}

	// パネル１枚を削除する。魔法実行時に呼び出される
	public void deletePanel() {
		int row, col;
		for (int i = 0; i < Magic.ROW(); i++) {
			for (int l = 0; l < Magic.COL(); l++) {
				row = this.magic.cursorY() + i;
				col = this.magic.cursorX() + l;
				this.panelArray[row][col] = null;
			}
		}
	}

	// 床またはパネルに衝突するまでパネルを落下させる
	// １マスずつ落下させる
	public void movePanel() {
		//int x = this.cursor.cursorX();
		//int y = this.cursor.cursorY();

		for (int row = (ROW - 1); row > 0; row--) {
			for (int col = 0; col < COL; col++) {

				/*
				if (col != x || row > y) {
					continue;
				}
				*/

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

	// 積みあがっているブミノの高さを返す
	public int getMaxHeight() {
		int result = ROW;
		for (int i = 0; i < ROW; i++) {
			for (int l = 0; l < COL; l++) {
				if (this.panelArray[i][l] != null) {
					if (i < result) {
						result = i;
					}
				}
			}
		}
		return result;
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
	public void show(GraphicsContext canvas) {

		// 背景及び積み上げられたパネルを表示
		for (int i = 0; i < ROW; i++) {
			for (int l = 0; l < COL; l++) {

				double x = l * Conf.PANEL_W + Conf.FIELD_X;
				double y = i * Conf.PANEL_H + Conf.FIELD_Y;
				double w = Conf.PANEL_W;
				double h = Conf.PANEL_H;

				/*
				if (this.viewType == FIELD_VIEW_RENSA) {
					canvas.setFill(Color.DARKGRAY);
				} else {
					canvas.setFill(Color.BLACK);
				}
				*/

				canvas.setFill(Color.BLACK);
				canvas.fillRect(x, y, w, h);

				if (this.panelArray[i][l] != null) {
					canvas.drawImage(panelArray[i][l].getImage(),x, y, w, h);
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
	}

	public static int ROW() {
		return ROW;
	}

	public static int COL() {
		return COL;
	}
}