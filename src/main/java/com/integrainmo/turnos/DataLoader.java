package com.integrainmo.turnos;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final EmpresaRepository empresaRepository;

    public DataLoader(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    @Override
    public void run(String... args) {

        if (empresaRepository.count() == 0) {

            String[] empresas = {
                "Exequiel Fernandez",
                "Element",
                "San Sebastian",
                "Hernan Cortes",
                "Terrazas de Apoquindo",
                "Flor de Azucena",
                "Alto Recoleta",
                "Echaurren",
                "Pucara 5050",
                "Al Ras",
                "Ramon Cruz",
                "Miravalle",
                "Parque Albano",
                "Madreselva",
                "Pedro Lira",
                "Pucara 5120",
                "Vicuña Mackenna",
                "Don Carlos",
                "Vista Parque",
                "Constantino",
                "Vista Santiago",
                "Gran Bretaña",
                "Marco Polo",
                "Vista Valle",
                "P Sanchez Fontecilla",
                "Edificio Geo",
                "Galvarino",
                "Pucara 4880",
                "Covarrubias",
                "Vista Albano",
                "Martin Alonso Pinzon"
            };

            for (String nombre : empresas) {
                empresaRepository.save(new Empresa(nombre));
            }
        }
    }
}
