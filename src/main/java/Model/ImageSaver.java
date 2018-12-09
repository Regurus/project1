package Model;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class ImageSaver {
    static int ID=0;
    public ImageSaver() {
    }
    public static String saveImage(File im){
        ID++;
        File file=im;
        //String folder_path="C:\\Users\\Razi\\Desktop\\project1\\src\\main\\resources\\images\\userImages";
        String folder_path=System.getProperty("user.dir")+"\\src\\main\\resources\\images\\userImages";
        //   boolean renameResult = file.renameTo(new File(String.valueOf(ID)));
        file.renameTo(new File(folder_path + "\\" + String.valueOf(ID) + ".png"));
        String absolutePath = String.valueOf(ID) + ".png";
        return absolutePath;
    }

    public static void  changeSizeImage(File file,int height,int width) throws IOException {
        BufferedImage im = ImageIO.read(file);
        int w = im.getWidth();
        int h = im.getHeight();
        BufferedImage dimg = new BufferedImage(width, height, im.getType());
        Graphics2D g = dimg.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(im, 0, 0, width, height, 0, 0, w, h, null);
        g.dispose();
        ImageIO.write(dimg, "PNG", file);

    }

    public static void main(String[] args) throws IOException {
        File s=new File("D:\\Users\\Bogdan\\Pictures\\GTR\\rear.jpg");
        changeSizeImage(s,100,100);
    }


}
