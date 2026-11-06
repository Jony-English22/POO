package com.twdev.poo.customer.repository;

import com.twdev.poo.customer.model.Cliente;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClienteRepository {
    private final List<Cliente> clientes = new ArrayList<>();
    private int siguienteId = 1;

    public Cliente agregarCliente(Cliente cliente) {
        Objects.requireNonNull(cliente, "El cliente no puede ser null");
        cliente.setId(siguienteId++);
        clientes.add(cliente);
        return cliente;
    }

    public List<Cliente> obtenerClientes() {
        return new ArrayList<>(clientes);
    }

    public Cliente buscarPorId(int id) {
        for (Cliente cliente : clientes) {
            if (cliente.getId() == id) {
                return cliente;
            }
        }
        return null;
    }

    public boolean actualizarCliente(int id, Cliente datosActualizados) {
        Objects.requireNonNull(datosActualizados, "Los datos no pueden ser null");
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getId() == id) {
                datosActualizados.setId(id);
                clientes.set(i, datosActualizados);
                return true;
            }
        }
        return false;
    }

    public boolean eliminarCliente(int id) {
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getId() == id) {
                clientes.remove(i);
                return true;
            }
        }
        return false;
    }
}
