package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
public class Tetris_Obj {

	// フレーム間引き
	private long execTime = System.nanoTime();
	private long nanoTime = 100000000;

	// アニメ表示用
	private int heroAnimeCount = 0;
	private boolean heroAnimeEnd = false;

	// ゲーム情報
	private int gameStatus = 0;
	private int level = 1; // レベル
	private int score = 0; // スコア
	private int maxLevel = 10; // 最大レベル
	private int maxScore = 100000;
	private int minoDownWait = 0; // ブロック落下待ち時間
	private String message = ""; // メイン画面に表示されるメッセージ

	private Mino mino; // 落下中のミノ
	private Mino nextMino; // 次に表示されるミノ
	private Field field; // パネル表示領域

	// 画面レイアウト
	// 得点表示
	private static final double scoreX = Panel.panelW() * 18;
	private static final double scoreY = Panel.panelW() * 1;
	private static final double scoreW = Panel.panelW() * 14;
	private static final double scoreH = Panel.panelH() * 2;
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
	// 魔法ストック表示
	private final double magicX = Panel.panelW() * 25;
	private final double magicY = Panel.panelH() * 7;
	private final double magicW = Panel.panelW() * 7;
	private final double magicH = Panel.panelH() * 2;
	// 主人公さん表示
	private final double heroX = Panel.panelW() * 19;
	private final double heroY = Panel.panelH() * 10;
	private final double heroW = Panel.panelW() * 8;
	private final double heroH = Panel.panelH() * 8;

	private TetrisImage heroImage = new TetrisImage();
	private TetrisImage flameImage_1 = new TetrisImage();
	private TetrisImage flameImage_2 = new TetrisImage();
	private TetrisImage flameImage_3 = new TetrisImage();

	// このメソッドが1秒間に60回ぐらい呼ばれるので
	// テトリスの内部処理をここに書く
	public void update() {

		// 不要なフレームを間引く
		long now = System.nanoTime();
		if (now - execTime < nanoTime) {
			return;
		}
		this.execTime = now;

		switch(this.gameStatus) {
		case 0: // 画面の初期化
			this.gameStatus = 1;
			this.field = new Field();
			this.nextMino = Mino.getMino();
			this.field.setMino(Mino.getMino());
			this.heroImage.init();
			break;

		case 1: // タイトル画面
			this.message = "SMILE TETLIS";
			if (GameLib.isKeyOn("ENTER")) {
				this.heroAnimeCount = 0;
				this.gameStatus = 2;
			}
			break;

		case 2: // ゲーム画面
			this.message = "";

			if (GameLib.isKeyOn("P")) {
				this.heroAnimeCount = 0;
				this.gameStatus = 4; // ポーズ
				return;
			}

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
				this.heroAnimeCount = 0;
				this.gameStatus = 6;
				return;
			} else if (GameLib.isKeyOn("I")) {
				// これから実装
			}

			// キーが押下されている間は落下しない
			// 現在のレベルに応じて落下のタイミングを変更する
			if (!isKeyOn && (minoDownWait > (this.maxLevel - this.level))) {
				this.minoDownWait = 0;
				this.field.moveDown();
			}
			this.minoDownWait++;

			// ミノが床またはパネルに接地した
			if (this.field.colision()) {

				// ミノのパネル情報をフィールドにセット
				this.field.setMino2Field();
				this.gameStatus = 3;

				// 行が揃っていれば削除
				int checkNum = this.field.checkRemoveRow();
				if (checkNum > 0) {
					this.field.deleteRow();
					// スコアを加算
					if (this.score <= this.maxScore) {
						this.score = this.score + (checkNum * 100);
					}
					// スコア3000点毎にレベルアップ
					this.level = (this.score / 3000) + 1;

					this.heroAnimeCount = 0;
					this.heroAnimeEnd = false;
					this.gameStatus = 8;
				}
			}
			break;

		case 3: // 新しいミノを出現させる
			nextMino.initXY();
			this.field.setMino(nextMino);
			this.nextMino = Mino.getMino();
			this.gameStatus = 2;

			// 新しいミノが出現時に他パネルに接触している場合ゲームオーバ＾とする
			if (this.field.colision()) {
				this.gameStatus = 5;
				this.heroAnimeCount = 0;
			}
			break;

		case 4: // ポーズ画面
			this.message = "Pause Prease Press E";
			if (GameLib.isKeyOn("E")) {
				// ゲーム再開
				this.gameStatus = 2;
			}
			break;

		case 5: // ゲームオーバ画面
			this.message = "GAME OVER!! \rPrease Press ENTER";
			if (GameLib.isKeyOn("ENTER")) {
				// ゲーム再開
				this.gameStatus = 0;
			}
			break;

		case 6: // 魔法カーソル選択画面
			this.gameStatus = 6;
			this.message = "魔法を発動させたい場所を選択して ENTER \rやめる場合は L";

			// キー操作によってカーソルを移動させる
			if (GameLib.isKeyOn("W")) {
				// カーソルを上に移動
				this.field.getCursor().moveUp();
			} else if (GameLib.isKeyOn("X")) {
				// カーソルを下に移動
				this.field.getCursor().moveDown();
			} else if (GameLib.isKeyOn("A")) {
				// カーソルを左に移動
				this.field.getCursor().moveLeft();
			} else if (GameLib.isKeyOn("D")) {
				// カーソルを右に移動
				this.field.getCursor().moveRight();
			}

			if (GameLib.isKeyOn("ENTER")) {
				// 魔法発動
				this.field.deletePanel();
				this.heroAnimeCount = 0;
				this.heroAnimeEnd = false;
				this.gameStatus = 7;
				this.heroImage.init();

			} else if (GameLib.isKeyOn("L")) {
				// 魔法発動解除
				this.gameStatus = 2;
			}
			break;

		case 7: // 魔法発動画面（アニメーション）
			// 主人公さんの魔法モーション終了まで待つ
			/*
			if (this.heroAnimeEnd) {
				this.heroAnimeEnd = false;
				this.heroAnimeCount = 0;
				this.field.movePanel();
				this.gameStatus = 2;
			}
			*/

			if (this.heroImage.isEnd()) {
				this.heroAnimeEnd = false;
				this.heroAnimeCount = 0;
				this.field.movePanel();
				this.gameStatus = 2;
			}
			break;

		case 8: // ミノが１行削除された
			// 揃っている行があるかチェック。揃っていた場合その行のパネルを削除する
			// 主人公さんのモーション表示が終わるまで待つ
			// （TODO　6/14）削除された行にエフェクトを追加する
			if (this.heroAnimeEnd) {
				this.field.moveRow();
				this.gameStatus = 3;
			}
			break;
		}

