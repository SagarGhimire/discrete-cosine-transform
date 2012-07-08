import java.awt.Graphics;
import java.awt.Panel;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.PixelGrabber;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class PanelImage extends Panel {
	private static final long serialVersionUID = -8456286372929884974L;
	BufferedImage firstimage;
	BufferedImage secondImage;
	BufferedImage thirdImage;
	
	public void loadImage(String src) throws IOException {
		firstimage = ImageIO.read(new File(src)); 
		
		ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);  
		ColorConvertOp op = new ColorConvertOp(cs, null);  
		secondImage = op.filter(firstimage, null);

		thirdImage = op.filter(firstimage, null);
	}

	public void paint(Graphics g) {
		g.drawImage(firstimage, 5, 0, null);
		g.drawImage(secondImage, 399, 0, null);
		g.drawImage(thirdImage, 793, 0, null);
	}
	
	public static final boolean isGreyscaleImage(PixelGrabber pg) {
        return pg.getPixels() instanceof byte[];
    }

	public double[][] getPixels() throws Exception {
		int w = secondImage.getWidth();
		int h = secondImage.getHeight();
		WritableRaster raster = secondImage.getRaster();
		double[][] pixels = new double[w][h];
		int[] buffer = new int[1];
		
		System.out.println(secondImage.getType());
		
		for( int i = 0; i < w; i++ ) {
			for( int j = 0; j < h; j++ ) {
				raster.getPixel(i, j, buffer);
				pixels[i][j] = (double) buffer[0];
			}
		}
		
		return pixels;
	}
	
	public void setPixels(double[][] pixels) throws Exception {
		int w = thirdImage.getWidth();
		int h = thirdImage.getHeight();
		WritableRaster raster = thirdImage.getRaster();
		int[] buffer = new int[1];
		
		for( int i = 0; i < w; i++ ) {
			for( int j = 0; j < h; j++ ) {
				buffer[0] = (int) pixels[i][j];
				raster.setPixel(i, j, buffer);
			}
		} 
	}
}