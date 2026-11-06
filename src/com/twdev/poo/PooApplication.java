package com.twdev.poo;

import com.twdev.poo.company.model.Empresa;
import com.twdev.poo.company.repository.EmpresaRepository;
import com.twdev.poo.customer.model.Cliente;
import com.twdev.poo.customer.model.TipoCliente;
import com.twdev.poo.customer.repository.ClienteRepository;
import com.twdev.poo.employeer.model.Area;
import com.twdev.poo.employeer.model.Employee;
import com.twdev.poo.employeer.repository.EmpleadoRepository;

import java.math.BigDecimal;

public class PooApplication {

    public static void main(String[] args) {
        EmpleadoRepository empleados = new EmpleadoRepository();
        ClienteRepository clientes = new ClienteRepository();
        EmpresaRepository empresas = new EmpresaRepository();

        Employee ana = empleados.agregarEmpleado(new Employee(
                "Ana", "5551001000", "ana@ejemplo.com", "E-001",
                "Auxiliar", new BigDecimal("12000.00"), Area.CONTABILIDAD, true));
        empleados.agregarEmpleado(new Employee(
                "Luis", "5551002000", "luis@ejemplo.com", "E-002",
                "Analista", new BigDecimal("15000.00"), Area.RH, true));

        clientes.agregarCliente(new Cliente(null, "María", "5552001000",
                "maria@ejemplo.com", TipoCliente.FRECUENTE, "Centro", true));

        Empresa empresa = new Empresa();
        empresa.setNombre("Ejemplo SA");
        empresa.setRfc("EJE010101ABC");
        empresa.setTelefono("5553001000");
        empresa.setEmail("contacto@ejemplo.com");
        empresa.setDireccion("Av. Principal 1");
        empresa.setActivo(true);
        empresas.agregarEmpresa(empresa);

        System.out.println("Empleados creados: " + empleados.obtenerEmpleados());
        System.out.println("Buscar empleado 1: " + empleados.buscarPorId(ana.getId()));

        Employee anaActualizada = new Employee(
                "Ana", "5551001000", "ana@ejemplo.com", "E-001",
                "Jefa de contabilidad", new BigDecimal("18000.00"), Area.CONTABILIDAD, true);
        System.out.println("Actualizar empleado 1: " + empleados.actualizarEmpleado(ana.getId(), anaActualizada));
        System.out.println("Después de actualizar: " + empleados.buscarPorId(ana.getId()));
        System.out.println("Eliminar empleado 2: " + empleados.eliminarEmpleado(2));
        System.out.println("Empleados restantes: " + empleados.obtenerEmpleados());

        System.out.println("Clientes: " + clientes.obtenerClientes());
        System.out.println("Empresas: " + empresas.obtenerEmpresas());
    }
}
