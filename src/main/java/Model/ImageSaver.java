package Model;
import javax.imageio.ImageIO;
import java.io.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.UUID;


public class ImageSaver {
    //  private static final java.util.UUID UUID ="";
    static String uniqueID;


    // static int ID=0;
    public ImageSaver() {
    }

    public static String saveImage(File im) throws IOException {
        String absolutePath=new String();
        uniqueID = UUID.randomUUID().toString();
        String folder_path=System.getProperty("user.dir")+"\\src\\main\\resources\\images\\userImages\\"+uniqueID+".jpg";
        File file2=new File(folder_path);
        InputStream is = null;
        OutputStream os = null;
        is = new FileInputStream(im);
        os = new FileOutputStream(file2);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = is.read(buffer)) > 0) {
            os.write(buffer, 0, length);
        }

        absolutePath = uniqueID + ".png";
        return absolutePath;

    }

    public static void  changeSizeImage(File file,int height,int width) throws IOException {
        BufferedImage im = ImageIO.read(file);
        int w = im.getWidth();
        int h = im.getHeight();
        if (!(height==h&&width==w)){
            BufferedImage dimg = new BufferedImage(width, height, im.getType());
            Graphics2D g = dimg.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(im, 0, 0, width, height, 0, 0, w, h, null);
            g.dispose();
            ImageIO.write(dimg, "PNG", file);
        }
    }

    public static void main(String[] args) throws IOException {

        String st=new String();
        File s=new File("C:\\Users\\Razi\\Pictures\\Saved Pictures\\download.jpg");
        st=saveImage(s);
        System.out.println(st);
    }
}
