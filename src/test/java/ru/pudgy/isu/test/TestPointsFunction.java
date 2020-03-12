package ru.pudgy.isu.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.pudgy.isu.brain.PointsFunction;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


class TestPointsFunction {
    @Test
    @DisplayName("Поиск экстремумов: константная функция - отсутствуют")
    void testMonotoneConstFunction(){
        long[] data = new long[1024 * 80];
        UUID id = UUID.randomUUID();
        for(int i = 0; i < data.length /2; i++){
            data[2*i] = i;
            data[2*i+1] = 1000;
        }
        PointsFunction up = PointsFunction.create(id, data);
        var extr  = up.findExtremum();

        assertThat(extr).hasSize(0);

        for(int i = 0; i < data.length /2; i++){
            data[2*i] = i;
            data[2*i+1] = 0;
        }
        up = PointsFunction.create(id, data);
        extr  = up.findExtremum();

        assertThat(extr).hasSize(0);

    }
    @Test
    @DisplayName("Поиск экстремумов: мотнотонно возрастающая функция - отсутствуют")
    void testMonotoneRiseFunction(){
        long[] data = new long[1024 * 80];
        UUID id = UUID.randomUUID();
        for(int i = 0; i < data.length /2; i++){
            data[2*i] = i;
            data[2*i+1] = 1000+i;
        }
        PointsFunction up = PointsFunction.create(id, data);
        var extr  = up.findExtremum();

        assertThat(extr).hasSize(0);
    }
    @Test
    @DisplayName("Поиск экстремумов: мотнотонно убывающая функция - отсутствуют")
    void testMonotoneDownFunction(){
        long[] data = new long[1024 * 80];
        UUID id = UUID.randomUUID();
        for(int i = 0; i < data.length /2; i++){
            data[2*i] = i;
            data[2*i+1] = 10000-i;
        }
        PointsFunction up = PointsFunction.create(id, data);
        var extr  = up.findExtremum();

        assertThat(extr).hasSize(0);
    }
    @Test
    @DisplayName("Поиск экстремумов: функция с одним экстремумом")
    void testOneExtrFunction(){
        long[] data = new long[1024 * 80];
        UUID id = UUID.randomUUID();
        for(int i = 0; i < data.length /2; i++){
            data[2*i] = i;
            data[2*i+1] = 10000;
        }
        data[2*100+1] = 10000 + 10;
        data[2*100+1+2] = 10000 + 100;
        PointsFunction up = PointsFunction.create(id, data);
        var extr  = up.findExtremum();

        assertThat(extr).hasSize(1);
        assertThat(extr.get(0).getX()).isEqualTo(101);
        assertThat(extr.get(0).getY()).isEqualTo(10000+100);
    }
    @Test
    @DisplayName("Поиск экстремумов: функция с одним с половиной ???? экстремумом")
    void testOneAndHalfExtrFunction(){
        long[] data = new long[1024 * 80];
        UUID id = UUID.randomUUID();
        for(int i = 0; i < data.length /2; i++){
            data[2*i] = i;
            data[2*i+1] = 10000;
        }
        data[2*100+1] = 10000 + 10;
        data[2*100+1+2] = 10000 + 100;
        data[2*1000+1] = 10000 - 10;
        PointsFunction up = PointsFunction.create(id, data);
        var extr  = up.findExtremum();

        assertThat(extr).hasSize(1);
        assertThat(extr.get(0).getX()).isEqualTo(101);
        assertThat(extr.get(0).getY()).isEqualTo(10000+100);
    }
}
