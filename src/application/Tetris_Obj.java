package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
public class Tetris_Obj {

	// ゲームステータス
	private static final int GAME_INIT = 0; // 初期化
	private static final int GAME_MENU = 1; // スタート画面
	private static final int GAME_MAIN = 2; // メインゲーム画面
	private static final int GAME_MINO_CREATE = 3; // 新たなミノが上部に出現
	private static final int GAME_PAUSE = 4; // ポーズ画面
	private static final int GAME_OVER = 5; // ゲームオーバー画面
	private static final int GAME_MAGIC_IO_SELECT = 6; // 魔法（イオ）発動準備中
	private static final int GAME_MAGIC_MERA_SELECT = 11; // 魔法（メラ）発動準備中
	private static final int GAME_MAGIC_IO_EXEC = 7; // 魔法（イオ）発動
	private static final int GAME_MAGIC_MERA_EXEC = 12; // 魔法（メラ）発動
	private static final int GAME_MINO_DELETE = 8; // ミノが設置時に揃った行を削除
	private static final int GAME_ANIME_LEVELUP = 9; // 主人公さんのモーション（レベルアップ）
	private static final int GAME_ANIME_DELETE = 10; // 主人公さんのモーション（１行削除）
	private static final int GAME_RENSA = 13; // 魔法（イオ）発動後の連鎖処理

	// フレーム間引き用」
	private long execTime = System.nanoTime();

	// ゲーム情報
	private int gameStatus = GAME_INIT;
	private int level = 1; // レベル
	private int score = 0; // スコア
	private int minoDownCount = 0; // ミノ落下速度調整用ちカウンタ
	private int allDownCount = 0; // イオ発動後のブロック落下速度調整用カウンタ
	private String message = ""; // メイン画面に表示されるメッセージ

	private Field field; // ミノ表示領域
	private Hero hero; // 主人公さん
	private MStock mStock; // 魔法残弾
	private Mino nextMino; // 次に表示されるミノ

	// このメソッドが1秒間に60回ぐらい呼ばれるので
	// テトリスの内部処理をここに書く
	public void update() {
		if (this.isSkip()) {
			return;
		}

		this.hero = Hero.getInstance();
		this.mStock = MStock.getInstance();
		this.field = Field.getInstance();

		switch(this.gameStatus) {
		case GAME_INIT: // 画面の初期化
			this.doInit();
			break;
		case GAME_MENU: // タイトル画面
			this.dispTitle();
			break;
		case GAME_MAIN: // ゲーム画面
			this.dispGameMain();
			this.colision();
			break;
		case GAME_MINO_CREATE: // 新しいミノを画面上部に出現させる
			this.dispNextMino();
			break;
		case GAME_PAUSE: // ポーズ画面
			this.dispPause();
			break;
		case GAME_OVER: // ゲームオーバ画面
			this.dispGameOver();
			break;
		case GAME_MAGIC_MERA_SELECT: // 魔法発動準備中画面（アニメーション）
			this.dispMagicSelectMera();
			break;
		case GAME_MAGIC_MERA_EXEC: // 魔法発動画面（アニメーション）
			this.dispMagicMera();
			break;
		case GAME_MAGIC_IO_SELECT: // 魔法発動準備中画面（アニメーション）
			this.dispMagicSelectIo();
			break;
		case GAME_MAGIC_IO_EXEC: // 魔法発動画面（アニメーション）
			this.dispMagicIo();
			break;
		case GAME_MINO_DELETE: // ミノが１行削除された
			this.dispDeleteRow();
			break;
		case GAME_RENSA: // イオ発動後
			this.allDown();
			break;
		case GAME_ANIME_LEVELUP:
			this.dispAnimeLevelUp();
			break;
		case GAME_ANIME_DELETE:
			this.dispAnimeDelete();
			break;
		}

		this.show();
	}

	// 不要なフレームを間引く
	private boolean isSkip() {
		long now = System.nanoTime();
		if (now - this.execTime < Conf.WAIT_NANOTIME) {
			return true;
		} else {
			this.execTime = now;
			return false;
		}
	}

