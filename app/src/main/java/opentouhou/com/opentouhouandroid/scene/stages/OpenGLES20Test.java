package opentouhou.com.opentouhouandroid.scene.stages;

import android.content.Context;

import opentouhou.com.opentouhouandroid.graphics.opengl.common.Renderer;
import opentouhou.com.opentouhouandroid.graphics.opengl.opengles20.Renderer20;
import opentouhou.com.opentouhouandroid.scene.Stage;
import opentouhou.com.opentouhouandroid.scene.scenes.LoadingScreen20;

public class OpenGLES20Test extends Stage
{
    // Scenes
    private LoadingScreen20 loadingScreen;

    // Constructor(s)
    public OpenGLES20Test(String name, Context context)
    {
        super(name, context);

        renderer = new Renderer20(this);
    }

    public void setup()
    {
        // Load the scenes.
        loadingScreen = new LoadingScreen20("TEST", this);
        loadingScreen.setup();

        // Set the current scene.
        setCurrentScene(loadingScreen);

        // Start the audio.
        getAudioPlayer().play("audio/music/loadingMusic.mp3");
    }

    public void draw()
    {
        getCurrentScene().draw();
    }
}