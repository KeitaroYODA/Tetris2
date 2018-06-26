package application;

//ミノ（｜）
class MinoI extends Mino {

	public MinoI() {
		super();

		// ミノを構成するパネルの位置を指定（行、列）
		this.panelArray[1][0] = 1;
		this.panelArray[1][1] = 1;
		this.panelArray[1][2] = 1;
		this.panelArray[1][3] = 1;

		this.panel = new Panel(TetrisImage.minoI);

		// [ミノの向き][補正する回数][補正内容 0:X、1:Y]
		correctionLeftArray[DIRECTION_NORMAL][0][CORRECTION_X] = -1;
		correctionLeftArray[DIRECTION_NORMAL][0][CORRECTION_Y] = 0;
		correctionLeftArray[DIRECTION_NORMAL][1][CORRECTION_X] = 2;
		correctionLeftArray[DIRECTION_NORMAL][1][CORRECTION_Y] = 0;
		correctionLeftArray[DIRECTION_REVERCE][0][CORRECTION_X] = -1;
		correctionLeftArray[DIRECTION_REVERCE][0][CORRECTION_Y] = 0;
		correctionLeftArray[DIRECTION_REVERCE][1][CORRECTION_X] = 2;
		correctionLeftArray[DIRECTION_REVERCE][1][CORRECTION_Y] = 0;
	}

	// ミノを回転
	public void turn() {
		// パネルの配列情報を初期化
		this.initPanel();

		switch(this.direction) {
		case DIRECTION_NORMAL: // 正面
			this.panelArray[1][0] = 1;
			this.panelArray[1][1] = 1;
			this.panelArray[1][2] = 1;
			this.panelArray[1][3] = 1;
			break;
		case DIRECTION_RIGHT: // 右向き
			this.panelArray[0][2] = 1;
			this.panelArray[1][2] = 1;
			this.panelArray[2][2] = 1;
			this.panelArray[3][2] = 1;
			break;
		case DIRECTION_REVERCE: // 上下逆
			this.panelArray[2][0] = 1;
			this.panelArray[2][1] = 1;
			this.panelArray[2][2] = 1;
			this.panelArray[2][3] = 1;
			break;
		case DIRECTION_LEFT: // 左向き
			this.panelArray[0][1] = 1;
			this.panelArray[1][1] = 1;
			this.panelArray[2][1] = 1;
			this.panelArray[3][1] = 1;
			break;
		}
	}
}