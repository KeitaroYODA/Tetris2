package application;

//ミノ（■）
class MinoO extends Mino {

	public MinoO() {
		super();

		// ミノを構成するパネルの位置を指定（行、列）
		this.panelArray[1][1] = 1;
		this.panelArray[1][2] = 1;
		this.panelArray[2][1] = 1;
		this.panelArray[2][2] = 1;
		this.panel = new Panel(TetrisImage.minoO);
	}

	// ミノを回転
	public void turn() {
		return;
	}

	// 左回転
	// [ミノの向き][補正する回数][補正内容 0:X、1:Y]
	protected void initCorrectionLeft() {
		// 正面から左回転時
		correctionLeftArray[DIRECTION_NORMAL][0][CORRECTION_X] = 0;
		correctionLeftArray[DIRECTION_NORMAL][0][CORRECTION_Y] = 0;
		correctionLeftArray[DIRECTION_NORMAL][1][CORRECTION_X] = 0;
		correctionLeftArray[DIRECTION_NORMAL][1][CORRECTION_Y] = 0;
		correctionLeftArray[DIRECTION_NORMAL][2][CORRECTION_X] = 0;
		correctionLeftArray[DIRECTION_NORMAL][2][CORRECTION_Y] = 0;
		correctionLeftArray[DIRECTION_NORMAL][3][CORRECTION_X] = 0;
		correctionLeftArray[DIRECTION_NORMAL][3][CORRECTION_Y] = 0;
		// 左向きから左回転時
		correctionLeftArray[DIRECTION_LEFT][0][CORRECTION_X] = 0;
		correctionLeftArray[DIRECTION_LEFT][0][CORRECTION_Y] = 0;
		correctionLeftArray[DIRECTION_LEFT][1][CORRECTION_X] = 0;
		correctionLeftArray[DIRECTION_LEFT][1][CORRECTION_Y] = 0;
		correctionLeftArray[DIRECTION_LEFT][2][CORRECTION_X] = 0;
		correctionLeftArray[DIRECTION_LEFT][2][CORRECTION_Y] = 0;
		correctionLeftArray[DIRECTION_LEFT][3][CORRECTION_X] = 0;
		correctionLeftArray[DIRECTION_LEFT][3][CORRECTION_Y] = 0;
		// 逆向きから左回転時
		correctionLeftArray[DIRECTION_REVERCE][0][CORRECTION_X] = 0;
		correctionLeftArray[DIRECTION_REVERCE][0][CORRECTION_Y] = 0;
		correctionLeftArray[DIRECTION_REVERCE][1][CORRECTION_X] = 0;
		correctionLeftArray[DIRECTION_REVERCE][1][CORRECTION_Y] = 0;
		correctionLeftArray[DIRECTION_REVERCE][2][CORRECTION_X] = 0;
		correctionLeftArray[DIRECTION_REVERCE][2][CORRECTION_Y] = 0;
		correctionLeftArray[DIRECTION_REVERCE][3][CORRECTION_X] = 0;
		correctionLeftArray[DIRECTION_REVERCE][3][CORRECTION_Y] = 0;
		// 右向きから左回転時
		correctionLeftArray[DIRECTION_RIGHT][0][CORRECTION_X] = 0;
		correctionLeftArray[DIRECTION_RIGHT][0][CORRECTION_Y] = 0;
		correctionLeftArray[DIRECTION_RIGHT][1][CORRECTION_X] = 0;
		correctionLeftArray[DIRECTION_RIGHT][1][CORRECTION_Y] = 0;
		correctionLeftArray[DIRECTION_RIGHT][2][CORRECTION_X] = 0;
		correctionLeftArray[DIRECTION_RIGHT][2][CORRECTION_Y] = 0;
		correctionLeftArray[DIRECTION_RIGHT][3][CORRECTION_X] = 0;
		correctionLeftArray[DIRECTION_RIGHT][3][CORRECTION_Y] = 0;
	}

	// 右回転
	// [ミノの向き][補正する回数][補正内容 0:X、1:Y]
	protected void initCorrectionRight() {
		// 正面から右回転時
		correctionRightArray[DIRECTION_NORMAL][0][CORRECTION_X] = 0;
		correctionRightArray[DIRECTION_NORMAL][0][CORRECTION_Y] = 0;
		correctionRightArray[DIRECTION_NORMAL][1][CORRECTION_X] = 0;
		correctionRightArray[DIRECTION_NORMAL][1][CORRECTION_Y] = 0;
		correctionRightArray[DIRECTION_NORMAL][2][CORRECTION_X] = 0;
		correctionRightArray[DIRECTION_NORMAL][2][CORRECTION_Y] = 0;
		correctionRightArray[DIRECTION_NORMAL][3][CORRECTION_X] = 0;
		correctionRightArray[DIRECTION_NORMAL][3][CORRECTION_Y] = 0;
		// 右向きから右回転時
		correctionRightArray[DIRECTION_RIGHT][0][CORRECTION_X] = 0;
		correctionRightArray[DIRECTION_RIGHT][0][CORRECTION_Y] = 0;
		correctionRightArray[DIRECTION_RIGHT][1][CORRECTION_X] = 0;
		correctionRightArray[DIRECTION_RIGHT][1][CORRECTION_Y] = 0;
		correctionRightArray[DIRECTION_RIGHT][2][CORRECTION_X] = 0;
		correctionRightArray[DIRECTION_RIGHT][2][CORRECTION_Y] = 0;
		correctionRightArray[DIRECTION_RIGHT][3][CORRECTION_X] = 0;
		correctionRightArray[DIRECTION_RIGHT][3][CORRECTION_Y] = 0;
		// 逆向きから右回転時
		correctionRightArray[DIRECTION_REVERCE][0][CORRECTION_X] = 0;
		correctionRightArray[DIRECTION_REVERCE][0][CORRECTION_Y] = 0;
		correctionRightArray[DIRECTION_REVERCE][1][CORRECTION_X] = 0;
		correctionRightArray[DIRECTION_REVERCE][1][CORRECTION_Y] = 0;
		correctionRightArray[DIRECTION_REVERCE][2][CORRECTION_X] = 0;
		correctionRightArray[DIRECTION_REVERCE][2][CORRECTION_Y] = 0;
		correctionRightArray[DIRECTION_REVERCE][3][CORRECTION_X] = 0;
		correctionRightArray[DIRECTION_REVERCE][3][CORRECTION_Y] = 0;
		// 左向きから右回転時
		correctionRightArray[DIRECTION_LEFT][0][CORRECTION_X] = 0;
		correctionRightArray[DIRECTION_LEFT][0][CORRECTION_Y] = 0;
		correctionRightArray[DIRECTION_LEFT][1][CORRECTION_X] = 0;
		correctionRightArray[DIRECTION_LEFT][1][CORRECTION_Y] = 0;
		correctionRightArray[DIRECTION_LEFT][2][CORRECTION_X] = 0;
		correctionRightArray[DIRECTION_LEFT][2][CORRECTION_Y] = 0;
		correctionRightArray[DIRECTION_LEFT][3][CORRECTION_X] = 0;
		correctionRightArray[DIRECTION_LEFT][3][CORRECTION_Y] = 0;
	}
}