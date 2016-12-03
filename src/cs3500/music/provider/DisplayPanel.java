package cs3500.music.provider;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Arrays;

import javax.swing.JPanel;


/**
 * A panel for displaying notes.
 */
public class DisplayPanel extends JPanel {

  /**
   * Scaling for the view.
   */
  public static final double SCALING = 1;

  /**
   * The size of the grid in pixels.
   */
  public static final int SPACING = (int) (20 * SCALING);

  /**
   * The horizontal size of a beat in pixels.
   */
  public static final int BEAT_SIZE = (int) (18 * SCALING);

  public static final int LEFT_PAD_SIZE = (int) (10 * SCALING);

  public static final int TOP_PAD = (int) (15 * SCALING);

  /**
   * The frame that encapsulates the panel.
   */
  private GuiViewFrame frame;

  /**
   * Creates a new DisplayPanel.
   * @param frame The GuiViewFrame which will contain the Panel.
   */
  public DisplayPanel(GuiViewFrame frame) {
    super(new BorderLayout());
    this.frame = frame;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    FixedGrid grid = this.frame.getGrid();
    String[] headers = grid.getNoteHeaders();
    g.translate(0, TOP_PAD);

    g.drawLine(this.beatToXPosition(0),
            (int) Math.round(-SPACING * 0.25),
            this.beatToXPosition(this.frame.length + 1),
            (int) Math.round(-SPACING * 0.25));
    // draw the headers and horizontal grid lines
    for (int i = 0; i < headers.length; ++i) {
      int headerHeight = (int) Math.round(SPACING * (i + 0.5));
      g.drawString(headers[headers.length - i - 1], LEFT_PAD_SIZE, headerHeight);
      g.drawLine(this.beatToXPosition(0),
              (int) Math.round(headerHeight + SPACING * 0.25),
              beatToXPosition(this.frame.length + this.frame.getMeasureLength() * 4 + 1),
              (int) Math.round(headerHeight + SPACING * 0.25));
    }

    // draw measure lines and numbers
    for (int i = 0; i < (this.frame.length + this.frame.getMeasureLength())
            / this.frame.getMeasureLength() + 2; ++i) {
      g.drawLine(this.beatToXPosition(i * this.frame.getMeasureLength()),
              (int) Math.round(-SPACING * 0.25),
              this.beatToXPosition(i * this.frame.getMeasureLength()),
              (int) Math.round((headers.length - 0.25) * SPACING));
      g.drawString(Integer.toString(i * this.frame.getMeasureLength()),
              this.beatToXPosition(i * this.frame.getMeasureLength()) - 5,
              (int) Math.round((headers.length - 0.25) * SPACING) + 15);
    }

    //draw the playhead
    g.setColor(Color.RED);
    g.drawLine(this.beatToXPosition(this.frame.getPlayHeadPosition()),
            (int) Math.round(-SPACING * 0.25),
            this.beatToXPosition(this.frame.getPlayHeadPosition()),
            (int) Math.round((headers.length - 0.25) * SPACING));

    FixedGridMarker[][] markers = this.frame.getGrid().getContents();
    for (int beat = 0; beat < markers.length; ++beat) {
      for (int pitch = 0; pitch < markers[0].length; ++pitch) {
        switch (markers[beat][pitch]) {
          case HEAD:
            g.setColor(Color.BLACK);
            break;
          case SUSTAIN:
            g.setColor(Color.GREEN);
            break;
          default:
            continue;
        }
        g.fillRect(this.beatToXPosition(beat),
                ((int) Math.round((headers.length - 0.25) * SPACING)) - SPACING * (pitch + 1),
                BEAT_SIZE, SPACING);
      }
    }

  }


  @Override
  public Dimension getPreferredSize() {
    int range = this.frame.getGrid().getNoteHeaders().length;
    return new Dimension(beatToXPosition(this.frame.length + 1) + 10,
            (int) Math.round(DisplayPanel.SPACING * (range + 2) + DisplayPanel.TOP_PAD));
  }

  /**
   * Gets the x position in the graphics pane corresponding to the given beat.
   * @param beat The beat to get the position of.
   * @return The x position in the graphics pane corresponding to the given beat.
   */
  public int beatToXPosition(int beat) {
    return LEFT_PAD_SIZE * 5 + BEAT_SIZE * beat;
  }

  // xpos = lpsize * 5 + beatsize * beat
  // xpos - lpsize * 5 = beatsize * beat
  // (xpos - lpsize * 5) / beatsize = beat

  /**
   * Gets the x position in the graphics pane corresponding to the given beat.
   * @param beat The beat to get the position of.
   * @return The x position in the graphics pane corresponding to the given beat.
   */
  public int beatToXPosition(float beat) {
    return Math.round(LEFT_PAD_SIZE * 5 + BEAT_SIZE * beat);
  }

  /**
   * Gets the beat that corresponds with a given x position.
   * @param xposition The x position.
   * @return The beat corresponding to the x position.
   */
  public int xPositionToBeat(int xposition) {
    return (int) (xposition - LEFT_PAD_SIZE * 5) / BEAT_SIZE;
  }

  // Math.round((headers.length - 0.25) * SPACING) - SPACING * (pitch + 1) = ypos;
  // ypos - Math.round((headers.length - 0.25) * SPACING) = -SPACING * (pitch + 1)
  // ((ypos - Math.round((headers.length - 0.25) * SPACING)) / -SPACING) - 1 = pitch

  /**
   * Gets the pitch associated with the given y position.
   * @param yposition The y positon.
   * @return The pitch associated with the given y position.
   */
  public Pitch yPositionToPitch(float yposition) {
    FixedGrid grid = this.frame.getGrid();
    String[] headers = grid.getNoteHeaders();
    int pitch = (int) ((yposition - Math.round((headers.length - 0.25) * SPACING)) / -SPACING) + 1;
    return Pitch.fromString(headers[pitch]);
  }

  /**
   * Gets the y position associated with the given pitch.
   */
  public int pitchToYPosition(Pitch pitch) {
    String[] headers = this.frame.getGrid().getNoteHeaders();
    int pitchValue = Arrays.asList(headers).indexOf(pitch.toString()) - 1;

    return ((int) Math.round((headers.length - 0.25) * SPACING)) - SPACING * (pitchValue + 1);
  }

  /**
   * Gets the note corresponding to a mouse event received at the given x and y corrdinates.
   * @param x The x coordinate.
   * @param y The y coordinate.
   * @return The corresponding note.
   */
  public Note positionToNote(int x, int y) {
    Pitch pitch = this.yPositionToPitch(y);
    int beat = this.xPositionToBeat(x);
    return new Note(pitch, beat, 0, 1);
  }

}