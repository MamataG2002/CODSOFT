import java.util.Random;
import java.util.Scanner;
public class App {
    int score=0,out_of=0;
    void game(Scanner sc){
        int range,random_number,input_number,game_counter=3;
        out_of++;
        System.out.println("Enter the maximum range:");
        range=sc.nextInt();
        Random rand=new Random();
        random_number=1+rand.nextInt(range);
        while(game_counter>=0){
            System.out.println("Enter the number:");
            input_number=sc.nextInt();
            if (input_number<random_number)
                System.out.println("The number is lesser than the number.No of tries left .. "+ --game_counter);
            else if(input_number>random_number)
                System.out.println("The number is greater than the number.No of tries left .. "+ --game_counter);
            else{
                System.out.println("You Win!!You Guessed The Correct Number");
                score++;
                return;
            }
        }
        System.out.println("You Lose!!The number was "+random_number);
    }
    public static void main(String[] args) throws Exception {
        Scanner sc=null;
        try{
            App object=new App();
            sc = new Scanner(System.in);
            char game_stop;
            do{
                object.game(sc);
                System.out.println("Do you wish to continue..(y/n)");
                game_stop=sc.next().charAt(0);
                if(game_stop=='n'){
                    System.out.println("Game Over!!!.You scored .. "+object.score+" wins out of "+object.out_of+" games.");
                    return;
               }
            }while(true);
        } catch (Exception e) {
            System.out.println(e);
        }
        finally{
            sc.close();
        }
    }
}