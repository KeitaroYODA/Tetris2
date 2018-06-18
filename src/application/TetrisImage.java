package application;

import java.io.File;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

final class TetrisImage{

	private boolean isEnd; // アニメ再生終了フラグ
	private int count; // アニメ再生用カウンタ

	public TetrisImage() {
		this.init();
	}

	// アニメーション再生前に呼び出す
	public void init() {
		this.isEnd = false;
		this.count = 0;
	}

	// アニメーションが終了している場合真を返す
	public boolean isEnd() {
		return this.isEnd;
	}

	// 画像ファイル
	static Image minoImg = new Image(new File("tile_1.png").toURI().toString());
	static Image magicImg = new Image(new File("tile_1.png").toURI().toString());
	static Image heroImg = new Image(new File("hero.png").toURI().toString());
	static Image haikeiHeroImg = new Image(new File("haikei_hero.jpg").toURI().toString());

	// ミノ画像
	static WritableImage minoBar = new WritableImage(minoImg.getPixelReader(),0, 32, (int) (minoImg.getWidth() / 16), (int) (minoImg.getHeight() / 11));
	static WritableImage minoKagi1 = new WritableImage(minoImg.getPixelReader(),32, 32, (int) (minoImg.getWidth() / 16), (int) (minoImg.getHeight() / 16));
	static WritableImage minoKagi2 = new WritableImage(minoImg.getPixelReader(),64, 32, (int) (minoImg.getWidth() / 16), (int) (minoImg.getHeight() / 16));
	static WritableImage minoSquare = new WritableImage(minoImg.getPixelReader(),96, 32, (int) (minoImg.getWidth() / 16), (int) (minoImg.getHeight() / 16));
	static WritableImage minoTotu = new WritableImage(minoImg.getPixelReader(),128, 32, (int) (minoImg.getWidth() / 16), (int) (minoImg.getHeight() / 16));

	// 主人公さん背景画像
	static WritableImage haikei = new WritableImage(minoImg.getPixelReader(),448, 96, (int) (minoImg.getWidth() / 16), (int) (minoImg.getHeight() / 16));

	// 魔法陣
	public WritableImage magicCircle() {
		WritableImage[] resizedImage = new WritableImage[4];
		resizedImage[0] = new WritableImage(magicImg.getPixelReader(),256, 0, (int) (magicImg.getWidth() / 16), (int) (magicImg.getHeight() / 11));
		resizedImage[1] = new WritableImage(magicImg.getPixelReader(),288, 0, (int) (magicImg.getWidth() / 16), (int) (magicImg.getHeight() / 11));
		resizedImage[2] = new WritableImage(magicImg.getPixelReader(),320, 0, (int) (magicImg.getWidth() / 16), (int) (magicImg.getHeight() / 11));
		resizedImage[3] = new WritableImage(magicImg.getPixelReader(),352, 0, (int) (magicImg.getWidth() / 16), (int) (magicImg.getHeight() / 11));

		int index = this.count;
		this.count++;
		if (this.count >= resizedImage.length) {
			this.count = 0;
		}
		return resizedImage[index];
	}

