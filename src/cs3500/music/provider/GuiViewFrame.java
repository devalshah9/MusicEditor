package cs3500.music.provider;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JScrollPane;

import cs3500.music.provider.fixedgrid.FixedGrid;
import cs3500.music.provider.note.Note;

/**
 * A skeleton Frame (i.e., a window) in Swing
 */
public class GuiViewFrame extends javax.swing.JFrame implements IMusicEditorGuiView {

  /**
   * The notes to initialize.
   */
  protected ArrayList<Note> notes;

  /**
   * The boundaries of the notes in the view.
   */
  protected ArrayList<NoteBounds> noteBounds;

  /**
   * The length of the piece to initialize.
   */
  protected int length;

  /**
   * The tempo of the piece to initialize or -1 if not specified.
   */
  protected int tempo;

  /**
   * The number of beats per measure to display the view with.
   */
  private int beatsPerMeasure;

  /**
   * The playhead position to display the view with.
   */
  private float playheadPosition;

  /**
   * The FixedGrid to use for displaying.
   */
  private FixedGrid grid = null;

  /**
   * The scroll pane to hold content.
   */
  private JScrollPane scrollPane;

  /**
   * The display panel for the view.
   */
  private DisplayPanel panel;

  /**
   * If the music is currently playing.
   */
  private boolean playing;

  /**
   * Creates new GuiView.
   */
  public GuiViewFrame() {
    this.notes = new ArrayList<Note>();
    this.noteBounds = new ArrayList<NoteBounds>();
    this.length = 0;
    this.tempo = -1;
    this.panel = new DisplayPanel(this);
    this.scrollPane = new JScrollPane(this.panel);
    this.playing = false;
    this.add(this.scrollPane);
    this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
  }

  @Override
  public void setTempo(int tempo) {
    this.tempo = tempo;
  }

  @Override
  public void setLength(int length) {
    this.length = length;
  }

  @Override
  public int getLength() {
    return this.length;
  }

  @Override
  public void setNotes(Collection<Note> notes) {
    this.notes.clear();
    for (Note note : notes) {
      this.notes.add(note);
    }
    this.grid = new FixedGrid(this.notes, this.length + 1);
    // Create the boundaries for each note
    this.noteBounds.clear();
    for (Note note : this.notes) {
      this.addNoteBound(new NoteBounds(note, this.panel));
    }
  }

  @Override
  public void addMouseEventHandler(IMouseEventHandler handler) {
    this.panel.addMouseListener(new GuiViewFrameMouseListener(handler, this));
  }

  @Override
  public void setMeasureLength(int beats) {
    this.beatsPerMeasure = beats;
  }

  @Override
  public void setPlayHeadPosition(float beat) {
    this.playheadPosition = beat;
    int playHeadXPosition = this.panel.beatToXPosition(beat);
    if (this.playing) {
      if (playHeadXPosition > this.scrollPane.getHorizontalScrollBar().getValue()
              + 1000) {
        this.scrollPane.getHorizontalScrollBar().setValue(playHeadXPosition
                - DisplayPanel.BEAT_SIZE * 4);
      }
    }
  }

  @Override
  public float getPlayHeadPosition() {
    return this.playheadPosition;
  }

  @Override
  public void initialize() {
    this.setVisible(true);
    this.pack();
  }

  @Override
  public void scrollToEnd() {
    int max = this.scrollPane.getHorizontalScrollBar().getMaximum();
    this.scrollPane.getHorizontalScrollBar().setValue(max);
  }

  @Override
  public void start() {
    this.playing = true;
  }

  @Override
  public void stop() {
    this.playing = false;
  }

  @Override
  public void scrollToStart() {
    this.scrollPane.getHorizontalScrollBar().setValue(0);
  }

  @Override
  public void scrollLeft() {
    int increment = this.scrollPane.getHorizontalScrollBar().getValue()
            - this.scrollPane.getHorizontalScrollBar().getBlockIncrement();
    this.scrollPane.getHorizontalScrollBar().setValue(increment);
  }

  @Override
  public void scrollRight() {
    int increment = this.scrollPane.getHorizontalScrollBar().getValue()
            + this.scrollPane.getHorizontalScrollBar().getBlockIncrement();
    this.scrollPane.getHorizontalScrollBar().setValue(increment);
  }

  @Override
  public void beatLeft() {
    float current = playheadPosition;
    this.setPlayHeadPosition(Math.round(current - 1));
  }

  @Override
  public void beatRight() {
    float current = playheadPosition;
    this.setPlayHeadPosition(Math.round(current + 1));
  }

  /**
   * The FixedGrid associated with the GuiViewFrame.
   * @return The FixedGrid containing what is being drawn.
   */
  public FixedGrid getGrid() {
    return this.grid;
  }


  @Override
  public Dimension getPreferredSize() {
    int range = this.grid.getNoteHeaders().length;
    return new Dimension(1000, (int) Math.round(DisplayPanel.SPACING * (range + 2)
      + DisplayPanel.TOP_PAD));
  }

  /**
   * Gets the note corresponding to the given mouse position.
   * @param x The x position.
   * @param y The y position.
   * @return The note (with 0 duration) corresponding to the mouse position.
   */
  public Note noteFromEmptyMousePosition(int x, int y) {
    return this.panel.positionToNote(x, y);
  }

  /**
   * Gets the length of a measure in beats.
   * @return The length of a measure in beats.
   */
  protected int getMeasureLength() {
    return this.beatsPerMeasure;
  }

  /**
   * Gets the collection of note boundaries.
   * @return The collection of note boundaries.
   */
  public Collection<NoteBounds> getNoteBounds() {
    return this.noteBounds;
  }

  /**
   * Adds a note boundary to the collection of note boundaries.
   * @param nb note boundary to add.
   */
  public void addNoteBound(NoteBounds nb) {
    this.noteBounds.add(nb);
  }

  /**
   * The DisplayPanel for the view.
   * @return The view's display panel.
   */
  public DisplayPanel getPanel() {
    return this.panel;
  }

}
