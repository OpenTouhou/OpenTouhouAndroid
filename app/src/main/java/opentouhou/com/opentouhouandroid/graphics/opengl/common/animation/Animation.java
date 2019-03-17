package opentouhou.com.opentouhouandroid.graphics.opengl.common.animation;

/*
 * Represents a generic sequence animation.
 */

import android.os.SystemClock;

public abstract class Animation<T> {
    private String name;

    protected int count = 0;
    protected int currentFrame = 0;
    protected int frameCount = 0;

    protected T[] sequence;
    protected long maxDuration;
    protected long curDuration;
    protected long lastTime;
    protected long curTime;

    /*
     * Constructor(s).
     */
    public Animation(String name) {
        this.name = name;

        maxDuration = 67;
        curDuration = 0;

        lastTime = SystemClock.uptimeMillis();
        curTime = SystemClock.uptimeMillis();
    }

    /*
     * Getter(s).
     */
    public String getName() { return name; }

    /*
     * Setter(s).
     */
    public void setMaxDuration(long time) {
        maxDuration = time;
    }

    /*
     * Retrieve the current sequence item.
     */
    public T currentFrame() {
        return sequence[currentFrame];
    }

    /*
     * Move to the next sequence item.
     */
    public void nextFrame() {
        curTime = SystemClock.uptimeMillis();
        curDuration += (curTime - lastTime);

        if (curDuration >= maxDuration) {
            if (currentFrame < (frameCount - 1)) {
                currentFrame++;
            }
            else {
                currentFrame = 0;
            }

            curDuration = 0;
        }

        lastTime = curTime;
    }

    abstract void setSequence(T[] sequence);

    @Override
    public String toString() {
        return "Animation: " + name;
    }
}