package com.twdev.poo.company.repository;

import com.twdev.poo.company.model.Empresa;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EmpresaRepository {
    private final List<Empresa> empresas = new ArrayList<>();
    private int siguienteId = 1;

    public Empresa agregarEmpresa(Empresa empresa) {
        Objects.requireNonNull(empresa, "La empresa no puede ser null");
        empresa.setId(siguienteId++);
        empresas.add(empresa);
        return empresa;
    }

    public List<Empresa> obtenerEmpresas() {
        return new ArrayList<>(empresas);
    }

    public Empresa buscarPorId(int id) {
        for (Empresa empresa : empresas) {
            if (empresa.getId() == id) {
                return empresa;
            }
        }
        return null;
    }

    public boolean actualizarEmpresa(int id, Empresa datosActualizados) {
        Objects.requireNonNull(datosActualizados, "Los datos no pueden ser null");
        for (int i = 0; i < empresas.size(); i++) {
            if (empresas.get(i).getId() == id) {
                datosActualizados.setId(id);
                empresas.set(i, datosActualizados);
                return true;
            }
        }
        return false;
    }

    public boolean eliminarEmpresa(int id) {
        for (int i = 0; i < empresas.size(); i++) {
            if (empresas.get(i).getId() == id) {
                empresas.remove(i);
                return true;
            }
        }
        return false;
    }
}
