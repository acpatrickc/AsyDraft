package AsyDraft.ui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class IconManager {
	/*
	 * Icons
	 */
	public enum Icons {
		undo,
		redo,
		mouse,
		segment,
		circle,
		arrow,
		reversearrow,
		midarrow,
		doublearrow,
		incircle,
		circumcircle,
		tangent,
		parallel,
		perpendicular,
		rightangle,
		point,
		anglebisector,
		congruentangle,
		congruentsegment,
		congruentarc,
		label,
		center,
	}
	/*
	 * runs a switch expression on an icon of choice
	 * displays icon in ImageView for buttons.
	 */
	public static ImageView getIcon(Icons icon) {
		InputStream stream = null;
		switch (icon) {
			case undo:
				stream = IconManager.class.getResourceAsStream("/icons/undo.png");
				break;
			case redo:
				stream = IconManager.class.getResourceAsStream("/icons/redo.png");
				break;
			case mouse:
				stream = IconManager.class.getResourceAsStream("/icons/mouse.png");
				break;
			case segment:
				stream = IconManager.class.getResourceAsStream("/icons/segment.png");
				break;				
			case arrow:
				stream = IconManager.class.getResourceAsStream("/icons/arrow.png");
				break;
			case reversearrow:
				stream = IconManager.class.getResourceAsStream("/icons/reversearrow.png");
				break;
			case midarrow:
				stream = IconManager.class.getResourceAsStream("/icons/midarrow.png");
				break;					
			case doublearrow:
				stream = IconManager.class.getResourceAsStream("/icons/doublearrow.png");
				break;					
			case circle:
				stream = IconManager.class.getResourceAsStream("/icons/circle.png");
				break;				
			case incircle:
				stream = IconManager.class.getResourceAsStream("/icons/incircle.png");
				break;				
			case circumcircle:
				stream = IconManager.class.getResourceAsStream("/icons/circumcircle.png");
				break;					
			case tangent:
				stream = IconManager.class.getResourceAsStream("/icons/tangent.png");
				break;
			case parallel:
				stream = IconManager.class.getResourceAsStream("/icons/parallel.png");
				break;					
			case perpendicular:
				stream = IconManager.class.getResourceAsStream("/icons/perpendicular.png");
				break;					
			case rightangle:
				stream = IconManager.class.getResourceAsStream("/icons/rightangle.png");
				break;					
			case point:
				stream = IconManager.class.getResourceAsStream("/icons/point.png");
				break;					
			case anglebisector:
				stream = IconManager.class.getResourceAsStream("/icons/anglebisector.png");
				break;					
			case congruentangle:
				stream = IconManager.class.getResourceAsStream("/icons/congruentangle.png");
				break;					
			case congruentsegment:
				stream = IconManager.class.getResourceAsStream("/icons/congruentsegment.png");
				break;					
			case congruentarc:
				stream = IconManager.class.getResourceAsStream("/icons/congruentarc.png");
				break;					
			case label:
				stream = IconManager.class.getResourceAsStream("/icons/pointlabel.png");
				break;					
			case center:
				stream = IconManager.class.getResourceAsStream("/icons/center.png");
				break;
			default:
				break;
		}
		/*
		 * filestream -> image -> imageview
		 * imageview if resized to fit button and blurred slightly to help with appearence
		 */
		ImageView img = new ImageView(new Image(stream));
		img.setFitWidth(25);
		img.setFitHeight(25);
		img.setEffect(new GaussianBlur(1));
		return img;
	}

	
}
