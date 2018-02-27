///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:  	     ImageLoopEditor.java
// File:             ImageLoopEditor.java
// Semester:         (CS367) Spring 2018
//
// Author:           Xianrun (Sheeran) Qu (xqu25@wisc.edu
// CS Login:         xianrun
// Lecturer's Name:  Charles Fischer
// Lab Section:      NA
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ////////////////////
//
// Author:           Yiqiao (Bob) Xin
// Email:            xin23@wisc.edu
// CS Login:         yiqiao
// Lecturer's Name:  Charles Fischer
// Lab Section:      NA
//
//////////////////// STUDENTS WHO GET HELP FROM OTHER THAN THEIR PARTNER //////
//                   fully acknowledge and credit all sources of help,
//                   other than Instructors and TAs.
//
// Persons:          Identify persons by name, relationship to you, and email.
//                   Describe in detail the the ideas and help they provided.
//
// Online sources:   avoid web searches to solve your problems, but if you do
//                   search, be sure to include Web URLs and description of 
//                   of any information you find.
//////////////////////////// 80 columns wide //////////////////////////////////
import java.io.*;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;

/********************************
 This class implements a GUI-based editor for an Image Loop editor.
 A LinkedLoop<Image> named loop is declared.
 You must complete the code in the methods named pushXXX
 to implement the individual editor commands.
 ********************************/
public class ImageLoopEditor {

    protected static LoopADT < Image > loop = new LinkedLoop < Image > ();

    /**
     * Helper class, update the current node to desired position
     *
     * @param The desired destination
     * @return NA
     */

    private static void moveCurr(Image dest) {
        try {
            while (loop.getCurrent() != dest) {
                loop.next();
            }
        } catch (Exception e) {
            System.out.println("moveCurr");
        }
    }

