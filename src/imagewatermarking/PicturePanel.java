
package imagewatermarking;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class PicturePanel extends JPanel{
    private JFrame imgframe;

    public PicturePanel(JFrame imgframe) {
        this.imgframe = imgframe;
    }

    
    private BufferedImage image=null;

    public BufferedImage getImage() {
        return image;
    }
    
    
    
    public void setImage(BufferedImage image)
    {
        this.image=image;
      
        this.repaint();
    }
 
    public void paintComponent(Graphics g)
    {
       super.paintComponent(g);
       if(image==null)
           return;
        Dimension d=getSize();
        g.drawImage(image, 0, 0,(int) d.getWidth(),(int)d.getHeight(), null);
    }
}
