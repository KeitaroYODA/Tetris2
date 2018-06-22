package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class Hero {

	// 主人公さんのモーション指定
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
	public static final int HERO_ACTION_ALLDOWN = 11;

	private static Hero hero = null;
	private int action = HERO_ACTION_MENU;
	private TetrisImage heroImage = new TetrisImage();

	private Hero() {
	}

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

	private void showMessage(GraphicsContext canvas, String message) {
		canvas.setFont(new Font("メイリオ", Conf.PANEL_W));
		canvas.setTextAlign(TextAlignment.LEFT);
		canvas.setFill(Color.WHITE);
		canvas.fillText(message, Conf.HERO_X + (Conf.PANEL_W * 1), Conf.HERO_Y + (Conf.PANEL_H * 1));
	}

	public void show(GraphicsContext canvas) {

		// 背景の表示
		canvas.setFill(Color.BLACK);
		canvas.fillRect(Conf.HERO_X, Conf.HERO_Y, (Conf.HERO_W / 3) * 4, Conf.HERO_H);
		canvas.setGlobalAlpha(0.7); // 背景画像を少し暗くする
		canvas.drawImage(TetrisImage.haikeiHeroImg, Conf.HERO_X, Conf.HERO_Y, (Conf.HERO_W / 3) * 4, Conf.HERO_H);
		canvas.setGlobalAlpha(1.0);

		// 主人公さんの表示切替
		switch(this.action) {
		case HERO_ACTION_MENU: // スタート画面（繰り返し）
			canvas.drawImage(heroImage.heroAnime_5(), Conf.HERO_X + (Conf.PANEL_W * 2), Conf.HERO_Y, Conf.HERO_W, Conf.HERO_H);
			break;
		case HERO_ACTION_REST: // ポーズ（繰り返し）
			canvas.drawImage(heroImage.heroAnime_1(), Conf.HERO_X + (Conf.PANEL_W * 2), Conf.HERO_Y, Conf.HERO_W, Conf.HERO_H);
			break;
		case HERO_ACTION_GUTS_1: // ブロックが１行揃った
			canvas.drawImage(heroImage.heroAnime_2(), Conf.HERO_X + (Conf.PANEL_W * 2), Conf.HERO_Y, Conf.HERO_W, Conf.HERO_H);
			break;
		case HERO_ACTION_GAMEOVER: // ゲームオーバー
			canvas.drawImage(heroImage.heroAnime_6(), Conf.HERO_X + (Conf.PANEL_W * 2), Conf.HERO_Y, Conf.HERO_W, Conf.HERO_H);
			break;
		case HERO_ACTION_CHARGE: // 魔法準備中（繰り返し）
			canvas.drawImage(heroImage.heroAnime_8(), Conf.HERO_X + (Conf.PANEL_W * 2), Conf.HERO_Y, Conf.HERO_W, Conf.HERO_H);
			break;
		case HERO_ACTION_MAGIC_1: // 魔法発動
			canvas.drawImage(heroImage.heroAnime_4(), Conf.HERO_X + (Conf.PANEL_W * 2), Conf.HERO_Y, Conf.HERO_W, Conf.HERO_H);
			break;
		case HERO_ACTION_LEVELUP: // レベルアップ
			this.showMessage(canvas, "Level UP !!");
			canvas.drawImage(heroImage.heroAnime_9(), Conf.HERO_X + (Conf.PANEL_W * 2), Conf.HERO_Y, Conf.HERO_W, Conf.HERO_H);
			break;
		case HERO_ACTION_PANIC: // パニック中
			canvas.drawImage(heroImage.heroAnime_3(), Conf.HERO_X + (Conf.PANEL_W * 2), Conf.HERO_Y, Conf.HERO_W, Conf.HERO_H);
			break;
		case HERO_ACTION_MAIN: // 通常（繰り返し）
			canvas.drawImage(heroImage.heroAnime_7(), Conf.HERO_X + (Conf.PANEL_W * 2), Conf.HERO_Y, Conf.HERO_W, Conf.HERO_H);
			break;
		case HERO_ACTION_ALLDOWN: // ワッショイ（繰り返し）
			canvas.drawImage(heroImage.heroAnime_10(), Conf.HERO_X + (Conf.PANEL_W * 2), Conf.HERO_Y, Conf.HERO_W, Conf.HERO_H);
			break;
		}
	}
}