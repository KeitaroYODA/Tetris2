package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Magic {

	// 表示位置
	private final double magicX = Panel.panelW() * 25;
	private final double magicY = Panel.panelH() * 7;
	private final double magicW = Panel.panelW() * 7;
	private final double magicH = Panel.panelH() * 4;

	// 炎画像
	private TetrisImage flameImage_1 = new TetrisImage();
	private TetrisImage flameImage_2 = new TetrisImage();
	private TetrisImage flameImage_3 = new TetrisImage();
	// イオ画像
	private TetrisImage ioImage_1 = new TetrisImage();
	private TetrisImage ioImage_2 = new TetrisImage();
	private TetrisImage ioImage_3 = new TetrisImage();

	private static Magic magic = null;

	private int magicNum_1; // 魔法使用可能回数
	private int magicNum_2;

	private Magic() {
		// new によるインスタンス化を許可しない
	}

	public void init() {
		magicNum_1 = 3; // 魔法使用可能回数
		magicNum_2 = 3;
	}

	public static Magic getInstance() {
		if (magic == null) {
			magic = new Magic();
			magic.init();
		}
		return magic;
	}

	// 魔法のストック表示
	public void show(GraphicsContext canvas) {
		canvas.setFill(Color.BLACK);
		canvas.fillRect(magicX, magicY, magicW, magicH);

		if (this.magicNum_1 > 0) {
			canvas.drawImage(this.flameImage_1.flameAnime(), magicX + (Panel.panelW() * 1), magicY + (Panel.panelH() * 0.5), Panel.panelW(), Panel.panelH());
		}
		if (this.magicNum_1 > 1) {
			canvas.drawImage(this.flameImage_2.flameAnime(), magicX + (Panel.panelW() * 3), magicY + (Panel.panelH() * 0.5), Panel.panelW(), Panel.panelH());
		}
		if (this.magicNum_1 > 2) {
			canvas.drawImage(this.flameImage_3.flameAnime(), magicX + (Panel.panelW() * 5), magicY + (Panel.panelH() * 0.5), Panel.panelW(), Panel.panelH());
		}

		if (this.magicNum_2 > 0) {
			canvas.drawImage(this.ioImage_1.ioAnime(), magicX + (Panel.panelW() * 1), magicY + (Panel.panelH() * 2), Panel.panelW(), Panel.panelH());
		}
		if (this.magicNum_2 > 1) {
			canvas.drawImage(this.ioImage_2.ioAnime(), magicX + (Panel.panelW() * 3), magicY + (Panel.panelH() * 2), Panel.panelW(), Panel.panelH());
		}
		if (this.magicNum_2 > 2) {
			canvas.drawImage(this.ioImage_3.ioAnime(), magicX + (Panel.panelW() * 5), magicY + (Panel.panelH() * 2), Panel.panelW(), Panel.panelH());
		}
	}

	// 魔法残回数を返す
	public int getMagicNum() {
		return this.magicNum_1;
	}

	public void setMagicNum(int magicNum) {
		this.magicNum_1 = magicNum;
	}
}