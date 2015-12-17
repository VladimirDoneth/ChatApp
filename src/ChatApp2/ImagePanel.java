import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.*;

class ImagePanel extends JPanel{

    private BufferedImage image;

    public ImagePanel(String path) {
        setImage(path);
    }

    public ImagePanel() {

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null); // see javadoc for more info on the parameters
    }
    public void setImage(String path){
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException ex) {
            // handle exception...
        }
    }
}