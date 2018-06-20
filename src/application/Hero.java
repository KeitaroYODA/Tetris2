package application;

import javafx.scene.canvas.GraphicsContext;

public class Hero {

	private static Hero hero = null;

	public static final int HERO_ACTION_MENU = 0;
	public static final int HERO_ACTION_MAIN = 1;
	public static final int HERO_ACTION_REST = 2;
	public static final int HERO_ACTION_GUTS_1 = 3;
	public static final int HERO_ACTION_GUTS_2 = 4;
	public static final int HERO_ACTION_CHARGE = 5;
	public static final int HERO_ACTION_MAGIC_1 = 6;
	public static final int HERO_ACTION_LEVELUP = 8;
	public static final int HERO_ACTION_GAMEOVER = 9;
	public static final int HERO_ACTION_PANIC = 10;

	private int action = 0;
	private TetrisImage heroImage = new TetrisImage();

	// 表示位置　<TODO>Tetris_Obj側で定義したほうが良いかも
	private static final double heroX = Panel.panelW() * 18;
	private static final double heroY = Panel.panelH() * 12;
	private static final double heroW = Panel.panelW() * 6;
	private static final double heroH = Panel.panelH() * 6;

	// 主人公さんクラスのインスタンスを返す
	public static Hero getInstance() {
		if (hero == null) {
			hero = new Hero();
		}
		return hero;
	}

	// アニメの再生が終わったら真を返す
	public boolean animeIsEnd() {
		return heroImage.isEnd();
	}

	// 表示させるアクションを設定
	public void setAction(int action) {
		if (this.action != action) {
			this.heroImage.init();
		}
		this.action = action;
	}

	public void show(GraphicsContext canvas) {

		// 背景の表示
		canvas.drawImage(TetrisImage.haikeiHeroImg, heroX, heroY, (heroH / 3) * 4, heroH);

		// 主人公さんの表示切替
		switch(this.action) {
		case HERO_ACTION_MENU: // スタート画面（繰り返し）
			canvas.drawImage(heroImage.heroAnime_5(), heroX + (Panel.panelW() * 2), heroY, heroW, heroH);
			break;
		case HERO_ACTION_REST: // ポーズ（繰り返し）
			canvas.drawImage(heroImage.heroAnime_1(), heroX + (Panel.panelW() * 2), heroY, heroW, heroH);
			break;
		case HERO_ACTION_GUTS_1: // ブロックが１行揃った
			canvas.drawImage(heroImage.heroAnime_2(), heroX + (Panel.panelW() * 2), heroY, heroW, heroH);
			break;
		case HERO_ACTION_GAMEOVER: // ゲームオーバー
			canvas.drawImage(heroImage.heroAnime_6(), heroX + (Panel.panelW() * 2), heroY, heroW, heroH);
			break;
		case HERO_ACTION_CHARGE: // 魔法準備中（繰り返し）
			canvas.drawImage(heroImage.heroAnime_8(), heroX + (Panel.panelW() * 2), heroY, heroW, heroH);
			break;
		case HERO_ACTION_MAGIC_1: // 魔法発動
			canvas.drawImage(heroImage.heroAnime_4(), heroX + (Panel.panelW() * 2), heroY, heroW, heroH);
			break;
		case HERO_ACTION_LEVELUP: // レベルアップ
			canvas.drawImage(heroImage.heroAnime_9(), heroX + (Panel.panelW() * 2), heroY, heroW, heroH);
			break;
		case HERO_ACTION_PANIC: // パニック中
			canvas.drawImage(heroImage.heroAnime_3(), heroX + (Panel.panelW() * 2), heroY, heroW, heroH);
			break;
		case HERO_ACTION_MAIN: // 通常（繰り返し）
			canvas.drawImage(heroImage.heroAnime_7(), heroX + (Panel.panelW() * 2), heroY, heroW, heroH);
			break;
		}
	}

	private Hero() {
		// new によるインスタンス化を許可しない
	}
}