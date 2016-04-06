/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicseditorsystem;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.io.Serializable;

/**
 *
 * @author Pranay Pratyush
 */
class ellipse extends GeometricObject implements Serializable
{

    double HAxis, VAxis;
    Point2D center;

    ellipse()
    {
        shape = 3;
    }

    ellipse(ellipse c1)
    {
        HAxis = c1.HAxis;
        VAxis = c1.VAxis;
        center = new Point2D.Double(c1.center.getX(), c1.center.getY());
        shape = 3;

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

    Ellipse2D returnellipse2D()
    {
        return new Ellipse2D.Double(center.getX() - HAxis / 2, center.getY() - VAxis / 2, HAxis, VAxis);
    }

    @Override
    public void move_to(Point2D p)
    {
        location = p;
        center = new Point2D.Double(p.getX() + HAxis / 2, p.getY() + VAxis / 2);
        cb.repaint();
    }

    @Override
    public void copy(int clip_id)
    {
        GraphicsEditorSystem.copy_object_clip[clip_id] = new ellipse(this);
    }

}
