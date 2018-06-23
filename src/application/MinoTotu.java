package application;

//ミノ（凸）
class MinoTotu extends Mino {

	public MinoTotu() {
		super();

		// ミノを構成するパネルの位置を指定（行、列）
		this.panelArray[1][1] = 1;
		this.panelArray[2][0] = 1;
		this.panelArray[2][1] = 1;
		this.panelArray[2][2] = 1;

		this.panel = new Panel(TetrisImage.minoTotu);
	}

	// ミノを回転
	public void turn() {
		// パネルの配列情報を初期化
		this.initPanel();

		switch(this.direction) {
		case DIRECTION_NORMAL: // 正面
			this.panelArray[1][1] = 1;
			this.panelArray[2][0] = 1;
			this.panelArray[2][1] = 1;
			this.panelArray[2][2] = 1;
			break;
		case DIRECTION_RIGHT: // 右向き
			this.panelArray[0][1] = 1;
			this.panelArray[1][1] = 1;
			this.panelArray[1][2] = 1;
			this.panelArray[2][1] = 1;
			break;
		case DIRECTION_REVERCE: // 上下逆
			this.panelArray[1][1] = 1;
			this.panelArray[1][2] = 1;
			this.panelArray[1][3] = 1;
			this.panelArray[2][2] = 1;
			break;
		case DIRECTION_LEFT: // 左向き
			this.panelArray[1][2] = 1;
			this.panelArray[2][1] = 1;
			this.panelArray[2][2] = 1;
			this.panelArray[3][2] = 1;
			break;
		}
	}
}