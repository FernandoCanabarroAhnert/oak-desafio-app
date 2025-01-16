package com.fernandocanabarro.oak_desafio.services.excel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fernandocanabarro.oak_desafio.models.ImagemProduto;
import com.fernandocanabarro.oak_desafio.models.Produto;
import com.fernandocanabarro.oak_desafio.repositories.ImagemProdutoRepository;
import com.fernandocanabarro.oak_desafio.repositories.ProdutoRepository;
import com.fernandocanabarro.oak_desafio.services.exceptions.BadRequestException;

@Service
public class ProdutosExcelImporter {

	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
    private ImagemProdutoRepository imagemProdutoRepository;

    public void save(MultipartFile file) {
		List<List<String>> rows = new ArrayList<>();

		try {
			Workbook workbook = WorkbookFactory.create(file.getInputStream());
			Sheet sheet = workbook.getSheetAt(0);
			rows = StreamSupport.stream(sheet.spliterator(), false)
					.map(row -> StreamSupport
						.stream(row.spliterator(), false)
						.map(this::getCellStringValue)
						.toList())
					.toList();

			List<Produto> produtos = new ArrayList<>();
			for (int i = 1; i < rows.size(); i++) {
				Row row = sheet.getRow(i);
				ImagemProduto imagemProduto = new ImagemProduto();
				imagemProduto.setImagemOriginal("");
				imagemProduto.setImagemMiniatura("");

				Produto produto = new Produto();
				produto.setNome(row.getCell(0).getStringCellValue());
				produto.setDescricao(row.getCell(1).getStringCellValue());

				String valorProduto = row.getCell(2).getStringCellValue().replace(",", ".");
				double valor = Double.parseDouble(valorProduto);
				produto.setValor(BigDecimal.valueOf(valor));

				produto.setDisponivel(row.getCell(3).getStringCellValue().equals("Sim") ? true : false);
				produto.setImagem(imagemProduto);
				produtos.add(produto);
			}
			produtos.forEach(produto -> imagemProdutoRepository.save(produto.getImagem()));
			produtoRepository.saveAll(produtos);
			workbook.close();
		}
		catch (Exception e){
			throw new BadRequestException("Falha ao fazer upload do arquivo Excel");
		}
	}

	private String getCellStringValue(Cell cell) {
		CellType cellType = cell.getCellType();

		if (cellType == CellType.STRING) {
			return cell.getStringCellValue();
		} else if (cellType == CellType.NUMERIC) {
			return String.valueOf(cell.getNumericCellValue());
		} else if (cellType == CellType.BOOLEAN) {
			return String.valueOf(cell.getBooleanCellValue());
		}

		return null;
	}
}
