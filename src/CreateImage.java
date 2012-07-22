import java.awt.Graphics2D;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.ColorModel;
import java.awt.image.PixelGrabber;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.PrintWriter;
import java.util.Date;

import javax.imageio.ImageIO;

import edu.emory.mathcs.jtransforms.dct.DoubleDCT_2D;

public class CreateImage {
	private BufferedImage firstimage;
	private BufferedImage secondImage;
	private BufferedImage thirdImage;

	public CreateImage(String src, double offset, PrintWriter debug) {
		try {
			firstimage = ImageIO.read(new File(src));
		
			ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);  
			ColorConvertOp op = new ColorConvertOp(cs, null);  
			firstimage = op.filter(firstimage, null);
			
			int divide = 20;
			
			double[][] pixels = CreateImage.getPixels(firstimage);
	
			long startS = new Date().getTime();
			double[][] result = Dct.dct2(pixels, offset);
			long endS =  new Date().getTime();
			
			debug.println("time on my dct2: "+(endS-startS));
			
//			int n = pixels.length;
//			int m = pixels[0].length;
//			long startO = new Date().getTime();
//			DoubleDCT_2D dct_2d = new DoubleDCT_2D(n, m);
//			dct_2d.forward(pixels, true);
//			long endO = new Date().getTime();
//			debug.println("time on jtransform dct2: "+(endO-startO));
			
			double[][] filtered = Dct.filter2(result);
			
			debug.println("filtered2");
			
			double[][] newPixels = Dct.idct2(filtered, -1*offset);
			secondImage = CreateImage.setPixels(CreateImage.deepCopy(firstimage), newPixels);
		
			File file = new File(src+"-result-f2.jpg");
			ImageIO.write(secondImage, "jpg", file);

			filtered = Dct.filter(result, 0.75);			
			debug.println("filtered at 0.75");
	
			newPixels = Dct.idct2(filtered, -1*offset);
			secondImage = CreateImage.setPixels(CreateImage.deepCopy(firstimage), newPixels);
		
			file = new File(src+"-result-0.75.jpg");
			ImageIO.write(secondImage, "jpg", file);
			
			filtered = Dct.filter(result, 0.50);
			
			debug.println("filtered at 0.50");
			
			newPixels = Dct.idct2(filtered, -1*offset);
			secondImage = CreateImage.setPixels(CreateImage.deepCopy(firstimage), newPixels);
		
			file = new File(src+"-result-0.50.jpg");
			ImageIO.write(secondImage, "jpg", file);

			filtered = Dct.filter(result, 0.25);
			
			debug.println("filtered at 0.25");
			
			newPixels = Dct.idct2(filtered, -1*offset);
			secondImage = CreateImage.setPixels(CreateImage.deepCopy(firstimage), newPixels);
		
			file = new File(src+"-result-0.25.jpg");
			ImageIO.write(secondImage, "jpg", file);
	
//			thirdImage = new BufferedImage((firstimage.getWidth()*2)+divide, firstimage.getHeight(), BufferedImage.TYPE_INT_RGB);
			
//			Graphics2D g2d = thirdImage.createGraphics();
			
//			g2d.drawImage(firstimage, 0, 0, null);
//			g2d.drawImage(secondImage, firstimage.getWidth()+divide, 0, null);
			
//			g2d.dispose();
			
//			File file = new File(src+"-result.jpg");
//			ImageIO.write(thirdImage, "jpg", file);
			
			debug.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public static final boolean isGreyscaleImage(PixelGrabber pg) {
        return pg.getPixels() instanceof byte[];
    }
	
	public static double[][] getPixels(BufferedImage image) throws Exception {
		int w = image.getWidth();
		int h = image.getHeight();
		WritableRaster raster = image.getRaster();
		double[][] pixels = new double[w][h];
		int[] buffer = new int[1];
		
		for( int i = 0; i < w; i++ ) {
			for( int j = 0; j < h; j++ ) {
				raster.getPixel(i, j, buffer);
				pixels[i][j] = (double) buffer[0];
			}
		}
		
		return pixels;
	}
	
	public static BufferedImage setPixels(BufferedImage image, double[][] pixels) throws Exception {
		int w = image.getWidth();
		int h = image.getHeight();
		WritableRaster raster = image.getRaster();
		int[] buffer = new int[1];
		
		for( int i = 0; i < w; i++ ) {
			for( int j = 0; j < h; j++ ) {
				buffer[0] = (int) pixels[i][j];
				raster.setPixel(i, j, buffer);
			}
		}
		
		return image;
	}
	
	static BufferedImage deepCopy(BufferedImage bi) {
		 ColorModel cm = bi.getColorModel();
		 boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		 WritableRaster raster = bi.copyData(null);
		 return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
}
