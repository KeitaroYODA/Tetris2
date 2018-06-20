package application;

//ミノ（｜）
class MinoBar extends Mino {

	public MinoBar() {
		super();

		// ミノを構成するパネルの位置を指定（行、列）
		this.panelArray[0][1] = 1;
		this.panelArray[1][1] = 1;
		this.panelArray[2][1] = 1;
		this.panelArray[3][1] = 1;

		this.panel = new Panel(TetrisImage.minoBar);
	}

	// ミノを回転
	public void turn() {
		// パネルの配列情報を初期化
		this.initPanel();

		switch(this.direction) {
		case 1: // 正面
			this.panelArray[0][1] = 1;
			this.panelArray[1][1] = 1;
			this.panelArray[2][1] = 1;
			this.panelArray[3][1] = 1;
			break;
		case 2: // 右向き
			this.panelArray[1][0] = 1;
			this.panelArray[1][1] = 1;
			this.panelArray[1][2] = 1;
			this.panelArray[1][3] = 1;
			break;
		case 3: // 上下逆
			this.panelArray[0][1] = 1;
			this.panelArray[1][1] = 1;
			this.panelArray[2][1] = 1;
			this.panelArray[3][1] = 1;
			break;
		case 4: // 左向き
			this.panelArray[1][0] = 1;
			this.panelArray[1][1] = 1;
			this.panelArray[1][2] = 1;
			this.panelArray[1][3] = 1;
			break;
		}
	}
}