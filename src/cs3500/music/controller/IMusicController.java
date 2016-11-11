package cs3500.music.controller;

import java.util.Map;

/**
 * Controller to work with Music Editor.
 */
public interface IMusicController {

  void renderSong();

  void createKeyboardHandler(Map<Integer, Runnable> keyTyped, Map<Integer,
          Runnable> keyPressed, Map<Integer, Runnable> keyReleased);

  void createMouseHandler();

}
