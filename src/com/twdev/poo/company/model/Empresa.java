package com.twdev.poo.company.model;


public class Empresa {

    private Integer id;
    private String nombre;
    private String rfc;
    private String telefono;
    private String email;
    private String direccion;
    private String logo;
    private boolean activo;

    public Empresa(){

    }

    public Empresa(String logo, String direccion, String email, String telefono){
        this.logo=logo;
        this.direccion=direccion;
        this.email=email;
        this.telefono=telefono;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getRfc() {
        return rfc;
    }

    public String getTelefono(){
        return telefono;
    }

    public String getEmail(){
        return email;
    }

    public String getDireccion(){
        return direccion;
    }

    public String getLogo(){
        return logo;
    }

    public Boolean getActivo() {
        return activo;
        }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "Empresa{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", rfc='" + rfc + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                ", direccion='" + direccion + '\'' +
                ", logo='" + logo + '\'' +
                ", activo=" + activo +
                '}';
    }

    public void print() {
        System.out.println(toString());
    }
}
