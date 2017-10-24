Morena 7 is a multiplaform image acquisition framework, java
wrapper for WIA (Windows Image Acquisition) on Microsoft Windows
and ICA (Image Capture Architecture) on Apple OS X.

It is an evolution of Morena 6 framework (TWAIN wrapper),
but as far as both WIA and ICA has a quite different approach
than TWAIN, it is not fully compatible.

----------------------------------------------------------------

Current status of Morena 7 is public beta. Current framework API
is implemented with the following exceptions:

1. On ICA platform the built in user interface of driver is
   not implemented.

The following features are still omitted from framework API:

1. No units can be specified for Scanner.setFrame(...): pixels
   are used.

----------------------------------------------------------------

History of changes:

build 8 (December 13, 2011)

- return from Manager.listDevices() is delayed for 1 second if
  called within first second from static initialization.
- fireTransferFailed() is called instead of throwing an exception.

build 9 (December 17, 2011)

- Device changed to interface, base class is DeviceBase

build 10 (December 24, 2011)

- both error code and message in transferFailed() callback

build 11 (Janurary 5, 2012)

- synchronous approach to WIA device list

build 12 (January 6, 2012)

- memory leaks fixed
- recursion in fireDeviceListChanged() fixed

build 13 (January 18, 2012)

- new poll routine
- new logging

build 14 (March 6, 2012)

- preliminary functional unit support for WIA
- better native exception management

build 15 (August 9, 2012)

- functional unit support for ICA
- Oracle Java7 support on OSX

build 16 (September 4, 2012)

- Manager.listDevices() return value changed to List<Device>
- poll thread problem after applet reload fixed

build 17 (September 9, 2012)

- HP WIA drivers device identification bug workaround

build 18 (October 19, 2012)

- WIA poll thread finished on exception

Morena 7.1

build 19 (December 9, 2012)
- methods added to Scanner
  public void cancelTransfer()
  public double getProgress()
  public int getFlatbedFunctionalUnit()
  public int getFeederFunctionalUnit()
  
- added WIA2 implementation
  added MorenaStudioFX7
  added WIA1 transfer callback
  
build 20 (April 11, 2013)
- ADF support for ICA
  ADF support for WIA2
  
build 21 (May 28, 2013)
- get methods added to Scanner
- ADF support protocol - multiple transferDone finished by transferFailed(0, feederEmpty)
- added duplex support to Scanner
  public boolean isDuplexSupported()
  public boolean isDuplexEnabled()
  public void setDuplexEnabled(boolean enabled)
- added provisional UI dialog
- debug logs directed to java logger in ICA

build 22 (July 7, 2013)
- methods added to Scanner
  public List<Integer> getSupportedModes();
  public List<Integer> getSupportedResolutions()

 
build 23 (Sep 27, 2013)
- added swing UI dialog
- dll loading correction
- device initialization specific for WIA scanner & camera 
- logging maintenance

build 24 (Oct 8, 2013)
- WIA1 ADF support

build 25 (Oct 19, 2013)
- device browser thread detach fixed for ICA
- attributes added to manifest 

build 26 (Dec 15, 2013)
- fixed transfer on uninitialized scanner
- fixed pre-OSX 10.8 compatibility

build 27 (Sep 14, 2015)
- fixed 12 min bug (invalid BSTR handling)
- Windows 10 support

build 28 (Apr 11, 2016)
- duplex property fix for WIA (full toggle)
- page count can be specified for WIA (ADF scanning)
- file name can be specified
- delete policy added to configuration (leave, deleteOnExit, delete)
- device UID getter for WIA (+May 20, 2016)

build 29 (Sep 9, 2016)
- narrow scope where thread is attached to JVM in WiaTransferCallback::TransferCallback()
----------------------------------------------------------------

Morena 7 - Image Acquisition Framework

Copyright (c) 1999-2011 Gnome spol. s r.o. All Rights Reserved.

This software is the confidential and proprietary information of
Gnome spol. s r.o. You shall not disclose such Confidential
Information and shall use it only in accordance with the terms
of the license agreement you entered into with Gnome.
