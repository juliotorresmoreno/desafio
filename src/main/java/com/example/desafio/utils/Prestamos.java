package com.example.desafio.utils;

import java.util.Scanner;

public class Prestamos {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        double importe, tipo;
        int periodo;

        System.out.print("Introduce el importe del préstamo: ");
        importe = scanner.nextDouble();
        
        System.out.print("Introduce el tipo de interés: ");
        tipo = scanner.nextDouble();
        
        System.out.print("Introduce el tiempo de amortización (años): ");
        periodo = scanner.nextInt();

        periodo *= 12; // Convertir el tiempo a meses
        tipo = tipo / 1200; // Calcular el tipo mensual

        double numerador = tipo * Math.pow(1 + tipo, periodo);
        double denominador = Math.pow(1 + tipo, periodo) - 1;
        double cuota = importe * (numerador / denominador);
        
        System.out.println("La cuota mensual será de " + cuota);
    }
    
}