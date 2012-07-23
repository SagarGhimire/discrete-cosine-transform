public class Main {
	public static void main(String[] args) throws Exception {
		String src = "/home/simon/projects/discrete-cosine-transform/imgs/scaled/scaled/scaled/";
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
		
		for(int i=0; i<names.length; i++) {
			String input = src+names[i];
			System.out.println("read: "+input);
			new CreateImage(input, offset);
		}
		
		System.out.println("finish!");
	}
}