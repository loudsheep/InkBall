package app.scene;

public interface SceneManager {
    enum SceneType {
        MENU,
        GAME,
        SETTINGS,
    }
    void setTitle(String title);
    void quitApp();
    String getAbsolutePathToGame();
    void swapScenes(SceneType type);
    void fps(float fps);
}
