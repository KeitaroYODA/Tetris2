package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Magic {

	// 表示位置
	private final double magicX = Panel.panelW() * 25;
	private final double magicY = Panel.panelH() * 7;
	private final double magicW = Panel.panelW() * 7;
	private final double magicH = Panel.panelH() * 2;

	// 炎画像
	private TetrisImage flameImage_1 = new TetrisImage();
	private TetrisImage flameImage_2 = new TetrisImage();
	private TetrisImage flameImage_3 = new TetrisImage();

	private static Magic magic = null;
	private int magicNum = 3; // 魔法使用可能回数

	private Magic() {
		// new によるインスタンス化を許可しない
	}

	public static Magic getInstance() {
		if (magic == null) {
			magic = new Magic();
		}
		return magic;
	}

	// 魔法のストック表示
	public void show(GraphicsContext canvas) {
		canvas.setFill(Color.BLACK);
		canvas.fillRect(magicX, magicY, magicW, magicH);

		if (this.magicNum > 0) {
			canvas.drawImage(this.flameImage_1.starAnime(), magicX + (Panel.panelW() * 1), magicY, 32, 32);
		}
		if (this.magicNum > 1) {
			canvas.drawImage(this.flameImage_2.flameAnime(), magicX + (Panel.panelW() * 3), magicY, 32, 32);
		}
		if (this.magicNum > 2) {
			canvas.drawImage(this.flameImage_3.flameAnime(), magicX + (Panel.panelW() * 5), magicY, 32, 32);
		}
	}

	// 魔法残回数を返す
	public int getMagicNum() {
		return this.magicNum;
	}

	public void setMagicNum(int magicNum) {
		this.magicNum = magicNum;
	}
}