package audioplayer;

/**
 *
 * @author axel
 */
public interface AudioPlayer {
    
    public void play();
    public void playFrom(double d);
//    public void playSegment(double begin, double end);
    public void stop();
    public void pause();
    public double getFileDuration();
}
