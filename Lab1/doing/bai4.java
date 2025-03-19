import java.nio.file.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

public class bai4 {

    public static List<String> lines = new ArrayList<>();
    public static List<String> wordsList = new ArrayList<>();
    public static List<String> wordsList2 = new ArrayList<>();
    public static String[] words;
    public static Map<String, Integer> wordCount = new HashMap<>();
    public static Map<String, Integer> wordCount2 = new HashMap<>();
    public static Map<String, Integer> wordPairCount = new HashMap<>();
    public static Map<String, Double> Pw = new HashMap<>();
    public static Map<String, Double> Pw1w2 = new HashMap<>();
    public static Map<String, Double> Pw2Givenw1 = new HashMap<>();

    public static void readFile() {
         // Đọc file UIT-ViOCD.txt
         String filePath = "C:/Thanh_study_24-25/HK2_year3/IE303/Lab_UIT/Lab1/assignment/UIT-ViOCD.txt";
         try {
             lines = Files.readAllLines(Paths.get(filePath));
         } catch (IOException e) {
             e.printStackTrace();
         }
 
         // *** Bước 1: Xác định tập V tất cả các từ trong tập tin. Bỏ các từ có tần số xuất hiện < 5
         // + Tạo mảng chứa tất cả các từ trong tập tin
         for (String line : lines) {
             String[] splitWords = line.split(" ");
             for (String word : splitWords) {
                 if (word.length() > 0) {
                     wordsList.add(word);
                 }
             }
         }
         // Chuyển List<String> sang mảng String
         words = wordsList.toArray(new String[0]);
 
         // + Tính số lần xuất hiện từng từ trong mảng chuỗi words
         // Sử dụng HashMap để lưu tần số xuất hiện 
         // Duyệt qua mảng và đếm số lần xuất hiện của từng từ
         for (String word : words) {
             wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
         }
 
         // Xác định tập từ V' chứa các từ có tần số xuất hiện >= 5

         for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {
             if (entry.getValue() >= 5) {
                 wordsList2.add(entry.getKey());
             }
         }
 
         // tính số lần xuất hiện của từng từ trong tập từ V'
         wordCount2.clear();
         for (String word : wordsList2) {
             wordCount2.put(word, wordCount.get(word));
         }
 
         // Tập V' chứa các từ có tần số xuất hiện >= 5 
         // Ở đây V' = wordsList2 là mảng String
          words = wordsList2.toArray(new String[0]);
    }

    public static void Pw() {
        // *** Bước 2: Tính P(w) = f(w) / N
        // f(w) là số lần xuất hiện của từ w trong tập từ dựa trên tập V' (wordsList2)
        // N là tổng số từ trong tập từ dựa trên tập V' (wordsList2)
        int N = wordsList2.size();
        for (Map.Entry<String, Integer> entry : wordCount2.entrySet()) {
            Pw.put(entry.getKey(), (double) entry.getValue() / N);
        }
    }

    public static void Pw1w2() {
        // *** Bước 3: Tính P(w1, w2) = f(w1, w2)/ M
        // + Tính f(w1, w2) là số lần xuất hiện của cặp từ w1, w2 trong tập từ dựa trên từng dòng trong .txt
        for (String line : lines) {
            String[] splitWords = line.split(" ");
            for (int i = 0; i < splitWords.length - 1; i++) {
            String word1 = splitWords[i];
            String word2 = splitWords[i + 1];
            if (wordCount2.containsKey(word1) && wordCount2.containsKey(word2)) {
                String wordPair = word1 + " " + word2;
                wordPairCount.put(wordPair, wordPairCount.getOrDefault(wordPair, 0) + 1);
            }
            }
        }

        // + M là tổng số lần xuất hiện các cặp từ bất kỳ trong tập wordPairCount
        long M = 0;
        for (Map.Entry<String, Integer> entry : wordPairCount.entrySet()) {
            M += entry.getValue();
        }

        // => Tính P(w1, w2)
        for (Map.Entry<String, Integer> entry : wordPairCount.entrySet()) {
            Pw1w2.put(entry.getKey(), (double) entry.getValue() / M);
        }
    }

    public static void Pw2Givenw1() {
        for (Map.Entry<String, Double> entry : Pw1w2.entrySet()) {
            String[] wordsPair = entry.getKey().split(" ");
            String word1 = wordsPair[0];
            Pw2Givenw1.put(entry.getKey(), entry.getValue() / Pw.get(word1));
        }
    }

    public static void training() {
        readFile();
        Pw();
        Pw1w2();
        Pw2Givenw1();
    }
    
    public static void generateNextWords(String inputWord) {
        // + Nhập 1 từ bất kỳ và in ra 4 từ tiếp theo dựa trên P(w1,w2,w3,w4,w5) max
        String[] nextWords = new String[5];
        nextWords[0] = inputWord;
        for (int i = 0; i < 4; i++) {
            double maxP = 0;
            String maxWord = "";
            for (String word : words) {
                String wordPair = nextWords[i] + " " + word;
                if (Pw2Givenw1.containsKey(wordPair) && Pw2Givenw1.get(wordPair) > maxP) {
                    maxP = Pw2Givenw1.get(wordPair);
                    maxWord = word;
                }
            }
            if (maxWord.isEmpty()) {
                break; // không có từ tiếp theo
            }
            nextWords[i + 1] = maxWord;
        }

        // In kết quả
        for (String word : nextWords) {
            System.out.print(word + " ");
        }
    }   
    public static void main(String[] args) {
        training();
        // + Nhập 1 từ bất kỳ và in ra 4 từ tiếp theo dựa trên P(w1,w2,w3,w4,w5) max
        System.out.println("Nhập từ bất kỳ: ");
        Scanner sc = new Scanner(System.in);
        String inputWord = sc.nextLine();
        generateNextWords(inputWord);
        sc.close();
        System.exit(0);
    }   
}
