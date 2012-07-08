import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) throws Exception {
		JFrame frame = new JFrame("Display image");
		PanelImage image = new PanelImage();
		frame.getContentPane().add(image);
		frame.setSize(1182, 300);
		frame.setVisible(true);
		
		String src = "/home/simon/projects/discrete-cosine-transform/imgs/scaled/scaled/scaled/artificial.bmp";
		image.loadImage(src);
		
		double[][] pixels = image.getPixels();
		
		//Dct.printMatrix(pixels);
		System.out.println("get pixels, now calculate dct2");
		
		//double[][] result = Dct.dct2(pixels, -128.);
		
		//System.out.println("now dct2, set pixels");
		
		image.setPixels(pixels);
		
		System.out.println("tadaaam!");
		
		image.repaint();
	}
}
