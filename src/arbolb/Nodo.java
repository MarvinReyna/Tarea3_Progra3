/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package arbolb;
import java.util.ArrayList;
import java.util.Scanner;
/**
 *
 * @author gio10
 */
public class Nodo {
    ArrayList<Integer> keys;
    int t; // grado mínimo
    ArrayList<Nodo> children;
    boolean leaf; // indica si es una hoja

    Nodo(int t, boolean leaf) {
        this.t = t;
        this.leaf = leaf;
        keys = new ArrayList<>();
        children = new ArrayList<>();
    }
}