	// 初期化
	private void doInit() {
		this.gameStatus = GAME_MENU;
		this.hero.setAction(Hero.HERO_ACTION_MENU);
		this.nextMino = Mino.getMino();
		this.field.init();
		this.field.setMino(Mino.getMino());
		this.mStock.init();
		this.score = 0;
		this.level = 1;
	}

	// タイトル画面
	private void dispTitle() {
		this.message = Conf.MESSAGE_MENU;
		if (GameLib.isKeyOn("ENTER")) {
			TetrisAudio.start();
			TetrisAudio.bgm();
			this.gameStatus = GAME_MAIN;
			this.hero.setAction(Hero.HERO_ACTION_MAIN);
		}
	}

	// ゲームメイン画面を表示
	private void dispGameMain() {
		// ブロックが一定まで積みあがったらBGMテンポアップ
		if (this.field.getMaxHeight() < 8) {
			TetrisAudio.setBgmRate(1.2);
			this.hero.setAction(Hero.HERO_ACTION_PANIC);
		} else {
			TetrisAudio.setBgmRate(1.0);
			this.hero.setAction(Hero.HERO_ACTION_MAIN);
		}

		this.message = "";
		if (GameLib.isKeyOn("P")) {
			TetrisAudio.pause();
			this.gameStatus = GAME_PAUSE; // ポーズ
			this.hero.setAction(Hero.HERO_ACTION_REST);
			return;
		}

		// ミノ操作
		boolean isKeyOn = false;
		if (GameLib.isKeyOn("L")) {
			TetrisAudio.turn();
			isKeyOn = true;
			this.field.turnLeft();
		}
		if (GameLib.isKeyOn("D")) {
			isKeyOn = true;
			this.field.moveRight();
		}
		if (GameLib.isKeyOn("A")) {
			isKeyOn = true;
			this.field.moveLeft();
		}
		if (GameLib.isKeyOn("X")) {
			isKeyOn = true;
			this.field.moveDown();
		}

		// 魔法使用
		if (GameLib.isKeyOn("I")) {
			if (this.mStock.getMagicNumIo() > 0) {
				this.gameStatus = GAME_MAGIC_IO_SELECT;
				this.hero.setAction(Hero.HERO_ACTION_CHARGE);
				this.field.getMagic().setAction(Magic.MAGIC_ACTION_IO_SELECT);
				return;
			}
		} else 	if (GameLib.isKeyOn("M")) {
			if (this.mStock.getMagicNumMera() > 0) {
				this.gameStatus = GAME_MAGIC_MERA_SELECT;
				this.hero.setAction(Hero.HERO_ACTION_CHARGE);
				this.field.getMagic().setAction(Magic.MAGIC_ACTION_MERA_SELECT);
				this.field.getMagic().init(); // 魔法陣の表示位置を初期化
				return;
			}
		}

		// 現在のレベルに応じて落下のタイミングを変更する
		if (!isKeyOn && (this.minoDownCount > (Conf.MAX_LEVEL - this.level))) {
			this.minoDownCount = 0;
			this.field.moveDown();
		}
		this.minoDownCount++;
	}

	// 衝突判定をおこなう。行が揃っていれば削除する
	private void colision() {
		// ミノが床またはパネルに接地した
		if (this.field.colision()) {
			TetrisAudio.colision();
			// 接地したミノをフィールドにセット
			this.field.setMino2Field();

			// 行が揃っていれば削除
			int checkNum = this.field.checkDeleteRow();
			if (checkNum > 0) {

				this.field.deleteRow();
				int preLevel = this.level;
				this.updateScore(checkNum);
				if (this.level > preLevel) {
					// イオゲージ1回復、メラゲージ全回復
					this.mStock.setMagicNumIo(mStock.getMagicNumIo() + 1);
					this.mStock.setMagicNumMera(Conf.MAX_MSTOCK_MERA);
					this.gameStatus = GAME_ANIME_LEVELUP;
					this.hero.setAction(Hero.HERO_ACTION_LEVELUP);
				} else {
					this.gameStatus = GAME_ANIME_DELETE;
					this.hero.setAction(Hero.HERO_ACTION_GUTS_1);
				}
			} else {
				this.gameStatus = GAME_MINO_CREATE;
			}
		}
	}

