package com.fernandocanabarro.oak_desafio.services.csv;

import java.io.IOException;
import java.util.List;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.fernandocanabarro.oak_desafio.dtos.ProdutoResponseDTO;
import com.fernandocanabarro.oak_desafio.services.exceptions.BadRequestException;

import jakarta.servlet.http.HttpServletResponse;

public class ProdutosCsvExporter {

    public static void export(HttpServletResponse response,List<ProdutoResponseDTO> produtos) {
        String[] csvHeader = {"Id","Nome","Descrição","Valor","Disponível"};
        String[] attributeMapping = {"id","nome","descricao","valor","disponivel"};

        ICsvBeanWriter csvBeanWriter;
        try {
            csvBeanWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
            csvBeanWriter.writeHeader(csvHeader);
            for (ProdutoResponseDTO produto : produtos) {
                csvBeanWriter.write(produto, attributeMapping);
            }
            csvBeanWriter.close();
        }
        catch (IOException e){
            throw new BadRequestException("Falha ao exportar para csv");
        }
    }
}
