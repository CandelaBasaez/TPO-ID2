package model.entity;

import redis.clients.jedis.Jedis;

public class Usuario {

    private int dni;
    private String nombre;
    private String apellido;
    private String direccion;
    private String condIVA;
    private String permisos;

    public Usuario() {}

    public Usuario(int dni, String nombre, String apellido, String condIVA, String direccion, String permisos) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.condIVA = condIVA;
        this.direccion = direccion;
        this.permisos = permisos;
    }


    public void guardarEnRedis() {
        try (Jedis jedis = new Jedis("localhost", 6379)) {
            jedis.hset("usuario:" + dni, "dni", String.valueOf(dni));
            jedis.hset("usuario:" + dni, "nombre", nombre);
            jedis.hset("usuario:" + dni, "apellido", apellido);
            jedis.hset("usuario:" + dni, "condIVA", condIVA);
            jedis.hset("usuario:" + dni, "direccion", direccion);
            jedis.hset("usuario:"+ dni, "permisos", permisos);
        }
    }

    public static Usuario cargarDesdeRedis(int dni) {
        try (Jedis jedis = new Jedis("redis://localhost:6379")) {
            if (jedis.exists("usuario:" + dni)) {
                String nombre = jedis.hget("usuario:" + dni, "nombre");
                String apellido = jedis.hget("usuario:" + dni, "apellido");
                String condIVA = jedis.hget("usuario:" + dni, "condIVA");
                String direccion = jedis.hget("usuario:" + dni, "direccion");
                String permisos = jedis.hget("usuario" + dni, "permisos");
                return new Usuario(dni, nombre, apellido, condIVA, direccion, permisos);
            } else {
                return null;
            }
        }
    }

    public void agregarCategoria(int tiempo,int dni){
        try (Jedis jedis = new Jedis("localhost", 6379)){
            jedis.zadd("categorias",tiempo, String.valueOf(dni));
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

    public String getPermisos() {
        return permisos;
    }

    public void setPermisos(String permisos) {
        this.permisos = permisos;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "dni=" + dni +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", direccion='" + direccion + '\'' +
                ", condIVA='" + condIVA + '\'' +
                ", permisos='" + permisos + '\'' +
                '}';
    }
}
