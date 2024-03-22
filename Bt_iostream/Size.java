package Bt_iostream;

import java.io.File;
import java.util.Scanner;

public class Size {
    public Size() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Nhập đường dẫn tệp hoặc thư mục cần hiển thị kích thước: ");
        String path = sc.nextLine();
        File fileOrDirectory = new File(path);

        if (!fileOrDirectory.exists()) {
            System.out.println("Tệp hoặc thư mục không tồn tại.");
            return;
        }

        if (fileOrDirectory.isFile()) {
            System.out.println("Kích thước của tệp " + fileOrDirectory.getName() + " là " + fileOrDirectory.length() + " bytes.");
        } else if (fileOrDirectory.isDirectory()) {
            long size = calculateDirectorySize(fileOrDirectory);
            System.out.println("Kích thước của thư mục " + fileOrDirectory.getName() + " là " + size + " bytes.");
        }
    }

    public long calculateDirectorySize(File directory) {
        long size = 0;
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    size += calculateDirectorySize(file); // Đệ quy tính kích thước của thư mục con
                } else {
                    size += file.length(); // Cộng vào kích thước của tệp
                }
            }
        }
        return size;
    }

    public static void main(String[] args) {
        new Size();
    }
}

