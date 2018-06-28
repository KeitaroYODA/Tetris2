package application;

import java.util.ArrayList;
import java.util.Collections;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

// ミノクラス
public abstract class Mino implements Cloneable{
	// ミノの向き
	protected static final int DIRECTION_NORMAL = 0;
	protected static final int DIRECTION_RIGHT = 1;
	protected static final int DIRECTION_REVERCE = 2;
	protected static final int DIRECTION_LEFT = 3;

	// ミノを構成するパネル数
	private static final int ROW = 4;
	private static final int COL = 4;

	// 次のミノ取得用
	private static int listIndex = 0;
	private static ArrayList<Integer> list;

	static {
		list = new ArrayList<Integer>();
        for(int i = 0 ; i <= 6 ; i++) {
            list.add(i);
        }
	}

	// 回転時の補正情報
	public static final int CORRECTION_X = 0;
	public static final int CORRECTION_Y = 1;
	public static final int DIRECTIONS = 4; // ミノの向きの種別数(正逆左右の4種類)
	public static final int CORRECTIONS = 4; // 補正実施回数

	// 回転時の補正情報[ミノの向き][補正回][補正内容 0:X軸補正値、1:Y軸補正値]
	protected final int[][][] correctionLeftArray = new int[DIRECTIONS][CORRECTIONS][2]; // 左回転
	protected final int[][][] correctionRightArray = new int[DIRECTIONS][CORRECTIONS][2]; // 右回転

	// ミノに表示する画像情報を格納
	protected Panel panel;

	// ミノの左上の配列インデックス
	protected int x; // 横
	protected int y; // 縦

	// ミノの向き
	protected int direction = DIRECTION_NORMAL;

	// ミノを構成するパネルの位置情報を保持
	protected int[][] panelArray = new int[ROW][COL];

	/**
	 * コンストラクタ
	 */
	public Mino() {
		this.init();
		this.initPanel();
		this.initCorrection();
	}

	/**
	 * ランダムに異なる形状のミノのオブジェクトを返す
	 * @return Mino
	 */
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

	/**
	 * ミノの表示位置を初期化
	 */
	public void init() {
		this.x = Conf.MINO_X;
		this.y = Conf.MINO_Y;
	}

	/**
	 * 回転補正配列初期化
	 */
	protected void initCorrection() {
		this.initCorrectionLeft();
		this.initCorrectionRight();
	}

	/**
	 * ミノを構成するパネル配列の初期化
	 */
	protected void initPanel() {
		for (int i = 0; i < ROW; i++) {
			for (int l = 0; l < COL; l++) {
				this.panelArray[i][l] = 0;
			}
		}
	}

	/**
	 * ミノを構成するパネル情報配列を返す
	 * @return int[][] ミノの形状格納配列
	 */
	public int[][] getPanelArray() {
		return this.panelArray;
	}

	/**
	 * ミノを表示する
	 * @param canvas GraphicsContext2D
	 * @param isNext true:次の次のミノ(=少し暗く表示)、false:そのまま表示
	 */
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

	/**
	 * ミノの列数を返す
	 * @return ミノの列数
	 */
	public static int COL() {
		return COL;
	}

	/**
	 * ミノの行数を返す
	 * @return ミノの行数
	 */
	public static int ROW() {
		return ROW;
	}

	/**
	 * ミノを構成するパネルオブジェクトを返す
	 * @return Panel ミノを構成するパネルオブジェクト
	 */
	public Panel getPanel() {
		return this.panel;
	}

	/**
	 * ミノの現在の向きを返す
	 * @return ミノの現在の向き
	 * Mino.DIRECTION_NORMAL = 0
	 * Mino.DIRECTION_RIGHT = 1
	 * Mino.DIRECTION_REVERCE = 2
	 * Mino.DIRECTION_LEFT = 3
	 */
	public int getDirection() {
		return this.direction;
	}

	/**
	 * ミノの向きをセットする
	 * @param direction
	 * Mino.DIRECTION_NORMAL = 0
	 * Mino.DIRECTION_RIGHT = 1
	 * Mino.DIRECTION_REVERCE = 2
	 * Mino.DIRECTION_LEFT = 3
	 */
	public void setDirection(int direction) {
		this.direction = direction;
	}

	/**
	 * ミノの左上の位置（横軸）を返す
	 * @return ミノの左上の位置（横軸）
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * ミノの左上の位置（縦軸）を返す
	 * @return ミノの左上の位置（縦軸）
	 */
	public int getY() {
		return this.y;
	}

	/**
	 * ミノの左上の位置（横軸）をセット
	 * @param x ミノの左上の位置（横軸）
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * ミノの左上の位置（縦軸）をセット
	 * @param y ミノの左上の位置（縦軸）
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * 回転補正（左回転）情報配列を返す
	 * @return int[][][] 回転補正（左回転）情報配列
	 * [ミノの向き(this.direction)][補正回][補正軸 0:X、1:Y]
	 */
	public int[][][] getCorrectionLeftArray() {
		return this.correctionLeftArray;
	}

	/**
	 * 回転補正（右回転）情報配列を返す
	 * @return int[][][] 回転補正（右回転）情報配列
	 * [ミノの向き(this.direction)][補正回][補正軸 0:X、1:Y]
	 */
	public int[][][] getCorrectionRightArray() {
		return this.correctionLeftArray;
	}

	abstract void turn();
	abstract void initCorrectionLeft();
	abstract void initCorrectionRight();
}