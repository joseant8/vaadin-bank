package com.ingenia.bank.backend.model;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double cantidad;

    @CreatedDate
    @Column(nullable = false)
    private Date fechaInicio = new Date();

    private Date fechaFin;

    private Double interes;

    private Integer numeroCuotas;

    private Integer numeroCuotasPagadas = 0;

    private String concepto;

    // relaciones

    @ManyToOne
    @JoinColumn(name = "cuenta_ingreso")
    private Cuenta cuentaIngreso;

    @ManyToOne
    @JoinColumn(name = "cuenta_cobro")
    private Cuenta cuentaCobro;

    public Prestamo() {
    }

    public Prestamo(Double cantidad, Date fechaInicio, Date fechaFin, Double interes, Integer numeroCuotas, String concepto, Cuenta cuentaIngreso, Cuenta cuentaCobro) {
        this.cantidad = cantidad;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.interes = interes;
        this.numeroCuotas = numeroCuotas;
        this.concepto = concepto;
        this.cuentaIngreso = cuentaIngreso;
        this.cuentaCobro = cuentaCobro;
    }

    public Long getId() {
        return id;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Double getInteres() {
        return interes;
    }

    public void setInteres(Double interes) {
        this.interes = interes;
    }

    public Integer getNumeroCuotas() {
        return numeroCuotas;
    }

    public void setNumeroCuotas(Integer numeroCuotas) {
        this.numeroCuotas = numeroCuotas;
    }

    public Integer getNumeroCuotasPagadas() {
        return numeroCuotasPagadas;
    }

    public void setNumeroCuotasPagadas(Integer numeroCuotasPagadas) {
        this.numeroCuotasPagadas = numeroCuotasPagadas;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public Cuenta getCuentaIngreso() {
        return cuentaIngreso;
    }

    public void setCuentaIngreso(Cuenta cuentaIngreso) {
        this.cuentaIngreso = cuentaIngreso;
    }

    public Cuenta getCuentaCobro() {
        return cuentaCobro;
    }

    public void setCuentaCobro(Cuenta cuentaCobro) {
        this.cuentaCobro = cuentaCobro;
    }
}
