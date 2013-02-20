package view;

import controller.WAV;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import javax.swing.JLabel;

public class JAudioLabel extends JLabel {

  public JAudioLabel(MainForm mf) {
    this.mf = mf;
  }

  @Override
  public void paint(Graphics g) {
    super.paint(g);
    try {
      int pos = WAV.getInstance().getWavFile().getPositionByTime((int) WAV.getInstance().getCurrentTimeInSec());
      Graphics2D gr = (Graphics2D) g;
      gr.setPaint(Color.RED);
          if (pos <= this.getParent().getWidth() / 2 - 10) {
            gr.draw(new Line2D.Double(pos, 0, pos, WAV.getInstance().getWavFile().getImageHeigh()));
          } else if (pos <= this.getWidth() - this.getParent().getWidth() / 2 + 20) {
            gr.draw(new Line2D.Double(pos, 0, pos, WAV.getInstance().getWavFile().getImageHeigh()));
          } else {
            gr.draw(new Line2D.Double(pos, 0, pos, WAV.getInstance().getWavFile().getImageHeigh()));
          }  
    } catch (Exception ex) {
      System.err.println(ex);
    }
  }
  
  private MainForm mf;
}
