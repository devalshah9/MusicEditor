package cs3500.music.view;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.*;

import cs3500.music.commons.Note;
import cs3500.music.model.IViewModel;

/**
 * Panel on the top for the beats of the song.
 */
public class BeatsPanel extends JPanel {

  private IViewModel viewModel;
  JPanel p = new JPanel();

  public BeatsPanel(IViewModel viewModel) {
    super();
    this.viewModel = viewModel;
    p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
  }


  @Override
  public void paintComponent(Graphics g) {
    // Handle the default painting
    super.paintComponent(g);

    Graphics2D gimg = (Graphics2D) g;

    gimg.setColor(Color.BLACK);

    AffineTransform originalTransform = gimg.getTransform();
    gimg.translate(0, this.getPreferredSize().getHeight());
    gimg.scale(1, 1);

    int measureLength = this.viewModel.getMeasureLength();
    int endBeat = this.viewModel.getEndBeat();


    int height = (int) (this.getPreferredSize().getHeight() * 0.90);
    int widthScale = 30;
    int boxWidth =  measureLength * widthScale;
    int remainder = endBeat % measureLength;

    // Draws the text of beat numbers
    for (int n = 0; n <= endBeat/measureLength + remainder; n++) {
      gimg.drawString(Integer.toString(n * measureLength), (n * boxWidth + 25), 0);
    }

    gimg.setTransform(originalTransform);
  }
}


