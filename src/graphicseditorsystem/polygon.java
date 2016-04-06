/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicseditorsystem;

import static graphicseditorsystem.GraphicsEditorSystem.canvas;
import java.awt.Polygon;
import java.awt.geom.Point2D;
import java.io.Serializable;

/**
 *
 * @author Pranay Pratyush
 */
class polygon extends GeometricObject implements Serializable
{

    int npoints = 0;
    int[] xpoints = new int[100];
    int[] ypoints = new int[100];

    polygon(polygon c1)
    {
        npoints = c1.npoints;
        for (int i = 0; i < npoints; i++)
        {
            xpoints[i] = c1.xpoints[i];
            ypoints[i] = c1.ypoints[i];
        }
        shape = 4;

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

    polygon()
    {
        shape = 4;
    }

    Polygon returnpolygon()
    {
        int[] xp = new int[npoints];
        int[] yp = new int[npoints];
        for (int i = 0; i < npoints; i++)
        {
            xp[i] = xpoints[i];
            yp[i] = ypoints[i];
        }
        return new Polygon(xp, yp, npoints);
    }

    @Override
    public void move_to(Point2D p)
    {
        /*for(int i=1;i<npoints;i++){
            xpoints[i]=(int) (xpoints[i]+p.getX()-xpoints[0]);
            ypoints[i]=(int) (ypoints[i]+p.getY()-xpoints[0]);
        }
        location=p;
        xpoints[0]=(int) p.getX();
        ypoints[0]=(int) p.getY();
        cb.repaint();
         */
        int[] xp = new int[npoints];
        int[] yp = new int[npoints];
        for (int i = 0; i < npoints; i++)
        {
            xp[i] = xpoints[i];
            yp[i] = ypoints[i];
        }
        Polygon p1 = new Polygon(xp, yp, npoints);
        int x = (int) (p.getX() - location.getX());
        int y = (int) (p.getY() - location.getY());
        p1.translate(x, y);
        p1.invalidate();
        for (int i = 0; i < npoints; i++)
        {
            xpoints[i] = p1.xpoints[i];
            ypoints[i] = p1.ypoints[i];
        }
        location = new Point2D.Double(xpoints[0], ypoints[0]);
        canvas.repaint();
    }

    @Override
    public void copy(int clip_id)
    {
        GraphicsEditorSystem.copy_object_clip[clip_id] = new polygon(this);
    }

}
