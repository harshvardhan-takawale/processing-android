/* -*- mode: java; c-basic-offset: 2; indent-tabs-mode: nil -*- */

/*
 Part of the Processing project - http://processing.org

 Copyright (c) 2012-16 The Processing Foundation
 Copyright (c) 2010-12 Ben Fry and Casey Reas

 This program is free software; you can redistribute it and/or modify
 it under the terms of the GNU General Public License version 2
 as published by the Free Software Foundation.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program; if not, write to the Free Software Foundation,
 Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*/

package processing.mode.android;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

import processing.app.Platform;
import processing.app.Sketch;
import processing.app.ui.Toolkit;


@SuppressWarnings("serial")
public class Permissions extends JFrame {
  static final String GUIDE_URL =
    "http://developer.android.com/guide/topics/security/security.html#permissions";

  static final int BORDER_HORIZ = 5;
  static final int BORDER_VERT = 3;

  JScrollPane permissionScroller;
  JList<JCheckBox> permissionList;
  JLabel descriptionLabel;
//  JTextArea descriptionLabel;

//  Editor editor;
  Sketch sketch;
  
  int appComp;


  public Permissions(Sketch sketch, int appComp) {
  //public Permissions(Editor editor) {
    super("Android Permissions Selector");
    this.appComp = appComp;
    this.sketch = sketch;
//    this.editor = editor;

//    XMLElement xml =

    permissionList = new CheckBoxList();
//    permissionList.addMouseListener(new MouseAdapter() {
//      public void mousePressed(MouseEvent e) {
//        if (isEnabled()) {
//          int index = permissionList.locationToIndex(e.getPoint());
//          if (index == -1) {
//            descriptionLabel.setText("");
//          } else {
////            descriptionLabel.setText("<html>" + description[index] + "</html>");
//            descriptionLabel.setText(description[index]);
//          }
//        }
//      }
//    });

//    ListSelectionModel lsm = permissionList.getSelectionModel();
//    lsm.addListSelectionListener(new ListSelectionListener() {
//      public void valueChanged(ListSelectionEvent e) {
////        ListSelectionModel lsm = (ListSelectionModel) e.getSource();
//        int index = e.getFirstIndex();
//        if (index == -1) {
//          descriptionLabel.setText("");
//        } else {
//          descriptionLabel.setText("<html>" + description[index] + "</html>");
////          descriptionLabel.setText(description[index]);
//        }
//      }
//    });
    permissionList.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {
          int index = permissionList.getSelectedIndex();
          if (index == -1) {
            descriptionLabel.setText("");
          } else {
            descriptionLabel.setText("<html>" + description[index] + "</html>");
            //descriptionLabel.setText(description[index]);
          }
        }
      }
    });
//    permissionList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
//    permissionList.setFixedCellWidth(300);
//    int h = permissionList.getFixedCellHeight();
//    permissionList.setFixedCellHeight(h + 8);
    permissionList.setFixedCellHeight(20);
    permissionList.setBorder(new EmptyBorder(BORDER_VERT, BORDER_HORIZ,
                                             BORDER_VERT, BORDER_HORIZ));

    DefaultListModel<JCheckBox> model = new DefaultListModel<JCheckBox>();
    permissionList.setModel(model);
    for (String item : title) {
      model.addElement(new JCheckBox(item));
    }

    permissionScroller =
      new JScrollPane(permissionList,
                      ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                      ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
//    permissionList.setVisibleRowCount(20);
    permissionList.setVisibleRowCount(12);
//    permissionList.setPreferredSize(new Dimension(400, 300));
//    permissionsScroller.setPreferredSize(new Dimension(400, 300));
    permissionList.addKeyListener(new KeyAdapter() {
      public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == ' ') {
          int index = permissionList.getSelectedIndex();
          JCheckBox checkbox =
            permissionList.getModel().getElementAt(index);
          checkbox.setSelected(!checkbox.isSelected());
          permissionList.repaint();
        }
      }
    });

    Container outer = getContentPane();
//    outer.setLayout(new BorderLayout());

//    JPanel pain = new JPanel();
    Box pain = Box.createVerticalBox();
    pain.setBorder(new EmptyBorder(13, 13, 13, 13));