	// スコア、レベルを加算
	private void updateScore(int deleteRow) {
		// スコアを加算
		if (this.score <= Conf.MAX_SCORE) {
			this.score = this.score + (deleteRow * 100);
		}
		// スコア3000点毎にレベルアップ
		this.level = (this.score / Conf.LEVELUP_SCORE) + 1;
	}

	// 主人公さんのレベルアップモーション再生
	private void dispAnimeLevelUp() {
		if (this.hero.animeIsEnd()) {
			this.gameStatus = GAME_MINO_DELETE;
			this.hero.setAction(Hero.HERO_ACTION_MAIN);
		}
	}

	// 揃った行の削除画面
	private void dispDeleteRow() {
		this.field.moveRow();
		this.gameStatus = GAME_MINO_CREATE;
	}

	// 行が揃った際の主人公さんのモーション再生
	private void dispAnimeDelete() {
		// 主人公さんのアニメ表示が終わるまで待つ
		if (this.hero.animeIsEnd()) {
			this.gameStatus =GAME_MINO_DELETE;
			this.hero.setAction(Hero.HERO_ACTION_MAIN);
		}
	}

	// 次に落下するミノを画面上部に出現させる
	private void dispNextMino() {
		this.nextMino.initXY();
		this.field.setMino(nextMino);
		this.nextMino = Mino.getMino();
		this.gameStatus = GAME_MAIN;

		// 新しいミノが出現時に他パネルに接触している場合ゲームオーバ＾とする
		if (this.field.colision()) {
			TetrisAudio.gameOver();
			this.hero.setAction(Hero.HERO_ACTION_GAMEOVER);
			this.gameStatus = GAME_OVER;
		}
	}

	// ゲームオーバー画面
	private void dispGameOver() {
		this.message = Conf.MESSAGE_GAMEOVER;

		// 主人公さんのアニメ表示が終わるまで待つ
		if (this.hero.animeIsEnd()) {
			if (GameLib.isKeyOn("ENTER")) {
				this.gameStatus = GAME_INIT; // タイトル画面へ戻る
			}
		}
	}

	// ポーズ画面
	private void dispPause() {
		this.message = Conf.MESSAGE_PAUSE;
		if (GameLib.isKeyOn("E")) {
			this.gameStatus = GAME_MAIN; // ゲーム再開
			this.hero.setAction(Hero.HERO_ACTION_MAIN);
			TetrisAudio.replay();
		}
	}

	// 魔法発動準備中（メラ）
	private void dispMagicSelectMera() {
		this.message = Conf.MESSAGE_MERA;

		// キー操作によってカーソルを移動させる
		if (GameLib.isKeyOn("W")) {
			this.field.getMagic().moveUp(); // カーソルを上に移動
		}
		if (GameLib.isKeyOn("X")) {
			this.field.getMagic().moveDown(); // カーソルを下に移動
		}
		if (GameLib.isKeyOn("A")) {
			this.field.getMagic().moveLeft(); // カーソルを左に移動
		}
		if (GameLib.isKeyOn("D")) {
			this.field.getMagic().moveRight(); // カーソルを右に移動
		}

		if (GameLib.isKeyOn("ENTER")) {
			this.gameStatus = GAME_MAGIC_MERA_EXEC; // 魔法発動
			this.hero.setAction(Hero.HERO_ACTION_MAGIC_1);
			this.field.getMagic().setAction(Magic.MAGIC_ACTION_MERA);
			this.field.deletePanel();
		} else if (GameLib.isKeyOn("L")) {
			this.gameStatus = GAME_MAIN; // 魔法発動解除
			this.hero.setAction(Hero.HERO_ACTION_MAIN);
			this.field.getMagic().setAction(Magic.MAGIC_ACTION_NONE);
		}
	}

