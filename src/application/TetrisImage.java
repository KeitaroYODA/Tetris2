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
	static Image minoImg = new Image(new File("res/image/tile_1.png").toURI().toString());
	static Image magicImg = new Image(new File("res/image/tile_1.png").toURI().toString());
	static Image magicImg2 = new Image(new File("res/image/magic1.png").toURI().toString());
	static Image heroImg = new Image(new File("res/image/hero.png").toURI().toString()); // 主人公さん
	static Image magicIoImg = new Image(new File("res/image/kaenbeam.png").toURI().toString()); // 魔法（イオ）発動
	static Image haikeiHeroImg = new Image(new File("res/image/haikei_hero.jpg").toURI().toString());

	// ミノ画像
	static WritableImage minoBar = new WritableImage(minoImg.getPixelReader(),0, 32, (int) (minoImg.getWidth() / 16), (int) (minoImg.getHeight() / 11));
	static WritableImage minoKagi1 = new WritableImage(minoImg.getPixelReader(),32, 32, (int) (minoImg.getWidth() / 16), (int) (minoImg.getHeight() / 16));
	static WritableImage minoKagi2 = new WritableImage(minoImg.getPixelReader(),64, 32, (int) (minoImg.getWidth() / 16), (int) (minoImg.getHeight() / 16));
	static WritableImage minoSquare = new WritableImage(minoImg.getPixelReader(),96, 32, (int) (minoImg.getWidth() / 16), (int) (minoImg.getHeight() / 16));
	static WritableImage minoTotu = new WritableImage(minoImg.getPixelReader(),128, 32, (int) (minoImg.getWidth() / 16), (int) (minoImg.getHeight() / 16));

	// 背景画像
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
	// ほうきに座って休息
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

	// ガッツポーズ
	public WritableImage heroAnime_2() {
		WritableImage[] resizedImage = new WritableImage[3];
		resizedImage[0] = new WritableImage(heroImg.getPixelReader(),288, 384, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[1] = new WritableImage(heroImg.getPixelReader(),384, 384, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[2] = new WritableImage(heroImg.getPixelReader(),480, 384, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));

		int index;
		// アニメの再生が終わった
		if (this.count == resizedImage.length) {
			this.isEnd = true;
			index = resizedImage.length - 1;
		} else {
			index = this.count;
			this.count++;
		}

		if (index == 0) {
			TetrisAudio.success();
		}

		return resizedImage[index];
	}

	//　パニック中
	public WritableImage heroAnime_3() {
		WritableImage[] resizedImage = new WritableImage[10];
		resizedImage[0] = new WritableImage(heroImg.getPixelReader(),0, 480, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[1] = new WritableImage(heroImg.getPixelReader(),96, 480, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[2] = new WritableImage(heroImg.getPixelReader(),192, 480, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[3] = new WritableImage(heroImg.getPixelReader(),288, 480, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[4] = new WritableImage(heroImg.getPixelReader(),384, 480, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[5] = new WritableImage(heroImg.getPixelReader(),480, 480, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[6] = new WritableImage(heroImg.getPixelReader(),384, 480, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[7] = new WritableImage(heroImg.getPixelReader(),288, 480, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[8] = new WritableImage(heroImg.getPixelReader(),192, 480, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[9] = new WritableImage(heroImg.getPixelReader(),96, 480, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		int index = 0;
		if (this.count >= resizedImage.length) {
			this.count = 0;
		}
		index = this.count;
		this.count++;

		return resizedImage[index];
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

	// 飛行
	public WritableImage heroAnime_5() {
		WritableImage[] resizedImage = new WritableImage[4];
		resizedImage[0] = new WritableImage(heroImg.getPixelReader(),0, 96, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[1] = new WritableImage(heroImg.getPixelReader(),96, 96, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[2] = new WritableImage(heroImg.getPixelReader(),192, 96, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[3] = new WritableImage(heroImg.getPixelReader(),96, 96, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));

		int index = 0;
		if (this.count >= resizedImage.length) {
			this.count = 0;
		}
		index = this.count;
		this.count++;

		return resizedImage[index];
	}

	// ゲームオーバー
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

	// ほうきに乗って飛行
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
		WritableImage[] resizedImage = new WritableImage[4];
		resizedImage[0] = new WritableImage(heroImg.getPixelReader(),0, 192, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[1] = new WritableImage(heroImg.getPixelReader(),96, 192, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[2] = new WritableImage(heroImg.getPixelReader(),192, 192, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));
		resizedImage[3] = new WritableImage(heroImg.getPixelReader(),96, 192, (int) (heroImg.getWidth() / 9), (int) (heroImg.getHeight() / 6));

		int index = this.count;
		this.count++;
		if (this.count >= resizedImage.length) {
			this.count = 0;
		}

		if (index == 0) {
			TetrisAudio.charge();
		}

		return resizedImage[index];
	}

	// ガッツポーズ（大）
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

		if (index == 0) {
			TetrisAudio.levelUp();
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

	// 爆裂アニメ
	public WritableImage ioAnime() {
		WritableImage[] resizedImage = new WritableImage[6];
		resizedImage[0] = new WritableImage(magicImg.getPixelReader(),0, 256, (int) (magicImg.getWidth() / 16), (int) (magicImg.getHeight() / 11));
		resizedImage[1] = new WritableImage(magicImg.getPixelReader(),32, 256, (int) (magicImg.getWidth() / 16), (int) (magicImg.getHeight() / 11));
		resizedImage[2] = new WritableImage(magicImg.getPixelReader(),64, 256, (int) (magicImg.getWidth() / 16), (int) (magicImg.getHeight() / 11));
		resizedImage[3] = new WritableImage(magicImg.getPixelReader(),96, 256, (int) (magicImg.getWidth() / 16), (int) (magicImg.getHeight() / 11));
		resizedImage[4] = new WritableImage(magicImg.getPixelReader(),64, 256, (int) (magicImg.getWidth() / 16), (int) (magicImg.getHeight() / 11));
		resizedImage[5] = new WritableImage(magicImg.getPixelReader(),32, 256, (int) (magicImg.getWidth() / 16), (int) (magicImg.getHeight() / 11));

		int index = 0;
		if (this.count >= resizedImage.length) {
			this.count = 0;
		}
		index = this.count;
		this.count++;

		return resizedImage[index];
	}

	// 魔法（メラ）発動
	public WritableImage bombAnime() {
		WritableImage[] resizedImage = new WritableImage[15];
		resizedImage[0] = new WritableImage(magicImg2.getPixelReader(),288, 288, (int) (magicImg2.getWidth() / 6), (int) (magicImg2.getHeight() / 4));
		resizedImage[1] = new WritableImage(magicImg2.getPixelReader(),384, 288, (int) (magicImg2.getWidth() / 6), (int) (magicImg2.getHeight() / 4));
		resizedImage[2] = new WritableImage(magicImg2.getPixelReader(),480, 288, (int) (magicImg2.getWidth() / 6), (int) (magicImg2.getHeight() / 4));
		resizedImage[3] = new WritableImage(magicImg2.getPixelReader(),288, 288, (int) (magicImg2.getWidth() / 6), (int) (magicImg2.getHeight() / 4));
		resizedImage[4] = new WritableImage(magicImg2.getPixelReader(),384, 288, (int) (magicImg2.getWidth() / 6), (int) (magicImg2.getHeight() / 4));
		resizedImage[5] = new WritableImage(magicImg2.getPixelReader(),480, 288, (int) (magicImg2.getWidth() / 6), (int) (magicImg2.getHeight() / 4));
		resizedImage[6] = new WritableImage(magicImg2.getPixelReader(),0, 288, (int) (magicImg2.getWidth() / 6), (int) (magicImg2.getHeight() / 4));
		resizedImage[7] = new WritableImage(magicImg2.getPixelReader(),96, 288, (int) (magicImg2.getWidth() / 6), (int) (magicImg2.getHeight() / 4));
		resizedImage[8] = new WritableImage(magicImg2.getPixelReader(),192, 288, (int) (magicImg2.getWidth() / 6), (int) (magicImg2.getHeight() / 4));
		resizedImage[9] = new WritableImage(magicImg2.getPixelReader(),0, 288, (int) (magicImg2.getWidth() / 6), (int) (magicImg2.getHeight() / 4));
		resizedImage[10] = new WritableImage(magicImg2.getPixelReader(),96, 288, (int) (magicImg2.getWidth() / 6), (int) (magicImg2.getHeight() / 4));
		resizedImage[11] = new WritableImage(magicImg2.getPixelReader(),192, 288, (int) (magicImg2.getWidth() / 6), (int) (magicImg2.getHeight() / 4));
		resizedImage[12] = new WritableImage(magicImg2.getPixelReader(),0, 288, (int) (magicImg2.getWidth() / 6), (int) (magicImg2.getHeight() / 4));
		resizedImage[13] = new WritableImage(magicImg2.getPixelReader(),96, 288, (int) (magicImg2.getWidth() / 6), (int) (magicImg2.getHeight() / 4));
		resizedImage[14] = new WritableImage(magicImg2.getPixelReader(),192, 288, (int) (magicImg2.getWidth() / 6), (int) (magicImg2.getHeight() / 4));

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

		// 音を鳴らす
		if (index == 6) {
			TetrisAudio.bomb();
		}

		return resizedImage[index];
	}

	// 魔法（イオ）発動
	public WritableImage magicIoAnime() {
		WritableImage[] resizedImage = new WritableImage[20];
		resizedImage[0] = new WritableImage(magicIoImg.getPixelReader(),0, 0, (int) (magicIoImg.getWidth() / 2), (int) (magicIoImg.getHeight() / 10));
		resizedImage[1] = new WritableImage(magicIoImg.getPixelReader(),640, 0, (int) (magicIoImg.getWidth() / 2), (int) (magicIoImg.getHeight() / 10));
		resizedImage[2] = new WritableImage(magicIoImg.getPixelReader(),0, 480, (int) (magicIoImg.getWidth() / 2), (int) (magicIoImg.getHeight() / 10));
		resizedImage[3] = new WritableImage(magicIoImg.getPixelReader(),640, 480, (int) (magicIoImg.getWidth() / 2), (int) (magicIoImg.getHeight() / 10));
		resizedImage[4] = new WritableImage(magicIoImg.getPixelReader(),0, 960, (int) (magicIoImg.getWidth() / 2), (int) (magicIoImg.getHeight() / 10));
		resizedImage[5] = new WritableImage(magicIoImg.getPixelReader(),640, 960, (int) (magicIoImg.getWidth() / 2), (int) (magicIoImg.getHeight() / 10));
		resizedImage[6] = new WritableImage(magicIoImg.getPixelReader(),0, 1440, (int) (magicIoImg.getWidth() / 2), (int) (magicIoImg.getHeight() / 10));
		resizedImage[7] = new WritableImage(magicIoImg.getPixelReader(),640, 1440, (int) (magicIoImg.getWidth() / 2), (int) (magicIoImg.getHeight() / 10));
		resizedImage[8] = new WritableImage(magicIoImg.getPixelReader(),0, 1920, (int) (magicIoImg.getWidth() / 2), (int) (magicIoImg.getHeight() / 10));
		resizedImage[9] = new WritableImage(magicIoImg.getPixelReader(),640, 1920, (int) (magicIoImg.getWidth() / 2), (int) (magicIoImg.getHeight() / 10));
		resizedImage[10] = new WritableImage(magicIoImg.getPixelReader(),0, 2400, (int) (magicIoImg.getWidth() / 2), (int) (magicIoImg.getHeight() / 10));
		resizedImage[11] = new WritableImage(magicIoImg.getPixelReader(),640, 2400, (int) (magicIoImg.getWidth() / 2), (int) (magicIoImg.getHeight() / 10));
		resizedImage[12] = new WritableImage(magicIoImg.getPixelReader(),0, 2880, (int) (magicIoImg.getWidth() / 2), (int) (magicIoImg.getHeight() / 10));
		resizedImage[13] = new WritableImage(magicIoImg.getPixelReader(),640, 2880, (int) (magicIoImg.getWidth() / 2), (int) (magicIoImg.getHeight() / 10));
		resizedImage[14] = new WritableImage(magicIoImg.getPixelReader(),0, 3360, (int) (magicIoImg.getWidth() / 2), (int) (magicIoImg.getHeight() / 10));
		resizedImage[15] = new WritableImage(magicIoImg.getPixelReader(),640, 3360, (int) (magicIoImg.getWidth() / 2), (int) (magicIoImg.getHeight() / 10));
		resizedImage[16] = new WritableImage(magicIoImg.getPixelReader(),0, 3840, (int) (magicIoImg.getWidth() / 2), (int) (magicIoImg.getHeight() / 10));
		resizedImage[17] = new WritableImage(magicIoImg.getPixelReader(),640, 3840, (int) (magicIoImg.getWidth() / 2), (int) (magicIoImg.getHeight() / 10));
		resizedImage[18] = new WritableImage(magicIoImg.getPixelReader(),0, 4320, (int) (magicIoImg.getWidth() / 2), (int) (magicIoImg.getHeight() / 10));
		resizedImage[19] = new WritableImage(magicIoImg.getPixelReader(),640, 4320, (int) (magicIoImg.getWidth() / 2), (int) (magicIoImg.getHeight() / 10));

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

		if (index == 0) {
			TetrisAudio.bomb();;
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