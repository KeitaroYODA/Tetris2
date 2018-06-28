package application;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

/**
 * 画像及びアニメを扱うクラス
 *
 */
public final class TetrisImage{

	private boolean isEnd; // アニメ再生終了フラグ
	private int count; // アニメ再生用カウンタ

	// 画像ファイル
	private static final Image tile_1 = new Image(TetrisImage.class.getResource("/image/tile_1.png").toString());
	private static final Image magic1 = new Image(TetrisImage.class.getResource("/image/magic1.png").toString());
	private static final Image frameeffect = new Image(TetrisImage.class.getResource("/image/frameeffects006m.png").toString());
	private static final Image hero = new Image(TetrisImage.class.getResource("/image/hero.png").toString()); // 主人公さん
	private static final Image kaenbeam = new Image(TetrisImage.class.getResource("/image/kaenbeam.png").toString()); // 魔法（イオ）発動
	private static final Image hiteffects = new Image(TetrisImage.class.getResource("/image/hiteffects.png").toString());
	private static final Image frame = new Image(TetrisImage.class.getResource("/image/frame.png").toString());
	// ミノ画像
	public static final WritableImage minoZ = new WritableImage(tile_1.getPixelReader(),0, 32, (int) (tile_1.getWidth() / 16), (int) (tile_1.getHeight() / 11));
	public static final WritableImage minoO = new WritableImage(tile_1.getPixelReader(),32, 32, (int) (tile_1.getWidth() / 16), (int) (tile_1.getHeight() / 11));
	public static final WritableImage minoS = new WritableImage(tile_1.getPixelReader(),64, 32, (int) (tile_1.getWidth() / 16), (int) (tile_1.getHeight() / 11));
	public static final WritableImage minoJ = new WritableImage(tile_1.getPixelReader(),160, 32, (int) (tile_1.getWidth() / 16), (int) (tile_1.getHeight() / 11));
	public static final WritableImage minoL = new WritableImage(tile_1.getPixelReader(),128, 32, (int) (tile_1.getWidth() / 16), (int) (tile_1.getHeight() / 11));
	public static final WritableImage minoT = new WritableImage(tile_1.getPixelReader(),160, 32, (int) (tile_1.getWidth() / 16), (int) (tile_1.getHeight() / 11));
	public static final WritableImage minoI = new WritableImage(tile_1.getPixelReader(),96, 32, (int) (tile_1.getWidth() / 16), (int) (tile_1.getHeight() / 11));
	// 背景画像（タイル）
	public static final WritableImage haikei = new WritableImage(tile_1.getPixelReader(),448, 96, (int) (tile_1.getWidth() / 16), (int) (tile_1.getHeight() / 16));
	// 主人公さんの背景
	public static final Image haikeiHeroImg = new Image(TetrisImage.class.getResource("/image/haikei_hero.jpg").toString());

	/**
	 * コンストラクタ
	 */
	public TetrisImage() {
		this.init();
	}

	/**
	 * 初期化
	 * アニメーション再生前に呼び出す必要がある
	 */
	public void init() {
		this.isEnd = false;
		this.count = 0;
	}

	/**
	 * アニメの再生が終わった場合真を返す
	 * @return true:アニメ再生終了、false:アニメ再生中
	 */
	public boolean isEnd() {
		return this.isEnd;
	}

