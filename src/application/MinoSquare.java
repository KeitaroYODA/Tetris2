package application;

//ミノ（■）
class MinoSquare extends Mino {

	public MinoSquare() {
		super();

		// ミノを構成するパネルの位置を指定（行、列）
		this.panelArray[1][1] = 1;
		this.panelArray[1][2] = 1;
		this.panelArray[2][1] = 1;
		this.panelArray[2][2] = 1;

		this.panel = new Panel(TetrisImage.minoSquare);
	}

	// ミノを回転
	public void turn() {
		return;
	}
}