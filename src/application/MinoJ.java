package application;


class MinoJ extends Mino {
	public MinoJ() {
		super();

		// ミノを構成するパネルの位置を指定（行、列）
		this.panelArray[1][0] = 1;
		this.panelArray[2][0] = 1;
		this.panelArray[2][1] = 1;
		this.panelArray[2][2] = 1;
		this.panel = new Panel(TetrisImage.minoJ);
	}

	// ミノを回転
	public void turn() {
		// パネルの配列情報を初期化
		this.initPanel();

		switch(this.direction) {
		case DIRECTION_NORMAL: // 正面
			this.panelArray[1][0] = 1;
			this.panelArray[2][0] = 1;
			this.panelArray[2][1] = 1;
			this.panelArray[2][2] = 1;
			break;
		case DIRECTION_RIGHT: // 右向き
			this.panelArray[1][1] = 1;
			this.panelArray[1][2] = 1;
			this.panelArray[2][1] = 1;
			this.panelArray[3][1] = 1;
			break;
		case DIRECTION_REVERCE: // 上下逆
			this.panelArray[2][0] = 1;
			this.panelArray[2][1] = 1;
			this.panelArray[2][2] = 1;
			this.panelArray[3][2] = 1;
			break;
		case DIRECTION_LEFT: // 左向き
			this.panelArray[1][1] = 1;
			this.panelArray[2][1] = 1;
			this.panelArray[3][0] = 1;
			this.panelArray[3][1] = 1;
			break;
		}
	}

	// 左回転
	// [ミノの向き][補正する回数][補正内容 0:X、1:Y]
	protected void initCorrectionLeft() {
		// 正面から左回転時
		correctionLeftArray[DIRECTION_NORMAL][0][CORRECTION_X] = 1;
		correctionLeftArray[DIRECTION_NORMAL][0][CORRECTION_Y] = 0;
		correctionLeftArray[DIRECTION_NORMAL][1][CORRECTION_X] = 1;
		correctionLeftArray[DIRECTION_NORMAL][1][CORRECTION_Y] = -1;
		correctionLeftArray[DIRECTION_NORMAL][2][CORRECTION_X] = 0;
		correctionLeftArray[DIRECTION_NORMAL][2][CORRECTION_Y] = 2;
		correctionLeftArray[DIRECTION_NORMAL][3][CORRECTION_X] = 1;
		correctionLeftArray[DIRECTION_NORMAL][3][CORRECTION_Y] = 2;
		// 左向きから左回転時
		correctionLeftArray[DIRECTION_LEFT][0][CORRECTION_X] = -1;
		correctionLeftArray[DIRECTION_LEFT][0][CORRECTION_Y] = 0;
		correctionLeftArray[DIRECTION_LEFT][1][CORRECTION_X] = -1;
		correctionLeftArray[DIRECTION_LEFT][1][CORRECTION_Y] = 1;
		correctionLeftArray[DIRECTION_LEFT][2][CORRECTION_X] = 0;
		correctionLeftArray[DIRECTION_LEFT][2][CORRECTION_Y] = -2;
		correctionLeftArray[DIRECTION_LEFT][3][CORRECTION_X] = -1;
		correctionLeftArray[DIRECTION_LEFT][3][CORRECTION_Y] = -2;
		// 逆向きから左回転時
		correctionLeftArray[DIRECTION_REVERCE][0][CORRECTION_X] = -1;
		correctionLeftArray[DIRECTION_REVERCE][0][CORRECTION_Y] = 0;
		correctionLeftArray[DIRECTION_REVERCE][1][CORRECTION_X] = -1;
		correctionLeftArray[DIRECTION_REVERCE][1][CORRECTION_Y] = -1;
		correctionLeftArray[DIRECTION_REVERCE][2][CORRECTION_X] = 0;
		correctionLeftArray[DIRECTION_REVERCE][2][CORRECTION_Y] = 2;
		correctionLeftArray[DIRECTION_REVERCE][3][CORRECTION_X] = -1;
		correctionLeftArray[DIRECTION_REVERCE][3][CORRECTION_Y] = 2;
		// 右向きから左回転時
		correctionLeftArray[DIRECTION_RIGHT][0][CORRECTION_X] = 1;
		correctionLeftArray[DIRECTION_RIGHT][0][CORRECTION_Y] = 0;
		correctionLeftArray[DIRECTION_RIGHT][1][CORRECTION_X] = 1;
		correctionLeftArray[DIRECTION_RIGHT][1][CORRECTION_Y] = 1;
		correctionLeftArray[DIRECTION_RIGHT][2][CORRECTION_X] = 0;
		correctionLeftArray[DIRECTION_RIGHT][2][CORRECTION_Y] = -2;
		correctionLeftArray[DIRECTION_RIGHT][3][CORRECTION_X] = 1;
		correctionLeftArray[DIRECTION_RIGHT][3][CORRECTION_Y] = -2;
	}

