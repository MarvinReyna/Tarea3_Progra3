/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package arbolb;

import java.util.Scanner;

/**
 *
 * @author gio10
 */
public class Main {
        /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    System.out.println("Ingrese el grado del arbol B:");
    int grado = scanner.nextInt();
    ArbolB tree = new ArbolB(grado);

    while (true) {
        System.out.println("\nElija una opcion:");
        System.out.println("1. Insertar una clave");
        System.out.println("2. Eliminar una clave");
        System.out.println("3. Buscar una clave");
        System.out.println("4. Mostrar arbol");
        System.out.println("5. Salir");

        int opcion = scanner.nextInt();

        switch (opcion) {
            case 1:
                System.out.println("Ingrese la clave a insertar:");
                int claveInsertar = scanner.nextInt();
                tree.insert(claveInsertar);
                System.out.println("Clave insertada correctamente.\n");
                tree.traverse();
                break;
            case 2:
                System.out.println("Ingrese la clave a eliminar:");
                int claveEliminar = scanner.nextInt();
                tree.delete(claveEliminar);
                System.out.println("Clave eliminada correctamente.\n");
                tree.traverse();
                break;
            case 3:
                System.out.println("Ingrese la clave a buscar:");
                int claveBuscar = scanner.nextInt();
                boolean encontrado = tree.search(claveBuscar);
                if (encontrado)
                    System.out.println("La clave esta presente en el arbol.");
                else
                    System.out.println("La clave no esta presente en el arbol.");
                System.out.println();
                tree.traverse();
                break;
            case 4:
                System.out.println("Arbol B:\n");
                tree.traverse();
                break;
            case 5:
                System.out.println("Saliendo del programa...");
                System.exit(0);
            default:
                System.out.println("Opcion invalida. Intentelo de nuevo.");
        }
    }
}


}