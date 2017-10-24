

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import eu.gnome.morena.Configuration;
import eu.gnome.morena.Device;
import eu.gnome.morena.DeviceListChangeListener;
import eu.gnome.morena.Manager;
import eu.gnome.morena.Scanner;
import eu.gnome.morena.TransferListener;
import eu.gnome.morena.wia.WIAScanner;

public class MorenaStudioFX7 extends Application
{ HBox imageBox;
  Button removeButton;
  Button acquireButton;
  Button saveButton;
  Button uploadButton;
  CheckBox displayUICheckBox;
  CheckBox adfScan;
  CheckBox duplexScan;
  CheckBox transferProgres;
  ComboBox<Device> deviceComboBox;
//  AnchorPane statusPane;
  BorderPane statusPane;
  Label status;
  Button cancelButton;
  ProgressBar pbar;
  HBox progressBox;
  
  DeviceListListener deviceListListener;
  List<Device> liveDevices;
  ObservableList<Device> boundDevices;
  Scanner scanningDevice;
  
  private Manager manager;
  
  public static void main(String[] args)
  { System.out.println("MorenaStudioFX7 v1.0.2 started at "+(new Date()));
    launch(args);
  }
    
  @Override
  public void start(Stage primaryStage) throws Exception
  { Configuration.addDeviceType(".*fficejet.*", true);
    Configuration.setMode(Configuration.MODE_NATIVE_UI); // | Configuration.MODE_WIA1_POLL_ENABLED);
    manager=Manager.getInstance();
    liveDevices = manager.listDevices();
    deviceListListener=new DeviceListListener();
    manager.addDeviceListChangeListener(deviceListListener);
    boundDevices=FXCollections.observableList(new Vector<Device>(liveDevices));
    
    primaryStage.setTitle("Morena StudioFX");
    StackPane root=new StackPane();
    Text sceneTitle=new Text("Morena Studio 7");
    sceneTitle.setId("welcome-text");
    Image cancelIcon = new Image(MorenaStudioFX7.class.getResourceAsStream("/resource/cancel.png"));
    
    deviceComboBox=new ComboBox<Device>();
    deviceComboBox.setPrefWidth(200);
    deviceComboBox.setPromptText("Select device");
    BorderPane appletView=new BorderPane();
    ToolBar toolbar=new ToolBar();
    toolbar.getItems().add(acquireButton=new Button("acquire image"));
    toolbar.getItems().add(removeButton=new Button("remove all"));
    saveButton=new Button("save to file");
    uploadButton=new Button("upload to server");
//    toolbar.getItems().add(saveButton=new Button("save to file"));
//    toolbar.getItems().add(uploadButton=new Button("upload to server"));
    toolbar.getItems().add(new Separator());
    toolbar.getItems().add(new Label("        Device:"));
    toolbar.getItems().add(deviceComboBox);
    toolbar.getItems().add(displayUICheckBox=new CheckBox("display UI"));
    toolbar.getItems().add(adfScan=new CheckBox("ADF scan"));
    toolbar.getItems().add(duplexScan=new CheckBox("Duplex"));
    deviceComboBox.setItems(boundDevices);
    deviceComboBox.setOnAction(deviceSelected);
    deviceComboBox.setOnShowing(deviceSelecting);
    adfScan.setDisable(true);
    duplexScan.setDisable(true);
    removeButton.setOnAction(removeEvent);
    acquireButton.setOnAction(acquireEvent);
    saveButton.setOnAction(saveEvent);
    uploadButton.setOnAction(uploadEvent);    
    saveButton.setDisable(true);
    uploadButton.setDisable(true);

    ScrollPane scrollPane=new ScrollPane();
    imageBox=new HBox();
    imageBox.getChildren().addListener(new ListChangeListener<Node>()
    { //@Override
      public void onChanged(ListChangeListener.Change<? extends Node> c)
      {
        System.out.println("list changed "+c);
        if (imageBox.getChildren().size()>0)
        { saveButton.setDisable(false);
          uploadButton.setDisable(false);
        }
        else
        { saveButton.setDisable(true);
          uploadButton.setDisable(true);
        }
      }
  
    });
    scrollPane.setContent(imageBox);
    
//    statusPane=new AnchorPane();
    statusPane=new BorderPane();
    status=new Label("status");
    status.setAlignment(Pos.CENTER);
    pbar=new ProgressBar();
    ImageView cancelView = new ImageView(cancelIcon);
    cancelButton=new Button();
    cancelButton.setGraphic(cancelView);
    cancelButton.setId("cancelButton");
    cancelButton.setMinSize(cancelIcon.getWidth()+2, cancelIcon.getHeight()+2);
    cancelButton.setPrefSize(cancelIcon.getWidth()+2, cancelIcon.getHeight()+2);
    cancelButton.setMaxSize(cancelIcon.getWidth()+2, cancelIcon.getHeight()+2);
    System.out.println(cancelButton.getMaxHeight()+", "+cancelButton.getMaxWidth());
    cancelButton.setOnAction(cancelEvent);
    progressBox=new HBox();
    progressBox.getChildren().add(cancelButton);
    progressBox.getChildren().add(pbar);
    progressBox.setAlignment(Pos.CENTER);
    progressBox.setSpacing(5);
    
    statusPane.setLeft(status);
    statusPane.setRight(progressBox);
    BorderPane.setAlignment(status, Pos.CENTER_LEFT);
    BorderPane.setMargin(status, new Insets(0,0,0,5));
    BorderPane.setAlignment(progressBox, Pos.CENTER);
    BorderPane.setMargin(progressBox, new Insets(0,5,0,0));
    statusPane.setLeft(status);
    statusPane.setId("status-bar");
    statusPane.setMinHeight(22);
    statusPane.setPrefHeight(22);
    statusPane.setMaxHeight(22);
    disableProgressBar();

    appletView.setTop(toolbar);
    appletView.setCenter(scrollPane);
    appletView.setBottom(statusPane);
//    BorderPane.setMargin(scrollPane, new Insets(0,0,1,0));
    BorderPane.setMargin(statusPane, new Insets(1,0,0,0));
    
    root.setAlignment(Pos.TOP_CENTER);
    StackPane.setMargin(appletView, new Insets(30, 15, 30, 15));
    root.getChildren().add(sceneTitle);
    root.getChildren().add(appletView);
    Scene scene = new Scene(root, 1200, 720);
    primaryStage.setScene(scene);
    URL css=MorenaStudioFX7.class.getResource("/resource/MorenaStudioFX7.css");
//  System.out.println(css);
    scene.getStylesheets().add(css.toExternalForm());
    primaryStage.show();

  }