	/**
	 * ゲーム領域アニメ(行のパネルが揃った際の削除)
	 * @return WritableImage
	 */
	public WritableImage hiteffectAnime_1() {
		WritableImage[] resizedImage = new WritableImage[7];
		resizedImage[0] = new WritableImage(hiteffects.getPixelReader(),192, 576, (int) (hiteffects.getWidth() / 5), (int) (hiteffects.getHeight() / 10));
		resizedImage[1] = new WritableImage(hiteffects.getPixelReader(),0, 384, (int) (hiteffects.getWidth() / 5), (int) (hiteffects.getHeight() / 10));
		resizedImage[2] = new WritableImage(hiteffects.getPixelReader(),384, 1536, (int) (hiteffects.getWidth() / 5), (int) (hiteffects.getHeight() / 10));
		resizedImage[3] = new WritableImage(hiteffects.getPixelReader(),576, 1536, (int) (hiteffects.getWidth() / 5), (int) (hiteffects.getHeight() / 10));
		resizedImage[4] = new WritableImage(hiteffects.getPixelReader(),748, 1536, (int) (hiteffects.getWidth() / 5), (int) (hiteffects.getHeight() / 10));
		resizedImage[5] = new WritableImage(hiteffects.getPixelReader(),384, 1728, (int) (hiteffects.getWidth() / 5), (int) (hiteffects.getHeight() / 10));
		resizedImage[6] = new WritableImage(hiteffects.getPixelReader(),576, 1728, (int) (hiteffects.getWidth() / 5), (int) (hiteffects.getHeight() / 10));

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

	/**
	 * 主人公さんのアニメ(ほうきに座って休息)
	 * @return WritableImage
	 */
	public WritableImage heroAnime_1() {
		WritableImage[] resizedImage = new WritableImage[4];
		resizedImage[0] = new WritableImage(hero.getPixelReader(),0, 0, (int) (hero.getWidth() / 9), (int) (hero.getHeight() / 6));
		resizedImage[1] = new WritableImage(hero.getPixelReader(),96, 0, (int) (hero.getWidth() / 9), (int) (hero.getHeight() / 6));
		resizedImage[2] = new WritableImage(hero.getPixelReader(),192, 0, (int) (hero.getWidth() / 9), (int) (hero.getHeight() / 6));
		resizedImage[3] = new WritableImage(hero.getPixelReader(),96, 0, (int) (hero.getWidth() / 9), (int) (hero.getHeight() / 6));

		int index = this.count;
		this.count++;
		if (this.count >= resizedImage.length) {
			this.count = 0;
		}
		return resizedImage[index];
	}

	/**
	 * 主人公さんのアニメ(ガッツポーズ（小）)
	 * @return WritableImage
	 */
	public WritableImage heroAnime_2() {
		WritableImage[] resizedImage = new WritableImage[3];
		resizedImage[0] = new WritableImage(hero.getPixelReader(),288, 384, (int) (hero.getWidth() / 9), (int) (hero.getHeight() / 6));
		resizedImage[1] = new WritableImage(hero.getPixelReader(),384, 384, (int) (hero.getWidth() / 9), (int) (hero.getHeight() / 6));
		resizedImage[2] = new WritableImage(hero.getPixelReader(),480, 384, (int) (hero.getWidth() / 9), (int) (hero.getHeight() / 6));

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

	/**
	 * 主人公さんのアニメ(パニック中)
	 * @return WritableImage
	 */
	public WritableImage heroAnime_3() {
		WritableImage[] resizedImage = new WritableImage[10];
		resizedImage[0] = new WritableImage(hero.getPixelReader(),0, 480, (int) (hero.getWidth() / 9), (int) (hero.getHeight() / 6));
		resizedImage[1] = new WritableImage(hero.getPixelReader(),96, 480, (int) (hero.getWidth() / 9), (int) (hero.getHeight() / 6));
		resizedImage[2] = new WritableImage(hero.getPixelReader(),192, 480, (int) (hero.getWidth() / 9), (int) (hero.getHeight() / 6));
		resizedImage[3] = new WritableImage(hero.getPixelReader(),288, 480, (int) (hero.getWidth() / 9), (int) (hero.getHeight() / 6));
		resizedImage[4] = new WritableImage(hero.getPixelReader(),384, 480, (int) (hero.getWidth() / 9), (int) (hero.getHeight() / 6));
		resizedImage[5] = new WritableImage(hero.getPixelReader(),480, 480, (int) (hero.getWidth() / 9), (int) (hero.getHeight() / 6));
		resizedImage[6] = new WritableImage(hero.getPixelReader(),384, 480, (int) (hero.getWidth() / 9), (int) (hero.getHeight() / 6));
		resizedImage[7] = new WritableImage(hero.getPixelReader(),288, 480, (int) (hero.getWidth() / 9), (int) (hero.getHeight() / 6));
		resizedImage[8] = new WritableImage(hero.getPixelReader(),192, 480, (int) (hero.getWidth() / 9), (int) (hero.getHeight() / 6));
		resizedImage[9] = new WritableImage(hero.getPixelReader(),96, 480, (int) (hero.getWidth() / 9), (int) (hero.getHeight() / 6));
		int index = 0;
		if (this.count >= resizedImage.length) {
			this.count = 0;
		}
		index = this.count;
		this.count++;

		return resizedImage[index];
	}

	/**
	 * 主人公さんのアニメ(魔法発動)
	 * @return WritableImage
	 */
	public WritableImage heroAnime_4() {
		WritableImage[] resizedImage = new WritableImage[9];
		resizedImage[0] = new WritableImage(hero.getPixelReader(),0, 288, (int) (hero.getWidth() / 9), (int) (hero.getHeight() / 6));
		resizedImage[1] = new WritableImage(hero.getPixelReader(),96, 288, (int) (hero.getWidth() / 9), (int) (hero.getHeight() / 6));
		resizedImage[2] = new WritableImage(hero.getPixelReader(),192, 288, (int) (hero.getWidth() / 9), (int) (hero.getHeight() / 6));
		resizedImage[3] = new WritableImage(hero.getPixelReader(),288, 288, (int) (hero.getWidth() / 9), (int) (hero.getHeight() / 6));
		resizedImage[4] = new WritableImage(hero.getPixelReader(),384, 288, (int) (hero.getWidth() / 9), (int) (hero.getHeight() / 6));
		resizedImage[5] = new WritableImage(hero.getPixelReader(),480, 288, (int) (hero.getWidth() / 9), (int) (hero.getHeight() / 6));
		resizedImage[6] = new WritableImage(hero.getPixelReader(),576, 288, (int) (hero.getWidth() / 9), (int) (hero.getHeight() / 6));
		resizedImage[7] = new WritableImage(hero.getPixelReader(),672, 288, (int) (hero.getWidth() / 9), (int) (hero.getHeight() / 6));
		resizedImage[8] = new WritableImage(hero.getPixelReader(),768, 288, (int) (hero.getWidth() / 9), (int) (hero.getHeight() / 6));

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

	/**
	 * 主人公さんのアニメ(飛行1)
	 * @return WritableImage
	 */
	public WritableImage heroAnime_5() {
		WritableImage[] resizedImage = new WritableImage[4];
		resizedImage[0] = new WritableImage(hero.getPixelReader(),0, 96, (int) (hero.getWidth() / 9), (int) (hero.getHeight() / 6));
		resizedImage[1] = new WritableImage(hero.getPixelReader(),96, 96, (int) (hero.getWidth() / 9), (int) (hero.getHeight() / 6));
		resizedImage[2] = new WritableImage(hero.getPixelReader(),192, 96, (int) (hero.getWidth() / 9), (int) (hero.getHeight() / 6));
		resizedImage[3] = new WritableImage(hero.getPixelReader(),96, 96, (int) (hero.getWidth() / 9), (int) (hero.getHeight() / 6));

		int index = 0;
		if (this.count >= resizedImage.length) {
			this.count = 0;
		}
		index = this.count;
		this.count++;

		return resizedImage[index];
	}

	/**
	 * 主人公さんのアニメ(ゲームオーバ)
	 * @return WritableImage
	 */
	public WritableImage heroAnime_6() {
		WritableImage[] resizedImage = new WritableImage[9];
		resizedImage[0] = new WritableImage(hero.getPixelReader(),0, 480, (int) (hero.getWidth() / 9), (int) (hero.getHeight() / 6));
		resizedImage[1] = new WritableImage(hero.getPixelReader(),96, 480, (int) (hero.getWidth() / 9), (int) (hero.getHeight() / 6));
		resizedImage[2] = new WritableImage(hero.getPixelReader(),192, 480, (int) (hero.getWidth() / 9), (int) (hero.getHeight() / 6));
		resizedImage[3] = new WritableImage(hero.getPixelReader(),288, 480, (int) (hero.getWidth() / 9), (int) (hero.getHeight() / 6));
		resizedImage[4] = new WritableImage(hero.getPixelReader(),384, 480, (int) (hero.getWidth() / 9), (int) (hero.getHeight() / 6));
		resizedImage[5] = new WritableImage(hero.getPixelReader(),480, 480, (int) (hero.getWidth() / 9), (int) (hero.getHeight() / 6));
		resizedImage[6] = new WritableImage(hero.getPixelReader(),576, 480, (int) (hero.getWidth() / 9), (int) (hero.getHeight() / 6));
		resizedImage[7] = new WritableImage(hero.getPixelReader(),672, 480, (int) (hero.getWidth() / 9), (int) (hero.getHeight() / 6));
		resizedImage[8] = new WritableImage(hero.getPixelReader(),768, 480, (int) (hero.getWidth() / 9), (int) (hero.getHeight() / 6));

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

	/**
	 * 主人公さんのアニメ(飛行2)
	 * @return WritableImage
	 */
	public WritableImage heroAnime_7() {
		WritableImage[] resizedImage = new WritableImage[3];
		resizedImage[0] = new WritableImage(hero.getPixelReader(),288, 0, (int) (hero.getWidth() / 9), (int) (hero.getHeight() / 6));
		resizedImage[1] = new WritableImage(hero.getPixelReader(),384, 0, (int) (hero.getWidth() / 9), (int) (hero.getHeight() / 6));
		resizedImage[2] = new WritableImage(hero.getPixelReader(),480, 0, (int) (hero.getWidth() / 9), (int) (hero.getHeight() / 6));

		int index = this.count;
		this.count++;
		if (this.count >= resizedImage.length) {
			this.count = 0;
		}
		return resizedImage[index];
	}

	/**
	 * 主人公さんのアニメ(魔法チャージ中)
	 * @return WritableImage
	 */
	public WritableImage heroAnime_8() {
		WritableImage[] resizedImage = new WritableImage[4];
		resizedImage[0] = new WritableImage(hero.getPixelReader(),0, 192, (int) (hero.getWidth() / 9), (int) (hero.getHeight() / 6));
		resizedImage[1] = new WritableImage(hero.getPixelReader(),96, 192, (int) (hero.getWidth() / 9), (int) (hero.getHeight() / 6));
		resizedImage[2] = new WritableImage(hero.getPixelReader(),192, 192, (int) (hero.getWidth() / 9), (int) (hero.getHeight() / 6));
		resizedImage[3] = new WritableImage(hero.getPixelReader(),96, 192, (int) (hero.getWidth() / 9), (int) (hero.getHeight() / 6));

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

	/**
	 * 主人公さんのアニメ(ガッツポーズ（大）)
	 * @return WritableImage
	 */
	public WritableImage heroAnime_9() {
		WritableImage[] resizedImage = new WritableImage[6];
		resizedImage[0] = new WritableImage(hero.getPixelReader(),288, 96, (int) (hero.getWidth() / 9), (int) (hero.getHeight() / 6));
		resizedImage[1] = new WritableImage(hero.getPixelReader(),384, 96, (int) (hero.getWidth() / 9), (int) (hero.getHeight() / 6));
		resizedImage[2] = new WritableImage(hero.getPixelReader(),480, 96, (int) (hero.getWidth() / 9), (int) (hero.getHeight() / 6));
		resizedImage[3] = new WritableImage(hero.getPixelReader(),576, 96, (int) (hero.getWidth() / 9), (int) (hero.getHeight() / 6));
		resizedImage[4] = new WritableImage(hero.getPixelReader(),672, 96, (int) (hero.getWidth() / 9), (int) (hero.getHeight() / 6));
		resizedImage[5] = new WritableImage(hero.getPixelReader(),768, 96, (int) (hero.getWidth() / 9), (int) (hero.getHeight() / 6));

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

	/**
	 * 主人公さんのアニメ(魔法発動後のパネル落下中)
	 * @return WritableImage
	 */
	public WritableImage heroAnime_10() {
		WritableImage[] resizedImage = new WritableImage[8];
		resizedImage[0] = new WritableImage(hero.getPixelReader(),288, 288, (int) (hero.getWidth() / 9), (int) (hero.getHeight() / 6));
		resizedImage[1] = new WritableImage(hero.getPixelReader(),288, 288, (int) (hero.getWidth() / 9), (int) (hero.getHeight() / 6));
		resizedImage[2] = new WritableImage(hero.getPixelReader(),384, 288, (int) (hero.getWidth() / 9), (int) (hero.getHeight() / 6));
		resizedImage[3] = new WritableImage(hero.getPixelReader(),384, 288, (int) (hero.getWidth() / 9), (int) (hero.getHeight() / 6));
		resizedImage[4] = new WritableImage(hero.getPixelReader(),480, 288, (int) (hero.getWidth() / 9), (int) (hero.getHeight() / 6));
		resizedImage[5] = new WritableImage(hero.getPixelReader(),480, 288, (int) (hero.getWidth() / 9), (int) (hero.getHeight() / 6));
		resizedImage[6] = new WritableImage(hero.getPixelReader(),384, 288, (int) (hero.getWidth() / 9), (int) (hero.getHeight() / 6));
		resizedImage[7] = new WritableImage(hero.getPixelReader(),384, 288, (int) (hero.getWidth() / 9), (int) (hero.getHeight() / 6));

		int index = this.count;
		this.count++;
		if (this.count >= resizedImage.length) {
			this.count = 0;
		}

		return resizedImage[index];
	}

	/**
	 *  魔法ゲージアニメ(メラゲージ)
	 * @return WritableImage
	 */
	public WritableImage flameAnime() {
		WritableImage[] resizedImage = new WritableImage[3];
		resizedImage[0] = new WritableImage(tile_1.getPixelReader(),192, 256, (int) (tile_1.getWidth() / 16), (int) (tile_1.getHeight() / 11));
		resizedImage[1] = new WritableImage(tile_1.getPixelReader(),192, 288, (int) (tile_1.getWidth() / 16), (int) (tile_1.getHeight() / 11));
		resizedImage[2] = new WritableImage(tile_1.getPixelReader(),192, 320, (int) (tile_1.getWidth() / 16), (int) (tile_1.getHeight() / 11));

		int index = 0;
		if (this.count >= resizedImage.length) {
			this.count = 0;
		}
		index = this.count;
		this.count++;

		return resizedImage[index];
	}

	/**
	 *  魔法ゲージアニメ(イオゲージ)
	 * @return WritableImage
	 */
	public WritableImage ioAnime() {
		WritableImage[] resizedImage = new WritableImage[6];
		resizedImage[0] = new WritableImage(tile_1.getPixelReader(),0, 256, (int) (tile_1.getWidth() / 16), (int) (tile_1.getHeight() / 11));
		resizedImage[1] = new WritableImage(tile_1.getPixelReader(),32, 256, (int) (tile_1.getWidth() / 16), (int) (tile_1.getHeight() / 11));
		resizedImage[2] = new WritableImage(tile_1.getPixelReader(),64, 256, (int) (tile_1.getWidth() / 16), (int) (tile_1.getHeight() / 11));
		resizedImage[3] = new WritableImage(tile_1.getPixelReader(),96, 256, (int) (tile_1.getWidth() / 16), (int) (tile_1.getHeight() / 11));
		resizedImage[4] = new WritableImage(tile_1.getPixelReader(),64, 256, (int) (tile_1.getWidth() / 16), (int) (tile_1.getHeight() / 11));
		resizedImage[5] = new WritableImage(tile_1.getPixelReader(),32, 256, (int) (tile_1.getWidth() / 16), (int) (tile_1.getHeight() / 11));

		int index = 0;
		if (this.count >= resizedImage.length) {
			this.count = 0;
		}
		index = this.count;
		this.count++;

		return resizedImage[index];
	}

	/**
	 *  魔法効果アニメ(魔法陣)
	 * @return WritableImage
	 */
	public WritableImage magicCircle() {
		WritableImage[] resizedImage = new WritableImage[4];
		resizedImage[0] = new WritableImage(tile_1.getPixelReader(),256, 0, (int) (tile_1.getWidth() / 16), (int) (tile_1.getHeight() / 11));
		resizedImage[1] = new WritableImage(tile_1.getPixelReader(),288, 0, (int) (tile_1.getWidth() / 16), (int) (tile_1.getHeight() / 11));
		resizedImage[2] = new WritableImage(tile_1.getPixelReader(),320, 0, (int) (tile_1.getWidth() / 16), (int) (tile_1.getHeight() / 11));
		resizedImage[3] = new WritableImage(tile_1.getPixelReader(),352, 0, (int) (tile_1.getWidth() / 16), (int) (tile_1.getHeight() / 11));

		int index = this.count;
		this.count++;
		if (this.count >= resizedImage.length) {
			this.count = 0;
		}
		return resizedImage[index];
	}

	/**
	 *  魔法効果アニメ(メラ発動)
	 * @return WritableImage
	 */
	public WritableImage bombAnime() {
		WritableImage[] resizedImage = new WritableImage[15];
		resizedImage[0] = new WritableImage(magic1.getPixelReader(),288, 288, (int) (magic1.getWidth() / 6), (int) (magic1.getHeight() / 4));
		resizedImage[1] = new WritableImage(magic1.getPixelReader(),384, 288, (int) (magic1.getWidth() / 6), (int) (magic1.getHeight() / 4));
		resizedImage[2] = new WritableImage(magic1.getPixelReader(),480, 288, (int) (magic1.getWidth() / 6), (int) (magic1.getHeight() / 4));
		resizedImage[3] = new WritableImage(magic1.getPixelReader(),288, 288, (int) (magic1.getWidth() / 6), (int) (magic1.getHeight() / 4));
		resizedImage[4] = new WritableImage(magic1.getPixelReader(),384, 288, (int) (magic1.getWidth() / 6), (int) (magic1.getHeight() / 4));
		resizedImage[5] = new WritableImage(magic1.getPixelReader(),480, 288, (int) (magic1.getWidth() / 6), (int) (magic1.getHeight() / 4));
		resizedImage[6] = new WritableImage(magic1.getPixelReader(),0, 288, (int) (magic1.getWidth() / 6), (int) (magic1.getHeight() / 4));
		resizedImage[7] = new WritableImage(magic1.getPixelReader(),96, 288, (int) (magic1.getWidth() / 6), (int) (magic1.getHeight() / 4));
		resizedImage[8] = new WritableImage(magic1.getPixelReader(),192, 288, (int) (magic1.getWidth() / 6), (int) (magic1.getHeight() / 4));
		resizedImage[9] = new WritableImage(magic1.getPixelReader(),0, 288, (int) (magic1.getWidth() / 6), (int) (magic1.getHeight() / 4));
		resizedImage[10] = new WritableImage(magic1.getPixelReader(),96, 288, (int) (magic1.getWidth() / 6), (int) (magic1.getHeight() / 4));
		resizedImage[11] = new WritableImage(magic1.getPixelReader(),192, 288, (int) (magic1.getWidth() / 6), (int) (magic1.getHeight() / 4));
		resizedImage[12] = new WritableImage(magic1.getPixelReader(),0, 288, (int) (magic1.getWidth() / 6), (int) (magic1.getHeight() / 4));
		resizedImage[13] = new WritableImage(magic1.getPixelReader(),96, 288, (int) (magic1.getWidth() / 6), (int) (magic1.getHeight() / 4));
		resizedImage[14] = new WritableImage(magic1.getPixelReader(),192, 288, (int) (magic1.getWidth() / 6), (int) (magic1.getHeight() / 4));

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
			TetrisAudio.mera();
		}

		return resizedImage[index];
	}

	/**
	 *  魔法効果アニメ(イオチャージ中)
	 * @return WritableImage
	 */
	public WritableImage magicFlameEffectAnime() {
		WritableImage[] resizedImage = new WritableImage[10];
		resizedImage[0] = new WritableImage(frameeffect.getPixelReader(),0, 0, (int) (frameeffect.getWidth() / 2), (int) (frameeffect.getHeight() / 5));
		resizedImage[1] = new WritableImage(frameeffect.getPixelReader(),640, 0, (int) (frameeffect.getWidth() / 2), (int) (frameeffect.getHeight() / 5));
		resizedImage[2] = new WritableImage(frameeffect.getPixelReader(),0, 480, (int) (frameeffect.getWidth() / 2), (int) (frameeffect.getHeight() / 5));
		resizedImage[3] = new WritableImage(frameeffect.getPixelReader(),640, 480, (int) (frameeffect.getWidth() / 2), (int) (frameeffect.getHeight() / 5));
		resizedImage[4] = new WritableImage(frameeffect.getPixelReader(),0, 960, (int) (frameeffect.getWidth() / 2), (int) (frameeffect.getHeight() / 5));
		resizedImage[5] = new WritableImage(frameeffect.getPixelReader(),640, 960, (int) (frameeffect.getWidth() / 2), (int) (frameeffect.getHeight() / 5));
		resizedImage[6] = new WritableImage(frameeffect.getPixelReader(),0, 1440, (int) (frameeffect.getWidth() / 2), (int) (frameeffect.getHeight() / 5));
		resizedImage[7] = new WritableImage(frameeffect.getPixelReader(),640, 1440, (int) (frameeffect.getWidth() / 2), (int) (frameeffect.getHeight() / 5));
		resizedImage[8] = new WritableImage(frameeffect.getPixelReader(),0, 1920, (int) (frameeffect.getWidth() / 2), (int) (frameeffect.getHeight() / 5));
		resizedImage[9] = new WritableImage(frameeffect.getPixelReader(),640, 1920, (int) (frameeffect.getWidth() / 2), (int) (frameeffect.getHeight() / 5));

		int index = 0;
		if (this.count >= resizedImage.length) {
			this.count = 0;
		}
		index = this.count;
		this.count++;

		return resizedImage[index];
	}

	/**
	 *  魔法効果アニメ(イオ発動)
	 * @return WritableImage
	 */
	public WritableImage magicIoAnime() {
		WritableImage[] resizedImage = new WritableImage[20];
		resizedImage[0] = new WritableImage(kaenbeam.getPixelReader(),0, 0, (int) (kaenbeam.getWidth() / 2), (int) (kaenbeam.getHeight() / 10));
		resizedImage[1] = new WritableImage(kaenbeam.getPixelReader(),640, 0, (int) (kaenbeam.getWidth() / 2), (int) (kaenbeam.getHeight() / 10));
		resizedImage[2] = new WritableImage(kaenbeam.getPixelReader(),0, 480, (int) (kaenbeam.getWidth() / 2), (int) (kaenbeam.getHeight() / 10));
		resizedImage[3] = new WritableImage(kaenbeam.getPixelReader(),640, 480, (int) (kaenbeam.getWidth() / 2), (int) (kaenbeam.getHeight() / 10));
		resizedImage[4] = new WritableImage(kaenbeam.getPixelReader(),0, 960, (int) (kaenbeam.getWidth() / 2), (int) (kaenbeam.getHeight() / 10));
		resizedImage[5] = new WritableImage(kaenbeam.getPixelReader(),640, 960, (int) (kaenbeam.getWidth() / 2), (int) (kaenbeam.getHeight() / 10));
		resizedImage[6] = new WritableImage(kaenbeam.getPixelReader(),0, 1440, (int) (kaenbeam.getWidth() / 2), (int) (kaenbeam.getHeight() / 10));
		resizedImage[7] = new WritableImage(kaenbeam.getPixelReader(),640, 1440, (int) (kaenbeam.getWidth() / 2), (int) (kaenbeam.getHeight() / 10));
		resizedImage[8] = new WritableImage(kaenbeam.getPixelReader(),0, 1920, (int) (kaenbeam.getWidth() / 2), (int) (kaenbeam.getHeight() / 10));
		resizedImage[9] = new WritableImage(kaenbeam.getPixelReader(),640, 1920, (int) (kaenbeam.getWidth() / 2), (int) (kaenbeam.getHeight() / 10));
		resizedImage[10] = new WritableImage(kaenbeam.getPixelReader(),0, 2400, (int) (kaenbeam.getWidth() / 2), (int) (kaenbeam.getHeight() / 10));
		resizedImage[11] = new WritableImage(kaenbeam.getPixelReader(),640, 2400, (int) (kaenbeam.getWidth() / 2), (int) (kaenbeam.getHeight() / 10));
		resizedImage[12] = new WritableImage(kaenbeam.getPixelReader(),0, 2880, (int) (kaenbeam.getWidth() / 2), (int) (kaenbeam.getHeight() / 10));
		resizedImage[13] = new WritableImage(kaenbeam.getPixelReader(),640, 2880, (int) (kaenbeam.getWidth() / 2), (int) (kaenbeam.getHeight() / 10));
		resizedImage[14] = new WritableImage(kaenbeam.getPixelReader(),0, 3360, (int) (kaenbeam.getWidth() / 2), (int) (kaenbeam.getHeight() / 10));
		resizedImage[15] = new WritableImage(kaenbeam.getPixelReader(),640, 3360, (int) (kaenbeam.getWidth() / 2), (int) (kaenbeam.getHeight() / 10));
		resizedImage[16] = new WritableImage(kaenbeam.getPixelReader(),0, 3840, (int) (kaenbeam.getWidth() / 2), (int) (kaenbeam.getHeight() / 10));
		resizedImage[17] = new WritableImage(kaenbeam.getPixelReader(),640, 3840, (int) (kaenbeam.getWidth() / 2), (int) (kaenbeam.getHeight() / 10));
		resizedImage[18] = new WritableImage(kaenbeam.getPixelReader(),0, 4320, (int) (kaenbeam.getWidth() / 2), (int) (kaenbeam.getHeight() / 10));
		resizedImage[19] = new WritableImage(kaenbeam.getPixelReader(),640, 4320, (int) (kaenbeam.getWidth() / 2), (int) (kaenbeam.getHeight() / 10));

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
			TetrisAudio.io();
		}

		return resizedImage[index];
	}

	/**
	 *  魔法効果アニメ(魔法発動後の全パネル落下中)
	 * @return WritableImage
	 */
	public WritableImage frameAnime() {
		WritableImage[] resizedImage = new WritableImage[8];
		resizedImage[0] = new WritableImage(frame.getPixelReader(),0, 0, (int) (frame.getWidth()), (int) (frame.getHeight() / 8));
		resizedImage[1] = new WritableImage(frame.getPixelReader(),0, 240, (int) (frame.getWidth()), (int) (frame.getHeight() / 8));
		resizedImage[2] = new WritableImage(frame.getPixelReader(),0, 480, (int) (frame.getWidth()), (int) (frame.getHeight() / 8));
		resizedImage[3] = new WritableImage(frame.getPixelReader(),0, 720, (int) (frame.getWidth()), (int) (frame.getHeight() / 8));
		resizedImage[4] = new WritableImage(frame.getPixelReader(),0, 960, (int) (frame.getWidth()), (int) (frame.getHeight() / 8));
		resizedImage[5] = new WritableImage(frame.getPixelReader(),0, 1200, (int) (frame.getWidth()), (int) (frame.getHeight() / 8));
		resizedImage[6] = new WritableImage(frame.getPixelReader(),0, 1440, (int) (frame.getWidth()), (int) (frame.getHeight() / 8));
		resizedImage[7] = new WritableImage(frame.getPixelReader(),0, 1680, (int) (frame.getWidth()), (int) (frame.getHeight() / 8));

		int index = 0;
		if (this.count >= resizedImage.length) {
			this.count = 0;
		}
		index = this.count;
		this.count++;

		return resizedImage[index];
	}
}