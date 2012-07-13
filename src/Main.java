import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Date;

import javax.imageio.ImageIO;
//import javax.swing.JFrame;

import edu.emory.mathcs.jtransforms.dct.DoubleDCT_2D;

public class Main {
	public static void main(String[] args) throws Exception {
		//JFrame frame = new JFrame("Display image");
		//PanelImage image = new PanelImage();
		//frame.getContentPane().add(image);
		//frame.setVisible(true);
		
		PrintWriter out = new PrintWriter(new FileWriter("/home/simon/projects/discrete-cosine-transform/outputfile.txt")); 
		
		String src = "/home/simon/projects/discrete-cosine-transform/imgs/";
		String[] names = {
				"artificial.bmp",
				"big_building.bmp",
				"big_tree.bmp",
				"bridge.bmp",
				"cathedral.bmp",
				"deer.bmp",
				"fireworks.bmp",
				"flower_foveon.bmp",
				"hdr.bmp",
				"leaves_iso_200.bmp",
				"leaves_iso_1600.bmp",
				"nightshot_iso_100.bmp",
				"nightshot_iso_1600.bmp",
				"spider_web.bmp",
				"zone_plate.bmp"
		};
		for(int i=0; i<names.length; i++) {
			//int x = 500;
			//int y = 400;
			//frame.setSize((x+10)*3, y);
			//image.setXImage(x);
			//image.setYImage(y);
			//image.loadImage(src+names[i]);
			
			String input = src+names[i];
			
			BufferedImage firstimage = ImageIO.read(new File(input)); 
			
			ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);  
			ColorConvertOp op = new ColorConvertOp(cs, null);  
			BufferedImage secondImage = op.filter(firstimage, null);
			
			//double[][] pixels = image.getPixels();
			
			double[][] pixels = PanelImage.getPixels(secondImage);
			
			out.println("get pixels, now calculate dct2 on "+names[i]);
			out.flush();
			
			out.println("ciao");
			out.flush();
			
			double offset = -128.;
			
			long startS = new Date().getTime();
			double[][] result = Dct.dct2(pixels, offset);
			long endS =  new Date().getTime();
			
			out.println("time on my dct2: "+(endS-startS));
			out.flush();
			
			//System.out.println("now dct2, now calculate idct2");
			
			//double[][] ipixels = Dct.idct2(result, -offset);
			
			//System.out.println("set pixels");
			
			//image.setPixels(ipixels);
			
			//System.out.println("tadaaam!");
			
			//image.repaint();
			
			int n = pixels.length;
			int m = pixels[0].length;
			long startO = new Date().getTime();
			DoubleDCT_2D dct_2d = new DoubleDCT_2D(n, m);
			dct_2d.forward(pixels, true);
			long endO = new Date().getTime();
			
			out.println("time on jtransform dct2: "+(endO-startO));
			out.flush();
		}
		
		out.close();
	}
}