    /**
     * Helper class, display the context in the requirement
     *
     * @param display the context, with nimg as current
     * @return the display string
     */
    private static String getContext(Image img) {
        String ret = "";

        try {
            if (loop.size() == 1)
                ret = "--> " + img.toString() + " <--\n";

            else if (loop.size() == 2) {
                ret = "--> " + img.toString() + " <--\n";
                loop.next();
                ret += "\t" + loop.getCurrent().toString() + "\n";
                loop.previous();
            } else {
                loop.previous();
                ret = '\t' + loop.getCurrent().toString() + "\n";
                loop.next();
                ret += "--> \t" + img.toString() + " <--\n";
                loop.next();
                ret += '\t' + loop.getCurrent().toString() + "\n";
                loop.previous();
            }
        } catch (Exception e) {
            System.out.println("Find");
        }
        return ret;
    }
    /**
     * find and update image
     *
     * @param the title to find
     * @return display string of function
     */
    // Methods that implement the GUI buttons' actions, quotes attention
    static String pushFind(String title) {
        // Add code here to implement this GUI button
        if (title.length() > 1 && (title.charAt(0) == '\"' || title.charAt(0) == '\"'))
            title = title.substring(1, title.length());

        if (loop.isEmpty())
            return "no images";
        Iterator < Image > itr = loop.iterator();
        Image img = null;
        while (itr.hasNext()) {
            img = itr.next();
            String temp = img.getTitle();
            if (temp.contains(title))
                break;
            if (!itr.hasNext())
                return "not found";
        }
        moveCurr(img); //move to the new image
        String ret = getContext(img);

        return ret;
    }
    /**
     * save current list to file
     *
     * @param the saved filename
     * @return display string of function
     */
    static String pushSave(String filename) {
        // Add code here to implement this GUI button
        if (loop.isEmpty())
            return "no images to save";
        String ret = "";
        try {
            File fl = new File(filename);
            if (fl.exists())
                ret = "warning: file already exists, will be overwritten";

            Iterator < Image > itr = loop.iterator();
            String sum = "";
            while (itr.hasNext()) {
                Image img = itr.next();
                String ititle = (img.getTitle() == null) ? "" : img.getTitle();
                sum += img.getFile() + " " + img.getDuration() + " \"" + ititle + "\"\n";
            }
            if (sum.length() != 0)
                sum = sum.substring(0, sum.length() - 1);
            PrintStream fw = new PrintStream(new FileOutputStream(fl));
            fw.append(sum.trim());
            fw.close();
        } catch (Exception e) {
            return "unable to save";
        }
        return ret;
    }
    /**
     * load images to loop
     *
     * @param the file to load
     * @return display string of function
     */
    static String pushLoad(String filename) {
        // Add code here to implement this GUI button
        //System.out.println("in");
        String ret = "";
        try {
            File src = new File(filename); //source file
            if (!src.exists())
                return "unable to load";

            Scanner reader = new Scanner(src);
            loop = new LinkedLoop < Image > ();
            List < Image > tbl = new ArrayList < Image > (); //store image item
            while (reader.hasNextLine()) {
                String temp = reader.nextLine().trim();
                String[] templst = temp.split(" ");
                File tempf = new File("images", templst[0]);
                if (!tempf.exists()) {
                    return "Warning: " + templst[0] + " is not in images folder";
                }
                int dur = 0;
                try {
                    dur = Integer.parseInt(templst[1].trim());
                } catch (Exception e) {
                    return "number format,Load";
                }
                String ttl = temp.substring(templst[0].length() + templst[1].length());
                ttl = (ttl.length() == 1) ? "" : ttl.substring(1);
                ttl = ttl.trim();
                if (ttl.length() >= 2 && ttl.charAt(0) == '\"' && ttl.charAt(ttl.length() - 1) == '\"')
                    ttl = ttl.substring(1, ttl.length() - 1);
                Image img = new Image(templst[0], ttl, dur);
                tbl.add(img);
            }
            reader.close(); //end of reading
            //System.out.println("cool man");
            //put image items in order
            for (int i = tbl.size() - 1; i >= 0; i--) {
                loop.add(tbl.get(i));
            }
        } catch (Exception e) {
            //System.out.println( "unable to load, Load :" + e.toString());
        }
        return ret;
    }
    /**
     * add the image after current
     *
     * @param the name of image file
     * @return display string of function
     */
    // add new image AFTER current image
    static String pushAddImage(String filename) {
        // Add code here to implement this GUI button
        File tempf = new File("images", filename);
        if (!tempf.exists())
            return "Warning: " + filename + " is not in images folder";
        Image addee = new Image(filename, "", 5);
        if (loop.isEmpty()) {

            loop.add(addee);
        } else {
            loop.next();
            loop.add(addee);
        }
        try {
            return getContext(loop.getCurrent());
        } catch (Exception e) {
            return "addimage fault";
        }
    }
    /**
     * insert the image to previous
     *
     * @param the image to insert
     * @return display string of function
     */
    // insert new image BEFORE current image
    static String pushInsertImage(String filename) {
        // Add code here to implement this GUI button
        File tempf = new File("images", filename);
        if (!tempf.exists())
            return "Warning: " + filename + " is not in images folder";
        Image addee = new Image(filename, "", 5);
        if (loop.isEmpty()) {

            loop.add(addee);
        } else {
            loop.add(addee);
        }
        try {
            return getContext(loop.getCurrent());
        } catch (Exception e) {
            return "insert image fault";
        }
    }

    static String pushJump(String count) {
        // Add code here to implement this GUI button
        if (loop.isEmpty())
            return "no images";
        int ct = 0;
        Boolean forw;
        try {
            ct = Integer.parseInt(count.trim());
            forw = (ct > 0) ? true : false;
            ct = (ct > 0) ? ct : (-1 * ct);
        } catch (Exception e) {
            return "invalid input, jump";
        }

        while (ct > 0) {
            if (forw)
                loop.next();
            else
                loop.previous();
            ct--;
        }
        try {
            return (getContext(loop.getCurrent()));
        } catch (Exception e) {
            return "invalid, jump";
        }
    }
    /**
     * update time
     *
     * @param the time to update
     * @return display string of function
     */
    static String pushUpdate(String title) {
        // Add code here to implement this GUI button
        if (loop.isEmpty()) {
            return "no images";
        } else {
            try {
                int time = Integer.parseInt(title.trim());
                loop.getCurrent().setDuration(time);
                return getContext(loop.getCurrent());
            } catch (Exception e) {
                return "problems of Update";
            }
        }

    }

