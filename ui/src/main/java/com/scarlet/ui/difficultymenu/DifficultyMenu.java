package com.scarlet.ui.difficultymenu;

import android.opengl.GLES30;
import android.util.Log;
import android.view.MotionEvent;

import com.scarlet.graphics.opengl.Renderer;
import com.scarlet.graphics.opengl.font.Text;
import com.scarlet.math.Matrix4f;
import com.scarlet.math.Vector3f;
import com.scarlet.math.Vector4f;
import com.scarlet.ui.UIEntity;

public class DifficultyMenu extends UIEntity {
  // Menu Items
  private MenuItem[] menuItems;
  private int currentItem;
  private int minIndex = 0;
  private int maxIndex = 3;

  private Text title;

  // Motion Event
  private float startX;

  // State
  private boolean isSelected = false;
  private String selectedValue = "";

  /*
   * Constructor(s).
   */
  public DifficultyMenu(Renderer renderer) {
    // Formatting
    Vector4f initialPosition = new Vector4f(-3.0f, 1.0f, 3.0f + 20.0f, 1.0f);

    // Build the title
    title = new Text(renderer.getFontManager().getFont("fonts/popstar/popstarpop128.xml"));
    title.setText("Difficulty")
             .setPosition(new Vector3f(initialPosition.x + 0.2f, initialPosition.y + 1.1f, initialPosition.z - 20.0f))
             .setScaling(200f)
             .setColor(new Vector4f(1.0f, 1.0f, 1.0f, 1.0f))
             .setShader("Font2");

    menuItems = new MenuItem[4];

    menuItems[0] = new MenuItem(renderer, true);
    Vector4f newPosition = Matrix4f.multiply(Matrix4f.getYAxisRotation(15, true), initialPosition);
    menuItems[0].setPosition(newPosition.x, newPosition.y, newPosition.z - 20.0f);
    menuItems[0].setAngle(15);
    menuItems[0].setText("Easy");

    //Vector4f newPosition = Matrix4f.multiply(Matrix4f.getYAxisRotation(20, true), initialPosition);
    //Log.d("Position", "New Pos: " + newPosition.toString());
    menuItems[1] = new MenuItem(renderer, true);
    menuItems[1].setPosition(initialPosition.x, initialPosition.y, initialPosition.z - 20.0f);
    menuItems[1].setAngle(0);
    menuItems[1].setText("Normal");

    menuItems[2] = new MenuItem(renderer, true);
    newPosition = Matrix4f.multiply(Matrix4f.getYAxisRotation(-15, true), initialPosition);
    menuItems[2].setPosition(newPosition.x, newPosition.y, newPosition.z - 20.0f);
    menuItems[2].setAngle(-15);
    menuItems[2].setText("Hard");

    menuItems[3] = new MenuItem(renderer, true);
    newPosition = Matrix4f.multiply(Matrix4f.getYAxisRotation(-30, true), initialPosition);
    menuItems[3].setPosition(newPosition.x, newPosition.y, newPosition.z - 20.0f);
    menuItems[3].setAngle(-30);
    menuItems[3].setText("Lunatic");

    currentItem = 1;
  }

  /*
   * Implement UIEntity.
   */
  @Override
  public void handleInput(MotionEvent event, Renderer renderer) {
    float x = event.getX();
    float y = event.getY();

    Vector3f intersection;

    switch (event.getAction()) {
      case MotionEvent.ACTION_DOWN:
        intersection = computeIntersectionPoint(x, y, renderer);

        if (menuItems[currentItem].checkCollision(intersection.x, intersection.y)) {
          Log.d("Character Menu", "Menu Item Selected");
          isSelected = true;
          startX = event.getX();
        }

        break;

      case MotionEvent.ACTION_UP:
        Log.d("Character Menu", "deltaX: " + (x - startX));

        intersection = computeIntersectionPoint(x, y, renderer);

        if (isSelected && (x - startX > 200)) {
          Log.d("Character Menu", "Anim Right");
          setRightAnimation();

        } else if (isSelected && (x - startX < -200)) {
          Log.d("Character Menu", "Anim Left");
          setLeftAnimation();

        } else if (isSelected && menuItems[currentItem].checkCollision(intersection.x, intersection.y)) {
          Log.d("Character Menu", "Clicked menu item.");

          selectedValue = menuItems[currentItem].getText();
        }

        isSelected = false;

        break;
      }
  }

  @Override
  public void update() {
    for (MenuItem item : menuItems) {
      item.update();
    }
  }

  @Override
  public void draw(Renderer renderer) {
    GLES30.glDisable(GLES30.GL_CULL_FACE);

    for (MenuItem item : menuItems) {
      item.draw(renderer);
    }

    title.draw(renderer);

    GLES30.glEnable(GLES30.GL_CULL_FACE);
  }

  /*
   * Helper methods.
   */
  private Vector3f computeIntersectionPoint(float x, float y, Renderer renderer) {
    Vector4f ndc = renderer.getCamera().convertToNDC(x, y, renderer.getScreenWidth(), renderer.getScreenHeight());
    Vector3f position = renderer.getCamera().unProject(ndc);

    return renderer.getCamera().intersectionPoint(new Vector3f(0, 0, 3), new Vector3f(0, 0, 1), position);
  }

  private void setLeftAnimation() {
    if (currentItem < maxIndex) {
      for (MenuItem item : menuItems) {
        item.setAnimationLeft();
      }

      currentItem++;
    }
  }

  private void setRightAnimation() {
    if (currentItem > minIndex) {
      for (MenuItem item : menuItems) {
        item.setAnimationRight();
      }

      currentItem--;
    }
  }
}