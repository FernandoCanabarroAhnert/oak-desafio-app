package com.fernandocanabarro.oak_desafio.services.csv;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fernandocanabarro.oak_desafio.models.ImagemProduto;
import com.fernandocanabarro.oak_desafio.models.Produto;
import com.fernandocanabarro.oak_desafio.repositories.ImagemProdutoRepository;
import com.fernandocanabarro.oak_desafio.repositories.ProdutoRepository;
import com.fernandocanabarro.oak_desafio.services.exceptions.BadRequestException;

@Service
public class ProdutosCsvImporter {

    @Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
    private ImagemProdutoRepository imagemProdutoRepository;

    public void save(MultipartFile file){
        List<Produto> produtos = new ArrayList<>();
        try {
            InputStream inputStream = file.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"ISO-8859-1"));
            CSVParser csvParser = new CSVParser(bufferedReader, CSVFormat.DEFAULT);
            Iterable<CSVRecord> iterable = csvParser.getRecords();

            int recordCount = 1;
            for (CSVRecord record : iterable){
                if (recordCount == 1){
                    recordCount++;
                    continue;
                }
                ImagemProduto imagemProduto = new ImagemProduto();
				imagemProduto.setImagemOriginal("");
				imagemProduto.setImagemMiniatura("");

				Produto produto = new Produto();
                produto.setNome(record.get(0));
                produto.setDescricao(record.get(1));
                
                String valorProduto = record.get(2).replace(",", ".");
				double valor = Double.parseDouble(valorProduto);
				produto.setValor(BigDecimal.valueOf(valor));

                produto.setDisponivel(record.get(3).equals("true") ? true : false);
                produto.setImagem(imagemProduto);
				produtos.add(produto);
            }
            produtos.forEach(produto -> imagemProdutoRepository.save(produto.getImagem()));
			produtoRepository.saveAll(produtos);

            inputStream.close();
            bufferedReader.close();
            csvParser.close();
        }
        catch (Exception e){
            throw new BadRequestException("Falha ao fazer upload do arquivo CSV");
        }
        
    }

 }
