package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

final class Field {

	// 画面に表示するパネルの範囲
	private static final int COL = Conf.FIELD_COL; // 列数
	private static final int ROW = Conf.FIELD_ROW; // 行数

	// 画面に表示されるパネルの表示位置を保持
	private Panel[][] panelArray = new Panel[ROW][COL];

	private static Field field;
	private Mino mino;
	private Magic magic = Magic.getInstance();
	private TetrisImage image = new TetrisImage();

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
	public void moveRight() {
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
	public void moveLeft() {
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
	public void turnLeft() {
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
	
	// 魔法陣で指定された範囲のパネルを削除する。魔法（メラ）実行時に呼び出される
	// 削除したパネル数を返す
	public int deletePanels() {
		int row, col, deletePanels = 0;
		
		for (int i = 0; i < Magic.ROW(); i++) {
			for (int l = 0; l < Magic.COL(); l++) {
				row = this.magic.cursorY() + i;
				col = this.magic.cursorX() + l;

				if (this.panelArray[row][col] != null) {
					this.panelArray[row][col] = null;
					deletePanels++;
				}
			}
		}
		return deletePanels;
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
	public void rowDown() {
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

	// フィールド上のパネルをすべて落下させる。イオ発動時に呼び出される
	// 落下するパネルがあった場合真を返す
	public boolean allDown(int magicAction) {
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
					// メラ実行時には指定しされた範囲のみ落とす
					if (magicAction == Magic.MAGIC_ACTION_MERA) {
						if (!this.magic.inCursor(col, row)) {
							editArray[row][col] = this.panelArray[row][col];
							continue;
						}
					}

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

	// 積みあがっているミノの高さを返す
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

		// 渡し終わったミノを削除
		this.mino = null;
	}

	// 行が揃った際の消滅エフェクトを表示
	public void showDeleteAnime(GraphicsContext canvas) {

		// 揃った行があれば消滅エフェクトを表示
		if (this.checkDeleteRow() > 0) {

			boolean isCmpl = false;
			Image hitEffects = this.image.hiteffectAnime_1();

			for (int i = 0; i < ROW; i++) {
				isCmpl = true;
				for (int l = 0; l < COL; l++) {
					if (this.panelArray[i][l] == null) {
						isCmpl = false;
					}
				}
				// 行が揃っている
				if (isCmpl) {
					for (int k = 0; k < COL; k++) {
						double x = k * Conf.PANEL_W + Conf.FIELD_X;
						double y = i * Conf.PANEL_H + Conf.FIELD_Y;
						double w = Conf.PANEL_W;
						double h = Conf.PANEL_H;
						canvas.drawImage(hitEffects,x, y, w, h);
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

				canvas.setFill(Color.BLACK);
				canvas.fillRect(x, y, w, h);

				if (this.panelArray[i][l] != null) {
					canvas.drawImage(panelArray[i][l].getImage(),x, y, w, h);
				}
			}
		}

		// 落下中のミノを表示
		if (this.mino != null) {
			this.mino.show(canvas);
		}

		// 消滅エフェクトを表示
		this.showDeleteAnime(canvas);

		// 魔法を表示
		this.magic.show(canvas);

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
	}

	// アニメの再生が終わったら真を返す
	public boolean animeIsEnd() {
		return image.isEnd();
	}

	public void initAnime() {
		this.image.init();
	}

	public static int ROW() {
		return ROW;
	}

	public static int COL() {
		return COL;
	}
}