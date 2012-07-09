import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) throws Exception {
		JFrame frame = new JFrame("Display image");
		PanelImage image = new PanelImage();
		frame.getContentPane().add(image);
		
		frame.setVisible(true);
		
		String src = "/home/simon/projects/discrete-cosine-transform/imgs/scaled/scaled/artificial.bmp";
		int x = 768;
		int y = 512;
		frame.setSize((x+10)*3, y);
		image.setXImage(x);
		image.setYImage(y);
		image.loadImage(src);
		
		double[][] pixels = image.getPixels();
		
		//Dct.printMatrix(pixels);
		System.out.println("get pixels, now calculate dct2");
		
		double offset = -128.;
		
		double[][] result = Dct.dct2(pixels, offset);
		
		System.out.println("now dct2, now calculate idct2");
		
		double[][] ipixels = Dct.idct2(result, -offset);
		
		System.out.println("set pixels");
		
		image.setPixels(ipixels);
		
		System.out.println("tadaaam!");
		
		image.repaint();
	}
}
