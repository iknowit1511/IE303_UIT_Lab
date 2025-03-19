import java.util.Scanner;
import java.util.Random;
/*Bài 1: Chỉ sử dụng bán kính r, không được sử dụng bất kỳ hằng số nào khác, 
hãy xấp xỉ diện tích của hình tròn tâm O(0,0) bán kính r.
 */

/* dùng phương pháp mô phỏng Monte Carlo
 * - xét hình vuông có cạnh 2r, trong hình vuông này có hình tròn bán kính r
 * diện tích hình tròn S
 * diện tích hình vuông = (2r)^2 = 4r^2
 * - Chọn ngẫu nhiên nhiều điểm trong hình vuông và đếm tỷ lệ số điểm rơi vào trong hình tròn
 * - Tỷ lệ này xấp xỉ tỷ số diện tích giữa hình tròn và hình vuông
 * => p = S/4r^2 => S = 4r^2 * p
 * - =>bài toán này quy về tìm p tỷ số theo random các điểm x,y trong khoảng [-r,r]
 * + nếu x^2 + y^2 <= r^2 thì điểm đó nằm trong hình tròn
 * - có được p sẽ tính được Sc
 */
public class bai1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Nhập bán kính r: ");
        Double r = sc.nextDouble();
        int totalPoints = 1000000;
        int insideCircle = 0;
        Random rand = new Random();

        for (int i = 0; i < totalPoints; i++) {
            double x = rand.nextDouble() * 2 * r - r;
            double y = rand.nextDouble() * 2 * r - r;
            if (x * x + y * y <= r * r) {
                insideCircle++;
            }
        }

        double area = 4.0 * r * r * insideCircle / totalPoints;
        System.out.println("Diện tích hình tròn xấp xỉ là: " + area);
        sc.close();
    }

}