	// 右回転
	// [ミノの向き][補正する回数][補正内容 0:X、1:Y]
	protected void initCorrectionRight() {
		// 正面から右回転時
		correctionRightArray[DIRECTION_NORMAL][0][CORRECTION_X] = -1;
		correctionRightArray[DIRECTION_NORMAL][0][CORRECTION_Y] = 0;
		correctionRightArray[DIRECTION_NORMAL][1][CORRECTION_X] = -1;
		correctionRightArray[DIRECTION_NORMAL][1][CORRECTION_Y] = -1;
		correctionRightArray[DIRECTION_NORMAL][2][CORRECTION_X] = 0;
		correctionRightArray[DIRECTION_NORMAL][2][CORRECTION_Y] = 2;
		correctionRightArray[DIRECTION_NORMAL][3][CORRECTION_X] = -1;
		correctionRightArray[DIRECTION_NORMAL][3][CORRECTION_Y] = 2;
		// 右向きから右回転時
		correctionRightArray[DIRECTION_RIGHT][0][CORRECTION_X] = 1;
		correctionRightArray[DIRECTION_RIGHT][0][CORRECTION_Y] = 0;
		correctionRightArray[DIRECTION_RIGHT][1][CORRECTION_X] = 1;
		correctionRightArray[DIRECTION_RIGHT][1][CORRECTION_Y] = 1;
		correctionRightArray[DIRECTION_RIGHT][2][CORRECTION_X] = 0;
		correctionRightArray[DIRECTION_RIGHT][2][CORRECTION_Y] = -2;
		correctionRightArray[DIRECTION_RIGHT][3][CORRECTION_X] = 1;
		correctionRightArray[DIRECTION_RIGHT][3][CORRECTION_Y] = -2;
		// 逆向きから右回転時
		correctionRightArray[DIRECTION_REVERCE][0][CORRECTION_X] = 1;
		correctionRightArray[DIRECTION_REVERCE][0][CORRECTION_Y] = 0;
		correctionRightArray[DIRECTION_REVERCE][1][CORRECTION_X] = 1;
		correctionRightArray[DIRECTION_REVERCE][1][CORRECTION_Y] = -1;
		correctionRightArray[DIRECTION_REVERCE][2][CORRECTION_X] = 0;
		correctionRightArray[DIRECTION_REVERCE][2][CORRECTION_Y] = 2;
		correctionRightArray[DIRECTION_REVERCE][3][CORRECTION_X] = 1;
		correctionRightArray[DIRECTION_REVERCE][3][CORRECTION_Y] = 2;
		// 左向きから右回転時
		correctionRightArray[DIRECTION_LEFT][0][CORRECTION_X] = -1;
		correctionRightArray[DIRECTION_LEFT][0][CORRECTION_Y] = 0;
		correctionRightArray[DIRECTION_LEFT][1][CORRECTION_X] = -1;
		correctionRightArray[DIRECTION_LEFT][1][CORRECTION_Y] = 1;
		correctionRightArray[DIRECTION_LEFT][2][CORRECTION_X] = 0;
		correctionRightArray[DIRECTION_LEFT][2][CORRECTION_Y] = -2;
		correctionRightArray[DIRECTION_LEFT][3][CORRECTION_X] = -1;
		correctionRightArray[DIRECTION_LEFT][3][CORRECTION_Y] = -2;
	}
}