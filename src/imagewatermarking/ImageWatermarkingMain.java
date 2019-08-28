
package imagewatermarking;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ImageWatermarkingMain extends JFrame{
   private JPanel panelFolders;
   private JLabel lblSourceFolder,lblDestinationFolder;
   private JButton bttnSourceFolder,bttnDestinationFolder,bttnAddWatermark;
   private JList<File>lstFiles;
   private JFileChooser sourceFileChooser,destinationFileChooser;;
   private DefaultListModel<File> files;
   private JFrame imgframe,watermarkframe;
   private PicturePanel imgpanel,watermarkpanel;
   
   private Font font=new Font("Arial", Font.BOLD, 24);
  private Color color=Color.YELLOW;
    public ImageWatermarkingMain()
    {
           try {
              UIManager.setLookAndFeel(
            UIManager.getSystemLookAndFeelClassName());
        }       catch (Exception ex) {
            System.out.println(ex);
            
        }
           color=new Color(255,255,0,100);
                   
           imgframe=new JFrame();
           
           imgframe.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            imgpanel=new PicturePanel(imgframe);
            
        imgframe.add(imgpanel);
        imgpanel.setLayout(new BorderLayout());
        imgframe.setVisible(true);
           
            
        imgframe.setTitle("Image");
        watermarkframe=new JFrame();
        watermarkframe.setTitle("Watermark");
       watermarkframe .setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        watermarkpanel =new PicturePanel(watermarkframe);
        
        watermarkframe.add(watermarkpanel);
        watermarkframe.setVisible(true);
           
           
             sourceFileChooser=new JFileChooser();
        sourceFileChooser.setCurrentDirectory(new java.io.File("."));
    
    sourceFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        sourceFileChooser.setAcceptAllFileFilterUsed(false);
        
           destinationFileChooser=new JFileChooser();
       destinationFileChooser .setCurrentDirectory(new java.io.File("."));
    
    destinationFileChooser .setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      destinationFileChooser  .setAcceptAllFileFilterUsed(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(200,250);
        //*****************************************************'
        setLayout(new GridLayout(9, 1));
        JLabel lbl=new JLabel("Image Watermarking");
        add(lbl);
        lbl.setHorizontalAlignment(JLabel.CENTER);
         lbl=new JLabel("Select Folders");
        add(lbl);
        lbl.setHorizontalAlignment(JLabel.CENTER);
        //*****************************************************
        panelFolders=new JPanel();
        panelFolders.setBackground(Color.red);
        panelFolders.setLayout(new GridLayout(2,3));
        add(panelFolders);
          lbl=new JLabel("Input Folder");
     panelFolders.add(lbl);
        lbl.setHorizontalAlignment(JLabel.CENTER);
         lblSourceFolder=new JLabel("Input Folder Path");
     panelFolders.add(lblSourceFolder);
        lblSourceFolder.setHorizontalAlignment(JLabel.CENTER);
        
        bttnSourceFolder=new JButton("Choose");
        panelFolders.add(bttnSourceFolder);
       
         lbl=new JLabel("Output Folder");
     panelFolders.add(lbl);
        lbl.setHorizontalAlignment(JLabel.CENTER);
         lblDestinationFolder=new JLabel("Output Folder Path");
     panelFolders.add(lblDestinationFolder);
        lblDestinationFolder.setHorizontalAlignment(JLabel.CENTER);
        
        bttnDestinationFolder=new JButton("Choose");
        panelFolders.add(bttnDestinationFolder);
         lbl=new JLabel("Input Pics");
        add(lbl);
        lbl.setHorizontalAlignment(JLabel.CENTER);
        
        lstFiles=new JList<>(files=new DefaultListModel<>());
        lstFiles.setVisibleRowCount(20);
        add(new JScrollPane(lstFiles));
          bttnSourceFolder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int result= sourceFileChooser.showOpenDialog(ImageWatermarkingMain.this);
                    if(result!=JFileChooser.APPROVE_OPTION)
                        return;
                   
                    lblSourceFolder.setText(sourceFileChooser.getSelectedFile().getCanonicalPath());
                    ImageWatermarking.GetFiles(sourceFileChooser.getSelectedFile(),files);
                   
                 
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(ImageWatermarkingMain.this, ex);
                }
               
            }
        });
        
          
          
          
          
             lstFiles.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                try {
                    if(e.getValueIsAdjusting())
                        return;
                   // int n=e.getFirstIndex();
                    File file=lstFiles.getSelectedValue();
                    BufferedImage image=ImageIO.read(file);
                    imgpanel.setImage(image);
                    BufferedImage clone=ImageWatermarking.cloneIt(image);
                    ImageWatermarking.putTextWatermark("KashiKshetra.com",clone,font,color);
                    watermarkpanel.setImage(clone);
                    imgpanel.invalidate();
                } catch (IOException ex) {
                    System.out.println(ex);
                }
            }
        });
             
             bttnAddWatermark=new JButton("Watermark");
             add(bttnAddWatermark);
             bttnAddWatermark.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                   int n=files.getSize();
                   for(int i=0;i<=n-1;i++)
                   {
try           
{
    File file=files.get(i);
    BufferedImage image=ImageWatermarking.readImage(file);
    BufferedImage watermarkedimage=ImageWatermarking.cloneIt(image);
    ImageWatermarking.putTextWatermark("VaranasiKshetra.com", watermarkedimage, font, color);
    String filepath="K:\\output\\" + file.getName();
    ImageWatermarking.writeImage(filepath, "jpg", watermarkedimage);
    
}catch(Exception ex)
{
    System.out.println(ex);
}
                   }
               }
           });
             
    }
    public static void main(String[] args) {
        ImageWatermarkingMain f=new ImageWatermarkingMain();
        f.setVisible(true);
    }
}
