package com.integrainmo.turnos;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Turno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha;

    private double monto;

    @ManyToOne
    private Comprobante comprobante;

    public Turno() {}

    public Turno(LocalDate fecha, double monto, Comprobante comprobante) {
        this.fecha = fecha;
        this.monto = monto;
        this.comprobante = comprobante;
    }

    public Long getId() { return id; }
    public LocalDate getFecha() { return fecha; }
    public double getMonto() { return monto; }
    public Comprobante getComprobante() { return comprobante; }

    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public void setMonto(double monto) { this.monto = monto; }
    public void setComprobante(Comprobante comprobante) { this.comprobante = comprobante; }
}