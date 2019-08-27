package imagewatermarking;


import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;

public class ImageWatermarking {
  
    public static DefaultListModel<File> GetFiles(File directory,DefaultListModel<File> lstFiles)
    {
        lstFiles.removeAllElements();
        File[] files= directory.listFiles();
        //DefaultListModel<File> lstFiles=new DefaultListModel<>();
        for(File file:files)
            if(isImage(file))
             lstFiles.addElement(file);
        return lstFiles;
    }
    
    public static BufferedImage readImage(String filename)throws IOException
    {
        File file=new File(filename);
        BufferedImage image = ImageIO.read(file);
        return image;
    }
      public static boolean isImage(File file)
    {
        try
        {
         ImageIO.read(file);
        return true;
        }
        catch(Exception ex)
        {
            return false;
        }
    }
    
    
    public static BufferedImage readImage(File file)throws IOException
    {
        BufferedImage image = ImageIO.read(file);
        return image;
    }
    public static boolean writeImage(String filepath,String imagetype,BufferedImage image)throws IOException
    {try
    {
       ImageIO.write(image, imagetype,new File( filepath));
       return true;
    }
    catch(Exception ex)
    {
        System.out.println(ex);
        return false;
    }
       
    }
 public static boolean writeImage(File file,String imagetype,BufferedImage image)throws IOException
    {try
    {
       ImageIO.write(image, imagetype, file);
       return true;
    }
    catch(Exception ex)
    {
        System.out.println(ex);
        return false;
    }
       
    }
 public static BufferedImage cloneIt(BufferedImage image)
 {
     int width=image.getWidth();
     int height=image.getHeight();
   int type=  image.getType();
     BufferedImage clone=new BufferedImage(width, height, type);
     Graphics2D gd=clone.createGraphics();
     gd.drawImage(image, 0, 0, null);
     return clone;
 }
 
public static void putTextWatermark(String watermarkText,BufferedImage image,Font font,Color color) {

    

Graphics2D g2d = (Graphics2D) image.getGraphics();

g2d.setColor(color);
g2d.setFont(font);
FontMetrics fontMetrics = g2d.getFontMetrics();
Rectangle2D rect = fontMetrics.getStringBounds(watermarkText, g2d);
int centerX = (image.getWidth()/2 -(int) rect.getWidth() / 2);
int centerY = image.getHeight() / 2;
g2d.drawString(watermarkText, centerX, centerY);

g2d.dispose();

}
}
