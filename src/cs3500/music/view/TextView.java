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
  public void renderSong(IViewModel model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Invalid View Model!");
    }
    String result = renderNotes(viewModel);
    this.write(result);
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
    String result = "";
    if (viewModel.getEndBeat() == -1 || viewModel.getHighestNote() == null
            || viewModel.getLowestNote() == null) {
      return "No notes to present.";
    }

    int beginIndex = newNotes.indexOf(viewModel.getLowestNote());
    int endIndex = newNotes.indexOf(viewModel.getHighestNote());
    List<Note> printNotes = newNotes.subList(beginIndex, endIndex + 1);
    int columnLength = 5;
    result = result + "\n";
    ArrayList<Integer> beatNumbers = new ArrayList<Integer>();
    int beatNumberColumnLength = String.valueOf(viewModel.getEndBeat()).toString().length();
    for (int n = 0; n < beatNumberColumnLength; n++) {
      result = result + " ";
    }
    result = result + "  ";
    for (int n = 0; n < printNotes.size(); n++) {
      result = result + printNotes.get(n).toString();
      for (int i = printNotes.get(n).toString().length(); i < 5; i++) {
        result = result + " ";
      }
    }
    result = result + "\n";
    for (int n = 0; n < viewModel.getEndBeat(); n++) {
      beatNumbers.add(n);
    }
    for (int n = 0; n < beatNumbers.size(); n++) {
      Integer p = beatNumbers.get(n);
      for (int i = beatNumberColumnLength; i > p.toString().length(); i--) {
        result = result + " ";
      }
      result = result + (beatNumbers.get(n));
      result = result + "  ";
      if (!(viewModel.getNotes().containsKey(beatNumbers.get(n)))) {
        for (int j = 0; j < printNotes.size(); j++) {
          result = result + "     ";
        }
      } else {
        for (int j = 0; j < printNotes.size(); j++) {
          if (viewModel.getNotes().get(beatNumbers.get(n)).contains(printNotes.get(j))) {
            int indexNote = viewModel.getNotes().get(beatNumbers.get(n)).indexOf(printNotes.get(j));
            if (viewModel.getNotes().get(beatNumbers.get(n)).get(indexNote).getbeginningOfNote()) {
              result = result + "X" + "    ";
            } else {
              result = result + "|" + "    ";
            }
          } else {
            result = result + "     ";
          }
        }
      }
      result = result + "\n";
    }

    return result;
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