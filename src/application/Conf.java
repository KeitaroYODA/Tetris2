package application;

/**
 * 設定定義クラス
 *
 */
public final class Conf {
	// メッセージ関連 ///////////////////////////////////////
	// メイン画面上に表示されるメッセージ
	public static final String MESSAGE_NONE = "";
	public static final String MESSAGE_MENU = "SMILE  TETRIS";
	public static final String MESSAGE_PAUSE = "PAUSE...";
	public static final String MESSAGE_GAMEOVER = "GAME OVER!!";
	// 画面右下に表示されるインフォメーション
	public static final String INFO_NONE = "";
	public static final String INFO_MENU = "ENTER : ゲーム開始";
	public static final String INFO_MAIN = "A : ミノ左移動\rD : ミノ右移動\rJ : ミノ左回転\rL : ミノ右回転\rX : ミノ落下\rM : 魔法メラ発動\rI : 魔法イオ発動\rP : ポーズ";
	public static final String INFO_PAUSE = "E : ゲームに戻る";
	public static final String INFO_MAGIC_IO_SELECT = "ENTER : 魔法発動\rL : 魔法中止";
	public static final String INFO_MAGIC_MERA_SELECT = "W : 上移動\rX : 下移動\rA : 左移動\rD : 右移動\rENTER : 魔法発動\rL : 魔法中止";
	public static final String INFO_GAMEOVER = "ENTER : リスタート";
	public static final String INFO_RENSA = "Please Wait.....";

	// ゲーム設定 ///////////////////////////////////////
	public static final int DOWN_WAIT = 5; // 魔法後のブロック落下待ち時間（値が少ない程速く落ちる）
	public static final int MAX_SCORE = 100000; // 最大スコア
	public static final int MAX_LEVEL = 10; // 最大レベル
	public static final int LEVELUP_SCORE = 3000; // レベルアップ条件スコア
	public static final long WAIT_NANOTIME = 100000000; // フレーム間引き用設定
	public static final int WAIT_COLISION = 10; // ミノ接地後の操作可能時間（値が少ない程余裕がなくなる）
	public static final int MAX_MSTOCK_IO = 3; // 魔法（イオ）最大ストック可能数
	public static final int MAX_MSTOCK_MERA = 3; // 魔法（メラ）最大ストック可能数
	// 魔法（メラ）用魔法陣の初期表示位置
	public static final int MAGIC_CIRCLE_X = 7;
	public static final int MAGIC_CIRCLE_Y = 7;
	// ミノの初期表示位置
	public static final int MINO_X = 6;
	public static final int MINO_Y = 0;
	// 配列サイズ
	// メインフィールド
	public static final int FIELD_ROW = 18;
	public static final int FIELD_COL = 16;
	// メラ魔法陣
	public static final int MAGIC_ROW = 3;
	public static final int MAGIC_COL = 3;

	// 画面レイアウト ///////////////////////////////////////
	// ミノ構成するパネルサイズ
	public static final double PANEL_W = GameLib.width() / 33;
	public static final double PANEL_H = GameLib.height() / 19;
	// メッセージ画像
	public static final double MESSAGE_X = PANEL_W * 9;
	public static final double MESSAGE_Y = PANEL_H * 5;
	public static final double MESSAGE_W = PANEL_W * 2;
	public static final double MESSAGE_H = PANEL_H * 3;
	// メイン画面表示
	public static final double FIELD_X = PANEL_W;
	public static final double FIELD_Y = 0;
	// 得点表示
	public static final double SCORE_X = PANEL_W * 18;
	public static final double SCORE_Y = PANEL_H * 1;
	public static final double SCORE_W = PANEL_W * 14;
	public static final double SCORE_H = PANEL_H * 2;
	// 次のミノ表示
	public static final double NMINO_X = PANEL_W * 18;
	public static final double NMINO_Y = PANEL_H * 4;
	public static final double NMINO_W = PANEL_W * 6;
	public static final double NMINO_H = PANEL_H * 7;
	// レベル表示
	public static final double LEVEL_X = PANEL_W * 25;
	public static final double LEVEL_Y = PANEL_H * 4;
	public static final double LEVEL_W = PANEL_W * 7;
	public static final double LEVEL_H = PANEL_H * 2;
	// 主人公さん
	public static final double HERO_X = PANEL_W * 18;
	public static final double HERO_Y = PANEL_H * 12;
	public static final double HERO_W = PANEL_W * 6;
	public static final double HERO_H = PANEL_H * 6;
	// 説明表示
	public static final double INFO_X = PANEL_W * 27;
	public static final double INFO_Y = PANEL_H * 12;
	public static final double INFO_W = PANEL_W * 5;
	public static final double INFO_H = PANEL_H * 6;
	// 魔法ストック
	public static final double MSTOCK_X = PANEL_W * 25;
	public static final double MSTOCK_Y = PANEL_H * 7;
	public static final double MSTOCK_W = PANEL_W * 7;
	public static final double MSTOCK_H = PANEL_H * 4;

	private Conf() {
	}
}