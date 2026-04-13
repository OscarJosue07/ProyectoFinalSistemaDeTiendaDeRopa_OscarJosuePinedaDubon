/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Oscar Josue
 */

public class Inventario {
    private int id_inventario;
    private int id_producto;
    private int cantidad;      // Coincide con columna 'cantidad' en BD
    private String ubicacion;   // Coincide con columna 'ubicacion' en BD
    private String tipo;       // Coincide con columna 'Tipo' en BD
    private String fecha;      // Coincide con columna 'fecha_ingreso' en BD

    public Inventario() {}

    // Getters y Setters
    public int getId_inventario() { return id_inventario; }
    public void setId_inventario(int id_inventario) { this.id_inventario = id_inventario; }

    public int getId_producto() { return id_producto; }
    public void setId_producto(int id_producto) { this.id_producto = id_producto; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
}
