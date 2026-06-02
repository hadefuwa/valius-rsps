package com.valius.tools;

import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.client.Client;
import com.client.cache.definitions.ItemDefinition;
import com.client.cache.graphics.Sprite;
import com.client.draw.raster.Raster;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * A simple application to preview inventory model rotation and zoom
 * @author James
 *
 */
public class InventoryTool extends Application{
	
	/**
	 * Represents a "hardcoded" definition template
	 */
	private static final String TEMPLATE = "	case TEMPLATEID:\r\n" + 
			"			itemDef.modelZoom = TEMPLATEZOOM;\r\n" + 
			"			itemDef.modelOffset1 = TEMPLATEOFFSET1;\r\n" + 
			"			itemDef.modelOffset2 = TEMPLATEOFFSET2;\r\n" + 
			"			itemDef.modelRotation2 = TEMPLATEROTATION2;\r\n" + 
			"			itemDef.modelRotation1 = TEMPLATEROTATION1;\r\n" + 
			"			itemDef.modelRotationY = TEMPLATEROTATIONY;\r\n" + 
			"			break;";

	/**
	 * The current item being manipulated
	 */
	private ItemDefinition activeDef;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/item.fxml"));
		
		loader.setController(this);
		Parent content = loader.load();
		Scene scene = new Scene(content);
		
		
		primaryStage.setTitle("Inventory model builder");
		primaryStage.initStyle(StageStyle.DECORATED);
		primaryStage.setScene(scene);
		
		BiConsumer<Slider, TextField> onChange = (slider, textField) -> {
			slider.valueProperty().addListener((observable, oldVal, newVal) -> {
				textField.setText(newVal.intValue() + "");
				renderSprite();
			});
		};
		
		onChange.accept(zoomSlider, modelZoomText);
		onChange.accept(modelXOffsetSlider, modelXOffsetText);
		onChange.accept(modelYOffsetSlider, modelYOffsetText);
		onChange.accept(modelXAngleSlider, modelXAngleText);
		onChange.accept(modelYAngleSlider, modelYAngleText);
		onChange.accept(modelZAngleSlider, modelZAngleText);
		
		generateBtn.setOnAction(evt -> generateTemplate());
		
		modelZoomText.textProperty().addListener((ob, oldVal, newVal) -> {
			if(activeDef == null)
				return;
			if(!oldVal.equals(newVal)) {
				activeDef.modelZoom = Integer.parseInt(newVal);
				zoomSlider.adjustValue(activeDef.modelZoom);
			}
		});
		modelXOffsetText.textProperty().addListener((ob, oldVal, newVal) -> {
			if(activeDef == null)
				return;
			if(!oldVal.equals(newVal)) {
				activeDef.modelOffset1 = Integer.parseInt(newVal);
				modelXOffsetSlider.adjustValue(activeDef.modelOffset1);
			}
		});
		modelYOffsetText.textProperty().addListener((ob, oldVal, newVal) -> {
			if(activeDef == null)
				return;
			if(!oldVal.equals(newVal)) {
				activeDef.modelOffset2 = Integer.parseInt(newVal);
				modelYOffsetSlider.adjustValue(activeDef.modelOffset2);
			}
		});
		modelXAngleText.textProperty().addListener((ob, oldVal, newVal) -> {
			if(activeDef == null)
				return;
			if(!oldVal.equals(newVal)) {
				activeDef.modelRotation2 = Integer.parseInt(newVal);
				modelXAngleSlider.adjustValue(activeDef.modelRotation2);
			}
		});
		modelYAngleText.textProperty().addListener((ob, oldVal, newVal) -> {
			if(activeDef == null)
				return;
			if(!oldVal.equals(newVal)) {
				activeDef.modelRotationY = Integer.parseInt(newVal);
				modelYAngleSlider.adjustValue(activeDef.modelRotationY);
			}
		});
		modelZAngleText.textProperty().addListener((ob, oldVal, newVal) -> {
			if(activeDef == null)
				return;
			if(!oldVal.equals(newVal)) {
				activeDef.modelRotation1 = Integer.parseInt(newVal);
				modelZAngleSlider.adjustValue(activeDef.modelRotation1);
			}
		});
		loadBtn.setOnAction(evt -> {
			try {
				int itemId = Integer.parseInt(itemIdText.getText().trim());
				ItemDefinition def = ItemDefinition.forID(itemId);
				if(def == null || def.name == null || def.name.equals("undefined")) {
					throw new Exception("Base Item Def does not exist!");
				}
				populate(def);
			} catch (Exception e) {
				e.printStackTrace();
				Alert alert = new Alert(AlertType.ERROR);
				 
				alert.setTitle("Error alert");
				alert.setHeaderText("Failed to load definition!");
				alert.setContentText(e.toString());
				 
				alert.showAndWait();
			}
		});

		primaryStage.show();
	}
	
	/**
	 * Replaces placeholders in the template with the values given.
	 */
	private void generateTemplate() {
		String formatted = TEMPLATE
				.replace("TEMPLATEID", activeDef.id + "")
				.replace("TEMPLATEZOOM", activeDef.modelZoom + "")

				.replace("TEMPLATEOFFSET1", activeDef.modelOffset1 + "")
				.replace("TEMPLATEOFFSET2", activeDef.modelOffset2 + "")

				.replace("TEMPLATEROTATION2", activeDef.modelRotation2 + "")
				.replace("TEMPLATEROTATION1", activeDef.modelRotation1 + "")
				.replace("TEMPLATEROTATIONY", activeDef.modelRotationY + "")
				
				;
		
		srcOutText.setText(formatted);
	}


	/**
	 * Populates the sliders with the default values from a definition
	 * @param definition The item to grab values from
	 */
	public void populate(ItemDefinition definition) {
		activeDef = new ItemDefinition();
		activeDef.copyOf(definition);
		zoomSlider.setValue(definition.modelZoom);
		modelXOffsetSlider.setValue(definition.modelOffset1);
		modelYOffsetSlider.setValue(definition.modelOffset2);
		modelXAngleSlider.setValue(definition.modelRotation2);
		modelYAngleSlider.setValue(definition.modelRotationY);
		modelZAngleSlider.setValue(definition.modelRotation1);
		renderSprite();
	}
	
	/**
	 * Generates a sprite using the current values and renders it in the {@link ImageView}
	 */
	public void renderSprite() {
		if(activeDef == null)
			return;
		
		Client.onDrawEnd = () -> {
		
			Sprite sprite = ItemDefinition.getSpriteOnInventory(activeDef, 1, -1);

			if (sprite == null)
				return;

			Platform.runLater(() -> {
				Image image = SwingFXUtils.toFXImage(sprite.createImageFromPixels(), null);
				itemImageView.setImage(image);
			});
		};
		
	}


    @FXML
    private TextField itemIdText;

    @FXML
    private Button loadBtn;

    @FXML
    private TextArea srcOutText;

    @FXML
    private Button generateBtn;

    @FXML
    private ImageView itemImageView;

    @FXML
    private Slider zoomSlider;

    @FXML
    private Slider modelXOffsetSlider;

    @FXML
    private Slider modelYOffsetSlider;

    @FXML
    private Slider modelXAngleSlider;

    @FXML
    private Slider modelYAngleSlider;

    @FXML
    private Slider modelZAngleSlider;

    @FXML
    private TextField modelZoomText;

    @FXML
    private TextField modelXOffsetText;

    @FXML
    private TextField modelYOffsetText;

    @FXML
    private TextField modelXAngleText;

    @FXML
    private TextField modelYAngleText;

    @FXML
    private TextField modelZAngleText;

}
