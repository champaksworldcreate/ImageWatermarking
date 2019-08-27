package imagewatermarking;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ImageWatermarkingFrame extends JFrame{
    JFrame imgframe,watermarkframe;
    JPanel toppanel;
    DefaultListModel<File> files;
    JPanel panelfilelist;
    JList<File>lstFiles;
    JLabel lblSourceFolder,lblDestinationSource;
    JTextField txtSourceFolder,txtDestinationFolder;
    JButton bttnSourceFolder,bttnDestinationFolder,bttnAddWatermark;
    JFileChooser sourceFileChooser,destinationFileChooser;
    PicturePanel imgpanel,watermarkpanel;
    
     Font font=new Font("Arial", Font.BOLD, 24);
    Color color=Color.YELLOW;
    public ImageWatermarkingFrame()
    {
        imgframe=new JFrame();
        imgframe.setTitle("Image");
        watermarkframe=new JFrame();
        watermarkframe.setTitle("Watermark");
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
        setSize(400,400);
        setTitle("Image Watermarking tool");
        
        setLayout(new GridLayout(4,1));
        toppanel=new JPanel();
        toppanel.setLayout(new GridLayout(2, 3));
        add(toppanel);
        panelfilelist =new JPanel();
        panelfilelist.setLayout(new GridLayout(2, 1));
          lstFiles=new JList<>();
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
          files=new DefaultListModel<>();
          lstFiles.setModel(files);
                   lstFiles.setVisibleRowCount(5);
         panelfilelist.add(new JScrollPane( lstFiles));
        add(panelfilelist);
        toppanel.add(lblSourceFolder=new JLabel("Source Folder"));
        toppanel.add(txtSourceFolder= new JTextField());
        toppanel.add(bttnSourceFolder =new JButton("Source Folder"));
         toppanel.add(lblDestinationSource=new JLabel("Destination Folder"));
        toppanel.add(txtDestinationFolder= new JTextField());
        toppanel.add(bttnDestinationFolder =new JButton("Destination Folder"));
        imgpanel=new PicturePanel(imgframe);
        imgframe.add(imgpanel);
        imgpanel.setLayout(new BorderLayout());
        imgframe.setVisible(true);
        bttnSourceFolder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int result= sourceFileChooser.showOpenDialog(ImageWatermarkingFrame.this);
                    if(result!=JFileChooser.APPROVE_OPTION)
                        return;
                   
                    txtSourceFolder.setText(sourceFileChooser.getSelectedFile().getCanonicalPath());
                    ImageWatermarking.GetFiles(sourceFileChooser.getSelectedFile(),files);
                   
                 
                   
                    panelfilelist.invalidate();
                     ImageWatermarkingFrame.this.invalidate();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(ImageWatermarkingFrame.this, ex);
                }
               
            }
        });
        
        bttnDestinationFolder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 try {
                    int result=destinationFileChooser.showOpenDialog(ImageWatermarkingFrame.this);
                    if(result!=JFileChooser.APPROVE_OPTION)
                        return;
                    txtDestinationFolder.setText(destinationFileChooser.getSelectedFile().getCanonicalPath());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(ImageWatermarkingFrame.this, ex);
                }
            }
        });
        bttnAddWatermark =new JButton("Save");
        bttnAddWatermark.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i=0;i<=files.getSize()-1;i++)
                {
                try
                {
                    File file=files.getElementAt(i);
                    String outputfolder="K:\\output\\"; 
                    String outputfilename=outputfolder+  file.getName();
                    
                    BufferedImage outputImage=ImageWatermarking.readImage(file);
                    ImageWatermarking.putTextWatermark("VaranasiKshetra.com",outputImage,font,color);
                    ImageWatermarking.writeImage(outputfilename, "jpg", outputImage);
                    System.out.println(outputfilename);
                }
                catch(Exception ex)
                {
                    System.out.println(ex);
                }
                }
            }
        });
        panelfilelist.add(bttnAddWatermark);
    }
    public static void main(String[] args) {
        ImageWatermarkingFrame frame=new ImageWatermarkingFrame();
        frame.setVisible(true);
        
    }
}
