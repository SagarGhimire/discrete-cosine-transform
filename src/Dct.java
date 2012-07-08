public class Dct {
	public static double[][] dct2old(double[][] z) throws Exception {
		if(z.length == 0)
			throw new Exception("vals empty");
		
		if(z[0].length == 0)
			throw new Exception("row empty");
		
		int n = z.length;
		int m = z[0].length;
		
		System.out.println(n+" "+m);
		
		double[][] c = new double[n][m];
		/*double[][] alfa = new double[n][m];
		
		for(int k=0; k<n; k++) {
			for(int l=0; l<m; l++) {
				if(k == 0 && l == 0) {
					alfa[k][l] = 1./Math.sqrt(n*m);
					continue;
				}
				
				if(k == 0) {
					alfa[k][l] = 1./(Math.sqrt(n)*Math.sqrt(m/2.));
					continue;
				}
				
				if(l == 0) {
					alfa[k][l] = 1./(Math.sqrt(n/2.)*Math.sqrt(m));
					continue;
				}
				
				alfa[k][l] = 1./(Math.sqrt(n/2.)*Math.sqrt(m/2.));
			}
		}
		
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
		
		double x;
		double y;
		double commonx = 1./(2.*n);
		double commony = 1./(2.*m);
		double subsumx;
		double subsumy;
		
		for(int k=0; k<n; k++) {
			for(int l=0; l<m; l++) {
				subsumx = 0;
				for(int i=0; i<n; i++) {
					subsumy = 0;
					for(int j=0; j<m; j++) {
						y = ((double) j)/m + commony;
						subsumy += z[i][j] * Math.cos(l*Math.PI*y);
					}
					x = ((double) i)/n + commonx;
					subsumx += alf1[k]*alf2[l]*subsumy*Math.cos(k*Math.PI*x);
				}
				c[k][l] =  alf1[k]*alf2[l]*subsumx;
			}
		}*/
		
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
						sum += z[i][j]*Math.cos((Math.PI*(2*i+1)*k)/(2*n))*Math.cos((Math.PI*(2*j+1)*l)/(2*m));
					}
				}
				
				c[k][l] = alf1[k]*alf2[l]*sum;
				System.out.println(k+" "+l+": "+sum+"*"+alf1[k]+"*"+alf2[l]+" -> "+c[k][l]+" "+z[k][l]);
			}
		}
		
		return c;
	}
	
	public static double[][] dctrow(double[][] z, int r) {
		int n = z[0].length;
		double n_d = n;
		
		double[] alfa = new double[n];
		alfa[0] = Math.sqrt(1./n_d);
		for(int k=1; k<n; k++) {
			alfa[k] = Math.sqrt(2./n_d);
		}
		
		for(int k=0; k<n; k++) {
			double sum = 0;
			double k_d = k;
			for(int i=0; i<n; i++) {
				double i_d = i;
				sum += z[r][i]*Math.cos((Math.PI*(2.*i_d+1.)*k_d)/(2.*n_d));
			}
			z[r][k] = alfa[k]*sum;
		}
		
		return z;
	}
	
	public static double[][] dctcol(double[][] z, int c) {
		int n = z.length;
		double n_d = n;
		
		double[] alfa = new double[n];
		alfa[0] = Math.sqrt(1./n_d);
		for(int k=1; k<n; k++) {
			alfa[k] = Math.sqrt(2./n_d);
		}
		
		for(int k=0; k<n; k++) {
			double sum = 0;
			double k_d = k;
			for(int i=0; i<n; i++) {
				double i_d = i;
				sum += z[i][c]*Math.cos((Math.PI*(2.*i_d+1.)*k_d)/(2.*n_d));
			}
			z[k][c] = alfa[k]*sum;
		}
		
		return z;
	}
	
	public static double[][] dct2(double[][] z) throws Exception {
		int n = z.length;
		int m = z[0].length;
		
		System.out.println(n+" "+m);
		
		for(int r=0; r<n; r++) {
			z = dctrow(z, r);
		}
		
		for(int c=0; c<m; c++) {
			z = dctcol(z, c);
		}
		
		return z;
	}
	
	public static void test1() throws Exception {
		 double[][] vals = {{1.,2.,3.},{4.,5.,6.}};
		 double[][] vals2 = {{1.,2.,3.},{4.,5.,6.}};
		 
		 System.out.print("[");
		 System.out.print(vals[0][0]+" ");
		 System.out.print(vals[0][1]+" ");
		 System.out.print(vals[0][2]);
		 System.out.println();
		 System.out.print(vals[1][0]+" ");
		 System.out.print(vals[1][1]+" ");
		 System.out.print(vals[1][2]);
		 System.out.println("]");
		 
		 double[][] result = dct2(vals);
		 
		 System.out.print("[");
		 System.out.print(result[0][0]+" ");
		 System.out.print(result[0][1]+" ");
		 System.out.print(result[0][2]);
		 System.out.println();
		 System.out.print(result[1][0]+" ");
		 System.out.print(result[1][1]+" ");
		 System.out.print(result[1][2]);
		 System.out.println("]");
		 
		 result = dct2old(vals2);
		 
		 System.out.print("[");
		 System.out.print(result[0][0]+" ");
		 System.out.print(result[0][1]+" ");
		 System.out.print(result[0][2]);
		 System.out.println();
		 System.out.print(result[1][0]+" ");
		 System.out.print(result[1][1]+" ");
		 System.out.print(result[1][2]);
		 System.out.println("]");
	}
	
	public static void main(String[] args) throws Exception {
		test1();
	}
}