	// 魔法発動準備中（イオ）
	private void dispMagicSelectIo() {
		this.message = Conf.MESSAGE_IO;

		if (GameLib.isKeyOn("ENTER")) {
			this.gameStatus = GAME_MAGIC_IO_EXEC; // 魔法発動
			this.hero.setAction(Hero.HERO_ACTION_MAGIC_1);
			this.field.getMagic().setAction(Magic.MAGIC_ACTION_IO);
		} else if (GameLib.isKeyOn("L")) {
			this.gameStatus = GAME_MAIN; // 魔法発動解除
			this.hero.setAction(Hero.HERO_ACTION_MAIN);
			this.field.getMagic().setAction(Magic.MAGIC_ACTION_NONE);
		}
	}

	// 魔法実行画面表示
	private void dispMagicMera() {
		this.message = Conf.MESSAGE_NONE;
		// 主人公さんのアニメが終わるまで待つ
		if (this.hero.animeIsEnd()) {
			// 魔法の効果アニメが終わるまで待つ
			if (this.field.getMagic().animeIsEnd()) {
				this.mStock.setMagicNumMera(this.mStock.getMagicNumMera() - 1); // 魔法残弾マイナス1
				this.field.movePanel(); // 魔法によって削除した行より上のパネルを落下
				this.gameStatus = GAME_MAIN;
				this.hero.setAction(Hero.HERO_ACTION_MAIN);
				this.field.getMagic().setAction(Magic.MAGIC_ACTION_NONE);
			}
		}
	}

	// 魔法実行画面表示
	private void dispMagicIo() {
		this.message = Conf.MESSAGE_NONE;
		// 主人公さんのアニメが終わるまで待つ
		if (this.hero.animeIsEnd()) {
			// 魔法の効果アニメが終わるまで待つ
			if (this.field.getMagic().animeIsEnd()) {
				this.mStock.setMagicNumIo(this.mStock.getMagicNumIo() - 1); // 魔法残弾マイナス1
				this.gameStatus = GAME_RENSA;
				this.hero.setAction(Hero.HERO_ACTION_MAIN);
				this.field.getMagic().setAction(Magic.MAGIC_ACTION_NONE);
			}
		}
	}

	// イオ実行後の連鎖処理
	private void allDown() {
		this.message = Conf.MESSAGE_RENSA;
		this.field.setViewType(Field.FIELD_VIEW_NORMAL);

		// ブロックの落下速度調整
		if (this.allDownCount < Conf.ALL_DOWN_WAIT) {
			this.field.setViewType(Field.FIELD_VIEW_RENSA);
			this.allDownCount++;
			return;
		}
		this.allDownCount = 0;

		boolean result = this.field.allDown();
		if (result) {
			this.gameStatus = GAME_RENSA;
		} else {
			this.field.setViewType(Field.FIELD_VIEW_NORMAL);
			this.gameStatus = GAME_MAIN;
		}

		int deleteRows = this.field.checkDeleteRow();
		if (deleteRows > 0) {
			// 揃っている行を削除
			this.field.deleteRow();

			// スコア、レベルを加算
			int preLevel = this.level;
			this.updateScore(deleteRows);

			// レベルアップしたら主人公さんのモーションを変える
			if (this.level > preLevel) {
				// イオゲージ1回復、メラゲージ全回復
				this.mStock.setMagicNumIo(mStock.getMagicNumIo() + 1);
				this.mStock.setMagicNumMera(Conf.MAX_MSTOCK_MERA);
				this.hero.setAction(Hero.HERO_ACTION_LEVELUP);
			} else {
				this.hero.setAction(Hero.HERO_ACTION_GUTS_1);
			}
		} else {
			// 連鎖落下中のモーションを表示
			this.hero.setAction(Hero.HERO_ACTION_ALLDOWN);
		}
	}

	private void show() {
		GraphicsContext canvas = GameLib.getGC();
		if (this.gameStatus == GAME_MENU) {
			this.showBackground(canvas); // 背景タイル画像
		}

		this.field.show(canvas); // ゲームメイン画面
		this.field.getMagic().show(canvas); // 魔法
		this.hero.show(canvas); // 主人公さん
		this.mStock.show(canvas); // 魔法残数
		this.showNextMino(canvas);
		this.showMessage(canvas);
		this.showScore(canvas);
		this.showLevel(canvas);
		this.showInfo(canvas);
	}

