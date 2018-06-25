package application;

import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

// ミノクラス
abstract class Mino implements Cloneable{

	// ミノを構成するパネル数
	private static final int ROW = 4;
	private static final int COL = 4;

	// ミノに表示する画像情報を格納
	protected Panel panel;

	// ミノの左上の配列インデックス
	protected int x; // 横
	protected int y; // 縦

	// ミノの向き
	// 1:正面、2:右向き、3:上下逆、4:左向き
	protected static final int DIRECTION_NORMAL = 1;
	protected static final int DIRECTION_RIGHT = 2;
	protected static final int DIRECTION_REVERCE = 3;
	protected static final int DIRECTION_LEFT = 4;
	protected int direction = DIRECTION_NORMAL;

	// ミノを構成するパネルの位置情報を保持s
	protected int[][] panelArray = new int[ROW][COL];

	public Mino() {
		this.init();
		this.initPanel();
	}

	// ミノの表示位置を初期化
	public void init() {
		this.x = Conf.MINO_X;
		this.y = Conf.MINO_Y;
	}

	// ミノを構成するパネル配列の初期化
	protected void initPanel() {
		for (int i = 0; i < ROW; i++) {
			for (int l = 0; l < COL; l++) {
				this.panelArray[i][l] = 0;
			}
		}
	}

	// ミノを構成するパネル情報配列を返す
	public int[][] getPanelArray() {
		return this.panelArray;
	}

	abstract void turn();

	// ランダムに異なる形状のミノのオブジェクトを返す
	public static Mino getMino() {
		Mino mino;
		Random randI = new Random();
		int num = randI.nextInt(10);

		if (num < 2) {
			mino = new MinoBar();
		} else if ((num >= 2) && (num <= 3)) {
			mino = new MinoTotu();
		} else if ((num >= 4) && (num <= 5)) {
			mino = new MinoSquare();
		} else if ((num >= 6) && (num <= 7)) {
			mino = new MinoKagi1();
		} else {
			mino = new MinoKagi2();
		}
//mino = new MinoBar();
		return mino;
	}

	// ミノを表示する
	protected void show(GraphicsContext canvas) {

		for (int row = 0; row < ROW; row++) {
			for (int col = 0; col < COL; col++) {
				double x = (col + this.x) * Conf.PANEL_W + Conf.FIELD_X;
				double y = (row + this.y) * Conf.PANEL_H + Conf.FIELD_Y;
				double w = Conf.PANEL_W;
				double h = Conf.PANEL_H;

				if (this.panelArray[row][col] == 1) {
					Image img = this.panel.getImage();
					canvas.drawImage(img, x, y, w, h);
				}
			}
		}
	}

	public static int COL() {
		return COL;
	}

	public static int ROW() {
		return ROW;
	}

	public Panel getPanel() {
		return this.panel;
	}

	public int getDirection() {
		return this.direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
}