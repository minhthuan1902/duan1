package Bt_iostream;

import java.io.File;
import java.io.FileInputStream;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main1 {
    public Main1() throws Exception {
        FileInputStream file = new FileInputStream("input.inp");
        int s;
        while ((s=file.read())!=-1) {
            System.out.print(Character.toChars(s));
        }
    }
    public static void main(String[] args) throws Exception{
        new Main1();
    }
}