//    outer.add(pain, BorderLayout.CENTER);
    outer.add(pain);
//    pain.setLayout(new BoxLayout(pain, BoxLayout.Y_AXIS));

    String labelText =
      "<html>" +
      "Android applications must specifically ask for permission\n" +
      "to do things like connect to the internet, write a file,\n" +
      "or make phone calls. When installing your application,\n" +
      "users will be asked whether they want to allow such access.\n" +
      "More about permissions can be found " +
      "<a href=\"" + GUIDE_URL + "\">here</a>.</body></html>";
//      "<html>" +
//      "Android applications must specifically ask for permission\n" +
//      "to do things like connect to the internet, write a file,\n" +
//      "or make phone calls. When installing your application,\n" +
//      "users will be asked whether they want to allow such access.\n" +
//      "More about permissions can be found " +
//      "<a href=\"" + GUIDE_URL + "\">here</a>.</body></html>";
//    JTextArea textarea = new JTextArea(labelText);
//    JTextArea textarea = new JTextArea(5, 40);
//    textarea.setText(labelText);
    JLabel textarea = new JLabel(labelText);
//    JLabel textarea = new JLabel(labelText) {
//      public Dimension getPreferredSize() {
//        return new Dimension(400, 100);
//      }
//      public Dimension getMinimumSize() {
//        return getPreferredSize();
//      }
//      public Dimension getMaximumSize() {
//        return getPreferredSize();
//      }
//    };
    textarea.setPreferredSize(new Dimension(400, 100));
    textarea.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        Platform.openURL(GUIDE_URL);
      }
    });
    //textarea.setHorizontalAlignment(SwingConstants.LEFT);
    textarea.setAlignmentX(LEFT_ALIGNMENT);

//    textarea.setBorder(new EmptyBorder(13, 8, 13, 8));

//    textarea.setBackground(null);
//    textarea.setBackground(Color.RED);
//    textarea.setEditable(false);
//    textarea.setHighlighter(null);
//    textarea.setFont(new Font("Dialog", Font.PLAIN, 12));
    pain.add(textarea);
//    textarea.setForeground(Color.RED);
//    pain.setBackground(Color.GREEN);

//    permissionList.setEnabled(false);

    permissionScroller.setAlignmentX(LEFT_ALIGNMENT);
    pain.add(permissionScroller);
//    pain.add(permissionList);
    pain.add(Box.createVerticalStrut(8));

//    descriptionLabel = new JTextArea(4, 10);
    descriptionLabel = new JLabel();
//    descriptionLabel = new JLabel() {
//      public Dimension getPreferredSize() {
//        return new Dimension(400, 100);
//      }
//      public Dimension getMinimumSize() {
//        return new Dimension(400, 100);
//      }
//      public Dimension getMaximumSize() {
//        return new Dimension(400, 100);
//      }
//    };
    descriptionLabel.setPreferredSize(new Dimension(400, 50));
    descriptionLabel.setVerticalAlignment(SwingConstants.TOP);
    descriptionLabel.setAlignmentX(LEFT_ALIGNMENT);
    pain.add(descriptionLabel);
    pain.add(Box.createVerticalStrut(8));

    JPanel buttons = new JPanel();
//    buttons.setPreferredSize(new Dimension(400, 35));
//    JPanel buttons = new JPanel() {
//      public Dimension getPreferredSize() {
//        return new Dimension(400, 35);
//      }
//      public Dimension getMinimumSize() {
//        return new Dimension(400, 35);
//      }
//      public Dimension getMaximumSize() {
//        return new Dimension(400, 35);
//      }
//    };

//    Box buttons = Box.createHorizontalBox();
    buttons.setAlignmentX(LEFT_ALIGNMENT);
    JButton okButton = new JButton("OK");
    Dimension dim = new Dimension(Toolkit.getButtonWidth(),
                                  okButton.getPreferredSize().height);
    okButton.setPreferredSize(dim);
    okButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        //PApplet.println(getSelections());
        saveSelections();
        setVisible(false);
      }
    });
    okButton.setEnabled(true);

    JButton cancelButton = new JButton("Cancel");
    cancelButton.setPreferredSize(dim);
    cancelButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        setVisible(false);
      }
    });
    cancelButton.setEnabled(true);

    // think different, biznatchios!
    if (Platform.isMacOS()) {
      buttons.add(cancelButton);
//      buttons.add(Box.createHorizontalStrut(8));
      buttons.add(okButton);
    } else {
      buttons.add(okButton);
//      buttons.add(Box.createHorizontalStrut(8));
      buttons.add(cancelButton);
    }
