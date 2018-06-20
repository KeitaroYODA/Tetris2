package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
public class Tetris_Obj {

	public static final int GAME_INIT = 0;
	public static final int GAME_MENU = 1;
	public static final int GAME_MAIN = 2;
	public static final int GAME_MINO_CREATE = 3;
	public static final int GAME_PAUSE = 4;
	public static final int GAME_OVER = 5;
	public static final int GAME_MAGIC_SELECT = 6;
	public static final int GAME_MAGIC_EXEC = 7;
	public static final int GAME_MINO_DELETE = 8;

	public static final int GAME_ANIME_LEVELUP = 9;
	public static final int GAME_ANIME_DELETE = 10;

	// フレーム間引き
	private long execTime = System.nanoTime();
	private long nanoTime = 100000000;

	// ゲーム情報
	private int gameStatus = 0;
	private int level = 1; // レベル
	private int maxLevel = 10; // 最大レベル
	private int score = 0; // スコア
	private int maxScore = 100000; // 最大スコア
	private int minoDownWait = 0; // ブロック落下待ち時間
	private String message = ""; // メイン画面に表示されるメッセージ

	// 画面レイアウト
	// 得点表示
	private final double scoreX = Panel.panelW() * 18;
	private final double scoreY = Panel.panelW() * 1;
	private final double scoreW = Panel.panelW() * 14;
	private final double scoreH = Panel.panelH() * 2;
	// 次のミノ表示
	private final double nextX = Panel.panelW() * 18;
	private final double nextY = Panel.panelH() * 4;
	private final double nextW = Panel.panelW() * 6;
	private final double nextH = Panel.panelH() * 7;
	// レベル表示
	private final double levelX = Panel.panelW() * 25;
	private final double levelY = Panel.panelH() * 4;
	private final double levelW = Panel.panelW() * 7;
	private final double levelH = Panel.panelH() * 2;
	// 説明表示
	private final double manualX = Panel.panelW() * 27;
	private final double manualY = Panel.panelH() * 12;
	private final double manualW = Panel.panelW() * 5;
	private final double manualH = Panel.panelH() * 6;

	private Field field; // ミノ表示領域
	private Hero hero; // 主人公さん
	private Magic magic; // 魔法残弾
	private Mino nextMino; // 次に表示されるミノ

