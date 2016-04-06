/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicseditorsystem;

import static graphicseditorsystem.GraphicsEditorSystem.copy_group_clip;
import static graphicseditorsystem.GraphicsEditorSystem.copy_object_clip;
import static graphicseditorsystem.gesworkspace.cur_but;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import static java.lang.Math.sqrt;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 *
 * @author Shubham Saxena
 */
public class ClipBoard extends javax.swing.JPanel implements Serializable, KeyListener, MouseListener, MouseMotionListener
{

    int keyPressed;
    boolean zoom = true;
    boolean zoomEnabled = true;
    int zoomCommandGiven = 0;
    Double zoomIn = 1.0;
    Double zoomOut = 1.0;
    ArrayList<Text> textList = new ArrayList<>();

    public ClipBoard()
    {
        ClipBoardinit();
    }

    public void ClipBoardinit()
    {
        initComponents();
        this.setSize(1800, 1000);
        this.setLocation(0, 131);
        this.setBackground(Color.WHITE);
    }

    private void initComponents()
    {
        this.requestFocusInWindow();
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addKeyListener(this);
    }

    public void keyTyped(KeyEvent evt)
    {

        int code = this.keyPressed;
        if (code == KeyEvent.VK_E)
        {
            this.removeMouseListener(this);
            this.removeMouseMotionListener(this);
            if (this.zoom)
            {
                this.zoomEnabled = true;
                this.zoomCommandGiven = 1;
                this.zoomIn += 0.1;
                this.zoom = true;
            } else
            {
                this.zoomEnabled = true;
                this.zoomCommandGiven = -1;
                this.zoomOut -= 0.1;
                this.zoom = false;
            }
        }
        if (code == KeyEvent.VK_S)
        {
            this.removeMouseListener(this);
            this.removeMouseMotionListener(this);
            if (this.zoom)
            {
                if (this.zoomIn != 1.0)
                {
                    this.zoomIn -= 0.1;
                    this.zoomEnabled = true;
                    this.zoomCommandGiven = +1;
                    this.zoom = true;
                } else
                {
                    this.zoomEnabled = true;
                    this.zoomCommandGiven = -1;
                    this.zoomOut += 0.1;
                    this.zoom = false;
                }
            } else
            {
                this.zoomEnabled = true;
                this.zoomCommandGiven = -1;
                this.zoomOut += 0.1;
                this.zoom = false;
            }
        }
        if (code == KeyEvent.VK_ENTER)
        {
            this.addMouseListener(this);
            this.addMouseMotionListener(this);
            this.zoomEnabled = false;
            this.zoomCommandGiven = 0;
            this.zoomIn = 1.0;
            this.zoomOut = 1.0;
        }
        repaint();
    }

    circle c = null;
    line l = null;
    rectangle r = null;
    ellipse e = null;
    polygon p = null;
    Boolean bclick = false;

    public void mouseClicked(java.awt.event.MouseEvent evt)
    {//GEN-FIRST:event_formMouseClicked
        deselect_group();
        if (cur_but == 0)
        {
            if (JOptionPane.showConfirmDialog(this, "Create Circle?") == JOptionPane.YES_OPTION)
            {
                circle c = new circle();
                c.center = evt.getPoint();
                c.location = c.center;
                c.radius = Double.valueOf(JOptionPane.showInputDialog("radius:"));
                add_object(c);
                repaint();
            }
        } else if (cur_but == 1)
        {
            if (JOptionPane.showConfirmDialog(this, "Create Line Segment?") == JOptionPane.YES_OPTION)
            {
                line l = new line();
                l.p1 = evt.getPoint();
                l.location = l.p1;
                l.p2 = new Point2D.Double(Double.valueOf(JOptionPane.showInputDialog("x2=")),
                        Double.valueOf(JOptionPane.showInputDialog("y2=")));
                add_object(l);
                repaint();
            }
        } else if (cur_but == 2)
        {
            if (JOptionPane.showConfirmDialog(this, "Create Rectangle?") == JOptionPane.YES_OPTION)
            {
                rectangle r = new rectangle();
                r.topleft = evt.getPoint();
                r.location = r.topleft;
                r.breadth = Double.valueOf(JOptionPane.showInputDialog("breadth="));
                r.length = Double.valueOf(JOptionPane.showInputDialog("length="));
                add_object(r);
                repaint();
            }
        } else if (cur_but == 3)
        {
            if (JOptionPane.showConfirmDialog(this, "Create Ellipse ?") == JOptionPane.YES_OPTION)
            {
                ellipse e = new ellipse();
                e.location = evt.getPoint();
                e.HAxis = Double.valueOf(JOptionPane.showInputDialog("HAxis="));
                e.VAxis = Double.valueOf(JOptionPane.showInputDialog("VAxis="));
                e.center = new Point2D.Double(e.location.getX() + e.HAxis / 2, e.location.getY() + e.VAxis / 2);
                add_object(e);
                repaint();
            }
        } else if (cur_but == 4)
        {
            if (JOptionPane.showConfirmDialog(this, "Create Polygon?") == JOptionPane.YES_OPTION)
            {
                polygon p = new polygon();
                p.npoints = Integer.valueOf(JOptionPane.showInputDialog("no. of vertices:"));
                for (int i = 0; i < p.npoints; i++)
                {
                    p.xpoints[i] = Integer.valueOf(JOptionPane.showInputDialog("xpoint[" + i + "]"));
                    p.ypoints[i] = Integer.valueOf(JOptionPane.showInputDialog("ypoint[" + i + "]"));
                }
                p.location = new Point2D.Double(p.xpoints[0], p.ypoints[0]);
                add_object(p);
                repaint();
            }
        } else if (cur_but == 5)
        {
            deselect_object();
            select_object(evt.getPoint());
            GeometricObject o = get_selected_object();
        }
    }//GEN-LAST:event_formMouseClicked

