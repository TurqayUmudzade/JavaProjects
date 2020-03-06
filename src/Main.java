import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

@SuppressWarnings("serial")
public class Main extends JPanel implements ActionListener,FileOperations {

    //Shapes
    private static Square mySquare;
    private static Circle myCircle;
    //Booleans for if statements
    public boolean SquareCheck, CircleCheck, UndoCheck, RedoCheck, UndoWasChecked, Select;

    //Swing Components
    private JToggleButton buttonSquare, buttonCircle;
    JFrame frame;
    private String fileName;
    private JButton red, blue, yellow, green, black, white;
    private JButton undo, redo, save, load, select, clear, erase;
    //Object manipulation variables
    private int listPosition;
    int x, y, x2, y2;
    int preX, preY;

    private static List<Object> myShapes;
    private static Color myColor;
    int pressOut = -1;
    int eraseme;


    //Paints and draws the shapes
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (SquareCheck) {//if Square Button was selected
            buttonSquare.setSelected(true);
            buttonCircle.setSelected(false);
            //If undo was pressed before adding
            if (UndoWasChecked) {
                for (int i = myShapes.size() - 1; i >= listPosition; i--) {
                    myShapes.remove(i);
                }
            } else {
                for (int i = myShapes.size() - 1; i > listPosition; i--) {
                    myShapes.remove(i);
                }
            }
            drawPerfectRect(g, x, y, x2, y2, mySquare.color);//Draw the Rectangle
            listPosition = myShapes.size();
            UndoWasChecked = false;
        }
        if (CircleCheck) {//if Circle was button was Pressed
            buttonCircle.setSelected(true);
            buttonSquare.setSelected(false);
            if (UndoWasChecked) {
                for (int i = myShapes.size() - 1; i >= listPosition; i--) {
                    myShapes.remove(i);
                }
            } else {
                for (int i = myShapes.size() - 1; i > listPosition; i--) {
                    myShapes.remove(i);
                }
            }
            drawPerfectOval(g, x, y, x2, y2, myCircle.color);
            listPosition = myShapes.size();
            UndoWasChecked = false;
        }
        if (UndoCheck) {
            UndoWasChecked = true;
            UndoCheck = false;
            if ((listPosition) > 0) {//Position is to know where we are in the list was undos and redos
                listPosition--;
            }
        }
        if (RedoCheck) {
            RedoCheck = false;
            if (!(listPosition == myShapes.size())) {
                listPosition++;
            }
        }
        if (myShapes.size() > 0) {
            //Draw all the previes elements until the position
            for (int i = 1; i <= listPosition; i++) {
                if (myShapes.get(i - 1) instanceof Square) {
                    drawPerfectRect(g, ((Square) myShapes.get(i - 1)).x, ((Square) myShapes.get(i - 1)).y, (((Square) myShapes.get(i - 1)).width + ((Square) myShapes.get(i - 1)).x), (((Square) myShapes.get(i - 1)).length + ((Square) myShapes.get(i - 1)).y), ((Square) myShapes.get(i - 1)).color);

                } else if (myShapes.get(i - 1) instanceof Circle) {
                    drawPerfectOval(g, ((Circle) myShapes.get(i - 1)).x, ((Circle) myShapes.get(i - 1)).y, (((Circle) myShapes.get(i - 1)).width + ((Circle) myShapes.get(i - 1)).x), (((Circle) myShapes.get(i - 1)).length + ((Circle) myShapes.get(i - 1)).y), ((Circle) myShapes.get(i - 1)).color);
                }
            }
        }
    }

    //Build the GUI
    public Main() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        x = y = x2 = y2 = 0;

        MyMouseListener listener = new MyMouseListener();
        addMouseListener(listener);
        addMouseMotionListener(listener);

        buttonSquare = new JToggleButton("■");
        buttonCircle = new JToggleButton("●");

        buttonCircle.addActionListener(this);
        buttonSquare.addActionListener(this);

        this.add(buttonCircle);
        this.add(buttonSquare);

        red = new JButton("   ");
        red.setBackground(Color.red);
        red.addActionListener(this);
        this.add(red);

        blue = new JButton("   ");
        blue.setBackground(Color.blue);
        blue.addActionListener(this);
        this.add(blue);

        yellow = new JButton("   ");
        yellow.setBackground(Color.yellow);
        yellow.addActionListener(this);
        this.add(yellow);

        green = new JButton("   ");
        green.setBackground(Color.green);
        green.addActionListener(this);
        this.add(green);

        black = new JButton("   ");
        black.setBackground(Color.black);
        black.addActionListener(this);
        this.add(black);

        white = new JButton("   ");
        white.setBackground(Color.white);
        white.addActionListener(this);
        this.add(white);

        undo = new JButton("UNDO");
        undo.addActionListener(this);
        this.add(undo);

        redo = new JButton("REDO");
        redo.addActionListener(this);
        this.add(redo);

        save = new JButton("save");
        save.addActionListener(this);
        this.add(save);

        load = new JButton("load");
        load.addActionListener(this);
        this.add(load);

        select = new JButton("select");
        select.addActionListener(this);
        this.add(select);

        erase = new JButton("Erase");
        erase.addActionListener(this);
        this.add(erase);

        clear = new JButton("clear");
        clear.addActionListener(this);
        this.add(clear);
        frame.add(this);

        frame.setSize(800, 500);
        frame.setVisible(true);

    }

    //button actions
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttonSquare) {
            CircleCheck = false;
            SquareCheck = true;
        } else if (e.getSource() == buttonCircle) {
            SquareCheck = false;
            CircleCheck = true;
        }
        if (e.getSource() == red) {
            myColor = Color.red;
        }
        if (e.getSource() == blue) {
            myColor = Color.blue;
        }
        if (e.getSource() == black) {
            myColor = Color.black;
        }
        if (e.getSource() == green) {
            myColor = Color.green;
        }
        if (e.getSource() == yellow) {
            myColor = Color.yellow;
        }
        if (e.getSource() == white) {
            myColor = Color.white;
        }
        if (e.getSource() == undo) {
            SquareCheck = false;
            CircleCheck = false;
            UndoCheck = true;
            repaint();
        }
        if (e.getSource() == redo) {
            SquareCheck = false;
            CircleCheck = false;
            RedoCheck = true;
            repaint();
        }
        if (e.getSource() == save) {
            for (int i = myShapes.size(); listPosition < i; i--) {//Remove elements that were undo-ed so we dont save the whole list
                myShapes.remove(i - 1);
            }
            fileName = JOptionPane.showInputDialog("Enter File Name");
            fileName = fileName.trim();//remove spaces
            fileName += ".ser";//Save as file.ser
            try {
                saveToFile(fileName, myShapes);
            } catch (IOException ex) {//in case the save fails
                ex.printStackTrace();
            }
        }

        if (e.getSource() == load) {
            final JFileChooser fc = new JFileChooser();//Opens the file Chooser
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Serialize", "ser", ".ser");//We allow only .ser files
            fc.setFileFilter(filter);

            int returnVal = fc.showOpenDialog(frame);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();//get our file
                fileName = file.getName();
            } else {
                System.out.println("Error");//if no file found
            }

            try {
                myShapes = (List<Object>) restoreFromFile(fileName);//Gettings the List from the file
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
            listPosition = myShapes.size();
            repaint();//Draw the list from the file
        }

        if (e.getSource() == select) {
            SquareCheck = false;
            CircleCheck = false;
            RedoCheck = false;
            UndoCheck = false;
            Select = true;
            repaint();
        }

        if (e.getSource() == erase) {
            if (Select){
                int answer=JOptionPane.showConfirmDialog(frame,"Are you sure you wanna erase?This action cant be undone");
                if (answer==0){
                    myShapes.remove(eraseme - 1);
                    listPosition--;
                    repaint();
                }
            }else{
                JOptionPane.showMessageDialog(frame,"Must Select before erasing");
            }
            Select=false;

        }
        if (e.getSource()==clear){
            int answer=JOptionPane.showConfirmDialog(frame,"Are you sure you wanna clear?This action cant be undone");
            if (answer==0){
                myShapes.clear();
                SquareCheck = false;
                CircleCheck = false;
                repaint();
            }
        }

    }

    public void setStartPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setEndPoint(int x, int y) {
        x2 = x;
        y2 = y;
    }

    public void drawPerfectRect(Graphics g, int x, int y, int x2, int y2, Color color) {
        int px = Math.min(x, x2);
        int py = Math.min(y, y2);
        int pw = Math.abs(x - x2);
        int ph = Math.abs(y - y2);
        g.setColor(color);
        g.drawRect(px, py, pw, ph);
    }

    public void drawPerfectOval(Graphics g, int x, int y, int x2, int y2, Color color) {
        int px = Math.min(x, x2);
        int py = Math.min(y, y2);
        int pw = Math.abs(x - x2);
        int ph = Math.abs(y - y2);
        g.setColor(color);
        g.drawOval(px, py, pw, ph);
    }


    class MyMouseListener extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            setStartPoint(e.getX(), e.getY());

            if (Select) {
                for (int i = listPosition; i > 0; i--) {
                    if (myShapes.get(i - 1) instanceof Square) {
                        if (((Square) myShapes.get(i - 1)).x < e.getX() && ((Square) myShapes.get(i - 1)).y < e.getY() && (((Square) myShapes.get(i - 1)).width + ((Square) myShapes.get(i - 1)).x) > e.getX() && (((Square) myShapes.get(i - 1)).length + ((Square) myShapes.get(i - 1)).y) > e.getY()) {
                            pressOut = i;
                            preX = ((Square) myShapes.get(i - 1)).x - e.getX();
                            preY = ((Square) myShapes.get(i - 1)).y - e.getY();
                            System.out.println("There is a square");
                            eraseme = pressOut;
                        }
                    }
                    if (myShapes.get(i - 1) instanceof Circle) {
                        if (((Circle) myShapes.get(i - 1)).x < e.getX() && ((Circle) myShapes.get(i - 1)).y < e.getY() && (((Circle) myShapes.get(i - 1)).width + ((Circle) myShapes.get(i - 1)).x) > e.getX() && (((Circle) myShapes.get(i - 1)).length + ((Circle) myShapes.get(i - 1)).y) > e.getY()) {
                            System.out.println("There is a circle");
                            pressOut = i;
                            preX = ((Circle) myShapes.get(i - 1)).x - e.getX();
                            preY = ((Circle) myShapes.get(i - 1)).y - e.getY();
                            eraseme = pressOut;
                        }
                    }
                }
            }
        }

        public void mouseDragged(MouseEvent e) {
            if (Select) {
                for (int i = listPosition; i > 0; i--) {
                    if (pressOut == i) {
                        if (myShapes.get(i - 1) instanceof Square) {
                            ((Square) myShapes.get(i - 1)).x = e.getX() + preX;
                            ((Square) myShapes.get(i - 1)).y = e.getY() + preY;
                        }
                        if (myShapes.get(i - 1) instanceof Circle) {
                            ((Circle) myShapes.get(i - 1)).x = e.getX() + preX;
                            ((Circle) myShapes.get(i - 1)).y = e.getY() + preY;
                        }
                    }
                }
            }
            setEndPoint(e.getX(), e.getY());
            repaint();
        }

        public void mouseReleased(MouseEvent e) {
            setEndPoint(e.getX(), e.getY());
            repaint();
            pressOut = -1;
            if (SquareCheck) {
                myShapes.add(new Square(Math.min(x, e.getX()), Math.min(y, e.getY()), Math.abs(e.getX() - x), Math.abs(e.getY() - y), myColor));//adds the new Shape to the list
            } else if (CircleCheck) {
                myShapes.add(new Circle(Math.min(x, e.getX()), Math.min(y, e.getY()), Math.abs(e.getX() - x), Math.abs(e.getY() - y), myColor));
            }
        }
    }


    static void saveToFile(String outputFile, Object object) throws IOException {
        FileOutputStream fileOut = new FileOutputStream(outputFile);
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(object);
        objectOut.close();
        System.out.println("The Object  was successfully written to a file");
    }

    //Restores the Object from a file
    static Object restoreFromFile(String filepath) throws IOException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream(filepath);
        ObjectInputStream objectIn = new ObjectInputStream(fileIn);
        Object obj = objectIn.readObject();
        System.out.println("The Object has been read from the file");
        objectIn.close();
        return obj;
    }



    public static void main(String[] args) {
        mySquare = new Square(30, 50, 50, 50, myColor);
        myCircle = new Circle(60, 100, 50, 50, myColor);
        myShapes = new ArrayList<Object>();
        new Main();
    }
}