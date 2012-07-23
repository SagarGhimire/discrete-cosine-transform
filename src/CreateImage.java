import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.ColorModel;
import java.awt.image.PixelGrabber;
import java.awt.image.WritableRaster;
import java.io.File;
import java.util.Date;

import javax.imageio.ImageIO;

import edu.emory.mathcs.jtransforms.dct.DoubleDCT_2D;

public class CreateImage {
	private BufferedImage firstimage;
	private BufferedImage secondImage;
	private DoubleDCT_2D dct_2d;
	
	public void saveFilteredImage(String src, double[][] dct2, double threshold, double offset, boolean fast) throws Exception {
		double[][] filtered;
		double[][] newPixels;
		File file;
		String fileName;
		
		System.out.println("start filtering at "+threshold);
		
		filtered = Dct.filter(dct2, threshold);
		System.out.println("filtered at "+threshold);

		if(!fast) {
			newPixels = Dct.idct2(filtered, -1*offset);
			secondImage = CreateImage.setPixels(CreateImage.deepCopy(firstimage), newPixels);
			fileName = src+"-result-"+threshold+".jpg";
		} else {
			dct_2d.inverse(filtered, true);
			newPixels = filtered;
			secondImage = CreateImage.setPixels(CreateImage.deepCopy(firstimage), newPixels);
			fileName = src+"-result-"+threshold+"-fast.jpg";
		}
		
		file = new File(fileName);
		ImageIO.write(secondImage, "jpg", file);
	}

	public CreateImage(String src, double offset) {
		try {
			firstimage = ImageIO.read(new File(src));
		
			ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);  
			ColorConvertOp op = new ColorConvertOp(cs, null);  
			firstimage = op.filter(firstimage, null);
			
			double[][] pixels = CreateImage.getPixels(firstimage);
	
//			long startS = new Date().getTime();
//			double[][] result = Dct.dct2(pixels, offset);
//			long endS =  new Date().getTime();
//			System.out.println("time on my dct2: "+(endS-startS));
			
			int n = pixels.length;
			int m = pixels[0].length;
			long startO = new Date().getTime();
			dct_2d = new DoubleDCT_2D(n, m);
			
			double[][] result = pixels;
			
			dct_2d.forward(result, true);
			long endO = new Date().getTime();
			System.out.println("time on jtransform dct2: "+(endO-startO));
			
			saveFilteredImage(src, result, 0.25, offset, true);
			saveFilteredImage(src, result, 0.10, offset, true);
			saveFilteredImage(src, result, 0.05, offset, true);
			saveFilteredImage(src, result, 0.01, offset, true);
			saveFilteredImage(src, result, 0.0075, offset, true);
			saveFilteredImage(src, result, 0.005, offset, true);
			saveFilteredImage(src, result, 0.004, offset, true);
			saveFilteredImage(src, result, 0.002, offset, true);
			saveFilteredImage(src, result, 0.001, offset, true);
			saveFilteredImage(src, result, 0.0005, offset, true);
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
				int newValue;
				
				if(pixels[i][j] > 255.0) {
					newValue = 255;
				} else if(pixels[i][j] < 0.0) {
					newValue = 0;
				} else {
					newValue = (int) pixels[i][j]; 
				}
				
				buffer[0] =  newValue;
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
