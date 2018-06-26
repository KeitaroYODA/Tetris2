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
}