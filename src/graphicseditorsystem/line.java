/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicseditorsystem;

import java.awt.Color;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.io.Serializable;

/**
 *
 * @author Pranay Pratyush
 */
class line extends GeometricObject implements Serializable
{

    Point2D p1 = null, p2 = null;

    line()
    {
        shape = 1;
    }

    line(line c1)
    {
        p1 = new Point2D.Double(c1.p1.getX(), c1.p1.getY());
        p2 = new Point2D.Double(c1.p2.getX(), c1.p2.getY());
        shape = 1;

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

    Line2D return2dline()
    {
        return new Line2D.Double(p1, p2);
    }

    @Override
    public void move_to(Point2D p)
    {
        p2 = new Point2D.Double(p2.getX() + p.getX() - p1.getX(), p2.getY() + p.getY() - p1.getY());
        location = p;
        p1 = p;
        cb.repaint();
    }

    @Override
    public void copy(int clip_id)
    {
        GraphicsEditorSystem.copy_object_clip[clip_id] = new line(this);
    }

}