  @Override
  public void stop() throws Exception
  { try
    { manager.close();
    }
    catch (Exception exception)
    { exception.printStackTrace();
    }
  }

  
  private void disableProgressBar() {
    cancelButton.setDisable(true);
    pbar.setDisable(true);
    pbar.setProgress(0);
  }
  
  private void enableProgressBar() {
    cancelButton.setDisable(false);
    pbar.setDisable(false);
  }
  
// ---------------------- Handlers -
  class DeviceListListener implements DeviceListChangeListener
  { Vector<Device> devAdded;
    Vector<Device> devRemoved;
    
//    @Override
    public void listChanged()
    { 
// deprecated
    }

//    @Override
    public void deviceConnected(Device device)
    { boundDevices.add(device);
      status.setText("device added : "+device);
    }

//    @Override
    public void deviceDisconnected(Device device)
    { boundDevices.remove(device);
      status.setText("device removed "+device);
    }
  }
  
  class FileTransferHandler implements TransferListener
  {
    File imageFile;
    String message;
    boolean transferDone=false;
    
    /** Transferred image is handled in this callback method. File containing the image is provided as an argument. The image file may be invalidated after this method returns 
     *  and so if the application needs to retain the file moving or renaming should be used. The image type may vary 
     *  depending on the interface (Wia/ICA) and the device driver. Typical format includes BMP for WIA scanners and JPEG for WIA camera and for ICA devices.
     *  Please note that this method runs in different thread than that where the device.startTransfer() has been called.
     *  
     *  @param file - the file containing the acquired image  
     * 
     *  @see eu.gnome.morena.TransferDoneListener#transferDone(java.io.File)
     */
//    @Override
    public void transferDone(File file)
    { imageFile=file;
      System.out.println("image file:"+imageFile.getAbsolutePath());
      if (!adfScan.isSelected())
        transferDone=true;
      Platform.runLater(new Runnable() {
//        @Override
        public void run()
        { imageBox.getChildren().add(new ImageView("file:"+imageFile.getAbsolutePath()));
          if (transferDone) {
            disableProgressBar();
            status.setText("Transfer done");
            transferDone=false;
          }
        }
      });
    }
    
