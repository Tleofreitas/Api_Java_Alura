package med.voll.api.domain.medico;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest // Testar Interface Repository
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Utilizar as informações do Banco real
@ActiveProfiles("test") // Usar o DataBase de Testes
class MedicoRepositoryTest {

    @Test
    void escolherMedicoAleatorioLivreNaData() {

    }
}