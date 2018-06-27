package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class Tetris_Obj {

	// <TODO> 壁に接触時の中心軸の変更
	// <TODO> T-SPINの導入

	// ゲームステータス
	private static final int GAME_INIT = 0; // 初期化
	private static final int GAME_MENU = 1; // スタート画面
	private static final int GAME_MAIN = 2; // メインゲーム画面
	private static final int GAME_PAUSE = 3; // ポーズ画面
	private static final int GAME_OVER = 4; // ゲームオーバー画面
	private static final int GAME_MINO_CREATE = 5; // 新たなミノが上部に出現
	private static final int GAME_MINO_DELETE = 6; // ミノが設置時に揃った行を削除
	private static final int GAME_MAGIC_IO_SELECT = 7; // 魔法（イオ）発動準備中
	private static final int GAME_MAGIC_MERA_SELECT = 8; // 魔法（メラ）発動準備中
	private static final int GAME_MAGIC_IO_EXEC = 9; // 魔法（イオ）発動
	private static final int GAME_MAGIC_MERA_EXEC = 10; // 魔法（メラ）発動
	private static final int GAME_RENSA_IO = 11; // 魔法（イオ）発動後の連鎖処理
	private static final int GAME_RENSA_MERA = 12; // 魔法（イオ）発動後の連鎖処理
	private static final int GAME_RENSA_IO_DELETE = 13; // 魔法（イオ）発動後に揃った行を削除
	private static final int GAME_RENSA_MERA_DELETE = 14; // 魔法（メラ）発動後に揃った行を削除

	private long execTime = System.nanoTime(); // フレーム間引き用」
	private int colisionCount = 0; // ミノ接地後の操作可能時間カウンタ
	private int minoDownCount = 0; // ミノ落下速度調整用カウンタ
	private int downCount = 0; // 魔法発動後のブロック落下速度調整用カウンタ
	private int keyCount = 0;

	// ゲーム情報
	private int gameStatus = GAME_INIT;
	private int level = 1; // レベル
	private int score = 0; // スコア
	private String message = Conf.MESSAGE_NONE; // メイン画面に表示されるメッセージ

	private Field field; // ミノ表示領域
	private Hero hero; // 主人公さん
	private MStock mStock; // 魔法残弾
	private Mino nextMino_1; // 次に表示されるミノ
	private Mino nextMino_2; // 次の次に表示されるミノ

	// このメソッドが1秒間に60回ぐらい呼ばれるので
	// テトリスの内部処理をここに書く
	public void update() {
		// フレーム間引き処理
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
		case GAME_MINO_DELETE: // 揃った行を削除
			this.deleteRow();
			break;
		case GAME_RENSA_IO_DELETE: // 揃った行を削除（連鎖中）
			this.deleteRowRensaIo();
			break;
		case GAME_RENSA_MERA_DELETE: // 揃った行を削除（連鎖中）
			this.deleteRowRensaMera();
			break;
		case GAME_RENSA_IO: // イオ発動後の連鎖中画面
			this.dispRensaIo();
			break;
		case GAME_RENSA_MERA: // メラ発動後の連鎖中画面
			this.dispRensaMera();
			break;
		}

		this.show();
	}

	// 初期化
	private void doInit() {
		this.gameStatus = GAME_MENU;
		this.nextMino_1 = Mino.getMino();
		this.nextMino_2 = Mino.getMino();

		this.field.init();
		this.field.setMino(Mino.getMino());
		this.mStock.init();
		this.initScore();
	}

	// タイトル画面
	private void dispTitle() {
		this.message = Conf.MESSAGE_MENU;
		this.hero.setAction(Hero.HERO_ACTION_MENU);

		if (GameLib.isKeyOn("ENTER")) {
			TetrisAudio.bgm();
			this.message = Conf.MESSAGE_NONE;
			this.gameStatus = GAME_MAIN;
		}
	}

	// ゲームメイン画面を表示
	private void dispGameMain() {

		// ポーズ
		if (GameLib.isKeyOn("P")) {
			TetrisAudio.pause();
			this.gameStatus = GAME_PAUSE;
			return;
		}

		// 魔法使用
		// イオ
		if (GameLib.isKeyOn("I")) {
			if (this.mStock.getMagicNumIo() > 0) {
				this.gameStatus = GAME_MAGIC_IO_SELECT;
				return;
			}
		// メラ
		} else 	if (GameLib.isKeyOn("M")) {
			if (this.mStock.getMagicNumMera() > 0) {
				this.gameStatus = GAME_MAGIC_MERA_SELECT;
				return;
			}
		}

		// ブロックが一定まで積みあがったらBGMテンポアップ
		if (this.field.getMaxHeight() < 8) {
			TetrisAudio.setBgmRate(1.2);
			this.hero.setAction(Hero.HERO_ACTION_PANIC);
		} else {
			TetrisAudio.setBgmRate(1.0);
			this.hero.setAction(Hero.HERO_ACTION_MAIN);
		}

		// ミノ操作
		boolean isKeyOn = false;

		// ミノ回転
		if (this.keyCount == 0) {
			if (GameLib.isKeyOn("J")) {
				isKeyOn = true;
				this.turnLeft();
			} else if (GameLib.isKeyOn("L")) {
				isKeyOn = true;
				this.turnRight();
			}

			if (isKeyOn) {
				TetrisAudio.turn();
				this.keyCount = 2;
			}
		}

		this.keyCount--;
		if (this.keyCount <= 0) {
			this.keyCount = 0;
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

		// 現在のレベルに応じて落下のタイミングを変更する
		if (!isKeyOn && (this.minoDownCount > (Conf.MAX_LEVEL - this.level))) {
			this.minoDownCount = 0;
			this.field.moveDown();
		}
		this.minoDownCount++;
	}

	// ミノを回転（左）
	private void turnLeft() {
		Mino mino = this.field.getMino();
		int[][][] correctionArray = mino.getCorrectionLeftArray();

		// 回転できない場合補正した値を使って再度回転を試行する
		boolean result = this.field.turnLeft();
		if (!result) {
			for (int i = 0; i < Mino.CORRECTIONS; i++) {
				int x = this.field.getMino().getX();
				int y = this.field.getMino().getY();

				// 補正値を取得してミノにセット
				int ex = x + correctionArray[mino.getDirection()][i][Mino.CORRECTION_X];
				int ey = y + correctionArray[mino.getDirection()][i][Mino.CORRECTION_Y];
				this.field.getMino().setX(ex);
				this.field.getMino().setY(ey);

				// ミノの回転を再試行
				result = this.field.turnLeft();
				if (result) {
					// 回転できたら終了
					return;
				} else {
					// 回転できなかったらXYの値を元に戻す
					this.field.getMino().setX(x);
					this.field.getMino().setY(y);
				}
			}
		}
	}

	// ミノを回転（左）
	private void turnRight() {
		Mino mino = this.field.getMino();
		int[][][] correctionArray = mino.getCorrectionRightArray();

		// 回転できない場合補正した値を使って再度回転を試行する
		boolean result = this.field.turnRight();
		if (!result) {
			for (int i = 0; i < Mino.CORRECTIONS; i++) {
				int x = this.field.getMino().getX();
				int y = this.field.getMino().getY();

				// 補正値を取得してミノにセット
				int ex = x + correctionArray[mino.getDirection()][i][Mino.CORRECTION_X];
				int ey = y + correctionArray[mino.getDirection()][i][Mino.CORRECTION_Y];
				this.field.getMino().setX(ex);
				this.field.getMino().setY(ey);

				// ミノの回転を再試行
				result = this.field.turnRight();
				if (result) {
					// 回転できたら終了
					return;
				} else {
					// 回転できなかったらXYの値を元に戻す
					this.field.getMino().setX(x);
					this.field.getMino().setY(y);
				}
			}
		}
	}

	// 衝突判定をおこなう。行が揃っていれば削除する
	private void colision() {

		// ミノが床またはパネルに接地した
		if (this.field.colision()) {

			if (!GameLib.isKeyOn("X")) {
				// 落下キーが押下されていない場合、接地後少しだけ操作可能とする
				if (this.colisionCount < Conf.WAIT_COLISION) {
					this.colisionCount++;
					return;
				}
			}

			TetrisAudio.colision();
			this.gameStatus = GAME_MINO_CREATE;

			// 接地したミノをフィールドにセット
			this.field.setMino2Field();

			int deleteRows = this.field.checkDeleteRow();
			if (deleteRows > 0) {
				this.gameStatus = GAME_MINO_DELETE;
				this.updateScore(deleteRows);
			}
		} else {
			// 一度接触してから離れた場合猶予時間をリセット
			this.colisionCount = 0;
		}
	}

	// 次に落下するミノを画面上部に出現させる
	private void dispNextMino() {

		this.nextMino_1.init();
		this.field.setMino(nextMino_1);
		this.nextMino_1 = this.nextMino_2;
		this.nextMino_2 = Mino.getMino();

		this.gameStatus = GAME_MAIN;

		// 新しいミノが出現時に他パネルに接触している場合ゲームオーバ＾とする
		if (this.field.colision()) {
			TetrisAudio.gameOver();
			this.gameStatus = GAME_OVER;
		}
	}

	// ゲームオーバー画面
	private void dispGameOver() {
		this.message = Conf.MESSAGE_GAMEOVER;
		this.hero.setAction(Hero.HERO_ACTION_GAMEOVER);

		// 主人公さんのアニメ表示が終わるまで待つ
		if (this.hero.animeIsEnd()) {
			if (GameLib.isKeyOn("ENTER")) {
				TetrisAudio.start();
				this.message = Conf.MESSAGE_NONE;
				this.gameStatus = GAME_INIT; // タイトル画面へ戻る
			}
		}
	}

	// ポーズ画面
	private void dispPause() {
		this.message = Conf.MESSAGE_PAUSE;
		this.hero.setAction(Hero.HERO_ACTION_REST);

		if (GameLib.isKeyOn("E")) {
			TetrisAudio.replay();
			this.message = Conf.MESSAGE_NONE;
			this.gameStatus = GAME_MAIN; // ゲーム再開
		}
	}

	// 魔法発動準備中（メラ）
	private void dispMagicSelectMera() {
		this.hero.setAction(Hero.HERO_ACTION_CHARGE);
		this.field.getMagic().setAction(Magic.MAGIC_ACTION_MERA_SELECT);

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
		} else if (GameLib.isKeyOn("L")) {
			this.gameStatus = GAME_MAIN; // 魔法発動解除
			this.field.getMagic().setAction(Magic.MAGIC_ACTION_NONE);
			this.field.getMagic().initCursor(); // 魔法陣の表示位置を初期化
		}
	}

	// 魔法発動準備中（イオ）
	private void dispMagicSelectIo() {
		this.hero.setAction(Hero.HERO_ACTION_CHARGE);
		this.field.getMagic().setAction(Magic.MAGIC_ACTION_IO_SELECT);

		if (GameLib.isKeyOn("ENTER")) {
			this.gameStatus = GAME_MAGIC_IO_EXEC; // 魔法発動
		} else if (GameLib.isKeyOn("L")) {
			this.gameStatus = GAME_MAIN; // 魔法発動解除
			this.field.getMagic().setAction(Magic.MAGIC_ACTION_NONE);
		}
	}

	// 魔法発動中（メラ）
	private void dispMagicMera() {
		this.hero.setAction(Hero.HERO_ACTION_MAGIC_1);
		this.field.getMagic().setAction(Magic.MAGIC_ACTION_MERA);

		// 主人公さんと魔法のアニメが終わるまで待つ
		if (this.hero.animeIsEnd() && this.field.getMagic().animeIsEnd()) {
			this.mStock.setMagicNumMera(this.mStock.getMagicNumMera() - 1); // 魔法残弾マイナス1
			this.gameStatus = GAME_RENSA_MERA;
			this.field.getMagic().setAction(Magic.MAGIC_ACTION_NONE);

			// 魔法陣で指定した範囲のパネルを削除
			int deletePanels = this.field.deletePanels();
			this.updateScore(deletePanels);
		}
	}

	// 魔法発動中（イオ）
	private void dispMagicIo() {
		this.hero.setAction(Hero.HERO_ACTION_MAGIC_1);
		this.field.getMagic().setAction(Magic.MAGIC_ACTION_IO);

		// 主人公さんと魔法のアニメが終わるまで待つ
		if (this.hero.animeIsEnd() && this.field.getMagic().animeIsEnd()) {
			this.mStock.setMagicNumIo(this.mStock.getMagicNumIo() - 1); // 魔法残弾マイナス1
			this.gameStatus = GAME_RENSA_IO;
			this.field.getMagic().setAction(Magic.MAGIC_ACTION_NONE);
		}
	}

	// メラ実行後の連鎖処理（落下）
	private void dispRensaMera() {

		// ブロックの落下速度調整
		if (!this.isDown()) {
			return;
		}

		if (this.field.allDown(Magic.MAGIC_ACTION_MERA)) {
			this.gameStatus = GAME_RENSA_MERA;
			this.hero.setAction(Hero.HERO_ACTION_ALLDOWN);
			this.field.getMagic().setAction(Magic.MAGIC_ACTION_FRAME_MERA);
		} else {
			this.gameStatus = GAME_MAIN;
			this.field.getMagic().setAction(Magic.MAGIC_ACTION_NONE);
			this.field.getMagic().initCursor(); // 魔法陣の表示位置を初期化
		}

		int deleteRows = this.field.checkDeleteRow();
		if (deleteRows > 0) {
			this.gameStatus = GAME_RENSA_MERA_DELETE;
			this.updateScore(deleteRows);
		}
	}

	// イオ実行後の連鎖処理（落下）
	private void dispRensaIo() {

		// ブロックの落下速度調整
		if (!this.isDown()) {
			return;
		}

		if (this.field.allDown(Magic.MAGIC_ACTION_IO)) {
			this.gameStatus = GAME_RENSA_IO;
			this.hero.setAction(Hero.HERO_ACTION_ALLDOWN);
			this.field.getMagic().setAction(Magic.MAGIC_ACTION_FRAME);
		} else {
			this.gameStatus = GAME_MAIN;
			this.field.getMagic().setAction(Magic.MAGIC_ACTION_NONE);
		}

		int deleteRows = this.field.checkDeleteRow();
		if (deleteRows > 0) {
			this.gameStatus = GAME_RENSA_IO_DELETE;
			this.updateScore(deleteRows);
		}
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

	// スコア、レベルを初期化
	private void initScore() {
		this.score = 0;
		this.level = 1;
	}

	// 揃った行を削除
	private void deleteRow() {
		if (this.field.animeIsEnd()) {
			this.field.deleteRow(); // 揃った行のブロックを消す

			// ブロックが消えてから少し時間をおいて落とす
			if (this.isDown()) {
				this.field.rowDown(); // 揃った行より上のブロックを1段落とす
				this.field.initAnime();
				this.gameStatus = GAME_MINO_CREATE;
			}
		}
	}

	// 揃った行を削除（イオ発動後）
	private void deleteRowRensaIo() {
		if (this.field.animeIsEnd()) {
			this.field.deleteRow(); // 揃った行のブロックを消す
			this.field.initAnime();
			this.gameStatus = GAME_RENSA_IO;
		}
	}

	// 揃った行を削除（メラ発動後）
	private void deleteRowRensaMera() {
		if (this.field.animeIsEnd()) {
			this.field.deleteRow(); // 揃った行のブロックを消す

			// ブロックが消えてから少し時間をおいて落とす
			if (this.isDown()) {
				this.field.rowDown(); // 揃った行より上のブロックを1段落とす
				this.field.initAnime();
				this.gameStatus = GAME_RENSA_MERA;
			}
		}
	}

	// ブロックの落下速度調整
	private boolean isDown() {
		boolean result = false;
		if (this.downCount > Conf.DOWN_WAIT) {
			this.downCount = 0;
			result = true;
		} else {
			this.downCount++;
		}
		return result;
	}

	// スコア、レベルを加算
	private void updateScore(int delete) {

		// 行の削除がなかった場合にはなにもしない
		if (delete == 0) {
			return;
		}

		int crtLevel = this.level;

		// スコアを加算
		if (this.score <= Conf.MAX_SCORE) {
			this.score = this.score + (delete * 100);
		}
		// 一定のスコア毎にレベルアップ
		this.level = (this.score / Conf.LEVELUP_SCORE) + 1;

		// レベルアップ
		if (this.level > crtLevel) {
			// イオゲージ1回復、メラゲージ全回復
			this.mStock.setMagicNumIo(mStock.getMagicNumIo() + 1);
			this.mStock.setMagicNumMera(Conf.MAX_MSTOCK_MERA);
			this.hero.setAction(Hero.HERO_ACTION_LEVELUP);
		} else {
			this.hero.setAction(Hero.HERO_ACTION_GUTS_1);
		}
	}

	private void show() {
		GraphicsContext canvas = GameLib.getGC();
		if (this.gameStatus == GAME_MENU) {
			this.showBackground(canvas); // 背景タイル画像
		}

		this.field.show(canvas); // ゲームメイン画面
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
		canvas.setFont(new Font("游ゴシック体", Conf.MESSAGE_W));
		canvas.setTextAlign(TextAlignment.CENTER);
		canvas.fillText(this.message, Conf.MESSAGE_X, Conf.MESSAGE_Y);
	}

	// 次のミノ表示
	private void showNextMino(GraphicsContext canvas) {
		canvas.setFill(Color.BLACK);
		canvas.fillRect(Conf.NMINO_X, Conf.NMINO_Y, Conf.NMINO_W, Conf.NMINO_H);
		nextMino_1.setX(18);
		nextMino_1.setY(4);
		nextMino_1.show(canvas, false);

		nextMino_2.setX(18);
		nextMino_2.setY(7);
		nextMino_2.show(canvas, true);
	}

	// 得点表示
	private void showScore(GraphicsContext canvas) {
		canvas.setFill(Color.BLACK);
		canvas.fillRect(Conf.SCORE_X, Conf.SCORE_Y, Conf.SCORE_W, Conf.SCORE_H);
		canvas.setFont(new Font("游ゴシック体", Conf.PANEL_W));
		canvas.setTextAlign(TextAlignment.LEFT);
		canvas.setFill(Color.WHITE);
		canvas.fillText("Score ： " + this.score, Conf.SCORE_X + (Conf.PANEL_W * 1), Conf.SCORE_Y + (Conf.PANEL_H * 1.5));
	}

	// レベル表示
	private void showLevel(GraphicsContext canvas) {
		canvas.setFill(Color.BLACK);
		canvas.fillRect(Conf.LEVEL_X, Conf.LEVEL_Y, Conf.LEVEL_W, Conf.LEVEL_H);
		canvas.setFont(new Font("游ゴシック体", Conf.PANEL_W));
		canvas.setTextAlign(TextAlignment.LEFT);
		canvas.setFill(Color.WHITE);
		canvas.fillText("Level ： " + this.level, Conf.LEVEL_X + (Conf.PANEL_W * 1), Conf.LEVEL_Y + (Conf.PANEL_H * 1.5));
	}

	// 説明表示
	private void showInfo(GraphicsContext canvas) {
		canvas.setFill(Color.BLACK);
		canvas.fillRect(Conf.INFO_X, Conf.INFO_Y, Conf.INFO_W, Conf.INFO_H);
		canvas.setTextAlign(TextAlignment.LEFT);
		canvas.setFont(new Font("游ゴシック体", Conf.PANEL_W / 2));
		canvas.setFill(Color.WHITE);

		String message;
		switch(this.gameStatus) {
		case GAME_INIT:
			message = Conf.INFO_NONE;
			break;
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
		case GAME_MAGIC_IO_EXEC:
		case GAME_MAGIC_MERA_EXEC:
		case GAME_RENSA_IO:
		case GAME_RENSA_MERA:
		case GAME_RENSA_MERA_DELETE:
		case GAME_RENSA_IO_DELETE:
			message = Conf.INFO_RENSA;
			break;
		default:
			message = Conf.INFO_MAIN;
			break;
		}
		canvas.fillText(message, Conf.INFO_X + (Conf.PANEL_W * 0.5), Conf.INFO_Y + (Conf.PANEL_H * 1));
	}
}