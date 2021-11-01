package cz.okozel.ristral.backend.entity.linkyJizdy.graf;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GrafTest {

    Graf<String, Integer> graf;

    @BeforeEach
    void setUp() {
        graf = new Graf<>(0);
    }

    String grafJakoText(Graf<?, ?> graf) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Vrchol<?, ?> vrchol : graf) {
            if (stringBuilder.length() != 0) stringBuilder.append(" - ");
            stringBuilder.append(vrchol.get());
            if (!vrchol.jeKoncovy()) stringBuilder.append(" - ").append(vrchol.getHranaKDalsimu().getHodnota());
        }
        return stringBuilder.toString();
    }

    @Test
    void vychozi() {
        assertThat(graf.getPrvni(), nullValue());
        Vrchol<String, Integer> vrchol = new Vrchol<>("já jsem nový výchozí vrchol");
        graf.vloz(vrchol);
        assertThat(graf.getPrvni(), equalTo(vrchol));
    }

    @Test
    void vlozNaZacatek() {
        graf.vloz(new Vrchol<>("ahoj"));
        graf.vloz(new Vrchol<>("čau"));
        assertThat(grafJakoText(graf), equalTo("ahoj - 0 - čau"));
        //
        graf = new Graf<>(237);
        graf.vloz(new Vrchol<>("Danny"));
        graf.vloz(new Vrchol<>("Tonny"));
        assertThat(grafJakoText(graf), equalTo("Danny - 237 - Tonny"));
    }
}