    /**
     * This callback method is called when scanning process failed for any reason. Description of the problem is provided.
     */
//    @Override
    public void transferFailed(int code, String error)
    { message = (code==0)?"Feeder empty":"Scan error ("+code+") "+error;
      System.out.println(message);
      transferDone=false;
      Platform.runLater(new Runnable() {
//        @Override
        public void run()
        { disableProgressBar();
          status.setText(message);
        }
      });
      
    }

//    @Override
    public void transferProgress(int percent)
    { pbar.setProgress(percent/100d);
      System.out.println("transfer progress "+percent);
    }

  }

  EventHandler<Event> deviceSelecting=new EventHandler<Event>() {
//    @Override
    public void handle(Event event)
    { System.out.println("Selecting device");
      if ((Configuration.getMode() & Configuration.MODE_NATIVE_UI)==Configuration.MODE_NATIVE_UI) {
        System.out.println("List devices");
        //deviceListListener.listChanged();
        liveDevices = manager.listDevices();
      }
    }
  };

  EventHandler<ActionEvent> deviceSelected=new EventHandler<ActionEvent>()
  { //@Override
    public void handle(ActionEvent event)
    { Device device=deviceComboBox.getValue();
      // display handling properties for wia device
      if (device instanceof WIAScanner) {
        WIAScanner ws=(WIAScanner)device;
        System.out.println("selected device: "+ws.toString()+" | cap="+String.format("%02X ", ws.getHandlingCapab())+" sel="+String.format("%03X ", ws.getHandlingSelect()));
      }
      if (device != null && device instanceof Scanner) {
        adfScan.setDisable(((Scanner)device).getFeederFunctionalUnit()<0);
        duplexScan.setDisable(!((Scanner)device).isDuplexSupported());
      }
      else {
        adfScan.setDisable(true);
        duplexScan.setDisable(true);
      }
    }
  };
  
  EventHandler<ActionEvent> removeEvent=new EventHandler<ActionEvent>()  { 
//  @Override
    public void handle(ActionEvent event)
    { System.out.println("remove event");
      imageBox.getChildren().clear();
    }
  };

  EventHandler<ActionEvent> acquireEvent=new EventHandler<ActionEvent>() {
//    @Override
    public void handle(ActionEvent event)
    { System.out.println("acquire event");
      try
      {
        status.setText("Working ...");
        boolean displayUI=displayUICheckBox.isSelected();
        Device device=deviceComboBox.getValue();
        if (device != null)
        {
          if (device instanceof Scanner)
          { Scanner scanner = (Scanner) device;
            if (!displayUI)
            { enableProgressBar();
              scanningDevice=scanner;
            }          
            int funit=adfScan.isSelected()?scanner.getFeederFunctionalUnit():scanner.getFlatbedFunctionalUnit();
            if (scanner.isDuplexSupported() && duplexScan.isSelected())
              scanner.setDuplexEnabled(true);
            else
              scanner.setDuplexEnabled(false);
            if (funit<0)  {
              System.out.println("functional unit type not reported by WIA");
              funit=0; // default functional unit
            }
            scanner.setMode(Scanner.RGB_8);
            scanner.setResolution(75);
            //scanner.setFrame(100, 100, 500, 500);
            scanner.startTransfer(new FileTransferHandler(), funit);
          }
          else
            // it would transfer all images from the camera storage and so always show driver UI to select image you want to pick up
            device.startTransfer(new FileTransferHandler());
          status.setText("Selected " + device + " transfering ...");
        }
        else
          status.setText("No device selected, please select one!");
      }
      catch (Throwable exception)
      {
        exception.printStackTrace();
        status.setText("Failed, try again ...");
      } 
    }
  };

  EventHandler<ActionEvent> saveEvent=new EventHandler<ActionEvent>() {
//    @Override
    public void handle(ActionEvent event)
    {  
    }
  };

  EventHandler<ActionEvent> uploadEvent=new EventHandler<ActionEvent>() {
//    @Override
    public void handle(ActionEvent event)
    {  
    }
  };

  EventHandler<ActionEvent> cancelEvent=new EventHandler<ActionEvent>() {
//    @Override
    public void handle(ActionEvent event)
    { scanningDevice.cancelTransfer();
    }
  };
  
}
