import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import eu.gnome.morena.Configuration;
import eu.gnome.morena.Configuration.DELETE_POLICY;
import eu.gnome.morena.Device;
import eu.gnome.morena.Manager;
import eu.gnome.morena.Scanner;
import eu.gnome.morena.TransferListener;
import eu.gnome.morena.wia.WIADevice;
import eu.gnome.morena.wia.WIAScanner;

public class MorenaTests implements TransferListener 
{ int pageCount;
  Scanner device;

  public void transferDone(File file) {
    try {
      int extIx = file.getName().lastIndexOf('.');
      File newFile = new File(file.getParentFile(), "MorenaImg_" + (pageCount++) + (extIx > 0 ? file.getName().substring(extIx) : ""));
      if (newFile.exists())
        newFile.delete();
      boolean renamed = file.renameTo(newFile);
      System.out.println("image renamed " + renamed + "  from : " + file.getAbsolutePath() + "  to : " + newFile.getAbsolutePath());

      BufferedImage image = ImageIO.read(newFile);
      System.out.println("scanned image "+newFile.getPath()+" : size=(" + image.getWidth() + ", " + image.getHeight() + ")   bit mode=" + image.getColorModel().getPixelSize());
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  public void transferFailed(int code, String message) {
    System.out.println((code==0?"Feeder empty":"Transfer failed " + message + " [0x" + Integer.toHexString(code) + "]"));
  }

  public void transferProgress(int percent) {
    System.out.println(percent + "%");
  }

  
  
  public static void main(String[] args)
  { MorenaTests tests=new MorenaTests();
    System.out.println("MorenaTests "+Arrays.toString(args)+" started at "+(new Date()));
    try {
      if (args.length == 0) // no args - printProperties
        tests.printProperties();
      else if (args[0].equalsIgnoreCase("single"))
        tests.singleScan();
      else if (args[0].equalsIgnoreCase("dual"))
        tests.dualScan();
      else if (args[0].equalsIgnoreCase("batch"))
        tests.batchScan();
      else if (args[0].equalsIgnoreCase("thread"))
        tests.threadScan();
      else if (args[0].equalsIgnoreCase("event"))
        tests.eventTest();
      else if (args[0].equalsIgnoreCase("multi"))
        tests.multiScan();
      else if (args[0].equalsIgnoreCase("list"))
        tests.deviceList();
      else if (args[0].equalsIgnoreCase("detail"))
        tests.detailScan();
      else if (args[0].equalsIgnoreCase("loop"))
        tests.loopScan();
    } catch (Throwable ex) {
      ex.printStackTrace();
    }
    System.out.println("... finished");
  }

  /**
   * This example method print properties of selected device and functional unit
   * default functional unit (flatbed)
   * 
   * @throws Exception
   */
  private void printProperties() throws Exception  {
    System.out.println("phase 1");
    Configuration.addDeviceType(".*fficejet.*", true);
    Configuration.setLogLevel(Level.ALL);
    Manager manager = Manager.getInstance();
    System.out.println("phase 2");
    List<? extends Device> devices = manager.listDevices();
    System.out.println("phase 3");
    if (devices.size() == 0)
      System.out.println("No device connected");
    for (int i=0; i<devices.size(); i++) {
      Device device=devices.get(i);
      if (device instanceof WIADevice) {
        ((WIADevice)device).displayProperties();
      }
    }
    Thread.sleep(1000);
    manager.close();
  }

  
  private void deviceList()
  {
    System.out.println("phase 1");
    Manager manager = Manager.getInstance();
    System.out.println("phase 2");
    manager.close();
    System.out.println("phase 3");
  }

  private void multiScan()
  {
    System.out.println("phase 1");
    Configuration.addDeviceType(".*fficejet.*", true);
    Manager manager = Manager.getInstance();
    System.out.println("phase 2");
    List<? extends Device> devices = manager.listDevices();
    System.out.println("phase 3");
    
    if (devices.size() > 0) {
      //Scanner device = (Scanner)devices.get(0);
      device = (Scanner)devices.get(0);
      int feederUnit=device.getFeederFunctionalUnit();
      System.out.println("Feeder unit : "+(feederUnit>0?feederUnit:"none"));
      try {
        if (feederUnit>0) {
          if (device.isDuplexSupported())
            device.setDuplexEnabled(true);
          device.startTransfer(this, device.getFeederFunctionalUnit());
        }
        else {
          device.startTransfer(this);
        }
        Thread.sleep(60000);
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }
    }
    System.out.println("phase 4");
    manager.close();
    
  }

  private void eventTest() throws Exception
  { System.out.println("phase 1");
    Manager manager = Manager.getInstance();
    System.out.println("phase 2");
    // Selecting a device (1st device available is selected)
    printDevices(manager);
    System.out.println("phase 3");
    Thread.sleep(30000);
    printDevices(manager);
//    scanStarted=true;
//    new ThreadedScan(manager).start();
    Thread.sleep(60000);
    printDevices(manager);
    System.out.println("phase 4");
    manager.close();
    System.out.println("phase 5");
  }
  
  private void printDevices(Manager manager)
  { System.out.println("----------- connected devices -------------");
    for (Device device : manager.listDevices())
      System.out.println(device);
    System.out.println("---------------- cd endlist ---------------");
  }

  private void threadScan()
  {
    // TODO Auto-generated method stub
    
  }

  private void batchScan()
  { System.out.println("phase 1");
    Configuration.addDeviceType(".*fficejet.*", true);

    Manager manager = Manager.getInstance();
    List<? extends Device> devices = manager.listDevices();
    if (devices.size() > 0) {
      Device device = devices.get(0);

      if (device != null) {

        // for scanner device set the scanning parameters
        if (device instanceof Scanner) {
          Scanner scanner = (Scanner) device;
          scanner.setMode(Scanner.RGB_8);
          scanner.setResolution(75);

          // start batch scan
          try {
            for (int i = 1; i < 10; i++) { // functional unit 1 designates a 2nd
                                           // unit available for this scanner
                                           // that is usually ADF
              File file = SynchronousHelper.scanFile(device, 1);
              int extIx = file.getName().lastIndexOf('.');
              File newFile = new File(file.getParentFile(), "MorenaImg_" + i + (extIx > 0 ? file.getName().substring(extIx) : ""));
              if (newFile.exists())
                newFile.delete();
              boolean renamed = file.renameTo(newFile);
              System.out.println("image renamed " + renamed + "  from : " + file.getAbsolutePath() + "  to : " + newFile.getAbsolutePath());
            }
          } catch (Exception ex) { // check if error is related to empty ADF
            if (ex.getMessage().indexOf("[" + SynchronousHelper.WIA_ERROR_PAPER_EMPTY + "]") < 0)
              ex.printStackTrace();
            else
              System.out.println("No more sheets in the document feeder");
          }
        }
      }
    } else
      System.out.println("No device connected!!!");
  }

  private void singleScan() throws Exception
  { System.out.println("phase 1");
    Manager manager = Manager.getInstance();
    System.out.println("phase 2");
    List<? extends Device> devices = manager.listDevices();
    System.out.println("phase 3");
    
    if (devices.size() > 0) {
      Device device = devices.get(0);
      File file = SynchronousHelper.scanFile(device, 0);
      int extIx = file.getName().lastIndexOf('.');
      File newFile = new File(file.getParentFile(), "MorenaImg" + (extIx > 0 ? file.getName().substring(extIx) : ""));
      if (newFile.exists())
        newFile.delete();
      boolean renamed = file.renameTo(newFile);
      System.out.println("image renamed " + renamed + "  from : " + file.getAbsolutePath() + "  to : " + newFile.getAbsolutePath());
    }
    System.out.println("phase 4");
    manager.close();
  }

  /**
   * Test scanning from two scanners
   * output file name settings
   * 
   * @throws Exception
   */
  private void dualScan() throws Exception
  { System.out.println("phase 1 - two scanners should be connected");
    Manager manager = Manager.getInstance();
    System.out.println("phase 2");
    List<? extends Device> devices = manager.listDevices();
    System.out.println("phase 3");
    Configuration.setDeletePolicy(DELETE_POLICY.LEAVE);
    
    if (devices.size() > 1) {
    	// 1. scanner
      Device device = devices.get(0);
      device.setFileName("imageFromFirstScanner");
      File file1 = SynchronousHelper.scanFile(device, 0);
      System.out.println("scanner 1 image : " + file1.getAbsolutePath());
      
      System.out.println("insert a sheet to second scanner - waiting 30 sec.");
      Thread.sleep(30000);
      
      // 2.scanner
      device=devices.get(1);
      device.setFileName("imageFromSecondScanner");
      File file2 = SynchronousHelper.scanFile(device, 0);
      System.out.println("scanner 2 image : " + file2.getAbsolutePath());
      Thread.sleep(60000);
      
      file1.deleteOnExit();
      file2.deleteOnExit();
    }
    else
    	System.err.println("At least two scanners connected are required!");
    System.out.println("phase 4");
    manager.close();
  }
  
  private void detailScan() throws Exception
  { System.out.println("phase 1");
    Manager manager = Manager.getInstance();
    System.out.println("phase 2");
    List<? extends Device> devices = manager.listDevices();
    System.out.println("phase 3");
    
    if (devices.size() > 0) {
      Scanner device = (Scanner)devices.get(0);
      
      int dpi = 150;
      float pixs= dpi/25.4f;
      int x   = Math.round(0 * pixs);  // mm
      int y   = Math.round(0 * pixs); // mm 50
      int w   = Math.round(210 * pixs);// mm 150
      int h   = Math.round(296 * pixs);// mm 100 297
      int mode= WIAScanner.GRAY_8; //WIADevice.RGB_8;
      
      device.setResolution(dpi);
//      device.setMode(mode);
      device.setFrame(x, y, w, h);  // robi problemy u HP s pageCountom

      File file = SynchronousHelper.scanFile(device, 0);
      int extIx = file.getName().lastIndexOf('.');
      File newFile = new File(file.getParentFile(), "MorenaImg" + (extIx > 0 ? file.getName().substring(extIx) : ""));
      if (newFile.exists())
        newFile.delete();
      boolean renamed = file.renameTo(newFile);
      System.out.println("image renamed " + renamed + "  from : " + file.getAbsolutePath() + "  to : " + newFile.getAbsolutePath());
    }
    System.out.println("phase 4");
    manager.close();
  }
  
  // ---------------------------------------
  
  Device selectedDevice;
  boolean hasMoreImages;
  
  class FujitsuTransferHandler implements TransferListener
  { boolean transferDoneCalled=false;
    
//    @Override
    public void transferDone(File file)
    { System.out.println("image file:"+file.getAbsolutePath());
      // do your job
      transferDoneCalled=true;
    }
    
//    @Override
    public void transferFailed(int code, String error)
    { System.out.println((code==0)?"Feeder empty":"Scan error ("+code+") "+error);    
      if (!transferDoneCalled)
        hasMoreImages=false;
      transferDoneCalled=false;
      notifyRequestor();
    }
      
//    @Override
    public void transferProgress(int percent)
    { System.out.println("transfer progress "+percent);
    }

    private synchronized void notifyRequestor() {
      this.notify();
    }
  }
  
  public void loopScan() throws Exception {
    System.out.println("loop scan phase 1");
    Manager manager = Manager.getInstance();
    System.out.println("phase 2");
    List<? extends Device> devices = manager.listDevices();
    System.out.println("phase 3");
    
    if (devices.size() > 0) {
      selectedDevice=devices.get(0);
      scanFujitsu(new FujitsuTransferHandler());
    }
  }
  
  private void scanFujitsu(TransferListener th) {
    if (selectedDevice != null) {
      if (selectedDevice instanceof Scanner) {
        Scanner scanner = (Scanner) selectedDevice;
        scanner.setMode(Scanner.RGB_8);
        scanner.setResolution(100);
        int feederUnit=scanner.getFeederFunctionalUnit();
        System.out.println("Feeder unit : "+(feederUnit>0?feederUnit:"none"));
            try {
              if (feederUnit>0) {
                if (scanner.isDuplexSupported())
                    scanner.setDuplexEnabled(true);
                      hasMoreImages=true;
                      while (hasMoreImages)
                        synchronized (th) {
                        scanner.startTransfer(th, feederUnit);
                        th.wait();
                      }
              }
              else {
                  synchronized (th) {
                  scanner.startTransfer(th);
                  th.wait();
                  }
              }
          } catch (Exception e) {
              e.printStackTrace();
          }
        }
    }
  }
  
// ------------------------------------  
  private static void printLoggers()
  { 
    Configuration.setLogLevel(Level.ALL);
    Enumeration<String> loggers=LogManager.getLogManager().getLoggerNames();
    System.out.println("Loggers :");
    while (loggers.hasMoreElements()) {
      String lname=loggers.nextElement();
      Logger lg=Logger.getLogger(lname);
      System.out.println(lname+" "+lg.getLevel()+" parent="+(lg.getParent()!=null?lg.getParent().getName():"null")+"  "+lg.getUseParentHandlers());
    }
  }

}
