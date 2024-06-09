package model.entity;

import redis.clients.jedis.Jedis;

public class Usuario {

    private int dni;
    private String nombre;
    private String apellido;
    private String direccion;
    private String condIVA;

    public Usuario() {}

    public Usuario(int dni, String nombre, String apellido, String condIVA, String direccion) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.condIVA = condIVA;
        this.direccion = direccion;
    }


    public void guardarEnRedis() {
        try (Jedis jedis = new Jedis("localhost", 6379)) {
            jedis.hset("usuario:" + dni, "dni", String.valueOf(dni));
            jedis.hset("usuario:" + dni, "nombre", nombre);
            jedis.hset("usuario:" + dni, "apellido", apellido);
            jedis.hset("usuario:" + dni, "condIVA", condIVA);
            jedis.hset("usuario:" + dni, "direccion", direccion);
        }
    }

    public static Usuario cargarDesdeRedis(int dni) {
        try (Jedis jedis = new Jedis("redis://localhost:6379")) {
            if (jedis.exists("usuario:" + dni)) {
                String nombre = jedis.hget("usuario:" + dni, "nombre");
                String apellido = jedis.hget("usuario:" + dni, "apellido");
                String condIVA = jedis.hget("usuario:" + dni, "condIVA");
                String direccion = jedis.hget("usuario:" + dni, "direccion");
                return new Usuario(dni, nombre, apellido, condIVA, direccion);
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

    public String getCondIVA() {
        return condIVA;
    }

    public void setCondIVA(String condIVA) {
        this.condIVA = condIVA;
    }


}
