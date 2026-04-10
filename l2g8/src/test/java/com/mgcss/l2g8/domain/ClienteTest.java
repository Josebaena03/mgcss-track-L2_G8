package com.mgcss.l2g8.domain;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class ClienteTest {

    @Test
    void debeCrearInstanciaDeCliente() {
        Cliente cliente = new Cliente();

        assertNotNull(cliente);
    }
}
