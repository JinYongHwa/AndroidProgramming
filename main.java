//	public static void main(String[] args) {
//		final int finalWidth=5;
//		final int finalHeight=5;
//		
//		int width=finalWidth;
//		int height=finalHeight;
//		
//		boolean horizontal=true; //true 왼쪽=>오른쪽
//		boolean vertical=true;	//true 위->아래
//		boolean vh=true; // 수평방향 true 수직방향 false
//		
//		int moveWidth=width;
//		int moveHeight=height;
//		
//		char[][] matrix=new char[width][height];
//		char a='A';
//		
//		int w=0;
//		int h=0;
//		
//		boolean first=false;
//		int i=0;
//		while(true) {
//			System.out.println(String.format("%d %d", w,h));
//			matrix[w][h]=a;
//			a++;
//			i++;
//			
//			
//			//방향이 수평일때
//			if(vh) {
//				//왼->오
//				if(horizontal) {
//					
//					w++;
//					if(w==width-1) {
//						vh=!vh;
//						horizontal=!horizontal;
//						if(!first) {
//							width--;
//						}
//						first=true;
//					}
//				}
//				//오->왼
//				else {
//					w--;
//					if(w==0) {
//						vh=!vh;
//						horizontal=!horizontal;
//						width--;
//					}
//				}
//			}
//			
//			//방향이 수직일때
//			else {
//				//위->아래
//				if(vertical) {
//					h++;
//					if(h==height-1) {
//						vh=!vh;
//						vertical=!vertical;
//						height--;
//					}
//					
//				}
//				//아래->위
//				else {
//					h--;
//					if(h==height-1) {
//						vh=!vh;
//						vertical=!vertical;
//						height--;
//					}
//				}
//				
//			}
//			
//			if(i>finalWidth*finalHeight) {
//				break;
//			}
//			
//		}
//		
//	
//		for(int n=0;n<5;n++) {
//			for(int m=0;m<5;m++) {
//				System.out.print(matrix[n][m]);
//			}
//			System.out.println();
//		}
//		
//	}
