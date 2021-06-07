package com.ingenia.bank.backend.service.impl;

import com.ingenia.bank.backend.model.Prestamo;
import com.ingenia.bank.backend.repository.PrestamoRepository;
import com.ingenia.bank.backend.service.PrestamoService;
import com.ingenia.bank.backend.utils.PrestamoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrestamoServiceImpl implements PrestamoService {

    private static final Double INTERES = 15.0;
    private static final Integer NUM_CUOTAS = 10;

    @Autowired
    PrestamoRepository prestamoRepositorio;

    @Override
    public List<Prestamo> obtenerPrestamosUsuario(Long usuario_id) {
        return prestamoRepositorio.obtenerPrestamosUsuario(usuario_id);
    }

    @Override
    public Prestamo crearPrestamo(Prestamo prestamo){

        Prestamo prestamoCreado = prestamoRepositorio.save(prestamo);
        PrestamoUtil prestamoUtil = new PrestamoUtil(prestamoCreado);
        Thread prestamoThread=new Thread(prestamoUtil);
        prestamoThread.start();

        return prestamoCreado;
    }

    @Override
    public Prestamo configurarPrestamo(Prestamo prestamo) {
        prestamo.setInteres(INTERES);
        prestamo.setNumeroCuotas(NUM_CUOTAS);
        prestamo.setCuota(cantidadTotalConIntereses(prestamo) / prestamo.getNumeroCuotas());
        return prestamo;
    }

    @Override
    public Double cantidadTotalConIntereses(Prestamo prestamo){
        Double intereses =  prestamo.getInteres() * 0.01 * prestamo.getCantidad();
        return prestamo.getCantidad() + intereses;
    }
}
