package cs3500.music.provider;

import java.io.IOException;

import cs3500.music.provider.FixedGrid;

/**
 * A textual view for the music editor.
 */
public class TextualView extends ANonGuiView {

  private Appendable appendable;

  public TextualView(Appendable appendable) {
    this.appendable = appendable;
  }

  @Override
  public void initialize() {
    try {
      this.appendable.append(new FixedGrid(this.notes, this.length + 1).toString());
    } catch (IOException e) {
      System.err.println("Unexpected IOException occured!");
      System.err.println(e.getMessage());
    }
  }

}
