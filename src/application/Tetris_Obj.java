package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
public class Tetris_Obj {

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
	private final double nextH = Panel.panelH() * 5;
	// レベル表示
	private final double levelX = Panel.panelW() * 25;
	private final double levelY = Panel.panelH() * 4;
	private final double levelW = Panel.panelW() * 7;
	private final double levelH = Panel.panelH() * 2;

	private Field field; // パネル表示領域
	private Hero hero; // 主人公さん
	private Magic magic; // 魔法残弾
	private Mino nextMino; // 次に表示されるミノ

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
		this.gameStatus = 1;
		this.nextMino = Mino.getMino();
		this.field.init();
		this.field.setMino(Mino.getMino());
	}

	// タイトル画面
	private void dispTitle() {
		this.message = "SMILE TETLIS";
		if (GameLib.isKeyOn("ENTER")) {
			this.gameStatus = 2;
		}
	}

	// 次に落下するミノを画面上部に出現させる
	private void dispNextMino() {
		this.nextMino.initXY();
		this.field.setMino(nextMino);
		this.nextMino = Mino.getMino();
		this.gameStatus = 2;

		// 新しいミノが出現時に他パネルに接触している場合ゲームオーバ＾とする
		if (this.field.colision()) {
			this.gameStatus = 5;
		}
	}

	// ゲームオーバー画面
	private void dispGameOver() {
		this.message = "GAME OVER!! \rPrease Press ENTER";
		// 主人公さんのアニメ表示が終わるまで待つ
		if (this.hero.animeIsEnd()) {
			if (GameLib.isKeyOn("ENTER")) {
				this.gameStatus = 0; // タイトル画面へ戻る
			}
		}
	}

	// ポーズ画面
	private void dispPause() {
		this.message = "Pause Prease Press E";
		if (GameLib.isKeyOn("E")) {
			this.gameStatus = 2; // ゲーム再開
		}
	}

	// 魔法陣カーソル表示画面
	private void dispMagicCursor() {
		this.message = "魔法を発動させたい場所を選択して ENTER \rやめる場合は L";

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
			this.gameStatus = 7; // 魔法発動
			this.field.deletePanel();
		} else if (GameLib.isKeyOn("L")) {
			this.gameStatus = 2; // 魔法発動解除
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
				this.gameStatus = 2;
			}
		}
	}

	// 揃った行の削除画面
	private void dispDeleteRow() {
		// 主人公さんのアニメ表示が終わるまで待つ
		if (this.hero.animeIsEnd()) {
			this.field.moveRow();
			this.gameStatus = 3;
		}
	}

	private void dispGameMain() {
		this.message = "";
		if (GameLib.isKeyOn("P")) {
			this.gameStatus = 4; // ポーズ
			return;
		}

		// ミノ操作
		boolean isKeyOn = false;
		if (GameLib.isKeyOn("L")) {
			isKeyOn = true;
			this.field.turnLeft(); // 左回転
		} else if (GameLib.isKeyOn("D")) {
			isKeyOn = true;
			this.field.moveRight(); // 右へ移動
		} else if (GameLib.isKeyOn("A")) {
			isKeyOn = true;
			this.field.moveLeft(); // 左へ移動
		} else if (GameLib.isKeyOn("X")) {
			isKeyOn = true;
			this.field.moveDown(); // 落下
		}

		// 魔法使用
		if (GameLib.isKeyOn("M")) {
			if (this.magic.getMagicNum() > 0) {
				this.gameStatus = 6;
				return;
			}
		}

		// キーが押下されている間は落下しない
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
			// ミノのパネル情報をフィールドにセット
			this.field.setMino2Field();
			// 行が揃っていれば削除
			int checkNum = this.field.checkRemoveRow();
			if (checkNum > 0) {
				this.field.deleteRow();
				this.updateScore(checkNum);
				this.gameStatus = 8;
			} else {
				this.gameStatus = 3;
			}
		}
	}

	// スコアとレベルを更新する
	private void updateScore(int deleteRow) {
		// スコアを加算
		if (this.score <= this.maxScore) {
			this.score = this.score + (deleteRow * 100);
		}
		// スコア3000点毎にレベルアップ
		this.level = (this.score / 3000) + 1;
	}

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
		case 0: // 画面の初期化
			this.doInit();
			break;
		case 1: // タイトル画面
			this.dispTitle();
			break;
		case 2: // ゲーム画面
			this.dispGameMain();
			this.colision();
			break;
		case 3: // 新しいミノを出現させる
			this.dispNextMino();
			break;
		case 4: // ポーズ画面
			this.dispPause();
			break;
		case 5: // ゲームオーバ画面
			this.dispGameOver();
			break;
		case 6: // 魔法カーソル選択画面
			this.dispMagicCursor();
			break;
		case 7: // 魔法発動画面（アニメーション）
			this.dispMagic();
			break;
		case 8: // ミノが１行削除された
			this.dispDeleteRow();
			break;
		}
		this.show();
	}

	// 画面表示
	private void show() {
		GraphicsContext canvas = GameLib.getGC();
		this.showBackground(canvas);
		this.field.show(canvas);
		this.magic.show(canvas);
		this.hero.show(canvas, this.gameStatus);
		this.field.getCursor().show(canvas, this.gameStatus);

		this.showNextMino(canvas);
		this.showMessage(canvas);
		this.showScore(canvas);
		this.showLevel(canvas);
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
		canvas.fillText(this.message, 60, 50);
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
		canvas.setFill(Color.WHITE);
		canvas.fillText("スコア：" + this.score, scoreX + Panel.panelW(), scoreY + Panel.panelW());
	}

	// レベル表示
	private void showLevel(GraphicsContext canvas) {
		canvas.setFill(Color.BLACK);
		canvas.fillRect(levelX, levelY, levelW, levelH);
		canvas.setFill(Color.WHITE);
		canvas.fillText("LV：" + this.level, levelX + Panel.panelW(), levelY + Panel.panelW());
	}
}