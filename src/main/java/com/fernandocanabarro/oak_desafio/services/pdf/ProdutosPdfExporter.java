package com.fernandocanabarro.oak_desafio.services.pdf;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import com.fernandocanabarro.oak_desafio.dtos.ProdutoResponseDTO;
import com.fernandocanabarro.oak_desafio.services.exceptions.BadRequestException;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProdutosPdfExporter {

    private List<ProdutoResponseDTO> produtos;

    private void writeTableHeader(PdfPTable table){
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLACK);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.COURIER);
        font.setColor(Color.WHITE);
        font.setStyle(Font.BOLD);

        cell.setPhrase(new Phrase("Id",font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Nome",font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Descrição",font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Valor",font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Disponível",font));
        table.addCell(cell);
    }   

    private void writeTableData(PdfPTable table){
        for (ProdutoResponseDTO produto : produtos) {
            table.addCell(String.valueOf(produto.getId()));
            table.addCell(produto.getNome());
            table.addCell(produto.getDescricao());
            table.addCell(String.valueOf("R$" + produto.getValor()));
            table.addCell(produto.getDisponivel() ? "Sim" : "Não");
        }
    }

    public void export(HttpServletResponse response){
        try {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            Font font = FontFactory.getFont(FontFactory.COURIER);
            font.setColor(Color.BLACK);
            font.setStyle(Font.BOLD);
            font.setSize(24);

            Paragraph title = new Paragraph("Lista de Produtos",font);
            title.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(title);

            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setSpacingBefore(16);
            table.setWidths(new float[]{1f,3f,3.5f,2f,2f});

            writeTableHeader(table);
            writeTableData(table);

            document.add(table);

            document.close();
        }
        catch (IOException e){
            throw new BadRequestException("Falha ao exportar para PDF");
        }
    }
}