		this.show();
	}

	// 画面表示
	private void show() {
		GraphicsContext canvas = GameLib.getGC();
		this.showBackground(canvas);
		this.field.show(canvas);

		// 魔法発動時のみカーソルを表示させる
		if (this.gameStatus == 6) {
			this.field.getCursor().show(canvas);
		}

		this.showNextMino(canvas);
		this.showMessage(canvas);
		this.showScore(canvas);
		this.showLevel(canvas);
		this.showMagic(canvas);
		this.showHero(canvas);
	}

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

	// 主人公さん表示
	// （TODO）キー＾操作時にキャラクタをアニメーションさせる
	private void showHero(GraphicsContext canvas) {
		// 背景の表示
		canvas.drawImage(TetrisImage.haikeiHeroImg, heroX, heroY, (heroH / 3) * 4, heroH);

		// 主人公さんの表示切替
		switch(this.gameStatus) {
		case 1: // スタート画面（繰り返し）
			/*
			if (this.heroAnimeCount >= TetrisImage.heroAnime_1().length) {
				this.heroAnimeCount = 0;
			}
			canvas.drawImage(TetrisImage.heroAnime_1()[this.heroAnimeCount], heroX + 40, heroY, heroW, heroH);
			this.heroAnimeCount++;
			*/
			canvas.drawImage(this.heroImage.heroAnime(), heroX + 40, heroY, heroW, heroH);
			break;

		case 4: // ポーズ（繰り返し）
			if (this.heroAnimeCount >= TetrisImage.heroAnime_1().length) {
				this.heroAnimeCount = 0;
			}
			canvas.drawImage(TetrisImage.heroAnime_1()[this.heroAnimeCount], heroX + 40, heroY, heroW, heroH);
			this.heroAnimeCount++;
			break;
		case 8: // ブロックが１行揃った
			canvas.drawImage(TetrisImage.heroAnime_9()[this.heroAnimeCount], heroX + 40, heroY, heroW, heroH);
			this.heroAnimeCount++;
			if (this.heroAnimeCount >= TetrisImage.heroAnime_9().length) {
				this.heroAnimeEnd = true; // モーションの表示が終わった
			}
			break;

		case 5: // ゲームオーバー
			canvas.drawImage(TetrisImage.heroAnime_6()[this.heroAnimeCount], heroX + 40, heroY, heroW, heroH);
			this.heroAnimeCount++;
			if (this.heroAnimeCount >= TetrisImage.heroAnime_6().length) {
				this.heroAnimeCount = TetrisImage.heroAnime_6().length - 1;
			}
			break;
		case 6: // 魔法準備中（繰り返し）
			if (this.heroAnimeCount >= TetrisImage.heroAnime_8().length) {
				this.heroAnimeCount = 0;
			}
			canvas.drawImage(TetrisImage.heroAnime_8()[this.heroAnimeCount], heroX + 40, heroY, heroW, heroH);
			this.heroAnimeCount++;
			break;

		case 7: // 魔法発動モーション
			/*
			canvas.drawImage(TetrisImage.heroAnime_4()[this.heroAnimeCount], heroX + 40, heroY, heroW, heroH);
			this.heroAnimeCount++;
			if (this.heroAnimeCount >= TetrisImage.heroAnime_4().length) {
				this.heroAnimeEnd = true; // モーションの表示が終わった
			}
			*/
			canvas.drawImage(heroImage.heroAnime_4(), heroX + 40, heroY, heroW, heroH);

			break;

		default: // 通常モーション（繰り返し）
			if (this.heroAnimeCount >= TetrisImage.heroAnime_7().length) {
				this.heroAnimeCount = 0;
			}
			canvas.drawImage(TetrisImage.heroAnime_7()[this.heroAnimeCount], heroX + 40, heroY, heroW, heroH);
			this.heroAnimeCount++;

			break;
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

	// 魔法のストック表示
	private void showMagic(GraphicsContext canvas) {
		canvas.setFill(Color.BLACK);
		canvas.fillRect(magicX, magicY, magicW, magicH);
		//canvas.drawImage(TetrisImage.flameAnime(), magicX + (Panel.panelW() * 1), magicY, 32, 32);
		canvas.drawImage(this.flameImage_1.flameAnime(), magicX + (Panel.panelW() * 1), magicY, 32, 32);
		canvas.drawImage(this.flameImage_2.flameAnime(), magicX + (Panel.panelW() * 3), magicY, 32, 32);
		canvas.drawImage(this.flameImage_3.flameAnime(), magicX + (Panel.panelW() * 5), magicY, 32, 32);
	}
}