	// キャラクタアニメ
	// ポーズ中
	public WritableImage heroAnime_1() {
		WritableImage[] resizedImage = new WritableImage[4];
		resizedImage[0] = new WritableImage(heroImg.getPixelReader(),0, 0, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[1] = new WritableImage(heroImg.getPixelReader(),96, 0, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[2] = new WritableImage(heroImg.getPixelReader(),192, 0, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[3] = new WritableImage(heroImg.getPixelReader(),96, 0, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));

		int index = this.count;
		this.count++;
		if (this.count >= resizedImage.length) {
			this.count = 0;
		}
		return resizedImage[index];
	}

	// 通常モーション（飛行）
	public WritableImage heroAnime_7() {
		WritableImage[] resizedImage = new WritableImage[3];
		resizedImage[0] = new WritableImage(heroImg.getPixelReader(),288, 0, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[1] = new WritableImage(heroImg.getPixelReader(),384, 0, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[2] = new WritableImage(heroImg.getPixelReader(),480, 0, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));

		int index = this.count;
		this.count++;
		if (this.count >= resizedImage.length) {
			this.count = 0;
		}
		return resizedImage[index];
	}

	// 魔法準備中モーション
	public WritableImage heroAnime_8() {
		WritableImage[] resizedImage = new WritableImage[3];
		resizedImage[0] = new WritableImage(heroImg.getPixelReader(),0, 192, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[1] = new WritableImage(heroImg.getPixelReader(),96, 192, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[2] = new WritableImage(heroImg.getPixelReader(),192, 192, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));

		int index = this.count;
		this.count++;
		if (this.count >= resizedImage.length) {
			this.count = 0;
		}
		return resizedImage[index];
	}

	// ブロックが1行揃った喜びモーション
	public WritableImage heroAnime_9() {
		WritableImage[] resizedImage = new WritableImage[6];
		resizedImage[0] = new WritableImage(heroImg.getPixelReader(),288, 96, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[1] = new WritableImage(heroImg.getPixelReader(),384, 96, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[2] = new WritableImage(heroImg.getPixelReader(),480, 96, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[3] = new WritableImage(heroImg.getPixelReader(),576, 96, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[4] = new WritableImage(heroImg.getPixelReader(),672, 96, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[5] = new WritableImage(heroImg.getPixelReader(),768, 96, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));

		int index;
		if (!this.isEnd) {
			index = this.count;
			this.count++;
		} else {
			index = resizedImage.length - 1;
		}

		// アニメの再生が終わった
		if (this.count == resizedImage.length) {
			this.isEnd = true;
		}

		return resizedImage[index];
	}

	// 通常モーション2
	public static WritableImage[] heroAnime_2() {
		WritableImage[] resizedImage = new WritableImage[9];
		resizedImage[0] = new WritableImage(heroImg.getPixelReader(),0, 96, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[1] = new WritableImage(heroImg.getPixelReader(),96, 96, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[2] = new WritableImage(heroImg.getPixelReader(),192, 96, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[3] = new WritableImage(heroImg.getPixelReader(),288, 96, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[4] = new WritableImage(heroImg.getPixelReader(),384, 96, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[5] = new WritableImage(heroImg.getPixelReader(),480, 96, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[6] = new WritableImage(heroImg.getPixelReader(),576, 96, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[7] = new WritableImage(heroImg.getPixelReader(),672, 96, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[8] = new WritableImage(heroImg.getPixelReader(),768, 96, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		return resizedImage;
	}

	// やられモーション
	public static WritableImage[] heroAnime_3() {
		WritableImage[] resizedImage = new WritableImage[9];
		resizedImage[0] = new WritableImage(heroImg.getPixelReader(),0, 192, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[1] = new WritableImage(heroImg.getPixelReader(),96, 192, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[2] = new WritableImage(heroImg.getPixelReader(),192, 192, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[3] = new WritableImage(heroImg.getPixelReader(),288, 192, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[4] = new WritableImage(heroImg.getPixelReader(),384, 192, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[5] = new WritableImage(heroImg.getPixelReader(),480, 192, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[6] = new WritableImage(heroImg.getPixelReader(),576, 192, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[7] = new WritableImage(heroImg.getPixelReader(),672, 192, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[8] = new WritableImage(heroImg.getPixelReader(),768, 192, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		return resizedImage;
	}

	// 魔法発動モーション
	public WritableImage heroAnime_4() {
		WritableImage[] resizedImage = new WritableImage[9];
		resizedImage[0] = new WritableImage(heroImg.getPixelReader(),0, 288, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[1] = new WritableImage(heroImg.getPixelReader(),96, 288, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[2] = new WritableImage(heroImg.getPixelReader(),192, 288, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[3] = new WritableImage(heroImg.getPixelReader(),288, 288, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[4] = new WritableImage(heroImg.getPixelReader(),384, 288, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[5] = new WritableImage(heroImg.getPixelReader(),480, 288, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[6] = new WritableImage(heroImg.getPixelReader(),576, 288, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[7] = new WritableImage(heroImg.getPixelReader(),672, 288, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[8] = new WritableImage(heroImg.getPixelReader(),768, 288, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));

		int index;
		// アニメの再生が終わった
		if (this.count == resizedImage.length) {
			this.isEnd = true;
			index = resizedImage.length - 1;
		} else {
			index = this.count;
			this.count++;
		}

		return resizedImage[index];
	}

	// 魔法発動モーション
	public static WritableImage[] heroAnime_5() {
		WritableImage[] resizedImage = new WritableImage[9];
		resizedImage[0] = new WritableImage(heroImg.getPixelReader(),0, 384, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[1] = new WritableImage(heroImg.getPixelReader(),96, 384, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[2] = new WritableImage(heroImg.getPixelReader(),192, 384, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[3] = new WritableImage(heroImg.getPixelReader(),288, 384, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[4] = new WritableImage(heroImg.getPixelReader(),384, 384, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[5] = new WritableImage(heroImg.getPixelReader(),480, 384, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[6] = new WritableImage(heroImg.getPixelReader(),576, 384, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[7] = new WritableImage(heroImg.getPixelReader(),672, 384, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[8] = new WritableImage(heroImg.getPixelReader(),768, 384, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		return resizedImage;
	}

	// ゲームオーバー
	/*
	public static WritableImage[] heroAnime_6() {
		WritableImage[] resizedImage = new WritableImage[9];
		resizedImage[0] = new WritableImage(heroImg.getPixelReader(),0, 480, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[1] = new WritableImage(heroImg.getPixelReader(),96, 480, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[2] = new WritableImage(heroImg.getPixelReader(),192, 480, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[3] = new WritableImage(heroImg.getPixelReader(),288, 480, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[4] = new WritableImage(heroImg.getPixelReader(),384, 480, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[5] = new WritableImage(heroImg.getPixelReader(),480, 480, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[6] = new WritableImage(heroImg.getPixelReader(),576, 480, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[7] = new WritableImage(heroImg.getPixelReader(),672, 480, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[8] = new WritableImage(heroImg.getPixelReader(),768, 480, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		return resizedImage;
	}
	*/
	public WritableImage heroAnime_6() {
		WritableImage[] resizedImage = new WritableImage[9];
		resizedImage[0] = new WritableImage(heroImg.getPixelReader(),0, 480, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[1] = new WritableImage(heroImg.getPixelReader(),96, 480, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[2] = new WritableImage(heroImg.getPixelReader(),192, 480, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[3] = new WritableImage(heroImg.getPixelReader(),288, 480, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[4] = new WritableImage(heroImg.getPixelReader(),384, 480, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[5] = new WritableImage(heroImg.getPixelReader(),480, 480, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[6] = new WritableImage(heroImg.getPixelReader(),576, 480, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[7] = new WritableImage(heroImg.getPixelReader(),672, 480, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[8] = new WritableImage(heroImg.getPixelReader(),768, 480, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));

		int index;
		if (!this.isEnd) {
			index = this.count;
			this.count++;
		} else {
			index = resizedImage.length - 1;
		}

		// アニメの再生が終わった
		if (this.count == resizedImage.length) {
			this.isEnd = true;
		}

		return resizedImage[index];
	}

	// 炎アニメ
	public WritableImage flameAnime() {
		WritableImage[] resizedImage = new WritableImage[3];
		resizedImage[0] = new WritableImage(magicImg.getPixelReader(),192, 256, (int) (magicImg.getWidth() / 16), (int) (magicImg.getHeight() / 11));
		resizedImage[1] = new WritableImage(magicImg.getPixelReader(),192, 288, (int) (magicImg.getWidth() / 16), (int) (magicImg.getHeight() / 11));
		resizedImage[2] = new WritableImage(magicImg.getPixelReader(),192, 320, (int) (magicImg.getWidth() / 16), (int) (magicImg.getHeight() / 11));

		int index = 0;
		if (this.count >= resizedImage.length) {
			this.count = 0;
		}
		index = this.count;
		this.count++;

		return resizedImage[index];
	}

	// 爆発
	public WritableImage bombAnime() {
		WritableImage[] resizedImage = new WritableImage[3];
		resizedImage[0] = new WritableImage(magicImg.getPixelReader(),256, 256, (int) (magicImg.getWidth() / 16), (int) (magicImg.getHeight() / 11));
		resizedImage[1] = new WritableImage(magicImg.getPixelReader(),256, 288, (int) (magicImg.getWidth() / 16), (int) (magicImg.getHeight() / 11));
		resizedImage[2] = new WritableImage(magicImg.getPixelReader(),256, 320, (int) (magicImg.getWidth() / 16), (int) (magicImg.getHeight() / 11));

		int index;
		if (!this.isEnd) {
			index = this.count;
			this.count++;
		} else {
			index = resizedImage.length - 1;
		}

		// アニメの再生が終わった
		if (this.count == resizedImage.length) {
			this.isEnd = true;
		}

		return resizedImage[index];
	}

	// 星きらめき
	public WritableImage starAnime() {
		WritableImage[] resizedImage = new WritableImage[8];
		resizedImage[0] = new WritableImage(magicImg.getPixelReader(),0, 288, (int) (magicImg.getWidth() / 16), (int) (magicImg.getHeight() / 11));
		resizedImage[1] = new WritableImage(magicImg.getPixelReader(),32, 288, (int) (magicImg.getWidth() / 16), (int) (magicImg.getHeight() / 11));
		resizedImage[2] = new WritableImage(magicImg.getPixelReader(),64, 288, (int) (magicImg.getWidth() / 16), (int) (magicImg.getHeight() / 11));
		resizedImage[3] = new WritableImage(magicImg.getPixelReader(),98, 288, (int) (magicImg.getWidth() / 16), (int) (magicImg.getHeight() / 11));
		resizedImage[4] = new WritableImage(magicImg.getPixelReader(),0, 320, (int) (magicImg.getWidth() / 16), (int) (magicImg.getHeight() / 11));
		resizedImage[5] = new WritableImage(magicImg.getPixelReader(),32, 320, (int) (magicImg.getWidth() / 16), (int) (magicImg.getHeight() / 11));
		resizedImage[6] = new WritableImage(magicImg.getPixelReader(),64, 320, (int) (magicImg.getWidth() / 16), (int) (magicImg.getHeight() / 11));
		resizedImage[7] = new WritableImage(magicImg.getPixelReader(),98, 320, (int) (magicImg.getWidth() / 16), (int) (magicImg.getHeight() / 11));

		int index;
		if (!this.isEnd) {
			index = this.count;
			this.count++;
		} else {
			index = resizedImage.length - 1;
		}

		// アニメの再生が終わった
		if (this.count == resizedImage.length) {
			this.isEnd = true;
		}

		return resizedImage[index];
	}
}