package controller;

import audioplayer.WavePlayer;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import view.MainForm;

/**
 * @author Sergii
 */
public class WAV {

  private WAV() {
    currentTimeInSec = 0;
  }
  private static WAV instance;

  public static WAV getInstance() {
    if (instance == null) {
      instance = new WAV();
    }
    return instance;
  }

  public boolean isLoaded() {
    if (player != null) {
      return true;
    }
    return false;
  }

  public void setMainForm(MainForm mf) {
    mainForm = mf;
  }

  public void load(String file) {
    player = new WavePlayer(file);
    overralTimeInSec = player.getFileDuration();
    //overralTimeInSec = 1000;
  }

  public WavePlayer getPlayer() {
    return player;
  }

  public double getCurrentTimeInSec() {
    return currentTimeInSec;
  }

  public void setCurrentTimeInSec(double time) {
    currentTimeInSec = time;
    if (mainForm != null)
      mainForm.setTimerLabel(getCurrentTimeInSec(), getOverralLength());
  }

  public double getOverralLength() {
    return overralTimeInSec;
  }

  public void startCounter() {
    if (timer != null) {
      timer.cancel();
    }
    timer = new Timer();
    timer.scheduleAtFixedRate(new TimerTask() {

      @Override
      public void run() {
        setCurrentTimeInSec(getCurrentTimeInSec() + 1);
        //System.out.println(currentTimeInSec);
      }
    }, new Date(), 1000);
  }

  public void stopCounter() {
    timer.cancel();
  }
  private Timer timer;
  private double currentTimeInSec;
  private double overralTimeInSec;
  private WavePlayer player;
  private MainForm mainForm;
}
