package com.aparkautepino.aparkautepino;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.aparkautepino.aparkautepino.Model.Dto.PersonaDto;
import com.aparkautepino.aparkautepino.Model.Entity.Persona;
import com.aparkautepino.aparkautepino.Model.Entity.SolicitudAbonado;
import com.aparkautepino.aparkautepino.Model.Repository.SolicitudAbonadoRepository;
import com.aparkautepino.aparkautepino.Model.Service.PersonaService;
import com.aparkautepino.aparkautepino.Model.Service.SolicitudAbonadoService;

@ExtendWith(MockitoExtension.class)
public class SolicitudAbonadoServiceImplTest {
/*
    @Mock 
    private PersonaService personaService;

    @Mock 
    private SolicitudAbonadoRepository solicitudAbonadoRepository;

    @InjectMocks 
    SolicitudAbonadoService solicitudAbonadoService;
    
    @Test 
    void registrarSolicitudCompleta_deberiaCrearPersonaYGuardarSolicitud(){
        //ARRANGE
        PersonaDto personaDto = new PersonaDto();
        personaDto.setNombres("Juan");
        personaDto.setNumeroDocumento("12345678");

        SolicitudAbonado solicitudAbonado = new SolicitudAbonado();
        solicitudAbonado.setPlaca("ABC-123");

        Persona personaCreada = new Persona();
        personaCreada.setId(1L);
        personaCreada.setNombres("Juan");

        when(solicitudAbonadoService.registrarSolicitudCompleta(personaDto, solicitudAbonado));

        //ACT 

        SolicitudAbonadoService.registrarSolicitudCompleta(personaDto, solicitudAbonado);

        // ASSERT
        assertThat(solicitudAbonado.getPersona()).isEqualTo(personaCreada);

        //VERIFY
        verify(personaService).crearPersona(personaDto);
        verify(personaService).guardarPersona(personaCreada);
        verify(SolicitudAbonadoRepository).save(solicitudAbonado);


    }
*/
}
