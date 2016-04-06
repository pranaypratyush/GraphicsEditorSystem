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
class circle extends GeometricObject implements Serializable
{

    double radius;
    Point2D center;

    circle()
    {
        shape = 0;
    }

    circle(circle c1)
    {
        radius = c1.radius;
        center = new Point2D.Double(c1.center.getX(), c1.center.getY());
        shape = 0;

        c = c1.c;
        cb = c1.cb;
        g = null;
        location = new Point2D.Double(center.getX(), center.getY());
        isSelected = false;
        isAlive = true;
        fill = c1.fill;
        groupID = -1;
        linetype = c1.linetype;
        linesize = c1.linesize;
    }

    Ellipse2D return2dcircle()
    {
        return new Ellipse2D.Double(center.getX() - radius, center.getY() - radius, 2 * radius, 2 * radius);
    }

    @Override
    public void move_to(Point2D p)
    {
        location = p;
        center = p;
        cb.repaint();
    }

    @Override
    public void copy(int clip_id)
    {
        GraphicsEditorSystem.copy_object_clip[clip_id] = new circle(this);
    }

}
