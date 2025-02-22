package com.scarlet.graphics.opengl.font;

import com.scarlet.graphics.opengl.Renderer;
import com.scarlet.graphics.opengl.animation.TextAnimation;
import com.scarlet.math.Matrix4f;
import com.scarlet.math.Vector3f;
import com.scarlet.math.Vector4f;

/**
 * Represents a text string that can drawn to the screen.
 */
public class Text {
    private Font font;
    private String value;
    private String shaderProgram;

    private Vector3f position;
    private Vector4f color;
    private float scaling;
    private float angle;

    private Vector4f drawOffset;

    private boolean enableAnim;
    private TextAnimation[] animations;
    private TextAnimation animation;
    private int numberOfAnimations;

    public Text(Font font) {
        this.font = font;
        value = "";
        shaderProgram = "";

        position = new Vector3f(0, 0, 0);
        color = new Vector4f(1, 1, 1, 1);
        scaling = 1.0f;
        angle = 0.0f;

        drawOffset = new Vector4f(1f, 0f, 0f, 1f);

        enableAnim = false;
        animations = new TextAnimation[4];
        animation = null;
        numberOfAnimations = 0;
    }

    public String getText() {
        return value;
    }

    public Text setText(String text) {
        value = text;
        return this;
    }

    public Vector3f getPosition() {
        return position.copy();
    }

    public Text setPosition(Vector3f position) {
        this.position.set(position);

        return this;
    }

    public float getScaling() {
        return scaling;
    }

    public Text setScaling(float scaling) {
        this.scaling = scaling;

        return this;
    }

    public float getAngle() {
        return angle;
    }

    public Text setRotation(float degree) {
        angle = degree;

        drawOffset = Matrix4f.multiply(Matrix4f.getYAxisRotation(angle, true), new Vector4f(1f, 0f, 0f, 1f));
        drawOffset.normalize();

        return this;
    }

    public Vector4f getColor() {
        return color;
    }

    public Text setColor(Vector4f color) {
        this.color.set(color);

        return this;
    }

    public String getShader() {
        return shaderProgram;
    }

    public Text setShader(String shader) {
        shaderProgram = shader;

        return this;
    }

    public Text addAnimation(TextAnimation newAnimation) {
        enableAnim = true;

        animations[numberOfAnimations] = newAnimation;

        animation = animations[numberOfAnimations];

        numberOfAnimations++;

        return this;
    }

    public void selectAnimation(int index) {
        animation = animations[index];
    }

    /*
     * Implement update method.
     */
    public void update() {
        if (enableAnim) {
            animation.update();
        }
    }

    // Drawing and rendering methods.
    public void draw(Renderer renderer) {
        char[] chars;

        // Update text.
        if (enableAnim) {
            chars = animation.currentFrame().toCharArray();
        } else {
            chars = value.toCharArray();
        }

        Vector3f drawPosition = new Vector3f(position);

        for (char c : chars) {
            // Draw the glyph.
            font.render(c, drawPosition, scaling, angle, color, shaderProgram, renderer);

            // Update the offset.
            float factor = font.getGlyph(c).getWidth() / scaling;

            // Update the render position of a glyph by the offset.
            drawPosition.selfAdd(new Vector3f(drawOffset.multiply(factor)));
        }
    }
}