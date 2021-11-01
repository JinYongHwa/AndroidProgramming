package test2;

import java.util.*;
public class Main {
    static String text="141592653589793238462643383279502884197169399375105820974944592307816406286208998628034825342117067982148086513282306647093844609550582231725359408128481117450284102701938521105559644622948954930381964428810975665933446128475648233786783165271201909145648566923460348610454326648213393607260249141273724587006606315588174881520920962829254091715364367892590360011330530548820466521384146951941511609433057270365759591953092186117381932611793105118548074462379962749567351885752724891227938183011949129833673362440656643086021394946395224737190702179860943702770539217176293176752384674818467669405132000568127145263560827785771342757789609173637178721468440901224953430146549585371050792279689258923542019956112129021960864034418159813629774771309960518707211349999998372978049951059731732816096318595024459455346908302642522308253344685035261931188171010003137838752886587533208381420617177669147303";

    //text를 두자리씩 끊어서 배열로 리턴
    public static ArrayList<String> splitText(){
//    	System.out.print(text.length());
        ArrayList<String> arr=new ArrayList();
        for(int i=0;i<text.length()-2;i++){
        	arr.add(text.substring(i,i+2));
        }
        return arr;
    }

    //배열을 입력받아 각배열의 갯수를 hashmap 에 넣어서 리턴
    public static HashMap<String,Integer> getMap(ArrayList<String> textArray){
        HashMap<String,Integer> map=new HashMap<>();
        
        for(int i=0;i<textArray.size();i++){
            if(map.containsKey(textArray.get(i))){
                map.put(textArray.get(i),map.get(textArray.get(i))+1);
            }else{
                map.put(textArray.get(i),1);
            }
        }
        return map;
    }


    public static void main(String[] args){
        ArrayList<String> arr=splitText();
        HashMap<String,Integer> map=getMap(arr);
        //map 에 value 가 가장큰 key를 찾기
        String maxKey=null;
        int maxValue=0;
        for(String key:map.keySet()){
            if(map.get(key)>maxValue){
                maxKey=key;
                maxValue=map.get(key);
            }
        }
        System.out.println(maxKey);

    }

}	
