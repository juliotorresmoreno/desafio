package com.example.desafio.utils;

import com.example.desafio.models.User;

public class Calc {
    public static int calculate(double importe, double tipo, int periodo) {
        tipo = tipo / 1200;

        double p = Math.pow(1 + tipo, periodo);
        double numerador = tipo * p;
        double denominador = p - 1;
        double cuota = importe * numerador / denominador;

        return (int) Math.ceil(cuota);
    }

    public static double creditScore(User user) {
        /**
         * TODO: Implementar un sistema que evalue el puntaje de credito.
         */
        return .9;
    }

    public static double rateOfInterest(User user) {
        /**
         * TODO: Implementar un sistema que evalue la tasa de interes.
         */
        return 15;
    }

    public static void main(String[] args) {
        System.out.println(Calc.calculate(1000000, 10, 12));
    }
}