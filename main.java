public class Main {
	public static void main(String[] args) {
		int size=40;
		int finalWidth=size;
		int finalHeight=size;
		
		int width=finalWidth;
		int height=finalHeight;
		
		boolean horizontal=true; //true 왼쪽=>오른쪽
		boolean vertical=true;	//true 위->아래
		boolean vh=true; // 수평방향 true 수직방향 false
		
		char[][] matrix=new char[width][height];
		char a='A';
		
		int w=0;
		int h=0;
		int length=finalWidth;
		int count=1;
		
		
		int i=0;
		int j=0;
		while(true) {
			
			
			 
			matrix[w][h]=a;
			a++;
			if(a==91) {
				a='A';
			}
			i++;
			j++;
			
			printMatrix(matrix);
			if(j==length) {
				j=0;
				count++;
				if(count==2) {
					count=0;

					length--;
				}
				
				if(vh) {
					horizontal=!horizontal;
				}
				else {
					vertical=!vertical;
				}
				vh=!vh;
			}
			
			if(vh) {
				if(horizontal) {
					w++;	
				}
				else {
					w--;
				}
				
				
			}
			else {
				if(vertical) {
					h++;	
				}
				else {
					h--;
				}
				
			}
			
			
			if(i>finalWidth*finalHeight) {
				break;
			}
			
		}
		
		resultString(matrix);
	}
	public static void resultString(char[][] matrix) {
		System.out.println("--------------------------------------------");
		for(int i=1;i<matrix.length;i++) {
			char a=matrix[i][i];
			System.out.print(a);
		}
		System.out.println();
	}
	public static void printMatrix(char[][] matrix) {
		
		System.out.println("--------------------------------------------");
		for(int n=0;n<matrix.length;n++) {
			for(int m=0;m<matrix.length;m++) {
				System.out.print(matrix[m][n]);
			}
			System.out.println();
		}
		
	}
}

