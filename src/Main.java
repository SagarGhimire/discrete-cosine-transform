import java.io.FileWriter;
import java.io.PrintWriter;

public class Main {
	public static void main(String[] args) throws Exception {
		PrintWriter out = new PrintWriter(new FileWriter("/home/simon/projects/discrete-cosine-transform/outputfile.txt")); 
		
		String src = "/home/simon/projects/discrete-cosine-transform/imgs/";
		String[] names = {
				"artificial.bmp",
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
				"zone_plate.bmp",
				"big_building.bmp",
				"big_tree.bmp"
		};
		double offset = -128.;
		
		for(int i=3; i<names.length; i++) {
			String input = src+names[i];
			out.println("read: "+input);
			out.flush();
			new CreateImage(input, offset, out, true);
		}
		
		out.close();
	}
}
