/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package graphicseditorsystem;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.Serializable;

/**
 *
 * @author Shubham Saxena
 */
public abstract class GeometricObject implements Serializable{
    Color c=Color.BLACK;
    ClipBoard cb=null;
    group g=null;
    Point2D location=null;
    Boolean isSelected=false;
    Boolean isAlive=true;
    Boolean fill=false;
    int groupID=-1;
    int linetype=0;          // 0 for continous 1 for dashed 2 for dotted
    int linesize=1;          // 0 for small 1 for medium 2 for large
    int shape=-1;            // 0 for circle 1 for line 2 for rectangle 3 for ellipse 4 for polygon
    
    public abstract void move_to(Point2D p);
    
    public void add_to_group(){
        g.add_object(this);
    }
    
    public void remove_from_group(){
        if(g!=null)
            g.remove_object(this);
    }
    
    public void add_to_clipboard(){
        cb.add_object(this);
        cb.repaint();
    }
    
    public void remove_from_clipboard(){
        if(cb!=null)
            cb.remove_object(this);
    }
    
    public void delete(){
        isAlive=false;
        c=Color.BLACK;
        location=null;
        isSelected=false;
        groupID=-1;
        linetype=0;
        linesize=1;
        shape=-1;
        remove_from_group();
        remove_from_clipboard();
    }
    
    public void draw(ClipBoard c){
        c.repaint();
    }
    
    abstract public void copy(int clip_id);
    
    public void select(){
        isSelected=true;
    }

    void fill_color(Color c) {
        this.c=c;
        fill=true;
        cb.deselect_object();
        cb.repaint();
    }
    
}
