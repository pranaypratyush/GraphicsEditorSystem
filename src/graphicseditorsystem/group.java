/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicseditorsystem;

import java.awt.geom.Point2D;
import java.io.Serializable;

/**
 *
 * @author Pranay Pratyush
 */
class group implements Serializable
{

    int groupID = -1;
    String name = null;
    Boolean isAlive = true;
    Boolean isSelected = false;
    GeometricObject[] golist = new GeometricObject[100];
    int nobj = 0;
    group[] glist = new group[100];
    int ngroups = 0;
    ClipBoard cb = null;

    group(group g)
    {
        groupID = -1;
        isAlive = false;
        isSelected = false;
        nobj = g.nobj;
        ngroups = g.ngroups;
        cb = g.cb;
        name = new String(g.name);
        for (int i = 0; i < nobj; i++)
        {
            if (g.golist[i].shape == 0)
            {
                golist[i] = new circle((circle) g.golist[i]);
            }
            else if (g.golist[i].shape == 1)
            {
                golist[i] = new line((line) g.golist[i]);
            }
            else if (g.golist[i].shape == 2)
            {
                golist[i] = new rectangle((rectangle) g.golist[i]);
            }
            else if (g.golist[i].shape == 3)
            {
                golist[i] = new ellipse((ellipse) g.golist[i]);
            }
            else if (g.golist[i].shape == 4)
            {
                golist[i] = new polygon((polygon) g.golist[i]);
            }
        }
    }

    group(String s)
    {
        name = new String(s);
    }

    public void add_object(GeometricObject o)
    {
        nobj++;
        golist[nobj - 1] = o;
        o.g = this;
        o.groupID = this.groupID;
    }

    public void remove_object(GeometricObject o)
    {
        for (int i = 0; i < nobj; i++)
        {
            if (golist[i] == o)
            {
                o.groupID = -1;
                golist[i] = golist[nobj - 1];
                nobj--;
                break;
            }
        }
    }

    public void remove_from_clipboard()
    {
        cb.remove_group(this);
    }

    public void copy(int clip_id)
    {
        GraphicsEditorSystem.copy_group_clip[clip_id] = new group(this);
    }

    public void delete()
    {
        remove_from_clipboard();
        ngroups = 0;
        nobj = 0;
        isAlive = false;
        isSelected = false;
        cb.repaint();
    }

    public void move_by(Point2D Location)
    {
        for (int i = 0; i < nobj; i++)
        {
            golist[i].move_to(new Point2D.Double(golist[i].location.getX() + Location.getX(),
                    golist[i].location.getY() + Location.getY()));
        }
    }

}