//    buttons.setMaximumSize(new Dimension(300, buttons.getPreferredSize().height));
    pain.add(buttons);

    JRootPane root = getRootPane();
    root.setDefaultButton(okButton);
    ActionListener disposer = new ActionListener() {
      public void actionPerformed(ActionEvent actionEvent) {
        setVisible(false);
      }
    };
    Toolkit.registerWindowCloseKeys(root, disposer);
    Toolkit.setIcon(this);

    pack();

    Dimension screen = Toolkit.getScreenSize();
    Dimension windowSize = getSize();

    setLocation((screen.width - windowSize.width) / 2,
                (screen.height - windowSize.height) / 2);

    Manifest mf = new Manifest(sketch, appComp, false);
    setSelections(mf.getPermissions());

    // show the window and get to work
    setVisible(true);
  }


  @SuppressWarnings("rawtypes")
  protected void setSelections(String[] sel) {
//    processing.core.PApplet.println("permissions are:");
//    processing.core.PApplet.println(sel);
    HashMap<String,Object> map = new HashMap<String, Object>();
    for (String s : sel) {
      map.put(s, new Object());
    }
    DefaultListModel model = (DefaultListModel) permissionList.getModel();
    for (int i = 0; i < count; i++) {
      JCheckBox box = (JCheckBox) model.get(i);
//      System.out.println(map.containsKey(box.getText()) + " " + box.getText());
      box.setSelected(map.containsKey(box.getText()));
    }
  }


  @SuppressWarnings("rawtypes")
  protected String[] getSelections() {
    ArrayList<String> sel = new ArrayList<String>();
    DefaultListModel model = (DefaultListModel) permissionList.getModel();
    for (int i = 0; i < count; i++) {
      if (((JCheckBox) model.get(i)).isSelected()) {
        sel.add(title[i]);
      }
    }
    return sel.toArray(new String[0]);
  }


  protected void saveSelections() {
    String[] sel = getSelections();
    Manifest mf = new Manifest(sketch, appComp, false);
    mf.setPermissions(sel);
  }


  public String getMenuTitle() {
    return "Android Permissions";
  }


//  public void init(Editor editor) {
//    this.editor = editor;
//  }


