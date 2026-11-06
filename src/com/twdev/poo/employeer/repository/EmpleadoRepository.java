package com.twdev.poo.employeer.repository;

import com.twdev.poo.employeer.model.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EmpleadoRepository {

    private final List<Employee> empleados = new ArrayList<>();
    private int siguienteId = 1;

    public Employee agregarEmpleado(Employee empleado) {
        Objects.requireNonNull(empleado, "El empleado no puede ser null");
        empleado.setId(siguienteId++);
        empleados.add(empleado);
        return empleado;
    }

    public List<Employee> obtenerEmpleados() {
        return new ArrayList<>(empleados);
    }

    public Employee buscarPorId(int id) {
        for (Employee empleado : empleados) {
            if (empleado.getId() == id) {
                return empleado;
            }
        }
        return null;
    }

    public boolean actualizarEmpleado(int id, Employee datosActualizados) {
        Objects.requireNonNull(datosActualizados, "Los datos no pueden ser null");
        for (int i = 0; i < empleados.size(); i++) {
            if (empleados.get(i).getId() == id) {
                datosActualizados.setId(id);
                empleados.set(i, datosActualizados);
                return true;
            }
        }
        return false;
    }

    public boolean eliminarEmpleado(int id) {
        for (int i = 0; i < empleados.size(); i++) {
            if (empleados.get(i).getId() == id) {
                empleados.remove(i);
                return true;
            }
        }
        return false;
    }

}
