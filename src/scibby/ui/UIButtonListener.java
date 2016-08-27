package scibby.ui;

import scibby.ui.components.UIButton;

public abstract class UIButtonListener{

	public void buttonEntered(UIButton button){
		System.out.println("Entered: " + button);
	}

	public void buttonExited(UIButton button){
		System.out.println("Exited: " + button);
	}

	public void buttonPressed(UIButton button){
		System.out.println("Pressed: " + button);
	}

	public void buttonReleased(UIButton button){
		System.out.println("Released: " + button);
	}
}