//  public void run() {
//    // parse the manifest file here and figure out what permissions are set
//    Manifest mf = new Manifest(editor);
//    setSelections(mf.getPermissions());
//
//    // show the window and get to work
//    setVisible(true);
//  }


  /**
   * Created by inserting the HTML doc into OpenOffice, then copy and pasting
   * the table into a plain text document, then adding the quotes via search
   * and replace. If there's a way to auto-create from aapt, that'd be better,
   * but I haven't found anything yet.
   */
  static final String[] listing = {
    "ACCESS_CHECKIN_PROPERTIES", "Allows read/write access to the \"properties\" table in the checkin database, to change values that get uploaded.",
    "ACCESS_COARSE_LOCATION", "Allows an application to access coarse (e.g., Cell-ID, WiFi) location",
    "ACCESS_FINE_LOCATION", "Allows an application to access fine (e.g., GPS) location",
    "ACCESS_LOCATION_EXTRA_COMMANDS", "Allows an application to access extra location provider commands",
    "ACCESS_MOCK_LOCATION", "Allows an application to create mock location providers for testing",
    "ACCESS_NETWORK_STATE", "Allows applications to access information about networks",
    "ACCESS_SURFACE_FLINGER", "Allows an application to use SurfaceFlinger's low level features",
    "ACCESS_WIFI_STATE", "Allows applications to access information about Wi-Fi networks",
    "ACCOUNT_MANAGER", "Allows applications to call into AccountAuthenticators.",
    "AUTHENTICATE_ACCOUNTS", "Allows an application to act as an AccountAuthenticator for the AccountManager",
    "BATTERY_STATS", "Allows an application to collect battery statistics",
    "BIND_APPWIDGET", "Allows an application to tell the AppWidget service which application can access AppWidget's data.",
    "BIND_DEVICE_ADMIN", "Must be required by device administration receiver, to ensure that only the system can interact with it.",
    "BIND_INPUT_METHOD", "Must be required by an InputMethodService, to ensure that only the system can bind to it.",
    "BIND_WALLPAPER", "Must be required by a WallpaperService, to ensure that only the system can bind to it.",
    "BLUETOOTH", "Allows applications to connect to paired bluetooth devices",
    "BLUETOOTH_ADMIN", "Allows applications to discover and pair bluetooth devices",
    "BRICK", "Required to be able to disable the device (very dangerous!).",
    "BROADCAST_PACKAGE_REMOVED", "Allows an application to broadcast a notification that an application package has been removed.",
    "BROADCAST_SMS", "Allows an application to broadcast an SMS receipt notification",
    "BROADCAST_STICKY", "Allows an application to broadcast sticky intents.",
    "BROADCAST_WAP_PUSH", "Allows an application to broadcast a WAP PUSH receipt notification",
    "CALL_PHONE", "Allows an application to initiate a phone call without going through the Dialer user interface for the user to confirm the call being placed.",
    "CALL_PRIVILEGED", "Allows an application to call any phone number, including emergency numbers, without going through the Dialer user interface for the user to confirm the call being placed.",
    "CAMERA", "Required to be able to access the camera device.",
    "CHANGE_COMPONENT_ENABLED_STATE", "Allows an application to change whether an application component (other than its own) is enabled or not.",
    "CHANGE_CONFIGURATION", "Allows an application to modify the current configuration, such as locale.",
    "CHANGE_NETWORK_STATE", "Allows applications to change network connectivity state",
    "CHANGE_WIFI_MULTICAST_STATE", "Allows applications to enter Wi-Fi Multicast mode",
    "CHANGE_WIFI_STATE", "Allows applications to change Wi-Fi connectivity state",
    "CLEAR_APP_CACHE", "Allows an application to clear the caches of all installed applications on the device.",
    "CLEAR_APP_USER_DATA", "Allows an application to clear user data",
    "CONTROL_LOCATION_UPDATES", "Allows enabling/disabling location update notifications from the radio.",
    "DELETE_CACHE_FILES", "Allows an application to delete cache files.",
    "DELETE_PACKAGES", "Allows an application to delete packages.",
    "DEVICE_POWER", "Allows low-level access to power management",
    "DIAGNOSTIC", "Allows applications to RW to diagnostic resources.",
    "DISABLE_KEYGUARD", "Allows applications to disable the keyguard",
    "DUMP", "Allows an application to retrieve state dump information from system services.",
    "EXPAND_STATUS_BAR", "Allows an application to expand or collapse the status bar.",
    "FACTORY_TEST", "Run as a manufacturer test application, running as the root user.",
    "FLASHLIGHT", "Allows access to the flashlight",
    "FORCE_BACK", "Allows an application to force a BACK operation on whatever is the top activity.",
    "GET_ACCOUNTS", "Allows access to the list of accounts in the Accounts Service",
    "GET_PACKAGE_SIZE", "Allows an application to find out the space used by any package.",
    "GET_TASKS", "Allows an application to get information about the currently or recently running tasks: a thumbnail representation of the tasks, what activities are running in it, etc.",
    "GLOBAL_SEARCH", "This permission can be used on content providers to allow the global search system to access their data.",
    "HARDWARE_TEST", "Allows access to hardware peripherals.",
    "INJECT_EVENTS", "Allows an application to inject user events (keys, touch, trackball) into the event stream and deliver them to ANY window.",
    "INSTALL_LOCATION_PROVIDER", "Allows an application to install a location provider into the Location Manager",
    "INSTALL_PACKAGES", "Allows an application to install packages.",
    "INTERNAL_SYSTEM_WINDOW", "Allows an application to open windows that are for use by parts of the system user interface.",
    "INTERNET", "Allows applications to open network sockets.",
    "KILL_BACKGROUND_PROCESSES", "Allows an application to call killBackgroundProcesses(String).",
    "MANAGE_ACCOUNTS", "Allows an application to manage the list of accounts in the AccountManager",
    "MANAGE_APP_TOKENS", "Allows an application to manage (create, destroy, Z-order) application tokens in the window manager.",
    "MASTER_CLEAR", "",
    "MODIFY_AUDIO_SETTINGS", "Allows an application to modify global audio settings",
    "MODIFY_PHONE_STATE", "Allows modification of the telephony state - power on, mmi, etc.",
    "MOUNT_FORMAT_FILESYSTEMS", "Allows formatting file systems for removable storage.",
    "MOUNT_UNMOUNT_FILESYSTEMS", "Allows mounting and unmounting file systems for removable storage.",
    "PERSISTENT_ACTIVITY", "Allow an application to make its activities persistent.",
    "PROCESS_OUTGOING_CALLS", "Allows an application to monitor, modify, or abort outgoing calls.",
    "READ_CALENDAR", "Allows an application to read the user's calendar data.",
    "READ_CONTACTS", "Allows an application to read the user's contacts data.",
    "READ_FRAME_BUFFER", "Allows an application to take screen shots and more generally get access to the frame buffer data",
    "READ_HISTORY_BOOKMARKS", "Allows an application to read (but not write) the user's browsing history and bookmarks.",
    "READ_INPUT_STATE", "Allows an application to retrieve the current state of keys and switches.",
    "READ_LOGS", "Allows an application to read the low-level system log files.",
    "READ_OWNER_DATA", "Allows an application to read the owner's data.",
    "READ_PHONE_STATE", "Allows read only access to phone state.",
    "READ_SMS", "Allows an application to read SMS messages.",
    "READ_SYNC_SETTINGS", "Allows applications to read the sync settings",
    "READ_SYNC_STATS", "Allows applications to read the sync stats",
    "REBOOT", "Required to be able to reboot the device.",
    "RECEIVE_BOOT_COMPLETED", "Allows an application to receive the ACTION_BOOT_COMPLETED that is broadcast after the system finishes booting.",
    "RECEIVE_MMS", "Allows an application to monitor incoming MMS messages, to record or perform processing on them.",
    "RECEIVE_SMS", "Allows an application to monitor incoming SMS messages, to record or perform processing on them.",
    "RECEIVE_WAP_PUSH", "Allows an application to monitor incoming WAP push messages.",
    "RECORD_AUDIO", "Allows an application to record audio",
    "REORDER_TASKS", "Allows an application to change the Z-order of tasks",
    "RESTART_PACKAGES", "This constant is deprecated. The restartPackage(String) API is no longer supported. ",
    "SEND_SMS", "Allows an application to send SMS messages.",
    "SET_ACTIVITY_WATCHER", "Allows an application to watch and control how activities are started globally in the system.",
    "SET_ALWAYS_FINISH", "Allows an application to control whether activities are immediately finished when put in the background.",
    "SET_ANIMATION_SCALE", "Modify the global animation scaling factor.",
    "SET_DEBUG_APP", "Configure an application for debugging.",
    "SET_ORIENTATION", "Allows low-level access to setting the orientation (actually rotation) of the screen.",
    "SET_PREFERRED_APPLICATIONS", "This constant is deprecated. No longer useful, see addPackageToPreferred(String) for details. ",
    "SET_PROCESS_LIMIT", "Allows an application to set the maximum number of (not needed) application processes that can be running.",
    "SET_TIME", "Allows applications to set the system time",
    "SET_TIME_ZONE", "Allows applications to set the system time zone",
    "SET_WALLPAPER", "Allows applications to set the wallpaper",
    "SET_WALLPAPER_HINTS", "Allows applications to set the wallpaper hints",
    "SIGNAL_PERSISTENT_PROCESSES", "Allow an application to request that a signal be sent to all persistent processes",
    "STATUS_BAR", "Allows an application to open, close, or disable the status bar and its icons.",
    "SUBSCRIBED_FEEDS_READ", "Allows an application to allow access the subscribed feeds ContentProvider.",
    "SUBSCRIBED_FEEDS_WRITE", "",
    "SYSTEM_ALERT_WINDOW", "Allows an application to open windows using the type TYPE_SYSTEM_ALERT, shown on top of all other applications.",
    "UPDATE_DEVICE_STATS", "Allows an application to update device statistics.",
    "USE_CREDENTIALS", "Allows an application to request authtokens from the AccountManager",
    "VIBRATE", "Allows access to the vibrator",
    "WAKE_LOCK", "Allows using PowerManager WakeLocks to keep processor from sleeping or screen from dimming",
    "WRITE_APN_SETTINGS", "Allows applications to write the apn settings",
    "WRITE_CALENDAR", "Allows an application to write (but not read) the user's calendar data.",
    "WRITE_CONTACTS", "Allows an application to write (but not read) the user's contacts data.",
    "WRITE_EXTERNAL_STORAGE", "Allows an application to write to external storage",
    "WRITE_GSERVICES", "Allows an application to modify the Google service map.",
    "WRITE_HISTORY_BOOKMARKS", "Allows an application to write (but not read) the user's browsing history and bookmarks.",
    "WRITE_OWNER_DATA", "Allows an application to write (but not read) the owner's data.",
    "WRITE_SECURE_SETTINGS", "Allows an application to read or write the secure system settings.",
    "WRITE_SETTINGS", "Allows an application to read or write the system settings.",
    "WRITE_SMS", "Allows an application to write SMS messages.",
    "WRITE_SYNC_SETTINGS", "Allows applications to write the sync settings"
  };

  static String[] title;
  static String[] description;
  static int count;
  static {
    count = listing.length / 2;
    title = new String[count];
    description = new String[count];
    for (int i = 0; i < count; i++) {
      title[i] = listing[i*2];
      description[i] = listing[i*2+1];
    }
  }
}


