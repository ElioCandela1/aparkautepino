
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import com.aparkautepino.aparkautepino.Model.Dto.PersonaDto;
import com.aparkautepino.aparkautepino.Model.Entity.Persona;
import com.aparkautepino.aparkautepino.Model.Entity.TipoDocumento;
import com.aparkautepino.aparkautepino.Model.Repository.PersonaRepository;
import com.aparkautepino.aparkautepino.Model.Repository.TipoDocumentoRepository;
import com.aparkautepino.aparkautepino.Model.Service.PersonaService;

@ExtendWith(MockitoExtension.class)
public class PersonaServiceTest {

    @Mock
    private PersonaRepository personaRepository;

    @Mock
    private TipoDocumentoRepository tipoDocumentoRepository;

    @InjectMocks
    private PersonaService personaService;

    private PersonaDto personaDto;
    private TipoDocumento tipoDocumento;

    @BeforeEach
    void setUp() {
        personaDto = new PersonaDto();
        personaDto.setIdTipoDocumento(1);
        personaDto.setNombres("Juan");
        personaDto.setPrimerApellido("Pérez");
        personaDto.setSegundoApellido("Gómez");
        personaDto.setNumeroDocumento("12345678");
        personaDto.setCorreo("juan@correo.com");
        personaDto.setTelefono("3001234567");

        tipoDocumento = new TipoDocumento();
        tipoDocumento.setId(1);
        tipoDocumento.setNombre("CC");
    }

    @Test
    @DisplayName("crearPersona debe crear la persona correctamente cuando el tipo de" +
            "documento esxite")

    void crearPersona_cuandoTipoDocumentoExiste_debeRetornarPersona() {

        when(tipoDocumentoRepository.findById(1)).thenReturn(Optional.of(tipoDocumento));

        // Act
        Persona persona = personaService.crearPersona(personaDto);

        // Assert
        assertNotNull(persona);
        assertEquals("Juan", persona.getNombres());
        assertEquals("Pérez", persona.getPrimerApellido());
        assertEquals("Gómez", persona.getSegundoApellido());
        assertEquals("12345678", persona.getNumeroDocumento());
        assertEquals("juan@correo.com", persona.getCorreo());
        assertEquals("3001234567", persona.getTelefono());
        assertEquals(tipoDocumento, persona.getTipoDocumento());
        assertTrue(persona.getActivo() == true, "La persona debe quedar activa");

        verify(tipoDocumentoRepository, times(1)).findById(1);
        verifyNoInteractions(personaRepository);
    }

    @Test
    @DisplayName("crearPersona: debe lanzar IllegalArgumentExcepcion cuando el tipo de documento no existe")
    void crearPersona_cuandoTipoDocumentoNoExiste_DebeLanzarExcepcion() {
        // Arrange

        when(tipoDocumentoRepository.findById(1)).thenReturn(Optional.empty());

        // Act y Assert

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> personaService.crearPersona(personaDto));

        assertEquals("Tipo de documento inválido", ex.getMessage());
        verify(tipoDocumentoRepository, times(1)).findById(1);
        verifyNoInteractions(personaRepository);
    }

    @Test
    @DisplayName("guardarPersona: debe delegar al repositorio para guardar la persona")
    void guardarPersona_debeInvocarSaveDelRepositorio() {
        // Arrange
        Persona persona = new Persona();
        persona.setNombres("Juan");
        when(personaRepository.save(any(Persona.class))).thenReturn(persona);

        //Act
        personaService.guardarPersona(persona);

        //Assert
        verify(personaRepository, times(1)).save(persona);
    }

    //==================== buscarpersonaPorNumeroDocumento ====================

    @Test 
    @DisplayName("buscarPersona: debe retornar la persona cuando existe")
    void buscarPersona_cuandoExiste_debeRetornarPersona(){

        // Arrange
        Persona personaEsperada = new Persona();
        personaEsperada.setNumeroDocumento("12345678");
        personaEsperada.setNombres("Juan");

        when(personaRepository.findByNumeroDocumento("12345678"))
                .thenReturn(Optional.of(personaEsperada));

        //Act
        Persona resultado = personaService.buscarpersonaPorNumeroDocumento("12345678");

        //Assert
        assertNotNull(resultado);
        assertEquals("12345678", resultado.getNumeroDocumento());
        assertEquals("Juan", resultado.getNombres());
        verify(personaRepository, times(1)).findByNumeroDocumento("12345678");
    }

     @Test
    @DisplayName("buscarPersona: debe lanzar RuntimeException cuando no existe")
    void buscarPersona_cuandoNoExiste_debeLanzarExcepcion() {
        // Arrange
        when(personaRepository.findByNumeroDocumento(anyString()))
                .thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> personaService.buscarpersonaPorNumeroDocumento("99999999")
        );

        assertEquals("Persona no encontrado", ex.getMessage());
        verify(personaRepository, times(1)).findByNumeroDocumento("99999999");
    }


    @Test
    @DisplayName("buscarPersona: debe propagar el número de documento consultado al repositorio")
    void buscarPersona_debeConsultarConElNumeroCorrecto() {
        // Arrange
        Persona persona = new Persona();
        when(personaRepository.findByNumeroDocumento("ABC123"))
                .thenReturn(Optional.of(persona));

        // Act
        personaService.buscarpersonaPorNumeroDocumento("ABC123");

        // Assert
        verify(personaRepository).findByNumeroDocumento("ABC123");
        verify(personaRepository, never()).findByNumeroDocumento("otro");
    }

}
