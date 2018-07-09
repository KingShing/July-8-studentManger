package basic_exercise;



import org.junit.Test;

public class printRhombus {
	@Test
	public void test2() throws Exception {
		int n = 15;
		for(int i=0;i<n;i++ ) {
			for (int j = 0; j < i; j++) {
				System.out.print("*");
			}
			System.out.println();
		}
	}
	
	
	@Test
	public void test1() throws Exception {
		int n = 5;// n为奇数 1,3,5,7,....
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				if(i<=(n+1)/2) {
					//上三角
					if (j == (n + 1) / 2+1-i||j==(n-1)/2+i) {
						System.out.print("*");
					} else {
						System.out.print(" ");
					}
				}else {
					//下三角
					if(j==(i-(n-1)/2)||j==(3*n+1)/2-i){
						System.out.print("*");
					}else {
						System.out.print(" ");
					}
				}
			}
			System.out.println();
		}
	}
}
