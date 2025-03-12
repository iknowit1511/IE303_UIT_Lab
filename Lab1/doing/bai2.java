import java.util.Random;
/* Bài 2: Xấp xỉ giá trị của pi thông qua đường tròn đơn vị (tâm O(0, 0) bán kính r=1). */

/*
 * Ta có O(0,0), r = 1 nên ta xét hình vuông 4 đỉnh (-1;1), (1;1), (-1;1), (1;-1) có độ dài là 2 (đường tròn nội tiếp hình vuông)
 * diện tích hình tròn Sc = πr^2 = π
 * diện tích hình vuông Sr = 2x2 = 4
 * pi = 4Sc/Sr
 * - Thực hiện Monte Carlo để xấp xỉ giá trị của π
 * thiết lập só lượng điểm nằm ngẫu nhiên trong hình vuông totalPoints = 1000000
 * đếm số điểm nằm trong hình tròn insideCircle
 * Tạo tập hợp các điểm nằm trong hình vuông 1x1 
 * - random x,y trong khoảng [-1,1]
 * - nếu x^2 + y^2 <= 1 thì điểm đó nằm trong hình tròn
 * => xấp xỉ giá trị của π = 4 * insideCircle / totalPoints (số điểm trong hình vuông)
 * 
 * ước tính sai số tương đối:
 * - sai số tương đối = |π - πApproximation| / π
 */
public class bai2 {
    public static void main(String[] args) {
        int totalPoints = 1000000;
        int insideCircle = 0;
        Random rand = new Random();

        for (int i = 0; i < totalPoints; i++) {
            double x = rand.nextDouble() * 2 - 1; // random trong khoảng [-1,1] tức r = 1
            double y = rand.nextDouble() * 2 - 1; // random trong khoảng [-1,1] tức r = 1
            if (x * x + y * y <= 1) {
                insideCircle++;
            }
        }

        double piApproximation = 4.0 * insideCircle / totalPoints;
        System.out.println("Giá trị xấp xỉ của π là: " + piApproximation);
    }
}