    public void mouseDragged(java.awt.event.MouseEvent evt)
    {//GEN-FIRST:event_formMouseDragged

        if (cur_but == 0)
        {
            if (c != null && bclick == true)
            {
                remove_object(c);
            }
            if (bclick == false)
            {
                c = new circle();
                //c.center=new Point2D.Double(evt.getXOnScreen()-this.getLocationOnScreen().getX(),
                //                            evt.getYOnScreen()-this.getLocationOnScreen().getY());
                c.center = new Point2D.Double(evt.getX(), evt.getY());
                c.location = c.center;
                bclick = true;
            }
            double x = evt.getPoint().getX() - c.center.getX();
            double y = evt.getPoint().getY() - c.center.getY();
            c.radius = sqrt((x * x + y * y));
            add_object(c);
            repaint();
        } else if (cur_but == 1)
        {
            if (l != null && bclick == true)
            {
                remove_object(l);
            }
            if (bclick == false)
            {
                l = new line();
                //c.center=new Point2D.Double(evt.getXOnScreen()-this.getLocationOnScreen().getX(),
                //                            evt.getYOnScreen()-this.getLocationOnScreen().getY());
                l.p1 = new Point2D.Double(evt.getX(), evt.getY());
                l.location = l.p1;
                bclick = true;
            }
            double x = evt.getPoint().getX();
            double y = evt.getPoint().getY();
            l.p2 = new Point2D.Double(x, y);
            add_object(l);
            repaint();
        } else if (cur_but == 2)
        {
            if (r != null && bclick == true)
            {
                remove_object(r);
            }
            if (bclick == false)
            {
                r = new rectangle();
                //c.center=new Point2D.Double(evt.getXOnScreen()-this.getLocationOnScreen().getX(),
                //                            evt.getYOnScreen()-this.getLocationOnScreen().getY());
                r.topleft = new Point2D.Double(evt.getX(), evt.getY());
                r.location = r.topleft;
                bclick = true;
            }
            r.breadth = (evt.getPoint().getX() - r.topleft.getX());
            r.length = (evt.getPoint().getY() - r.topleft.getY());

            add_object(r);
            repaint();
        } else if (cur_but == 3)
        {
            if (e != null && bclick == true)
            {
                remove_object(e);
            }
            if (bclick == false)
            {
                e = new ellipse();
                //c.center=new Point2D.Double(evt.getXOnScreen()-this.getLocationOnScreen().getX(),
                //                            evt.getYOnScreen()-this.getLocationOnScreen().getY());
                e.location = new Point2D.Double(evt.getX(), evt.getY());
                bclick = true;
            }
            e.HAxis = (evt.getPoint().getX() - e.location.getX());
            e.VAxis = (evt.getPoint().getY() - e.location.getY());
            e.center = new Point2D.Double(e.location.getX() + e.HAxis / 2, e.location.getY() + e.VAxis / 2);
            add_object(e);
            repaint();
        } else if (cur_but == 4)
        {

        } else if (cur_but == 5)
        {
            GeometricObject o = get_selected_object();
            if (o != null)
            {
                o.move_to(evt.getPoint());
                repaint();
            }
        }
    }//GEN-LAST:event_formMouseDragged

