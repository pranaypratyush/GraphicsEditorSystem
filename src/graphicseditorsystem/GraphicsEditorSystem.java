/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicseditorsystem;

/**
 *
 * @author Pranay Pratyush
 */
public class GraphicsEditorSystem
{

    /**
     * @param args the command line arguments
     */
    static GeometricObject[] copy_object_clip = new GeometricObject[11];
    static group[] copy_group_clip = new group[11];
    static ClipBoard canvas = null;
    static String currentfilename = "Untitled.gef";

    public static void main(String[] args)
    {
        canvas = new ClipBoard();
        gesworkspace g = new gesworkspace();
        g.setTitle(currentfilename + " - Graphics Editor System");
        g.setVisible(true);
        g.add(canvas);
        canvas.requestFocusInWindow();
        canvas.setVisible(true);
        canvas.repaint();
    }

}
