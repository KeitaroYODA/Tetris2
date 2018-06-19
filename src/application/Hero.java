package application;

import java.io.File;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.AudioClip;

public class Hero {

	private static Hero hero = null;

	// 表示中のアニメを切り替えるために前回のゲームステータスを保持しておく
	private int crtGameStatus = 0;
	private TetrisImage heroImage = new TetrisImage();

	// 表示位置
	private static final double heroX = Panel.panelW() * 19;
	private static final double heroY = Panel.panelH() * 10;
	private static final double heroW = Panel.panelW() * 8;
	private static final double heroH = Panel.panelH() * 8;

	public static AudioClip magicAudio;

	static {
		magicAudio = new AudioClip(new File("magic-flame2.mp3").toURI().toString());
		magicAudio.setVolume(0);
		magicAudio.play();
		magicAudio.setVolume(1.0);
	}

	// 主人公さんクラスのインスタンスを返す
	public static Hero getInstance() {
		if (hero == null) {
			hero = new Hero();
		}
		return hero;
	}

	private Hero() {
		// new によるインスタンス化を許可しない
	}

	public TetrisImage getImage() {
		return this.heroImage;
	}

	public boolean animeIsEnd() {
		return heroImage.isEnd();
	}

	public void show(GraphicsContext canvas, int gameStatus) {

		// 新しいアニメ表示をする場合には初期化する
		if (crtGameStatus != gameStatus) {
			crtGameStatus = gameStatus;
			heroImage.init();

			if (gameStatus == 7) {
				magicAudio.play();
			}
		}

		// 背景の表示
		canvas.drawImage(TetrisImage.haikeiHeroImg, heroX, heroY, (heroH / 3) * 4, heroH);

		// 主人公さんの表示切替
		switch(gameStatus) {
		case 1: // スタート画面（繰り返し）
			canvas.drawImage(heroImage.heroAnime_1(), heroX + (Panel.panelW() * 2), heroY, heroW, heroH);
			break;
		case 4: // ポーズ（繰り返し）
			canvas.drawImage(heroImage.heroAnime_1(), heroX + (Panel.panelW() * 2), heroY, heroW, heroH);
			break;
		case 8: // ブロックが１行揃った
			canvas.drawImage(heroImage.heroAnime_9(), heroX + (Panel.panelW() * 2), heroY, heroW, heroH);
			break;
		case 5: // ゲームオーバー
			canvas.drawImage(heroImage.heroAnime_6(), heroX + (Panel.panelW() * 2), heroY, heroW, heroH);
			break;
		case 6: // 魔法準備中（繰り返し）
			canvas.drawImage(heroImage.heroAnime_8(), heroX + (Panel.panelW() * 2), heroY, heroW, heroH);
			break;
		case 7: // 魔法発動
			canvas.drawImage(heroImage.heroAnime_4(), heroX + (Panel.panelW() * 2), heroY, heroW, heroH);
			break;
		default: // 通常（繰り返し）
			canvas.drawImage(heroImage.heroAnime_7(), heroX + (Panel.panelW() * 2), heroY, heroW, heroH);
			break;
		}
	}
}