	// 背景の表示
	private void showBackground(GraphicsContext canvas) {
		int col = GameLib.width() / (int) Conf.PANEL_W;
		int row = GameLib.height() / (int) Conf.PANEL_H;
		int x,y;

		for (int i = 0; i < col; i++) {
			for (int l = 0; l < row; l++) {
				x = i * (int) Conf.PANEL_W;
				y = l * (int) Conf.PANEL_H;
				canvas.drawImage(TetrisImage.haikei, x, y, Conf.PANEL_W, Conf.PANEL_H);
			}
		}
	}

	// メッセージの表示
	private void showMessage(GraphicsContext canvas) {
		canvas.setFill(Color.WHITE);
		canvas.setFont(new Font("メイリオ", Conf.PANEL_W));
		canvas.setTextAlign(TextAlignment.CENTER);
		canvas.fillText(this.message, Conf.PANEL_W * 8, Conf.PANEL_W * 3);
	}

	// 次のミノ表示
	private void showNextMino(GraphicsContext canvas) {
		canvas.setFill(Color.BLACK);
		canvas.fillRect(Conf.NMINO_X, Conf.NMINO_Y, Conf.NMINO_W, Conf.NMINO_H);
		nextMino.setX(18);
		nextMino.setY(5);
		nextMino.show(canvas);
	}

	// 得点表示
	private void showScore(GraphicsContext canvas) {
		canvas.setFill(Color.BLACK);
		canvas.fillRect(Conf.SCORE_X, Conf.SCORE_Y, Conf.SCORE_W, Conf.SCORE_H);
		canvas.setFont(new Font("メイリオ", Conf.PANEL_W));
		canvas.setTextAlign(TextAlignment.LEFT);
		canvas.setFill(Color.WHITE);
		canvas.fillText("Score ： " + this.score, Conf.SCORE_X + (Conf.PANEL_W * 1), Conf.SCORE_Y + (Conf.PANEL_H * 1.5));
	}

	// レベル表示
	private void showLevel(GraphicsContext canvas) {
		canvas.setFill(Color.BLACK);
		canvas.fillRect(Conf.LEVEL_X, Conf.LEVEL_Y, Conf.LEVEL_W, Conf.LEVEL_H);
		canvas.setFont(new Font("メイリオ", Conf.PANEL_W));
		canvas.setTextAlign(TextAlignment.LEFT);
		canvas.setFill(Color.WHITE);
		canvas.fillText("Level ： " + this.level, Conf.LEVEL_X + (Conf.PANEL_W * 1), Conf.LEVEL_Y + (Conf.PANEL_H * 1.5));
	}

	// 説明表示
	private void showInfo(GraphicsContext canvas) {
		canvas.setFill(Color.BLACK);
		canvas.fillRect(Conf.INFO_X, Conf.INFO_Y, Conf.INFO_W, Conf.INFO_H);
		canvas.setTextAlign(TextAlignment.LEFT);
		canvas.setFont(new Font("メイリオ", Conf.PANEL_W / 2));
		canvas.setFill(Color.WHITE);

		String message;
		switch(this.gameStatus) {
		case GAME_MENU:
			message = Conf.INFO_MENU;
			break;
		case GAME_PAUSE:
			message = Conf.INFO_PAUSE;
			break;
		case GAME_MAGIC_IO_SELECT:
			message = Conf.INFO_MAGIC_IO_SELECT;
			break;
		case GAME_MAGIC_MERA_SELECT:
			message = Conf.INFO_MAGIC_MERA_SELECT;
			break;
		case GAME_OVER:
			message = Conf.INFO_GAMEOVER;
			break;
		case GAME_RENSA:
			message = Conf.INFO_RENSA;
			break;
		default:
			message = Conf.INFO_MAIN;
			break;
		}
		canvas.fillText(message, Conf.INFO_X + (Conf.PANEL_W * 0.5), Conf.INFO_Y + (Conf.PANEL_H * 1));
	}
}