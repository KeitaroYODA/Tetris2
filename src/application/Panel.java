package application;

import javafx.scene.image.WritableImage;

/**
 * ミノを構成するパネルクラス
 *
 */
public final class Panel {
	// 画像オブジェクト
	private WritableImage resizedImage;

	/**
	 * コンストラクタ
	 * @param image ミノに表示されるImageオブジェクト
	 */
	public Panel(WritableImage image) {
		this.resizedImage = image;
	}

	/**
	 * 自身の画像オブジェクトを返す
	 * @return WritableImage
	 */
	public WritableImage getImage() {
		return resizedImage;
	}
}