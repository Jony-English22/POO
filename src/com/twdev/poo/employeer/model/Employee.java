package com.twdev.poo.employeer.model;

import com.twdev.poo.persona.model.Persona;

import java.math.BigDecimal;

public class Employee extends Persona {
    private String numeroEmpleado;
    private String puesto;
    private BigDecimal salario;
    private Area area;
    private boolean activo;

    public Employee() {
        super(null, null, null, null);
        this.activo = true;
        this.salario = BigDecimal.ZERO;
    }
    public Employee(String nombre, String telefono, String email, BigDecimal salario, Area area) {
        super(null, nombre, telefono, email);
        this.salario = salario;
        this.area = area;
        this.activo = true;
    }

    public Employee(String nombre, String telefono, String email,
                    String numeroEmpleado, String puesto, BigDecimal salario, Area area, boolean activo) {
        super(null, nombre, telefono, email);
        this.numeroEmpleado = numeroEmpleado;
        this.puesto = puesto;
        this.salario = salario;
        this.area = area;
        this.activo = activo;
    }

    public Employee(Integer id, String nombre, String telefono, String email,
                    String numeroEmpleado, String puesto, BigDecimal salario, Area area, boolean activo) {
        super(id, nombre, telefono, email); // Llama al constructor manual de Persona
        this.numeroEmpleado = numeroEmpleado;
        this.puesto = puesto;
        this.salario = salario;
        this.area = area;
        this.activo = activo;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public String getNumeroEmpleado() {
        return numeroEmpleado;
    }

    public Area getArea() {
        return area;
    }

    public boolean isActivo() {
        return activo;
    }

    public String getPuesto() {
        return puesto;
    }


    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public void setNumeroEmpleado(String numeroEmpleado) {
        this.numeroEmpleado = numeroEmpleado;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public void setPuesto(String puesto) {
            this.puesto = puesto;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + getId() +
                ", nombre='" + getNombre() + '\'' +
                ", telefono='" + getTelefono() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", numeroEmpleado='" + numeroEmpleado + '\'' +
                ", puesto='" + puesto + '\'' +
                ", salario=" + salario +
                ", area=" + area +
                ", activo=" + activo +
                '}';
    }
    @Override
    public void print() {
        System.out.println(toString());
    }
}
