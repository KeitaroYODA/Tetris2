package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

final class MStock {

	// メラゲージ画像
	private TetrisImage flameImage_1 = new TetrisImage();
	private TetrisImage flameImage_2 = new TetrisImage();
	private TetrisImage flameImage_3 = new TetrisImage();
	// イオゲージ画像
	private TetrisImage ioImage_1 = new TetrisImage();
	private TetrisImage ioImage_2 = new TetrisImage();
	private TetrisImage ioImage_3 = new TetrisImage();

	private static MStock mStock = null;
	private int magicNumMera; // 魔法（メラ）使用可能回数
	private int magicNumIo; // 魔法（イオ）使用可能回数

	private MStock() {}

	public void init() {
		magicNumMera = Conf.MAX_MSTOCK_MERA;
		magicNumIo = Conf.MAX_MSTOCK_IO;
	}

	public static MStock getInstance() {
		if (mStock == null) {
			mStock = new MStock();
			mStock.init();
		}
		return mStock;
	}

	// 魔法のストック表示
	public void show(GraphicsContext canvas) {
		canvas.setFill(Color.BLACK);
		canvas.fillRect(Conf.MSTOCK_X, Conf.MSTOCK_Y, Conf.MSTOCK_W, Conf.MSTOCK_H);

		// メラゲージ
		if (this.magicNumMera > 0) {
			canvas.drawImage(this.flameImage_1.flameAnime(), Conf.MSTOCK_X + (Conf.PANEL_W * 1), Conf.MSTOCK_Y + (Conf.PANEL_H * 0.5), Conf.PANEL_W, Conf.PANEL_H);
		}
		if (this.magicNumMera > 1) {
			canvas.drawImage(this.flameImage_2.flameAnime(), Conf.MSTOCK_X + (Conf.PANEL_W * 3), Conf.MSTOCK_Y + (Conf.PANEL_H * 0.5), Conf.PANEL_W, Conf.PANEL_H);
		}
		if (this.magicNumMera > 2) {
			canvas.drawImage(this.flameImage_3.flameAnime(), Conf.MSTOCK_X + (Conf.PANEL_W * 5), Conf.MSTOCK_Y + (Conf.PANEL_H * 0.5), Conf.PANEL_W, Conf.PANEL_H);
		}

		// イオゲージ
		if (this.magicNumIo > 0) {
			canvas.drawImage(this.ioImage_1.ioAnime(), Conf.MSTOCK_X + (Conf.PANEL_W * 1), Conf.MSTOCK_Y + (Conf.PANEL_H * 2), Conf.PANEL_W, Conf.PANEL_H);
		}
		if (this.magicNumIo > 1) {
			canvas.drawImage(this.ioImage_2.ioAnime(), Conf.MSTOCK_X + (Conf.PANEL_W * 3), Conf.MSTOCK_Y + (Conf.PANEL_H * 2), Conf.PANEL_W, Conf.PANEL_H);
		}
		if (this.magicNumIo > 2) {
			canvas.drawImage(this.ioImage_3.ioAnime(), Conf.MSTOCK_X + (Conf.PANEL_W * 5), Conf.MSTOCK_Y + (Conf.PANEL_H * 2), Conf.PANEL_W, Conf.PANEL_H);
		}
	}

	public int getMagicNumMera() {
		return this.magicNumMera;
	}

	public void setMagicNumMera(int magicNum) {
		this.magicNumMera = magicNum;
	}

	public int getMagicNumIo() {
		return this.magicNumIo;
	}

	public void setMagicNumIo(int magicNum) {
		this.magicNumIo = magicNum;
	}
}