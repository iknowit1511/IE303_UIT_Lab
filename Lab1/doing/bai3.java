import java.util.Scanner;
import java.util.Arrays;
import java.util.Stack;

class Diem {
    private int x;
    private int y;
    public Diem(int x, int y) {
        this.x = x;
        this.y = y ;

    }
    public Diem() {
        this(0, 0) ;
    }
    public Diem(Diem p){
        this(p.x, p.y);

    }
    public void nhap(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Nhap x: ");
        x = sc.nextInt();
        System.out.println("Nhap y: ");
        y = sc.nextInt();
    }
    public void hienThi(){
        System.out.println(x+" "+y);
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
}

public class bai3{
    public static Diem P0;

    // Tìm điểm có tung độ hoành độ nhỏ nhất
    public static Diem P_min(Diem[] a, int n){
        Diem p_min = a[0];
        for (int i = 1; i < n; i++) {
            if (a[i].getY() < p_min.getY()) {
                p_min = a[i];
            } else if (a[i].getY() == p_min.getY() && a[i].getX() < p_min.getX()) {
                p_min = a[i];
            }
        }
        return p_min;
    }
    
    // Tính góc cực của 2 điểm với gốc P0 và trục hoành Ox
    public static double goc_cuc(Diem p1, Diem p2){
        return Math.atan2(p2.getY() - p1.getY(), p2.getX()-p1.getX());
    }

    // Tính tích vô hướng của 2 vector (p1, p2) và (p1, p3)
    public static double cross_product(Diem p1, Diem p2, Diem p3){
        return (p2.getX() - p1.getX()) * (p3.getY() - p1.getY()) - (p2.getY() - p1.getY()) * (p3.getX() - p1.getX());
    }

    // Kiểm tra 3 điểm có tạo thành góc (counter clockwise) là xoay ngược chiều kim đồng hồ
    public static boolean ccw(Diem p1, Diem p2, Diem p3){
        return cross_product(p1, p2, p3) > 0;
    }

    // Kiểm tra thẳng hàng
    public static boolean thang_hang(Diem p1, Diem p2, Diem p3){
        return cross_product(p1, p2, p3) == 0;
    }

    public static void main(String[] args) {
        // Nhập input
        int n;
        do {
            Scanner sc = new Scanner(System.in);
            System.out.print("Nhập số điểm: ");
            n = sc.nextInt();
            if (n < 2) {
                System.out.println("Số điểm phải lớn hơn 1");
            }
        } while (n < 2);

        Diem[] a = new Diem[n];
        for (int i = 0; i < n; i++) {
            System.out.println("Nhap diem thu " + (i+1) + ": ");
            a[i] = new Diem();
            a[i].nhap();
        }
        
        // B1: Tìm điểm có tung độ hoành độ nhỏ nhất
        P0 = P_min(a, n);
        
        // Cho P0 lên đầu mảng
        Diem temp = a[0];
        a[0] = P0;
        P0 = temp;
        
        // B2: Sắp xếp các điểm theo góc tạo bởi P0 và các điểm còn lại
        Arrays.sort(a, (p1, p2) -> {
            double goc1 = goc_cuc(P0, p1);
            double goc2 = goc_cuc(P0, p2);
            if (goc1 < goc2) {
                return -1;
            } else if (goc1 > goc2) {
                return 1;
            } else {
                return 0;
            }
        });

        // B3: Graham
        Stack<Diem> stack = new Stack<>();
        stack.push(a[0]);
        stack.push(a[1]);
        for (int i = 2; i < n; i++) {
            while (stack.size() >= 2 && !ccw(stack.get(stack.size() - 2), stack.get(stack.size() - 1), a[i])) {
                stack.pop();
            
            }
            // nếu thẳng hàng thì bỏ điểm gần hơn
            if (stack.size() >= 2 && thang_hang(stack.get(stack.size() - 2), stack.get(stack.size() - 1), a[i])) {
                if (goc_cuc(stack.get(stack.size() - 2), stack.get(stack.size() - 1)) < goc_cuc(stack.get(stack.size() - 2), a[i])) {
                    stack.pop();
                }
            }
            
            stack.push(a[i]);
        }

        // In kết quả
        System.out.println("Cac diem cua bao loi la: ");
        Stack<Diem> reversedStack = new Stack<>();
        while (!stack.isEmpty()) {
            reversedStack.push(stack.pop());
        }
        for (Diem p : reversedStack) {
            p.hienThi();
        }
    }
}