	// このメソッドが1秒間に60回ぐらい呼ばれるので
	// テトリスの内部処理をここに書く
	public void update() {
		if (this.isSkip()) {
			return;
		}

		this.hero = Hero.getInstance();
		this.magic = Magic.getInstance();
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
		case GAME_MAGIC_SELECT: // 魔法カーソル選択画面
			this.dispMagicCursor();
			break;
		case GAME_MAGIC_EXEC: // 魔法発動画面（アニメーション）
			this.dispMagic();
			break;
		case GAME_MINO_DELETE: // ミノが１行削除された
			this.dispDeleteRow();
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
		if (now - this.execTime < this.nanoTime) {
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
		this.magic.init();
		this.score = 0;
		this.level = 1;
	}

	// タイトル画面
	private void dispTitle() {
		this.message = "SMILE TETLIS \rPlease Press ENTER!";
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
		if (GameLib.isKeyOn("M")) {
			if (this.magic.getMagicNum() > 0) {
				this.gameStatus = GAME_MAGIC_SELECT;
				this.hero.setAction(Hero.HERO_ACTION_CHARGE);
				return;
			}
		}

		// 現在のレベルに応じて落下のタイミングを変更する
		if (!isKeyOn && (minoDownWait > (this.maxLevel - this.level))) {
			this.minoDownWait = 0;
			this.field.moveDown();
		}
		this.minoDownWait++;
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
				// スコアを加算
				if (this.score <= this.maxScore) {
					this.score = this.score + (checkNum * 100);
				}
				// スコア3000点毎にレベルアップ
				int preLevel = this.level;
				this.level = (this.score / 1000) + 1;
				//this.level = (this.score / 3000) + 1;


				if (this.level > preLevel) {
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
		this.message = "GAME OVER!! \rPrease Press ENTER";

		// 主人公さんのアニメ表示が終わるまで待つ
		if (this.hero.animeIsEnd()) {
			if (GameLib.isKeyOn("ENTER")) {
				this.gameStatus = GAME_INIT; // タイトル画面へ戻る
			}
		}
	}

	// ポーズ画面
	private void dispPause() {
		this.message = "Pause Prease Press E";
		if (GameLib.isKeyOn("E")) {
			this.gameStatus = GAME_MAIN; // ゲーム再開
			this.hero.setAction(Hero.HERO_ACTION_MAIN);
			TetrisAudio.replay();
		}
	}

	// 魔法陣カーソル表示画面
	private void dispMagicCursor() {
		this.message = "魔法を発動: ENTER \r中止: L";

		// キー操作によってカーソルを移動させる
		if (GameLib.isKeyOn("W")) {
			this.field.getCursor().moveUp(); // カーソルを上に移動
		} else if (GameLib.isKeyOn("X")) {
			this.field.getCursor().moveDown(); // カーソルを下に移動
		} else if (GameLib.isKeyOn("A")) {
			this.field.getCursor().moveLeft(); // カーソルを左に移動
		} else if (GameLib.isKeyOn("D")) {
			this.field.getCursor().moveRight(); // カーソルを右に移動
		}

		if (GameLib.isKeyOn("ENTER")) {
			this.gameStatus = GAME_MAGIC_EXEC; // 魔法発動
			this.hero.setAction(Hero.HERO_ACTION_MAGIC_1);
			this.field.deletePanel();
		} else if (GameLib.isKeyOn("L")) {
			this.hero.setAction(Hero.HERO_ACTION_MAIN);
			this.gameStatus = GAME_MAIN; // 魔法発動解除
		}
	}

	// 魔法実行画面表示
	private void dispMagic() {
		// 主人公さんのアニメが終わるまで待つ
		if (this.hero.animeIsEnd()) {
			// 魔法の効果アニメが終わるまで待つ
			if (this.field.getCursor().animeIsEnd()) {
				this.magic.setMagicNum(this.magic.getMagicNum() - 1); // 魔法残弾マイナス1
				this.field.movePanel(); // 魔法によって削除した行より上のパネルを落下
				this.gameStatus = GAME_MAIN;
				this.hero.setAction(Hero.HERO_ACTION_MAIN);
			}
		}
	}

	private void show() {
		GraphicsContext canvas = GameLib.getGC();
		this.showBackground(canvas); // 背景タイル画像
		this.field.show(canvas, this.gameStatus); // ゲームメイン画面
		//this.hero.show(canvas, this.gameStatus); // 主人公さん
		this.hero.show(canvas); // 主人公さん
		this.field.getCursor().show(canvas, this.gameStatus); // 魔法陣
		this.magic.show(canvas); // 魔法残数
		this.showNextMino(canvas);
		this.showMessage(canvas);
		this.showScore(canvas);
		this.showLevel(canvas);
		this.showManual(canvas);
	}

	// 背景の表示
	private void showBackground(GraphicsContext canvas) {
		int col = GameLib.width() / (int) Panel.panelW();
		int row = GameLib.height() / (int) Panel.panelH();
		int x,y;

		for (int i = 0; i < col; i++) {
			for (int l = 0; l < row; l++) {
				x = i * (int) Panel.panelW();
				y = l * (int) Panel.panelH();
				canvas.drawImage(TetrisImage.haikei, x, y, Panel.panelW(), Panel.panelH());
			}
		}
	}

	// メッセージの表示
	private void showMessage(GraphicsContext canvas) {
		canvas.setFill(Color.WHITE);
		canvas.setFont(new Font("メイリオ",Panel.panelW()));
		canvas.setTextAlign(TextAlignment.CENTER);
		canvas.fillText(this.message, Panel.panelW() * 8, Panel.panelW() * 3);
	}

	// 次のミノ表示
	private void showNextMino(GraphicsContext canvas) {
		canvas.setFill(Color.BLACK);
		canvas.fillRect(nextX, nextY, nextW, nextH);
		nextMino.setX(18);
		nextMino.setY(5);
		nextMino.show(canvas);
	}

	// 得点表示
	private void showScore(GraphicsContext canvas) {
		canvas.setFill(Color.BLACK);
		canvas.fillRect(scoreX, scoreY, scoreW, scoreH);
		canvas.setFont(new Font("メイリオ",Panel.panelW()));
		canvas.setTextAlign(TextAlignment.LEFT);
		canvas.setFill(Color.WHITE);
		canvas.fillText("Score ： " + this.score, scoreX + (Panel.panelW() * 1), scoreY + (Panel.panelH() * 1.5));
	}

	// レベル表示
	private void showLevel(GraphicsContext canvas) {
		canvas.setFill(Color.BLACK);
		canvas.fillRect(levelX, levelY, levelW, levelH);
		canvas.setFont(new Font("メイリオ",Panel.panelW()));
		canvas.setTextAlign(TextAlignment.LEFT);
		canvas.setFill(Color.WHITE);
		canvas.fillText("Level ： " + this.level, levelX + (Panel.panelW() * 1), levelY + (Panel.panelH() * 1.5));
	}

	// 説明表示
	private void showManual(GraphicsContext canvas) {
		canvas.setFill(Color.BLACK);
		canvas.fillRect(manualX, manualY, manualW, manualH);
		canvas.setTextAlign(TextAlignment.LEFT);
		canvas.setFont(new Font("メイリオ",Panel.panelW() / 2));
		canvas.setFill(Color.WHITE);
		String message = "A : 左移動\rD : 右移動\rL : ミノ回転\rX : ミノ落下\rM : 魔法発動\rP:ポーズ";
		canvas.fillText(message, manualX + (Panel.panelW() * 1), manualY + (Panel.panelH() * 1));
	}
}