    /**
     * edit the current title
     *
     * @param the title to update
     * @return display string of function
     */
    static String pushEdit(String title) {
        // Add code here to implement this GUI button
        if (loop.isEmpty()) {
            return "no images";
        } else {
            try {
                loop.getCurrent().setTitle(title);
                return getContext(loop.getCurrent());
            } catch (Exception e) {
                return "bug @ edit";
            }
        }
    }
    /**
     * show the list
     *
     * @param NA
     * @return display string of function
     */
    static String pushDisplay() {
        // Add code here to implement this GUI button
        if (loop.isEmpty())
            return "no images";
        String ret = "";
        Iterator < Image > imglst = loop.iterator();
        //System.out.println(imglst.hasNext());
        while (imglst.hasNext()) {
            ret += imglst.next().toString() + "\n";
            //System.out.println("here in");
        }
        return ret.trim();
    }
    /**
     * display current image
     *
     * @param NA
     * @return display string of function
     */
    static String pushShow() {
        // Add code here to implement this GUI button
        if (loop.isEmpty())
            return "no images";
        try {
            loop.getCurrent().displayImage();
        } catch (Exception e) {
            return "fault in Show";
        }
        return "";
    }

    /**
     * show the current images in list
     *
     * @param NA
     * @return display string of function
     */
    static String pushTest() {
        // Add code here to implement this GUI button
        if (loop.isEmpty())
            return "no images";
        Iterator < Image > itr = loop.iterator();
        List < Image > imglst = new ArrayList < Image > ();
        while (itr.hasNext())
            imglst.add(itr.next());
        Image.displayImageList(imglst);
        return "";
    }
    /**
     * remove the current image
     *
     * @param the title to find
     * @return display string of function
     */
    static String pushRemove() {
        // Add code here to implement this GUI button
        if (loop.isEmpty() || loop.size() == 1)
            return "no images";
        try {
            loop.removeCurrent();
            return getContext(loop.getCurrent());
        } catch (Exception e) {
            return "Fault @ remove";
        }
    }


    static String pushForward() {
        // Add code here to implement this GUI button
        if (loop.isEmpty())
            return "no images";
        try {
            loop.next();
            return getContext(loop.getCurrent());
        } catch (Exception e) {
            return "Fault @ Forw";
        }
    }


    static String pushBack() {
        if (loop.isEmpty())
            return "no images";
        try {
            loop.previous();
            return getContext(loop.getCurrent());
        } catch (Exception e) {
            return "Fault @ Forw";
        }
    }


    static String pushHelp() {
        // You may use this method as implemented here
        String cmds = "";
        cmds += "Available commands:\n" +
            "\tSave image loop into filename\n" +
            "\tLoad image loop from filename\n" +
            "\tAdd Image at filename after current image\n" +
            "\tInsert Image at filename before current image\n" +
            "\tFind image matching title\n" +
            "\tUpdate display time of current image\n" +
            "\tEdit title of current image\n" +
            "\tJump count images\n" +
            "\tDisplay loop\n" +
            "\tShow current image in a window\n" +
            "\tTest image loop by showing all images\n" +
            "\tRemove current image from loop\n" +
            "\tMove current image forward one step\n" +
            "\tMove current image back one step\n" +
            "\tHelp on available commands\n" +
            "\tQuit and close this GUI\n";
        return cmds;
    }

    static String pushQuit() {
        // You may use this method as implemented here
        System.exit(0);
        return "";
    }


