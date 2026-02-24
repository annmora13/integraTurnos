package com.integrainmo.turnos;

import jakarta.persistence.*;
import java.util.Set;

@Entity
public class Trabajador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @ManyToMany
    @JoinTable(
        name = "empresa_trabajador",
        joinColumns = @JoinColumn(name = "trabajador_id"),
        inverseJoinColumns = @JoinColumn(name = "empresa_id")
    )
    private Set<Empresa> empresas = new java.util.HashSet<>();

    public Trabajador() {}

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public Set<Empresa> getEmpresas() { return empresas; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setEmpresas(Set<Empresa> empresas) { this.empresas = empresas; }
}