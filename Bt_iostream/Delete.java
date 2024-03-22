package Bt_iostream;

import java.io.File;
import java.util.Scanner;

public class Delete {
    public Delete() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Nhập đường dẫn tệp hoặc thư mục cần xóa: ");
        String path = sc.nextLine();
        File fileOrDirectory = new File(path);

        if (!fileOrDirectory.exists()) {
            System.out.println("Tệp hoặc thư mục không tồn tại.");
            return;
        }

        if (fileOrDirectory.isFile()) {
            deleteFile(fileOrDirectory);
        } else if (fileOrDirectory.isDirectory()) {
            deleteDirectory(fileOrDirectory);
        }
    }

    public void deleteFile(File file) {
        if (file.delete()) {
            System.out.println("Xóa tệp thành công.");
        } else {
            System.out.println("Không thể xóa tệp.");
        }
    }

    public void deleteDirectory(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file); // Đệ quy xóa thư mục con
                } else {
                    if (file.delete()) {
                        System.out.println("Xóa tệp " + file.getName() + " thành công.");
                    } else {
                        System.out.println("Không thể xóa tệp " + file.getName());
                    }
                }
            }
        }
        if (directory.delete()) {
            System.out.println("Xóa thư mục " + directory.getName() + " thành công.");
        } else {
            System.out.println("Không thể xóa thư mục " + directory.getName());
        }
    }

    public static void main(String[] args) {
        new Delete();
    }
}
