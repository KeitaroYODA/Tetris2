package application;

import java.util.ArrayList;
import java.util.Collections;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

// ミノクラス
abstract class Mino implements Cloneable{

	// [ミノの向き][補正する回数][補正内容 0:X軸補正値、1:Y軸補正値]
	public static final int CORRECTION_X = 0;
	public static final int CORRECTION_Y = 1;
	protected static final int DIRECTIONS = 4; // ミノの向きの種別数(正逆左右の4種類)
	protected static final int CORRECTIONS = 4; // 補正実施回数
	protected final int[][][] correctionLeftArray = new int[DIRECTIONS][CORRECTIONS][2]; // 左回転
	protected final int[][][] correctionRightArray = new int[DIRECTIONS][CORRECTIONS][2]; // 右回転

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
	protected static final int DIRECTION_NORMAL = 0;
	protected static final int DIRECTION_RIGHT = 1;
	protected static final int DIRECTION_REVERCE = 2;
	protected static final int DIRECTION_LEFT = 3;
	protected int direction = DIRECTION_NORMAL;

	// ミノを構成するパネルの位置情報を保持s
	protected int[][] panelArray = new int[ROW][COL];

	// 次のミノ取得用
	private static int listIndex = 0;

	public Mino() {
		this.init();
		this.initPanel();
		this.initCorrection();
	}

	// ランダムに異なる形状のミノのオブジェクトを返す
	public static Mino getMino() {
		Mino mino = null;

		// シャッフルして、順番を変える
        if (listIndex == 0) {
        	Collections.shuffle(list);
        }

        int num = list.get(listIndex);
        listIndex++;

        if (listIndex > 6) {
        	listIndex = 0;
        }

		switch(num) {
		case 0: mino = new MinoI();break;
		case 1: mino = new MinoJ();break;
		case 2: mino = new MinoL();break;
		case 3: mino = new MinoS();break;
		case 4: mino = new MinoZ();break;
		case 5: mino = new MinoT();break;
		case 6: mino = new MinoO();break;
		}
//mino = new MinoO();
		return mino;
	}

	// ミノの表示位置を初期化
	public void init() {
		this.x = Conf.MINO_X;
		this.y = Conf.MINO_Y;
	}

	// 回転補正配列初期化
	protected void initCorrection() {
		this.initCorrectionLeft();
		this.initCorrectionRight();
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

	private static ArrayList<Integer> list;

	static {
		list = new ArrayList<Integer>();
        for(int i = 0 ; i <= 6 ; i++) {
            list.add(i);
        }
	}

	// ミノに魔法薬をセットする
	protected void setMagicItem() {
		for (int row = 0; row < ROW; row++) {
			for (int col = 0; col < COL; col++) {
				if (this.panelArray[row][col] == 1) {
					this.panelArray[row][col] = 2;
					return;
				}
			}
		}
	}

	// ミノを表示する
	protected void show(GraphicsContext canvas, boolean isNext) {

		for (int row = 0; row < ROW; row++) {
			for (int col = 0; col < COL; col++) {
				double x = (col + this.x) * Conf.PANEL_W + Conf.FIELD_X;
				double y = (row + this.y) * Conf.PANEL_H + Conf.FIELD_Y;
				double w = Conf.PANEL_W;
				double h = Conf.PANEL_H;

				if (this.panelArray[row][col] == 1) {
					Image img = this.panel.getImage();
					if (isNext) {
						canvas.setGlobalAlpha(0.5);
						canvas.drawImage(img, x, y, w, h);
						canvas.setGlobalAlpha(1.0);
					} else {
						canvas.drawImage(img, x, y, w, h);
					}

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

	public int[][][] getCorrectionLeftArray() {
		return this.correctionLeftArray;
	}

	public int[][][] getCorrectionRightArray() {
		return this.correctionLeftArray;
	}

	abstract void turn();
	abstract void initCorrectionLeft();
	abstract void initCorrectionRight();
}