// Code for this CheckBoxList class found on the net, though I've lost the
// link. If you run across the original version, please let me know so that
// the original author can be credited properly. It was from a snippet
// collection, but it seems to have been picked up so many places with others
// placing their copyright on it that I haven't been able to determine the
// original author. [fry 20100216]
@SuppressWarnings("serial")
class CheckBoxList extends JList<JCheckBox> {
  protected static Border noFocusBorder = new EmptyBorder(1, 1, 1, 1);
  int checkboxWidth;

  public CheckBoxList() {
    setCellRenderer(new CellRenderer());

    // get the width of a checkbox so we can figure out if the mouse is inside
    checkboxWidth = new JCheckBox().getPreferredSize().width;
    // add the amount for the inset
    checkboxWidth += Permissions.BORDER_HORIZ;

    addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        if (isEnabled()) {
//          System.out.println("cbw = " + checkboxWidth);
          int index = locationToIndex(e.getPoint());
//          descriptionLabel.setText(description[index]);
          if (index != -1) {
            JCheckBox checkbox = getModel().getElementAt(index);
            //System.out.println("mouse event in list: " + e);
//            System.out.println(checkbox.getSize() + " ... " + checkbox);
//            if (e.getX() < checkbox.getSize().height) {
            if (e.getX() < checkboxWidth) {
              checkbox.setSelected(!checkbox.isSelected());
              repaint();
            }
          }
        }
      }
    });
    setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
  }


  protected class CellRenderer implements ListCellRenderer<JCheckBox> {
    public Component getListCellRendererComponent(JList<? extends JCheckBox> list,
                                                  JCheckBox checkbox,
                                                  int index, boolean isSelected,
                                                  boolean cellHasFocus) {
//      checkbox.setBorder(new EmptyBorder(13, 5, 3, 5));  // trying again
      checkbox.setBackground(isSelected ? getSelectionBackground() : getBackground());
      checkbox.setForeground(isSelected ? getSelectionForeground() : getForeground());
      //checkbox.setEnabled(isEnabled());
      checkbox.setEnabled(list.isEnabled());
      checkbox.setFont(getFont());
      checkbox.setFocusPainted(false);
      checkbox.setBorderPainted(true);
      checkbox.setBorder(isSelected ? UIManager.getBorder("List.focusCellHighlightBorder") : noFocusBorder);
      return checkbox;
    }

//    @Override
//    public Component getListCellRendererComponent(JList<? extends JCheckBox> list,
//                                                  JCheckBox value, int index,
//                                                  boolean isSelected,
//                                                  boolean cellHasFocus) {
//      // TODO Auto-generated method stub
//      return null;
//    }
  }
}
