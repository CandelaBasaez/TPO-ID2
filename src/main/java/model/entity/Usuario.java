package model.entity;

import redis.clients.jedis.Jedis;

public class Usuario {

    private int dni;
    private String nombre;
    private String apellido;
    private String direccion;
    private String posicionIVA;

    public Usuario() {}

    public Usuario(int dni, String nombre, String apellido, String posicionIVA) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.posicionIVA = posicionIVA;
    }

    public void guardarEnRedis() {
        try (Jedis jedis = new Jedis("localhost")) {
            jedis.hset("usuario:" + dni, "dni", String.valueOf(dni));
            jedis.hset("usuario:" + dni, "nombre", nombre);
            jedis.hset("usuario:" + dni, "apellido", apellido);
            jedis.hset("usuario:" + dni, "posicionIVA", posicionIVA);
        }
    }

    public static Usuario cargarDesdeRedis(int dni) {
        try (Jedis jedis = new Jedis("localhost")) {
            if (jedis.exists("usuario:" + dni)) {
                String nombre = jedis.hget("usuario:" + dni, "nombre");
                String apellido = jedis.hget("usuario:" + dni, "apellido");
                String posicionIVA = jedis.hget("usuario:" + dni, "posicionIVA");
                return new Usuario(dni, nombre, apellido, posicionIVA);
            } else {
                return null;
            }
        }
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getPosicionIVA() {
        return posicionIVA;
    }

    public void setPosicionIVA(String posicionIVA) {
        this.posicionIVA = posicionIVA;
    }
}
