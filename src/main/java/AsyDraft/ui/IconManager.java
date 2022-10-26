package AsyDraft.ui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

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
		FileInputStream stream = null;
		try {
			switch (icon) {
				case undo:
					stream = new FileInputStream("src/main/resources/icons/undo.png");
					break;
				case redo:
					stream = new FileInputStream("src/main/resources/icons/redo.png");
					break;
				case mouse:
					stream = new FileInputStream("src/main/resources/icons/mouse.png");
					break;
				case segment:
					stream = new FileInputStream("src/main/resources/icons/segment.png");
					break;				
				case arrow:
					stream = new FileInputStream("src/main/resources/icons/arrow.png");
					break;
				case reversearrow:
					stream = new FileInputStream("src/main/resources/icons/reversearrow.png");
					break;
				case midarrow:
					stream = new FileInputStream("src/main/resources/icons/midarrow.png");
					break;					
				case doublearrow:
					stream = new FileInputStream("src/main/resources/icons/doublearrow.png");
					break;					
				case circle:
					stream = new FileInputStream("src/main/resources/icons/circle.png");
					break;				
				case incircle:
					stream = new FileInputStream("src/main/resources/icons/incircle.png");
					break;				
				case circumcircle:
					stream = new FileInputStream("src/main/resources/icons/circumcircle.png");
					break;					
				case tangent:
					stream = new FileInputStream("src/main/resources/icons/tangent.png");
					break;
				case parallel:
					stream = new FileInputStream("src/main/resources/icons/parallel.png");
					break;					
				case perpendicular:
					stream = new FileInputStream("src/main/resources/icons/perpendicular.png");
					break;					
				case rightangle:
					stream = new FileInputStream("src/main/resources/icons/rightangle.png");
					break;					
				case point:
					stream = new FileInputStream("src/main/resources/icons/point.png");
					break;					
				case anglebisector:
					stream = new FileInputStream("src/main/resources/icons/anglebisector.png");
					break;					
				case congruentangle:
					stream = new FileInputStream("src/main/resources/icons/congruentangle.png");
					break;					
				case congruentsegment:
					stream = new FileInputStream("src/main/resources/icons/congruentsegment.png");
					break;					
				case congruentarc:
					stream = new FileInputStream("src/main/resources/icons/congruentarc.png");
					break;					
				case label:
					stream = new FileInputStream("src/main/resources/icons/pointlabel.png");
					break;					
				case center:
					stream = new FileInputStream("src/main/resources/icons/center.png");
					break;
				default:
					break;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
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
