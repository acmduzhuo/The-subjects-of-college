package jiemian;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 * Ω•±‰√Ê∞Â
 */
public class GradientPanel extends JPanel {
    int i;
    GradientPaint gradientPaint;
    public GradientPanel(LayoutManager lm) {
        super(lm);
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!isOpaque()) {
            return;
        }

        int width = getWidth();
        int height = getHeight();
        Graphics2D g2 = (Graphics2D) g;
       if(i==1){
           gradientPaint =new GradientPaint(width, height/3,Color.cyan, width, height,new Color(0,46,255),false);
       }
        else if (i==2){
           gradientPaint =new GradientPaint(width*41/39, height/2,new Color(50,60,255), width, height, Color.cyan,false);
       }else if (i==3){
           gradientPaint =new GradientPaint(width, height/2,new Color(0,46,255), width, height,new Color(0,60,255),false);

       }else{
           gradientPaint =new GradientPaint(width, height,Color.white, width, height,Color.white,false);

       }


        g2.setPaint(gradientPaint);
        g2.fillRect(0, 0, width, height);

    }

}