    public void mouseReleased(java.awt.event.MouseEvent evt)
    {//GEN-FIRST:event_formMouseReleased
        bclick = false;
    }//GEN-LAST:event_formMouseReleased

    GeometricObject[] golist = new GeometricObject[500];
    int nobj = 0;
    group[] glist = new group[500];
    int ngroups = 0;
    transient BufferedImage[] ilist = new BufferedImage[100];
    Point2D[] image_location = new Point2D[100];
    int nimages = 0;

    public void add_image(BufferedImage i, Point2D location)
    {
        nimages++;
        ilist[nimages - 1] = i;
        image_location[nimages - 1] = new Point2D.Double(location.getX(), location.getY());
    }

    public void add_object(GeometricObject o)
    {
        nobj++;
        golist[nobj - 1] = o;
        o.cb = this;
    }

    public void remove_object(GeometricObject o)
    {
        for (int i = 0; i < nobj; i++)
        {
            if (golist[i] == o)
            {
                o.cb = null;
                golist[i] = golist[nobj - 1];
                golist[nobj - 1] = null;
                nobj--;
                break;
            }
        }
        repaint();
    }

    public void clear()
    {
        for (int i = 0; i < nobj; i++)
        {
            golist[i] = null;
        }
        for (int i = 0; i < ngroups; i++)
        {
            glist[i] = null;
        }
        ngroups = 0;
        nobj = 0;
    }

    public void move()
    {

    }

    public void zoom(Point2D p1, Point2D p2, int dir)
    {

    }

    public void fitscreen()
    {

    }

    public void add_group(group g, String name)
    {
        ngroups++;
        glist[ngroups - 1] = g;
        g.groupID = ngroups - 1;
        g.name = new String(name);
        g.isAlive = true;
        g.cb = this;
        for (int i = 0; i < g.nobj; i++)
        {
            add_object(g.golist[i]);
        }
    }

