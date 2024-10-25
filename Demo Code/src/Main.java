import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args) {

//        Scanner sc = new Scanner(System.in);
//        System.out.println("Enter Line : ");
//        String sentence  = sc.nextLine();
//
//        String[] words = sentence.split(" ");
//        for(String word:words){
//            for(String i:words){
//                if(word.contains(i) && !word.equals(i)){
//                    System.out.println(" "+i);
//                }
//            }
//
//        }
//        List<String> list = new ArrayList<>(List.of(sentence.split(" ")));
//        List<String> sortdelist =  list.stream().sorted(Comparator.naturalOrder()).toList();
//       sortdelist.sort(Comparator.comparingInt(String::length));
//       for(String word: sortdelist){
//           System.out.println(word);
//       }
        Integer MAX = Integer.MAX_VALUE;

        int count = 0;
        for (int i = 2; i < MAX; i++) {
            if (getAbundance(i)) {
                count++;
            }
            if (count > 100)
                break;
        }
    }

    public static boolean getAbundance(int num) {
        int abundance = 0;
        for (int i = num - 1; i >= 1; i--) {
            if (num % i == 0) {
                abundance += i;
            }
        }
        if (abundance > num) {
            System.out.println("Number :" + num + " Sum :" + abundance + " Abundance :" + (abundance - num));
            return true;
        }
        return false;
    }

}