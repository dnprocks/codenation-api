package br.com.challenge.dto;

import br.com.challenge.enums.Environment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Tuple;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LogErrorCountDTO {

    Environment environment;
    BigInteger count;

    public static List<LogErrorCountDTO> buildList(List<Tuple> tupleList){

        if (tupleList == null)
            return null;

        List<LogErrorCountDTO> objectList = new ArrayList<>();

        if (tupleList.isEmpty())
            return objectList;

        for (Tuple tuple:tupleList){

            LogErrorCountDTO logErrorCountDTO = LogErrorCountDTO.builder()
                                                .environment(Environment.toEnum((Integer)tuple.get("environment")))
                                                .count((BigInteger)tuple.get("count")).build();
            objectList.add(logErrorCountDTO);
        }

        return objectList;
    }
}