    public void remove_group(group g)
    {
        for (int i = 0; i < ngroups; i++)
        {
            if (glist[i] == g)
            {
                for (int j = 0; j < glist[i].nobj; j++)
                {
                    glist[i].remove_object(glist[i].golist[0]);
                }
                glist[i] = glist[ngroups - 1];
                ngroups--;
            }
        }
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.clearRect(0, 0, super.getWidth(), super.getHeight());
        if (this.zoomEnabled)
        {
            if (this.zoomCommandGiven == 1)
            {
                g2.scale(zoomIn, zoomIn);
            }
            if (this.zoomCommandGiven == -1)
            {
                g2.scale(1 / zoomOut, 1 / zoomOut);
            }
        }
        for (Text t : this.textList)
        {
            g2.drawString(t.str, (float) t.x, (float) t.y);
        }
        for (int i = 0; i < nimages; i++)
        {
            g2.drawImage(ilist[i], (int) image_location[i].getX(), (int) image_location[i].getY(), null);
        }
        for (int i = 0; i < nobj; i++)
        {
            // 0 for circle 1 for line 2 for rectangle 3 for ellipse 4 for polygon
            if (!golist[i].isAlive)
            {
                continue;
            }
            if (golist[i].shape == 0)
            {
                circle c = (circle) golist[i];
                Ellipse2D c2D = c.return2dcircle();
                if (c.isSelected)
                {
                    g2.setColor(Color.BLACK);
                    g2.fill(c2D);
                    g2.draw(c2D);
                    continue;
                }
                g2.setStroke(get_Stroke(golist[i]));
                g2.draw(c2D);
                if (c.fill)
                {
                    g2.setColor(c.c);
                    g2.fill(c2D);
                    g2.setColor(Color.BLACK);
                }
            } else if (golist[i].shape == 1)
            {
                line l = (line) golist[i];
                Line2D l2D = l.return2dline();
                if (l.isSelected)
                {
                    g2.setColor(Color.BLACK);
                    g2.draw(l2D);
                    g2.fill(l2D);
                    continue;
                }
                g2.setStroke(get_Stroke(golist[i]));
                g2.draw(l2D);
                if (l.fill)
                {
                    g2.setColor(l.c);
                    g2.fill(l2D);
                    g2.draw(l2D);
                    g2.setColor(Color.BLACK);
                }
            } else if (golist[i].shape == 2)
            {
                rectangle r = (rectangle) golist[i];
                Rectangle2D r2D = r.return2Drectangle();
                if (r.isSelected)
                {
                    g2.setColor(Color.BLACK);
                    g2.fill(r2D);
                    g2.draw(r2D);
                    continue;
                }
                g2.setStroke(get_Stroke(golist[i]));
                g2.draw(r2D);
                if (r.fill)
                {
                    g2.setColor(r.c);
                    g2.fill(r2D);
                    g2.setColor(Color.BLACK);
                }
            } else if (golist[i].shape == 3)
            {
                ellipse e = (ellipse) golist[i];
                Ellipse2D e2D = e.returnellipse2D();
                if (e.isSelected)
                {
                    g2.setColor(Color.BLACK);
                    g2.fill(e2D);
                    g2.draw(e2D);
                    continue;
                }
                g2.setStroke(get_Stroke(golist[i]));
                g2.draw(e2D);
                if (e.fill)
                {
                    g2.setColor(e.c);
                    g2.fill(e2D);
                    g2.setColor(Color.BLACK);
                }
            } else if (golist[i].shape == 4)
            {
                polygon p = (polygon) golist[i];
                Polygon p2 = p.returnpolygon();
                if (p.isSelected)
                {
                    g2.setColor(Color.BLACK);
                    g2.fill(p2);
                    g2.draw(p2);
                    continue;
                }
                g2.setStroke(get_Stroke(golist[i]));
                g2.drawPolygon(p2);
                if (p.fill)
                {
                    g2.setColor(p.c);
                    g2.fillPolygon(p2);
                    g2.setColor(Color.BLACK);
                }
            }

        }

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    void select_object(Point2D p)
    {
        for (int i = 0; i < nobj; i++)
        {
            if (golist[i].shape == 0)
            {
                circle c = (circle) golist[i];
                if (c.return2dcircle().contains(p))
                {
                    c.isSelected = true;
                    repaint();
                    break;
                }
            } else if (golist[i].shape == 1)
            {
                line l = (line) golist[i];
                if (l.return2dline().getBounds2D().contains(p))
                {
                    l.isSelected = true;
                    repaint();
                    break;
                }
            } else if (golist[i].shape == 2)
            {
                rectangle r = (rectangle) golist[i];
                if (r.return2Drectangle().contains(p))
                {
                    r.isSelected = true;
                    repaint();
                    break;
                }
            } else if (golist[i].shape == 3)
            {
                ellipse e = (ellipse) golist[i];
                if (e.returnellipse2D().contains(p))
                {
                    e.isSelected = true;
                    repaint();
                    break;
                }
            } else if (golist[i].shape == 4)
            {
                polygon p1 = (polygon) golist[i];
                if (p1.returnpolygon().contains(p))
                {
                    p1.isSelected = true;
                    repaint();
                    break;
                }
            }
        }
        repaint();
    }

    GeometricObject get_selected_object()
    {
        for (int i = 0; i < nobj; i++)
        {
            if (golist[i].isSelected == true)
            {
                return golist[i];
            }
        }
        return null;
    }

    void deselect_object()
    {
        GeometricObject o = get_selected_object();
        if (o != null)
        {
            o.isSelected = false;
        }
        repaint();
    }

    BasicStroke get_Stroke(GeometricObject o)
    {
        float a = (float) 10.0, b = (float) 0.0, c = (float) 1.0;

        if (o.linetype == 0)
        {
            a = (float) 10.0;
            b = (float) 0.0;

        } else if (o.linetype == 1)
        {
            a = (float) 10.0;
            b = (float) 10.0;

        } else if (o.linetype == 2)
        {
            a = (float) 1.0;
            b = (float) 8.0;

        }
        if (o.linesize == 0)
        {
            c = (float) 2.0;

        } else if (o.linesize == 1)
        {
            c = (float) 3.5;

        } else if (o.linesize == 2)
        {
            c = (float) 5.0;

        }
        return new BasicStroke(c, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, 10.0f, new float[]
        {
            a, b
        }, 0.0f);

    }

    public void paste_object(Point2D location, int clipno)
    {
        if (copy_object_clip[clipno].shape == 0)
        {
            circle c = new circle((circle) copy_object_clip[clipno]);
            if (c == null)
            {
                JOptionPane.showMessageDialog(null, "No object copied on clipboard");
            }
            c.location = location;
            c.move_to(location);
            add_object(c);
            repaint();
        } else if (copy_object_clip[clipno].shape == 1)
        {
            line c = new line((line) copy_object_clip[clipno]);
            c.move_to(location);
            add_object(c);
            repaint();
        } else if (copy_object_clip[clipno].shape == 2)
        {
            rectangle c = new rectangle((rectangle) copy_object_clip[clipno]);
            c.move_to(location);
            add_object(c);
            repaint();
        } else if (copy_object_clip[clipno].shape == 3)
        {
            ellipse c = new ellipse((ellipse) copy_object_clip[clipno]);
            c.move_to(location);
            add_object(c);
            repaint();
        } else if (copy_object_clip[clipno].shape == 4)
        {
            polygon c = new polygon((polygon) copy_object_clip[clipno]);
            c.move_to(location);
            add_object(c);
            repaint();
        }

    }

    public void paste_group(Point2D Location, int clipno)
    {
        group g = new group(copy_group_clip[clipno]);
        for (int i = 0; i < g.nobj; i++)
        {
            g.golist[i].move_to(new Point2D.Double(g.golist[i].location.getX() + Location.getX(),
                    g.golist[i].location.getY() + Location.getY()));
        }
        /*
        int max=0;
        for(int i=0;i<ngroups;i++){
            if(glist[i].name.startsWith(g.name)){
                String s=glist[i].name.substring(g.name.length());
                if(s=="")
                    continue;
                else if(max<Integer.valueOf(s)){
                    max=Integer.valueOf(s);
                }
            }
        }
        max++;
         */
        g.name = g.name + "(copy)";
        add_group(g, g.name);
        repaint();
    }

    void select_group(String s)
    {
        for (int i = 0; i < ngroups; i++)
        {
            if (glist[i].name.compareTo(s) == 0)
            {
                glist[i].isSelected = true;
                for (int j = 0; j < glist[i].nobj; j++)
                {
                    if (glist[i].golist[j].isAlive)
                    {
                        glist[i].golist[j].isSelected = true;
                    }
                }
            }
        }
        repaint();
    }

    group get_selected_group()
    {
        for (int i = 0; i < ngroups; i++)
        {
            if (glist[i].isSelected)
            {
                return glist[i];
            }
        }
        return null;
    }

    void deselect_group()
    {
        for (int i = 0; i < ngroups; i++)
        {
            glist[i].isSelected = false;
        }
        for (int i = 0; i < nobj; i++)
        {
            golist[i].isSelected = false;
        }
        repaint();
    }

    private void writeObject(ObjectOutputStream out) throws IOException
    {
        out.defaultWriteObject();
        out.writeInt(this.nimages); // how many images are serialized?
        for (int i = 0; i < nimages; i++)
        {
            ImageIO.write(this.ilist[i], "png", out); // png is lossless
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
    {
        in.defaultReadObject();
        final int imageCount = in.readInt();
        System.out.println(imageCount);
        ilist = new BufferedImage[100];
        for (int i = 0; i < imageCount; i++)
        {
            ilist[i] = (ImageIO.read(in));
            System.out.println(i);
        }
    }

    @Override
    public void keyPressed(KeyEvent ke)
    {
        this.keyPressed = ke.getKeyCode();
    }

    @Override
    public void keyReleased(KeyEvent ke)
    {

    }

    @Override
    public void mousePressed(MouseEvent me)
    {
    }

    @Override
    public void mouseEntered(MouseEvent me)
    {

    }

    @Override
    public void mouseExited(MouseEvent me)
    {

    }

    @Override
    public void mouseMoved(MouseEvent me)
    {

    }

    void export_image(String filename)
    {
        this.repaint();
        BufferedImage bImg = new BufferedImage(this.getWidth(),
                this.getWidth(), BufferedImage.TYPE_INT_RGB);
        Graphics2D graph = bImg.createGraphics();
        graph.clearRect(this.getX(), this.getY(), 10000, 10000);
        graph.setBackground(Color.WHITE);
        this.paintAll(graph);
        File f = new File(filename);
        try
        {
            ImageIO.write(bImg, "png", f);
        } catch (IOException ex)
        {
            Logger.getLogger(ClipBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
