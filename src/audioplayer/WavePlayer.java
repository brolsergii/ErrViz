package audioplayer;

/**
 * @author axel
 * @author Sergii
 * based on fobec's code : http://www.fobec.com/CMS/java/class/jouer-son-musique-format-wav_1043.html
 */
public class WavePlayer implements AudioPlayer {

  private WavePlayerThread thread = null;
  private String wavefile = "";

  public WavePlayer(String filename) {
    wavefile = filename;
    this.thread = new WavePlayerThread(this.wavefile);
    playing = false;
  }

  /**
   * Lancer la lecture
   */
  @Override
  public void play() {
    if (this.thread != null) {
      //this.thread = new WavePlayerThread(this.wavefile);
      playing = true;
      this.thread.start();
    } else {
      this.thread = new WavePlayerThread(this.wavefile);
      playing = true;
      this.thread.start();
    }
  }

  @Override
  public void playFrom(double d) {
    if (this.thread != null) {
      //this.thread = new WavePlayerThread(this.wavefile);
      this.thread.playFrom(d);
      playing = true;
      this.thread.start();
    } else {
      this.thread = new WavePlayerThread(this.wavefile);
      this.thread.playFrom(d);
      playing = true;
      this.thread.start();
    }
  }

  /**
   * Mettre en pause ou reprendre la lecture
   */
  @Override
  public void pause() {
    if (this.thread != null) {
      playing = false;
      this.thread.pause();
    }
  }

  /**
   * Abandonner la lecture
   */
  @Override
  public void stop() {
    if (this.thread != null) {
      this.thread.cancel();
      playing = false;
      //will call onTerminated
      onTerminated();
    }
  }

  /**
   * Thread event: fin du thread de lecture
   */
  public void onTerminated() {
      //AH 21/02/2013 See with logger instead
//    System.out.println("end");
    this.thread = null;
    // pour relancer la lecture
    // play();
  }

  @Override
  public double getFileDuration() {
    double duration = 0;
    if (this.thread != null) {
      //System.out.println("ah ah\n");
      duration = this.thread.getFileDuration();
    }
    return duration;
  }

  public boolean isPlaying() {
    return playing;
  }
  private boolean playing;
}
