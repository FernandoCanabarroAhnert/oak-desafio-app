package com.fernandocanabarro.oak_desafio.services.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Base64;
import java.util.List;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fernandocanabarro.oak_desafio.dtos.ProductUpdateDTO;
import com.fernandocanabarro.oak_desafio.dtos.ProdutoRequestDTO;
import com.fernandocanabarro.oak_desafio.dtos.ProdutoResponseDTO;
import com.fernandocanabarro.oak_desafio.mapper.ProdutoMapper;
import com.fernandocanabarro.oak_desafio.models.ImagemProduto;
import com.fernandocanabarro.oak_desafio.models.Produto;
import com.fernandocanabarro.oak_desafio.repositories.ImagemProdutoRepository;
import com.fernandocanabarro.oak_desafio.repositories.ProdutoRepository;
import com.fernandocanabarro.oak_desafio.services.ProdutoService;
import com.fernandocanabarro.oak_desafio.services.exceptions.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ImagemProdutoRepository imagemProdutoRepository;

    @Override
    public Page<ProdutoResponseDTO> listarProdutosPaginados(Pageable pageable) {
        return produtoRepository.findAll(pageable)
                .map(ProdutoMapper::mapEntityToDto);
    }

    @Override
    public List<ProdutoResponseDTO> listarProdutos() {
        return produtoRepository.findAll().stream()
                .map(ProdutoMapper::mapEntityToDto).toList();
    }

    @Override
    public List<ProdutoResponseDTO> listarPorNome(String nome) {
        return produtoRepository.findByNomeContainingIgnoreCase(nome)
                .stream().map(ProdutoMapper::mapEntityToDto).toList();
    }

    // @Override
    // public List<ProdutoResponseDTO> listarPorValorAscendente() {
    //     return produtoRepository.ordenarProdutosPorValorAsc()
    //             .stream().map(ProdutoMapper::mapEntityToDto).toList();
    // }

    // @Override
    // public List<ProdutoResponseDTO> listarPorValorDecrescente() {
    //     return produtoRepository.ordenarProdutosPorValorDesc()
    //             .stream().map(ProdutoMapper::mapEntityToDto).toList();
    // }

    @Override
    public ProductUpdateDTO encontrarProdutoParaUpdate(Long id) {
        return new ProductUpdateDTO(produtoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado")));
    }

    @Override
    public void adicionarProduto(ProdutoRequestDTO dto) throws IOException {
        ImagemProduto imagemProduto = gerarBase64(dto.getImagem());
        Produto produto = ProdutoMapper.mapDtoToEntity(dto);
        produto.setImagem(imagemProduto);
        produtoRepository.save(produto);
    }

    @Override
    public void atualizarProduto(Long id, ProductUpdateDTO dto) throws IOException {
        Produto produto = produtoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

        String valorProduto = dto.getValor().replace(",", ".");
        double valor = Double.parseDouble(valorProduto);

        imagemProdutoRepository.delete(produto.getImagem());

        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setValor(BigDecimal.valueOf(valor));
        produto.setDisponivel(dto.getDisponivel());
        produto.setImagem(gerarBase64(dto.getImagem()));
        produtoRepository.save(produto);
    }

    @Override
    public void deletarProduto(Long id) {
        produtoRepository.deleteById(id);
    }

    public ImagemProduto gerarBase64(MultipartFile imagem) throws IOException {
        byte[] imageBytes = imagem.getBytes();
        BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageBytes));
        int type = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();

        int width = 800;
        int height = 600;

        BufferedImage resizedImage = new BufferedImage(width, height, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(bufferedImage, 0, 0, width, height, null);
        g.dispose();

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ImageIO.write(resizedImage, "png", output);

        String miniImageBase64 = "data:image/png;base64," + Base64.getEncoder().encodeToString(output.toByteArray());
        String originalImageBase64 = "data:image/png;base64," + Base64.getEncoder().encodeToString(imagem.getBytes());

        ImagemProduto imagemProduto = new ImagemProduto();
        imagemProduto.setImagemOriginal(originalImageBase64);
        imagemProduto.setImagemMiniatura(miniImageBase64);

        imagemProduto = imagemProdutoRepository.save(imagemProduto);

        bufferedImage.flush();
        resizedImage.flush();
        output.flush();
        output.close();

        return imagemProduto;
    }

}
