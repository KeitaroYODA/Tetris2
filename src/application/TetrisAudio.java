package application;

import java.io.File;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

final class TetrisAudio {

	// 効果音
	private static AudioClip decision26;
	private static AudioClip decision13;
	private static AudioClip decision4;
	private static AudioClip magicAudio;
	private static AudioClip magicCharge1;
	private static AudioClip puyon1;
	private static AudioClip correct1;
	private static AudioClip switch1;
	private static AudioClip brick1;
	private static AudioClip menu1;
	private static AudioClip sousou3;
	private static AudioClip levelup1;
	private static AudioClip magicelectron4;

	// BGM
	private static MediaPlayer bgm1;

	static {
		Media m = new Media(new File("res/audio/bgm1.mp3").toURI().toString());
		bgm1 = new MediaPlayer(m);
		bgm1.setCycleCount(99);

		// 音ずれ対策のため1回無音で再生。あまり関係ないかも
		levelup1 = new AudioClip(new File("res/audio/trumpet1.mp3").toURI().toString());
		levelup1.setVolume(0);
		levelup1.play();
		levelup1.setVolume(1.0);

		sousou3 = new AudioClip(new File("res/audio/sousou3.mp3").toURI().toString());
		sousou3.setVolume(0);
		sousou3.play();
		sousou3.setVolume(1.0);

		menu1 = new AudioClip(new File("res/audio/menu1.mp3").toURI().toString());
		menu1.setVolume(0);
		menu1.play();
		menu1.setVolume(1.0);

		brick1 = new AudioClip(new File("res/audio/brick1.mp3").toURI().toString());
		brick1.setVolume(0);
		brick1.play();
		brick1.setVolume(1.0);

		switch1 = new AudioClip(new File("res/audio/switch1.mp3").toURI().toString());
		switch1.setVolume(0);
		switch1.play();
		switch1.setVolume(1.0);

		correct1 = new AudioClip(new File("res/audio/correct1.mp3").toURI().toString());
		correct1.setVolume(0);
		correct1.play();
		correct1.setVolume(1.0);

		puyon1 = new AudioClip(new File("res/audio/puyon1.mp3").toURI().toString());
		puyon1.setVolume(0);
		puyon1.play();
		puyon1.setVolume(1.0);

		decision26 = new AudioClip(new File("res/audio/decision26.mp3").toURI().toString());
		decision26.setVolume(0);
		decision26.play();
		decision26.setVolume(1.0);

		decision13 = new AudioClip(new File("res/audio/decision13.mp3").toURI().toString());
		decision13.setVolume(0);
		decision13.play();
		decision13.setVolume(1.0);

		decision4 = new AudioClip(new File("res/audio/decision4.mp3").toURI().toString());
		decision4.setVolume(0);
		decision4.play();
		decision4.setVolume(1.0);

		magicAudio = new AudioClip(new File("res/audio/magic-flame2.mp3").toURI().toString());
		magicAudio.setVolume(0);
		magicAudio.play();
		magicAudio.setVolume(1.0);

		magicelectron4 = new AudioClip(new File("res/audio/magic-electron4.mp3").toURI().toString());
		magicelectron4.setVolume(0);
		magicelectron4.play();
		magicelectron4.setVolume(1.0);

		magicCharge1 = new AudioClip(new File("res/audio/magic-charge1.mp3").toURI().toString());
		magicCharge1.setVolume(0);
		magicCharge1.play();
		magicCharge1.setVolume(1.0);
	}

	// BGM
	public static void bgm() {
		bgm1.setRate(1.0);
		bgm1.play();
	}

	// BGMの再生速度の変更　1.0～8.0
	public static void setBgmRate(double value) {
		bgm1.setRate(value);
	}

	// ゲームオーバ
	public static void gameOver() {
		bgm1.stop();
		sousou3.play();
	}

	// レベルアップ
	public static void levelUp() {
		levelup1.play();
	}

	// スタート
	public static void start() {
		sousou3.stop();
		menu1.play();
	}

	// ミノ接地
	public static void colision() {
		switch1.play();
	}

	// ポーズ
	public static void pause() {
		bgm1.pause();
		decision26.play();
	}

	// ポーズ解除
	public static void replay() {
		bgm1.play();
	}

	// ミノ削除
	public static void success() {
		correct1.play();
	}

	// ミノ回転
	public static void turn() {
		puyon1.play();
	}

	// 魔法炸裂（メラ）
	public static void mera() {
		magicAudio.play();
	}

	// 魔法炸裂（イオ）
	public static void io() {
		magicelectron4.play();
	}

	// 魔法チャージ中
	public static void charge() {
		magicCharge1.play();
	}

	private TetrisAudio() {}
}