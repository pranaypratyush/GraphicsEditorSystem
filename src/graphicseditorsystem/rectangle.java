/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicseditorsystem;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

/**
 *
 * @author Pranay Pratyush
 */
class rectangle extends GeometricObject implements Serializable
{

    double length, breadth;
    Point2D topleft;

    rectangle()
    {
        shape = 2;
    }

    rectangle(rectangle c1)
    {
        length = c1.length;
        breadth = c1.breadth;
        topleft = new Point2D.Double(c1.topleft.getX(), c1.topleft.getY());
        shape = 2;

        c = c1.c;
        cb = c1.cb;
        g = null;
        location = new Point2D.Double(c1.location.getX(), c1.location.getY());
        isSelected = false;
        isAlive = true;
        fill = c1.fill;
        groupID = -1;
        linetype = c1.linetype;
        linesize = c1.linesize;
    }

    Rectangle2D return2Drectangle()
    {
        return new Rectangle2D.Double(topleft.getX(), topleft.getY(), breadth, length);
    }

    @Override
    public void move_to(Point2D p)
    {
        topleft = p;
        location = p;
        cb.repaint();
    }

    @Override
    public void copy(int clip_id)
    {
        GraphicsEditorSystem.copy_object_clip[clip_id] = new rectangle(this);
    }

}