    /********************************
      The following method actually implements our GUI.
    	Major components are buttons and text fields.
    	The components are defined and placed (in terms of pixels).
    	You should not change any of this unless you really know what you
    	are doing.
    	Each button has an "action listener."
    	When you push a button, the action listener calls a
    	pushXXX method that YOU must define.
    *********************************/
    public static void runGUI() {

        //f is the JFrame that will be our GUI
        JFrame f = new JFrame("Image Loop");
        // We define the buttons and text areas that will fill the GUI
        // The locations of each component are set, in terms of pixels
        final JTextField tf1 = new JTextField("");
        JTextArea ta = new JTextArea();
        ta.setTabSize(4);
        ta.setBounds(50, 450, 370, 300);
        JButton b1 = new JButton("Save");
        b1.setBounds(50, 25, 110, 30);
        tf1.setBounds(170, 25, 160, 30);
        tf1.setText("filename");
        JButton b2 = new JButton("Load");
        b2.setBounds(50, 60, 110, 30);
        final JTextField tf2 = new JTextField("");
        tf2.setBounds(170, 60, 160, 30);
        tf2.setText("filename");
        JButton b3 = new JButton("Add after");
        b3.setBounds(50, 95, 110, 30);
        final JTextField tf3 = new JTextField("");
        tf3.setBounds(170, 95, 150, 30);
        tf3.setText("filename of image");
        JButton b4 = new JButton("Insert before");
        b4.setBounds(50, 130, 110, 30);
        final JTextField tf4 = new JTextField("");
        tf4.setBounds(170, 130, 150, 30);
        tf4.setText("filename of image");
        JButton b5 = new JButton("Find");
        b5.setBounds(50, 165, 110, 30);
        final JTextField tf5 = new JTextField("");
        tf5.setBounds(170, 165, 150, 30);
        tf5.setText("title");
        JButton b6 = new JButton("Update");
        b6.setBounds(50, 200, 110, 30);
        final JTextField tf6 = new JTextField("");
        tf6.setBounds(170, 200, 150, 30);
        tf6.setText("time");
        JButton b7 = new JButton("Edit");
        b7.setBounds(50, 235, 110, 30);
        final JTextField tf7 = new JTextField("");
        tf7.setBounds(170, 235, 150, 30);
        tf7.setText("title");
        JButton b8 = new JButton("Jump");
        b8.setBounds(50, 270, 110, 30);
        final JTextField tf8 = new JTextField("");
        tf8.setBounds(170, 270, 150, 30);
        tf8.setText("count");
        JButton b9 = new JButton("Display loop");
        b9.setBounds(50, 305, 110, 30);
        JButton b10 = new JButton("Show image");
        b10.setBounds(170, 305, 110, 30);
        JButton b11 = new JButton("Test loop");
        b11.setBounds(50, 340, 110, 30);
        JButton b12 = new JButton("Remove image");
        b12.setBounds(170, 340, 120, 30);
        JButton b13 = new JButton("Move forward");
        b13.setBounds(50, 375, 110, 30);
        JButton b14 = new JButton("Move backward");
        b14.setBounds(170, 375, 110, 30);
        JButton b15 = new JButton("Quit");
        b15.setBounds(50, 410, 110, 30);
        JButton b16 = new JButton("Help");
        b16.setBounds(170, 410, 110, 30);


        //  The effect of pushing a GUI button is defined in an ActionListener
        //  The actionPerformed method is executed when the associated button is pushed

        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ta.setText(pushSave(tf1.getText()));
            }
        });

        b2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ta.setText(pushLoad(tf2.getText()));
            }
        });

        b3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ta.setText(pushAddImage(tf3.getText()));
            }
        });

        b4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ta.setText(pushInsertImage(tf4.getText()));
            }
        });

        b5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ta.setText(pushFind(tf5.getText()));
            }
        });

        b6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ta.setText(pushUpdate(tf6.getText()));
            }
        });

        b7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ta.setText(pushEdit(tf7.getText()));
            }
        });

        b8.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ta.setText(pushJump(tf8.getText()));
            }
        });


        b9.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ta.setText(pushDisplay());
            }
        });


        b10.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                ta.setText(pushShow());
            }
        });


        b11.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ta.setText(pushTest());
            }
        });

        b12.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ta.setText(pushRemove());
            }
        });


        b13.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ta.setText(pushForward());
            }
        });

        b14.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ta.setText(pushBack());
            }
        });


        b15.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ta.setText(pushQuit());
            }
        });


        b16.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ta.setText(pushHelp());
            }
        });

        //Buttons and text frames are added to the JFrame to build the GUI

        f.add(b1);
        f.add(tf1);
        f.add(ta);
        f.add(b1);
        f.add(tf1);
        f.add(ta);
        f.add(b1);
        f.add(tf1);
        f.add(ta);
        f.add(b2);
        f.add(tf2);
        f.add(b3);
        f.add(tf3);
        f.add(b4);
        f.add(tf4);
        f.add(b5);
        f.add(tf5);
        f.add(b6);
        f.add(tf6);
        f.add(b7);
        f.add(tf7);
        f.add(b8);
        f.add(tf8);
        f.add(b9); //f.add(tf9);
        f.add(b10); //f.add(tf10);
        f.add(b11); //f.add(tf10);
        f.add(b12); //f.add(tf10);
        f.add(b13); //f.add(tf10);
        f.add(b14); //f.add(tf10);
        f.add(b15); //f.add(tf10);
        f.add(b16); //f.add(tf10);
        f.setBounds(50, 50, 500, 800);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // The GUI is now up and running!

    }


    public static void main(String[] args) {
        runGUI();
    }
}
