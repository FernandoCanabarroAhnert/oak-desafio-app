package com.fernandocanabarro.oak_desafio.services.excel;

import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.fernandocanabarro.oak_desafio.dtos.ProdutoResponseDTO;
import com.fernandocanabarro.oak_desafio.services.exceptions.BadRequestException;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

public class ProdutosExcelExporter {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<ProdutoResponseDTO> produtos;

    public ProdutosExcelExporter(List<ProdutoResponseDTO> produtos) {
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Produtos");
        this.produtos = produtos;
    }

    private void writeHeaderRow() {
        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(14);
        style.setFont(font);

        Cell cell = row.createCell(0);
        cell.setCellValue("Id");
        cell.setCellStyle(style);

        cell = row.createCell(1);
        cell.setCellValue("Nome");
        cell.setCellStyle(style);

        cell = row.createCell(2);
        cell.setCellValue("Descrição");
        cell.setCellStyle(style);

        cell = row.createCell(3);
        cell.setCellValue("Valor");
        cell.setCellStyle(style);

        cell = row.createCell(4);
        cell.setCellValue("Disponível");
        cell.setCellStyle(style);
    }

    private void writeDataRows() {
        int rowCount = 1;

        for (ProdutoResponseDTO produto : produtos) {
            Row row = sheet.createRow(rowCount++);

            Cell cell = row.createCell(0);
            cell.setCellValue(produto.getId());
            sheet.autoSizeColumn(0);

            cell = row.createCell(1);
            cell.setCellValue(produto.getNome());
            sheet.autoSizeColumn(1);

            cell = row.createCell(2);
            cell.setCellValue(produto.getDescricao());
            sheet.autoSizeColumn(2);

            cell = row.createCell(3);
            cell.setCellValue(produto.getValor());
            sheet.autoSizeColumn(3);

            cell = row.createCell(4);
            cell.setCellValue(produto.getDisponivel() ? "Sim" : "Não");
            sheet.autoSizeColumn(4);
        }
    }

    public void export(HttpServletResponse response){
        try{
            writeHeaderRow();
            writeDataRows();
            ServletOutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
        }
        catch (IOException e){
            throw new BadRequestException("Erro ao exportar Excel");
        }
    }
}
