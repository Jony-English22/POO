package com.twdev.poo.customer.model;

import com.twdev.poo.persona.model.Persona;

public class Cliente extends Persona {

    private TipoCliente tipoCliente;
    private String direccion;
    private Boolean activo;

    public Cliente () {
        super(null, null, null, null);
    }

    public Cliente(Integer id, String nombre, String telefono, String email) {
        super(id, nombre, telefono, email);
    }

    public Cliente(Integer id, String nombre, String telefono, String email, TipoCliente tipoCliente, String direccion, Boolean activo) {
        super(id, nombre, telefono, email);
        this.tipoCliente = tipoCliente;
        this.direccion = direccion;
        this.activo = activo;
    }
    public TipoCliente getTipoCliente() {
        return tipoCliente;
    }

    public String getDireccion() {
        return direccion;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setTipoCliente(TipoCliente tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + getId() +
                ", nombre='" + getNombre() + '\'' +
                ", telefono='" + getTelefono() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", tipoCliente=" + tipoCliente +
                ", direccion='" + direccion + '\'' +
                ", activo=" + activo +
                '}';
    }

    @Override
    public void print() {
        System.out.println(toString());
    }
}
