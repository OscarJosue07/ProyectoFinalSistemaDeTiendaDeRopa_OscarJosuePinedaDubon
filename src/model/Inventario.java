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
    private int stock_total;
    private String nombre_producto; // Este lo usaremos para la fecha/nombre
    private String ubicacion;

    public Inventario() {}

    // Getters y Setters
    public int getId_inventario() { return id_inventario; }
    public void setId_inventario(int id_inventario) { this.id_inventario = id_inventario; }

    public int getId_producto() { return id_producto; }
    public void setId_producto(int id_producto) { this.id_producto = id_producto; }

    public int getStock_total() { return stock_total; }
    public void setStock_total(int stock_total) { this.stock_total = stock_total; }

    public String getNombre_producto() { return nombre_producto; }
    public void setNombre_producto(String nombre_producto) { this.nombre_producto = nombre_producto; }

    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

}
