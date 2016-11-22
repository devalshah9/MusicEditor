package cs3500.music.controller;

/**
 * Controller to work with Music Editor and the different views.
 */
public interface IMusicController {

  void createKeyboardHandler();

  void createMouseHandler();

  void createMetaHandler();

  void addRest();

  void onClick(int x, int y);

}
