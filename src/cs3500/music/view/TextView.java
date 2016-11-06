package cs3500.music.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import cs3500.music.model.IMusicEditor;
import cs3500.music.model.IViewModel;
import cs3500.music.model.MusicSheet;
import cs3500.music.commons.*;

/**
 * A view for the text representation of a Song.
 */
public class TextView implements IMusicView {

  private IViewModel viewModel;
  Appendable result;

  public TextView(IViewModel viewModel, Appendable ap) {
    this.viewModel = viewModel;
    this.result = ap;
  }

  @Override
  public void renderSong(IViewModel model, int tempo) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Invalid View Model!");
    }
    System.out.println(this.renderNotes(model));
  }

  /**
   * Creates a String that starts with all of the present notes on the first line.
   * Following by as many beats as required to reach the farthest beat, padded with
   * spaces. The beginnings of notes are marked by X, and a sustain is marked by |. A rest is
   * simply a space. If there are no notes in the piece, the message "No notes to present"
   * will be returned instead.
   * @return the string of notes
   */
  private String renderNotes(IViewModel viewModel) {
    ArrayList<Note> newNotes = new ArrayList<Note>();
    for (Octave oct : Octave.values()) {
      for (Pitch pit : Pitch.values()) {
        if(oct.equals(Octave.TEN) && pit.equals(Pitch.G)) {
          break;
        }
        newNotes.add(new Note(pit, oct, false, 0, 0));
      }
    }
    StringBuilder result = new StringBuilder("");
    if (viewModel.getEndBeat() == -1 || viewModel.getHighestNote() == null
            || viewModel.getLowestNote() == null) {
      return "No notes to present.";
    }

    int beginIndex = newNotes.indexOf(viewModel.getLowestNote());
    int endIndex = newNotes.indexOf(viewModel.getHighestNote());
    List<Note> printNotes = newNotes.subList(beginIndex, endIndex + 1);
    result.append("\n");
    ArrayList<Integer> beatNumbers = new ArrayList<Integer>();
    int beatNumberColumnLength = String.valueOf(viewModel.getEndBeat()).toString().length();
    for (int n = 0; n < beatNumberColumnLength; n++) {
      result.append(" ");
    }
    result.append("  ");
    for (int n = 0; n < printNotes.size(); n++) {
      result.append(printNotes.get(n).toString());
      for (int i = printNotes.get(n).toString().length(); i < 5; i++) {
        result.append(" ");
      }
    }
    result.append("\n");
    for (int n = 0; n < viewModel.getEndBeat(); n++) {
      beatNumbers.add(n);
    }
    for (int n = 0; n < beatNumbers.size(); n++) {
      Integer p = beatNumbers.get(n);
      for (int i = beatNumberColumnLength; i > p.toString().length(); i--) {
        result.append(" ");
      }
      result.append((beatNumbers.get(n)));
      result.append("  ");
      if (!(viewModel.getNotes().containsKey(beatNumbers.get(n)))) {
        for (int j = 0; j < printNotes.size(); j++) {
          result.append("     ");
        }
      } else {
        for (int j = 0; j < printNotes.size(); j++) {
          if (viewModel.getNotes().get(beatNumbers.get(n)).contains(printNotes.get(j))) {
            int indexNote = viewModel.getNotes().get(beatNumbers.get(n)).indexOf(printNotes.get(j));
            if (viewModel.getNotes().get(beatNumbers.get(n)).get(indexNote).getbeginningOfNote()) {
              result.append("X");
              result.append("    ");
            } else {
              result.append("|") ;
              result.append("    ");
            }
          } else {
            result.append("     ");
          }
        }
      }
      result.append("\n");
    }

    return result.toString();
  }

  @Override
  public void initialize() {
    // does not need to initialize anything
  }

  public Appendable getResult() {
    return this.result;
  }

  private void write(String s) {
    try {
      this.result.append(s);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}