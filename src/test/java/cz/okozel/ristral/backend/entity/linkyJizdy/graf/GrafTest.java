package cz.okozel.ristral.backend.entity.linkyJizdy.graf;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;

class GrafTest {

    Graf<String, Integer> graf;

    @BeforeEach
    void setUp() {
        graf = new Graf<>(0);
    }

    @Test
    void vychozi() {
        assertThat(graf.getPrvni(), nullValue());
        Vrchol<String, Integer> vrchol = new Vrchol<>("já jsem nový výchozí vrchol");
        graf.vlozNaKonec(vrchol);
        assertThat(graf.getPrvni(), equalTo(vrchol));
    }

    @Test
    void vkladaniNaKonec() {
        graf.vlozNaKonec(new Vrchol<>("ahoj"));
        graf.vlozNaKonec(new Vrchol<>("čau"));
        assertThat(graf.toString(), equalTo("ahoj - 0 - čau"));
        //
        graf = new Graf<>(237);
        graf.vlozNaKonec(new Vrchol<>("Danny"));
        graf.vlozNaKonec(new Vrchol<>("Tony"));
        graf.vlozNaKonec(new Vrchol<>("Johny"));
        assertThat(graf.toString(), equalTo("Danny - 237 - Tony - 237 - Johny"));
        //
        Graf<Integer, String> jinyGraf = new Graf<>("nenastaveno");
        jinyGraf.vlozNaKonec(new Vrchol<>(5));
        jinyGraf.vlozNaKonec(new Vrchol<>(3));
        jinyGraf.vlozNaKonec(new Vrchol<>(8));
        assertThat(jinyGraf.toString(), equalTo("5 - nenastaveno - 3 - nenastaveno - 8"));
    }

    @Test
    void vkladaniNaZacatek() {
        graf.vlozNaZacatek(new Vrchol<>("ahoj"));
        graf.vlozNaZacatek(new Vrchol<>("čau"));
        assertThat(graf.toString(), equalTo("čau - 0 - ahoj"));
        //
        graf = new Graf<>(237);
        graf.vlozNaZacatek(new Vrchol<>("Danny"));
        graf.vlozNaZacatek(new Vrchol<>("Tony"));
        graf.vlozNaZacatek(new Vrchol<>("Johny"));
        assertThat(graf.toString(), equalTo("Johny - 237 - Tony - 237 - Danny"));
        //
        Graf<Integer, String> jinyGraf = new Graf<>("nenastaveno");
        jinyGraf.vlozNaZacatek(new Vrchol<>(5));
        jinyGraf.vlozNaZacatek(new Vrchol<>(3));
        jinyGraf.vlozNaZacatek(new Vrchol<>(8));
        assertThat(jinyGraf.toString(), equalTo("8 - nenastaveno - 3 - nenastaveno - 5"));
    }

    @Test
    void vkladaniJinam() {
        Vrchol<String, Integer>[] vrcholy = demoVrcholy();
        assertThat(graf.toString(), equalTo("jedna - 0 - dva - 0 - tři - 0 - čtyři - 0 - pět"));
        graf.vloz(new Vrchol<>("dva a půl"), vrcholy[1]);
        assertThat(graf.toString(), equalTo("jedna - 0 - dva - 0 - dva a půl - 0 - tři - 0 - čtyři - 0 - pět"));
        graf.vloz(new Vrchol<>("číslo"), new Vrchol<>("nevím"));
        assertThat(graf.toString(), equalTo("jedna - 0 - dva - 0 - dva a půl - 0 - tři - 0 - čtyři - 0 - pět"));
        graf.vloz(new Vrchol<>("jedna a čtvrt"), vrcholy[0]);
        assertThat(graf.toString(), equalTo("jedna - 0 - jedna a čtvrt - 0 - dva - 0 - dva a půl - 0 - tři - 0 - čtyři - 0 - pět"));
        graf.vloz(new Vrchol<>("šest"), vrcholy[4]);
        assertThat(graf.toString(), equalTo("jedna - 0 - jedna a čtvrt - 0 - dva - 0 - dva a půl - 0 - tři - 0 - čtyři - 0 - pět - 0 - šest"));
        graf.vlozNaZacatek(new Vrchol<>("jedna polovina"));
        assertThat(graf.toString(), equalTo("jedna polovina - 0 - jedna - 0 - jedna a čtvrt - 0 - dva - 0 - dva a půl - 0 - tři - 0 - čtyři - 0 - pět - 0 - šest"));
    }

    private Vrchol<String, Integer>[] demoVrcholy() {
        @SuppressWarnings("unchecked")
        Vrchol<String, Integer>[] vrcholy = new Vrchol[] {
                new Vrchol<>("jedna"),
                new Vrchol<>("dva"),
                new Vrchol<>("tři"),
                new Vrchol<>("čtyři"),
                new Vrchol<>("pět")
        };
        Arrays.stream(vrcholy).forEachOrdered(vrchol -> graf.vlozNaKonec(vrchol));
        return vrcholy;
    }
}