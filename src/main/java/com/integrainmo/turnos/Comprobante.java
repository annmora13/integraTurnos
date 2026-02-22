package com.integrainmo.turnos;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import java.util.List;

@Entity
public class Comprobante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int anio;
    private int mes;

    @ManyToOne
    private Empresa empresa;
    @ManyToOne
    private Trabajador trabajador;
    @OneToMany(mappedBy = "comprobante", cascade = CascadeType.ALL)
private List<Turno> turnos;

    public Comprobante() {
    }

    public Comprobante(int anio, int mes, Trabajador trabajador) {
        this.anio = anio;
        this.mes = mes;
        this.trabajador = trabajador;
    }

    public Long getId() {
        return id;
    }

    public int getAnio() {
        return anio;
    }

    public int getMes() {
        return mes;
    }

    public Trabajador getTrabajador() {
        return trabajador;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }

    public void setEmpresa(Empresa empresa2) {
        throw new UnsupportedOperationException("Unimplemented method 'setEmpresa'");
    }

    public void setTurnos(List<Turno> turnos2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setTurnos'");
    }
}
