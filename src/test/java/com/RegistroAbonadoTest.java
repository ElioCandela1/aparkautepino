package com;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.aparkautepino.aparkautepino.Model.Entity.TipoDocumento;
import com.aparkautepino.aparkautepino.Model.Repository.PersonaRepository;
import com.aparkautepino.aparkautepino.Model.Repository.TipoDocumentoRepository;

import tools.jackson.databind.ObjectMapper;

@SpringBootTest 
@AutoConfigureMockMvc                             // 2. Habilita MockMvc
@ActiveProfiles("test")                           // 3. Perfil de test (BD H2, etc.)
@Transactional                                    // 4. Rollback automático tras cada test
public class RegistroAbonadoTest {

    private final MockMvc mockMvc;
    private final PersonaRepository personaRepository;
    private final TipoDocumentoRepository tipoDocumentoRepository;
    private final ObjectMapper objectMapper;
    private final TipoDocumento tipoDocumento;
    public RegistroAbonadoTest(MockMvc mockMvc, PersonaRepository personaRepository,
            TipoDocumentoRepository tipoDocumentoRepository, ObjectMapper objectMapper, TipoDocumento tipoDocumento) {
        this.mockMvc = mockMvc;
        this.personaRepository = personaRepository;
        this.tipoDocumentoRepository = tipoDocumentoRepository;
        this.objectMapper = objectMapper;
        this.tipoDocumento = tipoDocumento;
    }

    @BeforeEach 
    void setUp(){

    }

    @Test 
    void test(){
        
    }

    

}
