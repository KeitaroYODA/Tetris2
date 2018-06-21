package application;

import javafx.scene.image.WritableImage;

// ミノを構成するパネルクラス
class Panel {

	// 画像オブジェクト
	private WritableImage resizedImage;

	public Panel(WritableImage image) {
		this.resizedImage = image;
	}

	// パネルの画像オブジェクトを返す
	public WritableImage getImage() {
		return resizedImage;
	}
}