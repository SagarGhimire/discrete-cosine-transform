public class Dct {
	
	/* k=dct2(zij-128) per ogni i e j (e ottieni le frequenze dell'immagine) OK
	 * r=idct(k) OK 
	 * r=rij+128 per ogni i e j (e dovresti riottenere l'immagine precedente) OK
	 */
	public static void printMatrix(double[][] z) {
		int n = z.length;
		int m = z[0].length;
		
		System.out.print("[");
		for(int i=0; i<n; i++) {
			for(int j=0; j<m; j++) {
				System.out.print(z[i][j]);
				if(j != m-1)
					System.out.print(" ");
			}
			if(i != n-1)
				System.out.println();
		}
		System.out.println("]");
	}
	
	public static double[][] dct2in2dimension(double[][] z, double offset) throws Exception {
		if(z.length == 0)
			throw new Exception("z empty");
		
		if(z[0].length == 0)
			throw new Exception("z row empty");
		
		int n = z.length;
		int m = z[0].length;
		
		double[][] c = new double[n][m];
		double[] alf1 = new double[n];
		double[] alf2 = new double[m];
		
		alf1[0] = 1. / Math.sqrt(n);
		for(int k=1; k<n; k++) {
			alf1[k] = Math.sqrt(2./n);
		}
		
		alf2[0] = 1. / Math.sqrt(m);
		for(int l=1; l<m; l++) {
			alf2[l] = Math.sqrt(2./m);
		}
		
		double sum;
		for(int k=0; k<n; k++) {
			for(int l=0; l<m; l++) {
				sum = 0;
				for(int i=0; i<n; i++) {
					for(int j=0; j<m; j++) {
						sum += (z[i][j]+offset)*Math.cos((Math.PI*(2*i+1)*k)/(2*n))*Math.cos((Math.PI*(2*j+1)*l)/(2*m));
					}
				}
				c[k][l] = alf1[k]*alf2[l]*sum;
				System.out.println(k+" "+l+": "+sum+"*"+alf1[k]+"*"+alf2[l]+" -> "+c[k][l]);
			}
		}
		
		return c;
	}
	
	public static double[][] idct2in2dimension(double[][] z, double offset) throws Exception {
		if(z.length == 0)
			throw new Exception("z empty");
		
		if(z[0].length == 0)
			throw new Exception("z row empty");
		
		int n = z.length;
		int m = z[0].length;
		
		double[][] c = new double[n][m];
		double[] alf1 = new double[n];
		double[] alf2 = new double[m];
		
		alf1[0] = 1. / Math.sqrt(n);
		for(int k=1; k<n; k++) {
			alf1[k] = Math.sqrt(2./n);
		}
		
		alf2[0] = 1. / Math.sqrt(m);
		for(int l=1; l<m; l++) {
			alf2[l] = Math.sqrt(2./m);
		}
		
		for(int k=0; k<n; k++) {
			for(int l=0; l<m; l++) {
				c[k][l] = 0;
				for(int i=0; i<n; i++) {
					for(int j=0; j<m; j++) {
						c[k][l] += alf1[i]*alf2[j]*z[i][j]*Math.cos((Math.PI*(2*k+1)*i)/(2*n))*Math.cos((Math.PI*(2*l+1)*j)/(2*m));
					}
				}
				c[k][l] += offset;
				System.out.println(k+" "+l+": "+c[k][l]);
			}
		}
		
		return c;
	}
	
	public static double[][] dct2(double[][] z, double offset) throws Exception {
		if(z.length == 0)
			throw new Exception("z empty");
		
		if(z[0].length == 0)
			throw new Exception("z row empty");
		
		int n = z.length;
		int m = z[0].length;
		double[][] c = new double[n][m];
		double[][] c2 = new double[n][m];
		double alfa;
		double sum;
		
		for(int k=0; k<n; k++) {
			for(int l=0; l<m; l++) {
				sum = 0;
				for(int i=0; i<n; i++) {
					sum += (z[i][l]+offset)*Math.cos((Math.PI*(2.*i+1.)*k)/(2.*n));
				}
				alfa = k == 0 ? 1. / Math.sqrt(n) : Math.sqrt(2./n);
				c[k][l] = alfa*sum;
			}
		}
		
		for(int l=0; l<m; l++) {
			for(int k=0; k<n; k++) {
				sum = 0;
				for(int j=0; j<m; j++) {
					sum += c[k][j]*Math.cos((Math.PI*(2.*j+1.)*l)/(2.*m));
				}
				alfa = l == 0 ? 1. / Math.sqrt(m) : Math.sqrt(2./m);
				c2[k][l] = alfa*sum;
			}
		}
		
		return c2;
	}
	
	public static double[][] idct2(double[][] z, double offset) throws Exception {
		if(z.length == 0)
			throw new Exception("z empty");
		
		if(z[0].length == 0)
			throw new Exception("z row empty");
		
		int n = z.length;
		int m = z[0].length;
		double[][] c = new double[n][m];
		double[][] c2 = new double[n][m];
		double alfa;
		
		for(int k=0; k<n; k++) {
			for(int l=0; l<m; l++) {
				c[k][l] = 0;
				for(int i=0; i<n; i++) {
					alfa = i == 0 ? 1. / Math.sqrt(n) : Math.sqrt(2./n);
					c[k][l] += alfa*z[i][l]*Math.cos((Math.PI*(2*k+1)*i)/(2*n));
				}
			}
		}
		
		for(int l=0; l<m; l++) {
			for(int k=0; k<n; k++) {
				c2[k][l] = 0;
				for(int j=0; j<m; j++) {
					alfa = j == 0 ? 1. / Math.sqrt(m) : Math.sqrt(2./m);
					c2[k][l] += alfa*c[k][j]*Math.cos((Math.PI*(2*l+1)*j)/(2*m));
				}
				c2[k][l] += offset;
			}
		}
		
		return c2;
	}
	
	public static void test1() throws Exception {
		double[][] vals = {{1.,2.,3.}, {4.,5.,6.}};
		double offset = 0;
		System.out.println("vals: ");
		printMatrix(vals);
		double[][] result = dct2(vals, offset);
		System.out.println("result: ");
		printMatrix(result);
		double[][] ivals = idct2(result, -offset);
		System.out.println("idct2 result: ");
		printMatrix(ivals);
	}
	
	public static void test2() throws Exception {
		double[][] vals = {{139., 144., 149., 153., 155., 155., 155., 155.}, 
				{144., 151., 153., 156., 159., 156., 156., 156.}, 
				{150., 155., 160., 163., 158., 156., 156., 156.}, 
				{159., 161., 162., 160., 160., 159., 159., 159.}, 
				{159., 160., 161., 162., 162., 155., 155., 155.}, 
				{161., 161., 161., 161., 160., 157., 157., 157.}, 
				{162., 162., 161., 163., 162., 157., 157., 157.}, 
				{162., 162., 161., 161., 163., 158., 158., 158.}};
		double offset = -128;
		System.out.println("vals: ");
		printMatrix(vals);
		double[][] result = dct2(vals, offset);
		System.out.println("result: ");
		printMatrix(result);
		double[][] ivals = idct2(result, -offset);
		System.out.println("idct2 result: ");
		printMatrix(ivals);
	}
	
	public static void test3() throws Exception {
		double[][] vals = {{3.,7.,-5.}, {8.,-9.,7.}};
		double offset = 0;
		System.out.println("vals: ");
		printMatrix(vals);
		double[][] result = dct2(vals, offset);
		System.out.println("dct2 result: ");
		printMatrix(result);
		double[][] ivals = idct2(result, -offset);
		System.out.println("idct2 result: ");
		printMatrix(ivals);
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println("TEST1");
		test1();
		System.out.println("\nTEST2");
		test2();
		System.out.println("\nTEST3");
		test3();
	}
}