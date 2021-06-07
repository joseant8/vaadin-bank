package com.ingenia.bank.backend.utils;

import com.ingenia.bank.backend.model.Movimiento;
import com.ingenia.bank.backend.model.Prestamo;
import com.ingenia.bank.backend.model.TipoMovimiento;
import com.ingenia.bank.backend.service.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;

public class PrestamoUtil implements Runnable{

    @Autowired
    MovimientoService movimientoSerivicio;

    Prestamo prestamo;

    public PrestamoUtil(Prestamo prestamo){
        this.prestamo = prestamo;
    }

    @Override
    public void run() {

        try {
            creaMovimientoPrestamo();
            cobroCuotas();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Realiza todos los cobros de las cuotas del préstamo correspondiente.
     * @throws InterruptedException
     */
    private void cobroCuotas() throws Exception {
        while(prestamo.getNumeroCuotasPagadas() < prestamo.getNumeroCuotas()){
            Thread.sleep(5000);   // 5 segundos
            prestamo.setNumeroCuotasPagadas(prestamo.getNumeroCuotasPagadas()+1);
            creaMovimientoCobroCuota();
        }
    }


    /**
     * Creal el movimiento del cobro de la cuota del préstamo correspondiente.
     * @throws Exception
     */
    private void creaMovimientoCobroCuota() throws Exception {
        Movimiento movimiento = new Movimiento();
        movimiento.setCantidad(prestamo.getCuota());
        movimiento.setTipo(TipoMovimiento.GASTO);
        movimiento.setConcepto("Cuota " + prestamo.getNumeroCuotas() +  " del préstamo " + prestamo.getId());
        movimiento.setCuenta(prestamo.getCuentaCobro());

        movimientoSerivicio.crearMovimiento(movimiento);
    }

    /**
     * Creal el movimiento del ingreso del préstamo correspondiente.
     * @throws Exception
     */
    private void creaMovimientoPrestamo() throws Exception {
        Movimiento movimiento = new Movimiento();
        movimiento.setCantidad(prestamo.getCantidad());
        movimiento.setTipo(TipoMovimiento.INGRESO);
        movimiento.setConcepto("Préstamo " + prestamo.getId());
        movimiento.setCuenta(prestamo.getCuentaIngreso());

        movimientoSerivicio.crearMovimiento(movimiento);
    }
}
