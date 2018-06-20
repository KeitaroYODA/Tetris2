package application;

import javafx.scene.image.WritableImage;

// ミノを構成するパネルクラス
class Panel {
	// パネル幅
	private static final double panelW = 20;

	// パネル高さ
	private static final double panelH = 20;

	// 画像オブジェクト
	private WritableImage resizedImage;

	public Panel(WritableImage image) {
		this.resizedImage = image;
	}

	// パネルの画像オブジェクトを返す
	public WritableImage getImage() {
		return resizedImage;
	}

	// パネルのサイズ（幅）を返す
	public static double panelW() {
		return panelW;
	}

	// パネルのサイズ（高さ）を返す
	public static double panelH() {
		return panelH;
	}
}