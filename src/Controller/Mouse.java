package Controller;

import Model.Paddle;
import javafx.scene.canvas.Canvas;

public class Mouse {

    public Paddle paddle;
    public Canvas canvas;

    public Mouse(Paddle paddle, Canvas canvas) {
        this.paddle = paddle;
        this.canvas = canvas;
    }

    public void initInputListeners() {
        canvas.setOnMouseMoved(x -> mouseMoved((int) x.getX()));
    }

    // Paddel anhand der Mauszeiger-Position bewegen
    // Zeigerposition wird als absolute Position angegeben <-> Paddle Position ist relativ
    // Zeigerposition muss durch Skalierungsfaktor dividiert werden, um relative Position zu erhalten
    public void mouseMoved(int posX) {

        // TODO Wert ?
        double scaleFactor = 100;

        this.paddle.move(posX / scaleFactor);
    }

}
